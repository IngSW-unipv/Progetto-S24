package it.unipv.sfw.model.staff;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.dao.interfacedao.IUserDAO;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;

/**
 * Sessione utente (Singleton):
 * - conserva solo lo stato dell'utente autenticato e pochi metadati semplici;
 * - fornisce login;
 * - NON istanzia oggetti di dominio, NON usa DAO specifici diversi da IUserDAO per il login,
 *   NON contiene logiche applicative (pit-stop, richieste, tempi).
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

    // Metadati "leggeri" che puoi voler mostrare in UI
    private String name = "";
    private String surname = "";
    private String operation = "";
    private String strategy = "";
    private String id_pilot = "";

    // Costruttore privato: nessuna istanziazione pesante qui
    private Session() { }

    // --- Autenticazione ---

    /**
     * Effettua il login: valida credenziali contro la persistenza,
     * imposta l'utente corrente se ok.
     *
     * Input:
     *  - id: username/ID staff
     *  - pwd: password (char[]) da azzerare appena usata
     *
     * Output/effetti:
     *  - se ok: this.currentUser viene settato all'istanza Staff concreta (Mechanic/Strategist/Warehouseman)
     *  - eccezioni se account mancante o password errata
     */
    public void login(String id, char[] pwd)
            throws AccountNotFoundException, WrongPasswordException {

        // prendi il DAO utente dalla factory (interfaccia)
        IUserDAO userDAO = DAOFactory.createUserDAO();

        // 1) lookup utente per id
        Staff user = userDAO.selectById(id);
        if (user == null) {
            // pulizia di sicurezza
            java.util.Arrays.fill(pwd, '\0');
            throw new AccountNotFoundException(id);
        }

        // 2) confronta la password
        String rawPwd = new String(pwd);
        java.util.Arrays.fill(pwd, '\0');

        if (!rawPwd.equals(user.getPwd())) {
            throw new WrongPasswordException("***");
        }

        // 3) ok → utente corrente impostato
        this.currentUser = user;
    }

    // --- Accessors principali ---

    public Staff getCurrentUser() {
    	return currentUser; 
    }
    public void setCurrentUser(Staff user) {
    	this.currentUser = user;
    	} // resta per compatibilità

    public boolean isAuthenticated() {
    	return currentUser != null; 
    	}
    
    public Staff.TypeController getCurrentRole() {
        return currentUser != null ? currentUser.getType() : null;
    }
    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getID() : null;
    }

    // --- Metadati "leggeri" (usati dalla UI) ---

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
    	this.name = name;
    }      // tenuti per compatibilità con l'attuale UserDAO

    public String getSurname() { 
    	return surname; 
    }
    public void setSurname(String surname) { 
    	this.surname = surname; 
    	} // idem
    
}
