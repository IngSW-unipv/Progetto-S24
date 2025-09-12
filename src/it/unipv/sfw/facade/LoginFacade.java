package it.unipv.sfw.facade;

import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.staff.Staff;

/**
 * Facade di autenticazione:
 * - valida le credenziali (interroga IUserDAO);
 * - costruisce la sottoclasse concreta di Staff in base al ruolo;
 * - aggiorna la Session con l'utente autenticato e i metadati "leggeri";
 * - restituisce un oggetto  minimale per il routing dell'applicazione.
 */
public interface LoginFacade {

    /**
     * Autentica l'utente e aggiorna la Session.
     * @param id   username/ID staff
     * @param password password in chiaro (char[]) — verrà azzerata internamente dopo l'uso
     * @return AuthResult con dati minimi utili (id, ruolo, nome, cognome)
     * @throws AccountNotFoundException se l'account non esiste o il ruolo è sconosciuto
     * @throws WrongPasswordException   se la password non coincide
     */
    AuthResult authenticate(String id, char[] password)
            throws AccountNotFoundException, WrongPasswordException;

    /**
     * @return true se esiste un utente autenticato in Session.
     */
    boolean isAuthenticated();

    /**
     * @return ruolo dell'utente corrente (se autenticato), altrimenti null.
     */
    Staff.TypeRole currentRoleOrNull();

}
