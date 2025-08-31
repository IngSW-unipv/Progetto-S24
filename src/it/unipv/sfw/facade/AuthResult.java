package it.unipv.sfw.facade;

import it.unipv.sfw.model.staff.Staff;

/**
 * Risultato dell'operazione di autenticazione.
 * <p>
 * Incapsula i dati minimi dell'utente autenticato, evitando che i
 * {@code Controller} debbano interrogare direttamente la {@link it.unipv.sfw.model.staff.Session}.
 * </p>
 * <p>
 * Contiene:
 * <ul>
 *   <li>ID dell'utente</li>
 *   <li>Ruolo dell'utente ({@link Staff.TypeRole})</li>
 *   <li>Nome</li>
 *   <li>Cognome</li>
 * </ul>
 * </p>
 */
public class AuthResult {

    private final String userId;
    private final Staff.TypeRole role;
    private final String name;
    private final String surname;

    /**
     * Costruisce un nuovo risultato di autenticazione.
     *
     * @param userId  identificativo univoco dell'utente
     * @param role    ruolo dell'utente autenticato
     * @param name    nome dell'utente
     * @param surname cognome dell'utente
     */
    public AuthResult(String userId, Staff.TypeRole role, String name, String surname) {
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    /**
     * Restituisce l'ID dell'utente autenticato.
     *
     * @return ID utente
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Restituisce il ruolo dell'utente autenticato.
     *
     * @return ruolo dell'utente
     */
    public Staff.TypeRole getRole() {
        return role;
    }

    /**
     * Restituisce il nome dell'utente autenticato.
     *
     * @return nome
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce il cognome dell'utente autenticato.
     *
     * @return cognome
     */
    public String getSurname() {
        return surname;
    }
}
