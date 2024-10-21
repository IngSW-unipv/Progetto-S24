package it.unipv.sfw.controller;


/**
 * Classe utilizzata per assicurare che ogni controller venga inizializzato solo
 * una volta.
 *
 */

public class ControllerCache {

		private AbsController controller;
		private boolean isInit;

		/**
		 * @param controller Controller di cui si vuole mantenere la cache.
		 */
		public ControllerCache(AbsController controller) {
			this.controller = controller;
			this.isInit = false;
		}

		/**
		 * Funzione che esegue l'onLoad del controller dopo aver controllato che esso
		 * sia già stato inizializzato.
		 *
		 * @return Controller inizializzato e caricato.
		 */
		public AbsController loadController() {
			System.out.println("Caricamento controller: " + controller.getType());
			if (!isInit) {
				System.out.println("Inizializzazione del controller: " + controller.getType());
				controller.initialize();
				isInit = true;
				System.out.println("Controller " + controller.getType() + " inizializzato.");
			}
		
			return controller;
		}
}