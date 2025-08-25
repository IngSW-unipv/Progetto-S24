package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

/**
 * Handler popup per l'eliminazione di una richiesta.
 * Dipende dal Warehouseman passato dal Controller e notifica via Observable.
 */
public class WhPopUpDeleteRequestHandler {

    private final WhPopUpDeleteRequestView pdr;
    private final WarehousemanDAO md;
    private final Warehouseman warehouseman;   // model utente corrente
    private final Observable observable;       // per notificare la view principale

    public WhPopUpDeleteRequestHandler(Warehouseman warehouseman, Observable observable) {
        this.pdr = new WhPopUpDeleteRequestView();
        this.md  = new WarehousemanDAO();
        this.warehouseman = warehouseman;
        this.observable   = observable;

        wireEvents();
    }

    private void wireEvents() {
        pdr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onDeleteRequest();
            }
        });
    }

    private void onDeleteRequest() {
        String idStaff = pdr.getId_s().getText();
        String idComp  = pdr.getId_c().getText();
        String msn     = pdr.getId_v().getText() != null ? pdr.getId_v().getText().toUpperCase() : "";

        try {
            // 1) Validazione: la richiesta deve esistere
            md.checkRequest(idStaff, idComp, msn);

            // 2) Persistenza: elimina la richiesta dal DB
            md.removeRequest(idComp);

            // 3) Model:
            warehouseman.getRequest().removeIf(r -> String.valueOf(r.getId_c()).equals(idComp));

            // 4) UI: pulisci form + messaggio successo
            pdr.clearComponents(pdr.getDataPanel());
            pdr.mex2();

            // 5) Observer: notifica nuovo totale richieste
            int totalRequest = warehouseman.totalRequest();
            observable.notifyObservers(totalRequest);

            // 6) Log
            md.insertLogEvent(warehouseman.getID(), "DELETE REQUEST ID COMPONENT: " + idComp);

        } catch (RequestNotFoundException err) {
            pdr.mex1();                      // messaggio di errore
            pdr.clearComponents(pdr.getDataPanel());
        }
    }

    public void showWindow() { pdr.show(); }

    public void clear() { pdr.clearComponents(pdr.getSendPanel()); }
}
