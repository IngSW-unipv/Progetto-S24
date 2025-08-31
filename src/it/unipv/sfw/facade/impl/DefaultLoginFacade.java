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
 * Implementazione di default della {@link LoginFacade}.
 * <p>
 * Responsabilità principali:
 * <ul>
 *   <li>Usare {@link IUserDAO} per recuperare credenziali e ruolo dell'utente</li>
 *   <li>Validare la password fornita</li>
 *   <li>Mappare il ruolo in una sottoclasse di {@link Staff} coerente
 *       ({@link Mechanic}, {@link Strategist}, {@link Warehouseman})</li>
 *   <li>Aggiornare lo stato della {@link Session} con l'utente autenticato</li>
 *   <li>Restituire un {@link AuthResult} con i dati minimi per routing/UI</li>
 * </ul>
 * </p>
 */
public class DefaultLoginFacade implements LoginFacade {

    private final IUserDAO userDAO;

    // Indici delle colonne
    private static final int IDX_ID = 0;
    private static final int IDX_PWD = 1;
    private static final int IDX_ROLE = 2;
    private static final int IDX_NAME = 3;
    private static final int IDX_SURNAME = 4;

    /**
     * Costruisce una facade di login di default.
     *
     * @param userDAO implementazione di {@link IUserDAO} da utilizzare
     */
    public DefaultLoginFacade(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Esegue l’autenticazione dell’utente dato un ID e una password.
     * <ol>
     *   <li>Recupera tutti i campi dell’utente dal DB tramite {@link IUserDAO}</li>
     *   <li>Verifica la password rispetto a quella memorizzata</li>
     *   <li>Mappa il ruolo e costruisce la sottoclasse {@link Staff} appropriata</li>
     *   <li>Aggiorna la {@link Session} con l’utente autenticato</li>
     *   <li>Restituisce un {@link AuthResult} per il controller</li>
     * </ol>
     *
     * @param id       ID utente
     * @param password password in chiaro (viene azzerata dopo l’uso per motivi di sicurezza)
     * @return risultato dell’autenticazione ({@link AuthResult})
     * @throws AccountNotFoundException se l’ID non è presente o il ruolo è sconosciuto
     * @throws WrongPasswordException   se la password non corrisponde
     */
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
            default             -> throw new AccountNotFoundException(id); // ruolo sconosciuto
        }

        // 4) Aggiorna lo stato della Session
        Session.getIstance().setAuthenticatedUser(user, row[IDX_NAME], row[IDX_SURNAME]);

        // 5) Ritorna oggetto per routing/UI
        return new AuthResult(
                row[IDX_ID],
                user.getType(),
                row[IDX_NAME],
                row[IDX_SURNAME]
        );
    }

    /**
     * Verifica se esiste un utente autenticato in sessione.
     *
     * @return {@code true} se l’utente è autenticato, {@code false} altrimenti
     */
    @Override
    public boolean isAuthenticated() {
        return Session.getIstance().isAuthenticated();
    }

    /**
     * Restituisce il ruolo dell’utente attualmente autenticato.
     *
     * @return ruolo utente oppure {@code null} se non autenticato
     */
    @Override
    public Staff.TypeRole currentRoleOrNull() {
        return Session.getIstance().getCurrentRole();
    }

    /**
     * Effettua il logout dell’utente corrente,
     * azzerando lo stato della {@link Session}.
     */
    @Override
    public void logout() {
        Session.getIstance().clearAuthentication();
    }
}
