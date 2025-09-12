package it.unipv.sfw.controller;

import java.util.EnumMap;
import java.util.Map;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.frame.Frame;
import it.unipv.sfw.model.staff.Staff;

/**
 * Classe Singleton che si occupa della gestione dei {@link AbsController}
 * e del caricamento della loro {@link it.unipv.sfw.view.AbsView} 
 * all’interno del {@link Frame} principale dell’applicazione.
 * <p>
 * Rappresenta il punto di accesso centrale per il cambio di controller
 * in base al contesto applicativo
 * </p>
 *
 * @see AbsController
 * @see ControllerFactory
 * @see Frame
 */
public class ControllerManager {

    // ------------------ Singleton ------------------

    /**
     * Istanza unica del Singleton.
     */
    private static ControllerManager instance = null;

    /**
     * Restituisce l’unica istanza di {@code ControllerManager}.
     * <p>
     * Se non è ancora stata creata, inizializza un nuovo oggetto.
     * </p>
     *
     * @return istanza unica del manager
     */
    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    // ------------------ Stato UI -------------------

    /**
     * Finestra principale dell’applicazione.
     */
    private final Frame frame;

    /**
     * Controller attualmente attivo e in uso nell’interfaccia.
     */
    private AbsController currentController;

    /**
     * Costruttore privato del Singleton.
     * <p>
     * Inizializza la {@link DAOFactory}, la finestra principale
     * e carica come primo controller quello di login.
     * </p>
     */
    private ControllerManager() {
        // Inizializza DAOFactory
        DAOFactory.createInstance(DAOFactory.DbType.MYSQL);

        // Inizializza frame
        frame = new Frame(560, 600);
        currentController = null;

        // All’avvio: mostra il login
        loadController(AbsController.TypeController.LOGIN);
    }

    /**
     * Mappa di corrispondenza 1:1 tra i ruoli di dominio {@link Staff.TypeRole}
     * e i rispettivi tipi di controller dell’interfaccia {@link AbsController.TypeController}.
     */
    private static final Map<Staff.TypeRole, AbsController.TypeController> roleToController =
            new EnumMap<>(Staff.TypeRole.class);

    static {
    	roleToController.put(Staff.TypeRole.MECHANIC,     AbsController.TypeController.MECHANIC);
    	roleToController.put(Staff.TypeRole.STRATEGIST,   AbsController.TypeController.STRATEGIST);
    	roleToController.put(Staff.TypeRole.WAREHOUSEMAN, AbsController.TypeController.WAREHOUSEMAN);
    }

    /**
     * Carica un nuovo controller del tipo specificato, lo inizializza
     * e monta la sua view all’interno del {@link Frame}.
     *
     * @param controllerType il tipo di controller da caricare
     * @throws IllegalArgumentException se {@code controllerType} è nullo o non supportato
     */
    public void loadController(AbsController.TypeController controllerType) {
        if (controllerType == null) {
            throw new IllegalArgumentException("Il tipo di controller non può essere null.");
        }
        // Factory crea l’istanza concreta
        currentController = ControllerFactory.createController(controllerType);
        currentController.initialize();
        frame.loadView(currentController.getView());
    }

    /**
     * Carica il controller associato al <b>ruolo dell’utente autenticato</b>.
     * <p>
     * Recupera il tipo corretto tramite la mappa ruolo->tipo e delega
     * a {@link #loadController(AbsController.TypeController)}.
     * </p>
     *
     * @param role il ruolo dell’utente autenticato
     * @throws IllegalArgumentException se il ruolo non è mappato in {@link #ROLE_TO_CONTROLLER}
     */
    public void loadControllerForRole(Staff.TypeRole role) {
        AbsController.TypeController type = roleToController.get(role);
        if (type == null) {
            throw new IllegalArgumentException("Ruolo non supportato: " + role);
        }
        loadController(type);
    }

    /**
     * Chiude la finestra principale dell’applicazione.
     */
    public void closeWindow() {
        frame.dispose();
    }

    /**
     * Entry point dell’applicazione.
     * <p>
     * Crea l’istanza del {@link ControllerManager} e avvia il ciclo principale.
     * </p>
     */
    public static void main(String[] args) {
        ControllerManager.getInstance();
    }
}
