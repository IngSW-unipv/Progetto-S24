package it.unipv.sfw.controller;

import java.awt.Dimension;

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
		 * @param dim Dimensione corrente del frame.
		 * @return Controller inizializzato e caricato.
		 */
		public AbsController loadController(Dimension dim) {
			if (!isInit) {
				
				isInit = true;
			}
		
			return controller;
		}
}