package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpShowRequestView;

/**
 * Handler per la finestra di pop-up di visualizzazione delle richieste.
 * <p>
 * Utilizza il modello {@link Warehouseman} per recuperare l'elenco delle
 * richieste correnti e costruire la {@link WhPopUpShowRequestView}.
 * Non utilizza la {@link it.unipv.sfw.model.staff.Session}, ma lavora
 * direttamente sul modello passato dal {@link WarehousemanController}.
 * </p>
 *
 * @see Warehouseman
 * @see WhPopUpShowRequestView
 * @see WarehousemanController
 */
public class WhPopUpShowRequestHandler {

    private final Warehouseman warehouseman;
    private WhPopUpShowRequestView psr; // inizializzata al momento della visualizzazione

    /**
     * Costruttore che inizializza l'handler con il modello del magazziniere.
     *
     * @param warehouseman il magazziniere da cui recuperare le richieste
     */
    public WhPopUpShowRequestHandler(Warehouseman warehouseman) {
        this.warehouseman = warehouseman;
    }

    /**
     * Recupera le richieste correnti dal modello {@link Warehouseman}.
     *
     * @return insieme delle richieste attuali
     */
    private Set<Request> getRequests() {
        return warehouseman.getRequest();
    }

    /**
     * Mostra la finestra di pop-up contenente le richieste aggiornate.
     * <p>
     * Crea una nuova istanza di {@link WhPopUpShowRequestView} popolata
     * con le richieste recuperate e la rende visibile.
     * </p>
     */
    public void showWindow() {
        psr = new WhPopUpShowRequestView(getRequests());
        psr.show();
    }
}
