package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

/**
 * Handler popup per l'eliminazione di una richiesta.
 * Dipende dal Warehouseman passato dal Controller e notifica via Observable.
 */
public class WhPopUpDeleteRequestHandler {

    private final WhPopUpDeleteRequestView pdr;
    private final Warehouseman warehouseman;   // model utente corrente
    private final Observable observable;       // per notificare la view principale

    // Facade
    private final WarehousemanFacade facade;

    public WhPopUpDeleteRequestHandler(Warehouseman warehouseman, Observable observable, WarehousemanFacade facade) {
        this.pdr = new WhPopUpDeleteRequestView();
        this.warehouseman = warehouseman;
        this.observable   = observable;
        this.facade       = facade;

        wireEvents();
    }

    private void wireEvents() {
        pdr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onDeleteRequest();
            }
        });
    }

    /**
     * Gestione dell'evento di eliminazione di una richiesta.
     *
     * Flusso:
     *  1) Legge i dati dalla UI.
     *  2) Delega alla Facade la validazione e la cancellazione sul DB.
     *  3) Aggiorna il Model locale
     *  4) Log (via Facade) e aggiornamento UI/Observable.
     */
    private void onDeleteRequest() {
        String idStaff = pdr.getId_s().getText();
        String idComp  = pdr.getId_c().getText();
        String msn     = pdr.getId_v().getText() != null ? pdr.getId_v().getText().toUpperCase() : "";

        try {
            // 1) Validazione/persistenza su DB
            facade.deleteRequest(idStaff, idComp, msn);

            // 2) Model: rimuovo localmente la request
            warehouseman.getRequest().removeIf(r -> String.valueOf(r.getId_c()).equals(idComp));

            // 3) UI: pulisco e conferma
            pdr.clearComponents(pdr.getDataPanel());
            pdr.mex2();

            // 4) Observer: notifica nuovo totale
            int totalRequest = warehouseman.totalRequest();
            observable.notifyObservers(totalRequest);

            // 5) Log
            facade.log(warehouseman.getID(), "DELETE REQUEST ID COMPONENT: " + idComp);

        } catch (RequestNotFoundException err) {
            pdr.mex1(); // errore
            pdr.clearComponents(pdr.getDataPanel());
        }
    }

    public void showWindow() { 
    	pdr.show();
    }

    public void clear() { pdr.clearComponents(pdr.getSendPanel()); }
}
