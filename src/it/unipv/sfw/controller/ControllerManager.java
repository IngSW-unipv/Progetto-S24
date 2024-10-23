package it.unipv.sfw.controller;

import java.util.HashMap;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.frame.Frame;
import it.unipv.sfw.view.LoginView;

/**
 * Classe Singleton che si occupa della gestione dei controllers e del
 * caricamento della view corrente nel frame.
 *
 * @see AbsController
 * @see Frame
 * @see it.unipv.sfw.view.AbsView
 */

public class ControllerManager {
	
	//Contenitore del Singleton
	private static ControllerManager instance = null;
	
	public static ControllerManager getInstance() {
		if(instance == null) {
				instance = new ControllerManager();
		}
		return instance;
	}
		
	/**
	 * Punto d'ingresso dell'applicazione.
	*
	 * @param args
	*/
	public static void main(String[] args) {
		
		ControllerManager manager = ControllerManager.getInstance();
		manager.loadController(AbsController.TypeController.LOGIN);
		
	}
	
	//set impostazioni della view
	private Frame frame;
	
	private AbsController currentController;
	
	//struttura dati che memorizza i dati in coppie di chiave-valore e consente un accesso rapido ai valori tramite la chiave
	private HashMap<AbsController.TypeController, ControllerCache> controllers;
		
	private ControllerManager() {
		
		// init DAOFactory
		DAOFactory.createInstance(DAOFactory.DbType.MYSQL);
	
		//init frame
		frame = new Frame(900,600);
		
		//init controller
		controllers = new HashMap<>(AbsController.TypeController.values().length);
		
		this.addController(new LoginController());
		
		this.addController(new MeccanicoController());
//		this.addController(new StrategaController());
		this.addController(new MagazziniereController());
		
		currentController = null;
	}
	
	/**
	 * Aggiunge un controller a "controllers" dopo averlo inserito in un
	 * ControllerCache.
	 *
	 * @param contr Controller da inserire.
	 */
	private void addController(AbsController controller) {
		
		AbsController.TypeController   t = controller.getType();
		
		//verifico se una chiave specifica t è presente all'interno di questa mappa
		if (!controllers.containsKey(t))
			controllers.put(t, new ControllerCache(controller));
		
	}

	/**
	 * Funzione utilizzata per caricare un controller e la sua rispettiva view nel
	 * {@link Frame}.
	 *
	 * @param controller Controller type
	 * @throws RuntimeException se il controller richiesto non esiste.
	 * @see AbsController
	 * @see it.unipv.sfw.view.AbsView
	 * @see Frame
	 */
	public void loadController(AbsController.TypeController  controller) {
		
		System.out.println(controller);
		
		if (!controllers.containsKey(controller))
			throw new RuntimeException("Il controller :\"" + controller + "\" non esiste.");
		
		currentController = controllers.get(controller).loadController();
		
		frame.loadView(currentController.getView());
	}
	
	public void closeWindow() {
		frame.dispose();
	}

	
}
