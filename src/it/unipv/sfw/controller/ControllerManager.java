package it.unipv.sfw.controller;

import java.util.HashMap;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.frame.Frame;

/**
 * Classe Singleton che si occupa della gestione dei controllers e del
 * caricamento della view corrente nel frame.
 *
 * @see AbsController
 * @see Frame
 */

public class ControllerManager {

    // Contenitore del Singleton
    private static ControllerManager instance = null;

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    // Entry point dell'applicazione
    public static void main(String[] args) {
        ControllerManager manager = ControllerManager.getInstance();
        manager.loadController(AbsController.TypeController.LOGIN);
    }

    // Componenti principali
    private Frame frame;
    private AbsController currentController;
    private HashMap<AbsController.TypeController, AbsController> controllers;

    private ControllerManager() {
        // Inizializza DAOFactory
        DAOFactory.createInstance(DAOFactory.DbType.MYSQL);

        // Inizializza il frame
        frame = new Frame(560, 600);

        // Inizializza la cache dei controller
        controllers = new HashMap<>();

        // Carica il primo controller
        loadController(AbsController.TypeController.LOGIN);

        currentController = null;
    }

    /**
     * Funzione per caricare un controller e la sua rispettiva view nel {@link Frame}.
     *
     * @param controllerType Il tipo di controller da caricare.
     * @throws RuntimeException se il controller richiesto non esiste.
     */
    public void loadController(AbsController.TypeController controllerType) {
        
        if (!controllers.containsKey(controllerType)) {
            // Crea il controller utilizzando la factory
            AbsController controller = ControllerFactory.createController(controllerType);
            controllers.put(controllerType, controller); // Memorizza nella cache
        }

        // Ottieni il controller dalla cache
        currentController = controllers.get(controllerType);
        currentController.initialize();
       // currentController.onLoad();

        // Carica la view del controller nel frame
        frame.loadView(currentController.getView());
    }

    public void closeWindow() {
        frame.dispose();
    }
}
