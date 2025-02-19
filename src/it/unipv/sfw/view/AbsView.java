package it.unipv.sfw.view;

import javax.swing.JPanel;

/**
 * Classe astratta che rappresenta una vista generica.
 * Estende JPanel e fornisce un metodo onLoad() vuoto che può essere sovrascritto
 * dalle sottoclassi per eseguire azioni specifiche al momento del caricamento della vista.
 */
public abstract class AbsView extends JPanel {

    /**
     * Funzione chiamata quando la view viene caricata dal controller.
     * Questa implementazione è vuota e può essere sovrascritta dalle sottoclassi
     * per eseguire azioni specifiche al momento del caricamento della vista.
     */
    public void onLoad() {
    }

}