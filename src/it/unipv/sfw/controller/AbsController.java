package it.unipv.sfw.controller;


import it.unipv.sfw.view.AbsView;

/**
 * Classe astratta di un generico controller, viene usata da
 * {@link ControllerManager}.
 **/

public abstract class AbsController {
	/**
	 * Enumerazione che rappresenta ogni tipo di controller.
	 */
	public enum TypeController  {
		LOGIN, STRATEGA, MECCANICO, MAGAZZINIERE;
		
	}

	/**
	 * {@link it.unipv.sfw.view.AbsView} utilizzata dalle sottoclassi.
	 */
	protected AbsView view = null;

	/**
	 * @return Il tipo del controller.
	 */
	public abstract TypeController  getType();

	/**
	 * @return Una {@link it.unipv.sfw.view.AbsView} inizializzata dal controller.
	 * @see it.unipv.sfw.view.AbsView
	 */
	public AbsView getView() {
		return view;
	}

	/**
	 * Funzione chiamata durante l'inizializzazione del controller. Normalmente è
	 * chiamata solo una volta durante la vita dell'applicazione.
	 *
	 */
	public abstract void initialize();

	public void onLoad() {
		view.onLoad();
	}
}
