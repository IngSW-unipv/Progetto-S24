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
     * Effettua il login: valida credenziali
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
    
 // Indici 
    private static final int IDX_ID = 0;
    private static final int IDX_PWD = 1;
    private static final int IDX_ROLE = 2;
    private static final int IDX_NAME = 3;
    private static final int IDX_SURNAME = 4;
    
    public void login(String id, char[] pwd)
            throws AccountNotFoundException, WrongPasswordException {

        IUserDAO userDAO = DAOFactory.createUserDAO();

        // 1) Unica query: prendo tutti i campi come array
        String[] row = userDAO.selectRowFieldsById(id);
        if (row == null) {
            java.util.Arrays.fill(pwd, '\0');
            throw new AccountNotFoundException(id);
        }

        // 2) Password check
        String rawPwd = new String(pwd);
        java.util.Arrays.fill(pwd, '\0');
        String storedPw = row[IDX_PWD];
        if (storedPw == null || !storedPw.equals(rawPwd)) {
            throw new WrongPasswordException("***");
        }

        // 3) Mappo il ruolo e costruisco la sottoclasse Staff
        String normRole = (row[IDX_ROLE] == null ? "" : row[IDX_ROLE].trim().toUpperCase());

        Staff user;
        switch (normRole) {
            case "MECHANIC":
                user = new Mechanic(row[IDX_ID], storedPw);
                break;
            case "STRATEGIST":
                user = new Strategist(row[IDX_ID], storedPw);
                break;
            case "WAREHOUSEMAN":
                user = new Warehouseman(row[IDX_ID], storedPw);
                break;
            default:
                // ruolo sconosciuto → tratto come account non valido
                throw new AccountNotFoundException(id);
        }

        // 4) inizializzazione campi
        this.currentUser = user;
        this.name = row[IDX_NAME] != null ? row[IDX_NAME] : "";
        this.surname = row[IDX_SURNAME] != null ? row[IDX_SURNAME] : "";
    
    }

    // getters e setters

    public Staff getCurrentUser() {
    	return currentUser; 
    }
    public void setCurrentUser(Staff user) {
    	this.currentUser = user;
    	} // resta per compatibilità

    public boolean isAuthenticated() {
    	return currentUser != null; 
    	}
    
    public Staff.TypeRole getCurrentRole() {
        return currentUser != null ? currentUser.getType() : null;
    }
    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getID() : null;
    }

    // --- Metadati "leggeri"

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
    }

    public String getSurname() { 
    	return surname; 
    }
    public void setSurname(String surname) { 
    	this.surname = surname; 
    	}
    
}
