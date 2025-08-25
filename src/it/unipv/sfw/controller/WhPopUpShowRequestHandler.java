package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpShowRequestView;

/**
 * Handler per la finestra di pop-up di visualizzazione richieste.
 * Dipende dal Warehouseman passato dal Controller (no Model in Session).
 */
public class WhPopUpShowRequestHandler {

    private final Warehouseman warehouseman;
    private WhPopUpShowRequestView psr; // inizializzata quando serve

    public WhPopUpShowRequestHandler(Warehouseman warehouseman) {
        this.warehouseman = warehouseman;
    }

    /** Recupera le richieste correnti dal model. */
    private Set<Request> getRequests() {
        return warehouseman.getRequest();
    }

    /** Mostra la finestra di pop-up con le richieste aggiornate. */
    public void showWindow() {
        psr = new WhPopUpShowRequestView(getRequests());
        psr.show();
    }
}
