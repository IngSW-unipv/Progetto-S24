package it.unipv.sfw.facade;

import it.unipv.sfw.model.staff.Staff;

/**
 * Dati minimi utili dopo l'autenticazione.
 * (Evita che il Controller debba interrogare direttamente la Session per fare routing/UI.)
 */
public class AuthResult {
    private final String userId;
    private final Staff.TypeRole role;
    private final String name;
    private final String surname;

    public AuthResult(String userId, Staff.TypeRole role, String name, String surname) {
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    // Metodi Getters
    
    public String getUserId() {
    	return userId;
    }
    
    public Staff.TypeRole getRole() { 
    	return role; 
    }
    
    public String getName() { 
    	return name;
    }
    
    public String getSurname() { 
    	return surname;
    }
}
