// FILE: src/it/unipv/sfw/facade/login/impl/DefaultLoginFacade.java
package it.unipv.sfw.facade.impl;

import java.util.Arrays;

import it.unipv.sfw.dao.interfacedao.IUserDAO;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.facade.AuthResult;
import it.unipv.sfw.facade.LoginFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Strategist;
import it.unipv.sfw.model.staff.Warehouseman;

/**
 * Implementazione di default della Facade di login.
 * - usa IUserDAO per leggere le credenziali/ruolo;
 * - valida la password;
 * - mappa ROLE - sottoclasse Staff;
 * - aggiorna la Session con l'utente autenticato;
 * - restituisce AuthResult per il routing.
 */
public class DefaultLoginFacade implements LoginFacade {

    private final IUserDAO userDAO;

    // Indici colonne (coerenti con UserDAO.selectRowFieldsById)
    private static final int IDX_ID = 0;
    private static final int IDX_PWD = 1;
    private static final int IDX_ROLE = 2;
    private static final int IDX_NAME = 3;
    private static final int IDX_SURNAME = 4;

    public DefaultLoginFacade(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public AuthResult authenticate(String id, char[] password)
            throws AccountNotFoundException, WrongPasswordException {

        // 1) Unica query: prendo tutti i campi come array
        String[] row = userDAO.selectRowFieldsById(id);
        if (row == null) {
            Arrays.fill(password, '\0');
            throw new AccountNotFoundException(id);
        }

        // 2) Password check
        String rawPwd = new String(password);
        Arrays.fill(password, '\0'); // sicurezza: azzera la password
        String storedPw = row[IDX_PWD];
        if (storedPw == null || !storedPw.equals(rawPwd)) {
            throw new WrongPasswordException("***");
        }

        // 3) Mappo il ruolo e costruisco la sottoclasse Staff
        String normRole = (row[IDX_ROLE] == null ? "" : row[IDX_ROLE].trim().toUpperCase());
        Staff user;
        switch (normRole) {
            case "MECHANIC"     -> user = new Mechanic(row[IDX_ID], storedPw);
            case "STRATEGIST"   -> user = new Strategist(row[IDX_ID], storedPw);
            case "WAREHOUSEMAN" -> user = new Warehouseman(row[IDX_ID], storedPw);
            default             -> throw new AccountNotFoundException(id); // ruolo sconosciuto -> account non valido
        }

        // 4) inizializzazione campi di Session (stato utente autenticato + metadati leggeri)
        Session.getIstance().setAuthenticatedUser(user, row[IDX_NAME], row[IDX_SURNAME]);

        // 5) ritorno DTO minimale per routing/UI
        return new AuthResult(
                row[IDX_ID],
                user.getType(),
                row[IDX_NAME],
                row[IDX_SURNAME]
        );
    }

    @Override
    public boolean isAuthenticated() {
        return Session.getIstance().isAuthenticated();
    }

    @Override
    public Staff.TypeRole currentRoleOrNull() {
        return Session.getIstance().getCurrentRole();
    }

    @Override
    public void logout() {
        Session.getIstance().clearAuthentication();
    }
}
