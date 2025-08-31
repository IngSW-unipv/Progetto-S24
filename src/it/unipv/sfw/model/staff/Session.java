package it.unipv.sfw.model.staff;

/**
 * Gestore della <b>sessione utente</b> (pattern Singleton).
 * <p>
 * Responsabilità principali:
 * <ul>
 *   <li>Mantenere lo stato dell’utente autenticato (istanza di {@link Staff})</li>
 *   <li>Conservare alcuni metadati leggeri utili alla UI (nome, cognome, operazione corrente, strategia, id pilota)</li>
 *   <li>Fornire metodi di login/logout</li>
 * </ul>
 * </p>
 * <p>
 * Vincoli:
 * <ul>
 *   <li>Non istanzia oggetti di dominio complessi</li>
 *   <li>Non utilizza DAO specifici (eccetto {@code IUserDAO} per il login)</li>
 *   <li>Non contiene logica applicativa: funge da semplice contenitore di stato</li>
 * </ul>
 * </p>
 */
public final class Session {

    // --- Singleton ---
    private static Session istance = null;

    /**
     * Restituisce l’unica istanza della sessione (pattern Singleton).
     *
     * @return istanza unica di {@link Session}
     */
    public static Session getIstance() {
        if (istance == null) {
            istance = new Session();
        }
        return istance;
    }

    // --- Stato utente autenticato ---
    private Staff currentUser;

    // Metadati semplici
    private String name = "";
    private String surname = "";
    private String operation = "";
    private String strategy = "";
    private String id_pilot = "";

    /** Costruttore privato per impedire l'istanziazione esterna (Singleton). */
    private Session() { }

    // --- Stato autenticazione ---

    /**
     * Imposta l'utente autenticato e i metadati leggeri visualizzabili in UI.
     *
     * @param user    istanza di {@link Staff} autenticato
     * @param name    nome utente (può essere {@code null})
     * @param surname cognome utente (può essere {@code null})
     */
    public void setAuthenticatedUser(Staff user, String name, String surname) {
        this.currentUser = user;
        this.name = (name != null ? name : "");
        this.surname = (surname != null ? surname : "");
    }

    /** 
     * Pulisce completamente lo stato della sessione, 
     * azzerando utente autenticato e metadati. 
     */
    public void clearAuthentication() {
        this.currentUser = null;
        this.name = "";
        this.surname = "";
        this.operation = "";
        this.strategy = "";
        this.id_pilot = "";
    }

    // --- Getter/Setter stato utente ---

    /** @return utente corrente autenticato, o {@code null} se non autenticato */
    public Staff getCurrentUser() { return currentUser; }

    /** Imposta manualmente l’utente corrente (sconsigliato: preferire {@link #setAuthenticatedUser}). */
    public void setCurrentUser(Staff user) { this.currentUser = user; } 

    /** @return {@code true} se esiste un utente autenticato, {@code false} altrimenti */
    public boolean isAuthenticated() { return currentUser != null; }

    /** @return ruolo dell’utente autenticato, oppure {@code null} se non autenticato */
    public Staff.TypeRole getCurrentRole() {
        return currentUser != null ? currentUser.getType() : null;
    }

    /** @return ID utente autenticato, oppure {@code null} se non autenticato */
    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getID() : null;
    }

    // --- Getter/Setter metadati ---

    /** @return operazione corrente (campo UI) */
    public String getOperation() { return operation; }

    /** @param operation operazione corrente da impostare */
    public void setOperation(String operation) { this.operation = operation; }

    /** @return strategia corrente (campo UI) */
    public String getStrategy() { return strategy; }

    /** @param strategy strategia corrente da impostare */
    public void setStrategy(String strategy) { this.strategy = strategy; }

    /** @return ID del pilota associato alla sessione */
    public String getId_pilot() { return id_pilot; }

    /** @param id_pilot ID del pilota da associare alla sessione */
    public void setId_pilot(String id_pilot) { this.id_pilot = id_pilot; }

    /** @return nome dell’utente autenticato */
    public String getName() { return name; }

    /** @param name nome utente; se {@code null} viene impostata stringa vuota */
    public void setName(String name) { this.name = (name != null ? name : ""); }

    /** @return cognome dell’utente autenticato */
    public String getSurname() { return surname; }

    /** @param surname cognome utente; se {@code null} viene impostata stringa vuota */
    public void setSurname(String surname) { this.surname = (surname != null ? surname : ""); }
}
