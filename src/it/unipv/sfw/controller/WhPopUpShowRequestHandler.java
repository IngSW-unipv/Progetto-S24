package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpShowRequestView;

/**
 * Controller per la finestra di pop-up di visualizzazione richieste.
 * Recupera le richieste dal magazzino tramite la sessione e le visualizza
 * utilizzando la {@link WhPopUpShowRequestView}.
 */
public class WhPopUpShowRequestHandler {

    /**
     * La vista per la visualizzazione delle richieste.
     * Viene inizializzata con le richieste recuperate dal metodo {@link #getRequest()}.
     */
    private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(getRequest());

    /**
     * Recupera l'insieme delle richieste dal magazzino memorizzato nella sessione.
     *
     * @return Un insieme di oggetti {@link Request} che rappresenta le richieste.
     */
    private Set<Request> getRequest() {
        return Session.getIstance().getWh().getRequest();
    }

    /**
     * Mostra la finestra di pop-up per la visualizzazione delle richieste.
     */
    public void showWindow() {
        psr.show();
    }

}