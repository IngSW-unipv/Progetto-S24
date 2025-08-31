package it.unipv.sfw.dao.interfacedao;

/**
 * Interfaccia DAO che definisce le operazioni di accesso ai dati
 * relative agli utenti del sistema.
 * <p>
 * Fornisce metodi per recuperare i dati di un utente a partire dal suo ID.
 * </p>
 */
public interface IUserDAO {

    /**
     * Restituisce i campi della riga utente come array ordinato:
     * <ul>
     *   <li>[0] = ID</li>
     *   <li>[1] = PASSWORD</li>
     *   <li>[2] = ROLE</li>
     *   <li>[3] = NAME</li>
     *   <li>[4] = SURNAME</li>
     * </ul>
     * <p>
     * Se l'utente non esiste, ritorna {@code null}.
     * </p>
     *
     * @param id identificativo dell'utente da cercare
     * @return array di stringhe con i campi utente, oppure {@code null} se non trovato
     */
    String[] selectRowFieldsById(String id);
}
