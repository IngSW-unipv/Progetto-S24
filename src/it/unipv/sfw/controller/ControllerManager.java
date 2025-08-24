package it.unipv.sfw.controller;

import java.util.EnumMap;
import java.util.Map;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.frame.Frame;
import it.unipv.sfw.model.staff.Staff;

/**
 * Classe Singleton che gestisce i controller e carica la loro view nel {@link Frame}.
 */
public class ControllerManager {

    // ------------------ Singleton ------------------
    private static ControllerManager instance = null;

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    // ------------------ Stato UI -------------------
    private final Frame frame;
    private AbsController currentController;

    private ControllerManager() {
        // Inizializza DAOFactory
        DAOFactory.createInstance(DAOFactory.DbType.MYSQL);

        // Inizializza frame
        frame = new Frame(560, 600);
        currentController = null;

        // All’avvio: mostra il login
        loadController(AbsController.TypeController.LOGIN);
    }

    // Mappa 1:1 ruolo dominio → tipo controller UI
    private static final Map<Staff.TypeRole, AbsController.TypeController> ROLE_TO_CONTROLLER =
            new EnumMap<>(Staff.TypeRole.class);
    static {
        ROLE_TO_CONTROLLER.put(Staff.TypeRole.MECHANIC,     AbsController.TypeController.MECHANIC);
        ROLE_TO_CONTROLLER.put(Staff.TypeRole.STRATEGIST,   AbsController.TypeController.STRATEGIST);
        ROLE_TO_CONTROLLER.put(Staff.TypeRole.WAREHOUSEMAN, AbsController.TypeController.WAREHOUSEMAN);
    }

    /**
     * Carica un controller del tipo indicato, lo inizializza e monta la view nel {@link Frame}.
     *
     * @param controllerType il tipo di controller da caricare (non {@code null})
     * @throws IllegalArgumentException se {@code controllerType} è nullo o non supportato
     */
    public void loadController(AbsController.TypeController controllerType) {
        if (controllerType == null) {
            throw new IllegalArgumentException("Il tipo di controller non può essere null.");
        }
        // QUI FACTORY crea l’istanza
        currentController = ControllerFactory.createController(controllerType);
        currentController.initialize();
        frame.loadView(currentController.getView());
    }

    /**
     * Carica il controller congruente al <b>ruolo dell'utente autenticato</b>.
     * <p>
     * Usa il mapping ruolo→tipo e delega a {@link #loadController(AbsController.TypeRole)}.
     *
     * @param role il ruolo dell’utente (non {@code null})
     * @throws IllegalArgumentException se il ruolo non è mappato
     */
    public void loadControllerForRole(Staff.TypeRole role) {
        AbsController.TypeController type = ROLE_TO_CONTROLLER.get(role);
        if (type == null) {
            throw new IllegalArgumentException("Ruolo non supportato: " + role);
        }
        loadController(type);
    }

    /** Chiude la finestra principale. */
    public void closeWindow() {
        frame.dispose();
    }

    // Entry point dell'app (opzionale; evita di caricare due volte il login)
    public static void main(String[] args) {
        ControllerManager.getInstance(); // il costruttore carica già il LOGIN
    }
}
