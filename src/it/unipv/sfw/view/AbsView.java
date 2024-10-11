package it.unipv.sfw.view;

import javax.swing.JPanel;

public abstract class AbsView extends JPanel{
	/**
	 * Fuzione chiamata quando la view viene caricata dal controller.
	 * {@link it.unipv.sfw.view.AbsView} la implementa vuota, per avere un
	 * comportamento diverso deve essere sovrascritta dalle sottoclassi.
	 */
	public void onLoad() {
	}

}
