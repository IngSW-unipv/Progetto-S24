package it.unipv.sfw.model.staff;

/**
 * Sessione utente (Singleton):
 * - conserva solo lo stato dell'utente autenticato e pochi metadati semplici;
 * - fornisce login;                              
 * - NON istanzia oggetti di dominio, NON usa DAO specifici diversi da IUserDAO per il login,
 *   NON contiene logiche applicative
 */
public final class Session {

    // --- Singleton ---
    private static Session istance = null;
    public static Session getIstance() {
        if (istance == null) {
            istance = new Session();
        }
        return istance;
    }

    // --- Stato utente autenticato ---
    private Staff currentUser;

    // Metadati
    private String name = "";
    private String surname = "";
    private String operation = "";
    private String strategy = "";
    private String id_pilot = "";

    private Session() { }

    // --- Stato autenticazione ---
    /**
     * Imposta l'utente autenticato e i metadati leggeri visualizzabili in UI.
     */
    public void setAuthenticatedUser(Staff user, String name, String surname) {
        this.currentUser = user;
        this.name = (name != null ? name : "");
        this.surname = (surname != null ? surname : "");
    }

    /** Pulisce completamente lo stato di sessione. */
    public void clearAuthentication() {
        this.currentUser = null;
        this.name = "";
        this.surname = "";
        this.operation = "";
        this.strategy = "";
        this.id_pilot = "";
    }

    // getters e setters

    public Staff getCurrentUser() { return currentUser; }
    public void setCurrentUser(Staff user) { this.currentUser = user; } 

    public boolean isAuthenticated() { return currentUser != null; }

    public Staff.TypeRole getCurrentRole() {
        return currentUser != null ? currentUser.getType() : null;
    }
    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getID() : null;
    }

    // --- Metodi Getter e Setter

    public String getOperation() {
    	return operation; 
    }
    
    public void setOperation(String operation) {
    	this.operation = operation; 
    }

    public String getStrategy() {
    	return strategy;
    }
    
    public void setStrategy(String strategy) { 
    	this.strategy = strategy; 
    }

    public String getId_pilot() {
    	return id_pilot;
    }
    
    public void setId_pilot(String id_pilot) {
    	this.id_pilot = id_pilot;
    }

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = (name != null ? name : ""); 
    }

    public String getSurname() { 
    	return surname; 
    }
    
    public void setSurname(String surname) {
    	this.surname = (surname != null ? surname : "");
    }
}
