package it.unipv.sfw.controller;

import it.unipv.sfw.view.AbsView;

/**
 * Classe astratta che rappresenta un generico controller dell'applicazione.
 * <p>
 * Ogni controller concreto estende questa classe e implementa le logiche
 * specifiche per la propria tipologia. È inoltre gestita da 
 * {@link ControllerManager} che ne centralizza la creazione e l'utilizzo.
 * </p>
 *
 * @see ControllerManager
 * @see it.unipv.sfw.view.AbsView
 */
public abstract class AbsController {
	
    /**
     * Enumerazione che rappresenta i diversi tipi di controller
     * gestiti dall'applicazione.
     */
    public enum TypeController {
        LOGIN, STRATEGIST, MECHANIC, WAREHOUSEMAN;
    }

    /**
     * Vista associata al controller. Viene inizializzata
     * dalle sottoclassi al momento opportuno.
     */
    protected AbsView view = null;

    /**
     * Restituisce il tipo del controller concreto.
     *
     * @return il valore {@link TypeController} corrispondente al controller.
     */
    public abstract TypeController getType();

    /**
     * Restituisce la vista inizializzata e associata al controller.
     *
     * @return un'istanza di {@link it.unipv.sfw.view.AbsView} legata al controller.
     */
    public AbsView getView() {
        return view;
    }

    /**
     * Metodo astratto invocato durante l'inizializzazione del controller.
     * <p>
     * Viene tipicamente chiamato una sola volta nella vita dell'applicazione,
     * ed è il punto in cui il controller prepara la propria logica
     * e associa la vista.
     * </p>
     */
    public abstract void initialize();
    
}
