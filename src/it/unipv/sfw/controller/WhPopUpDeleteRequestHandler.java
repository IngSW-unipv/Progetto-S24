package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

/**
 * Handler popup per l'eliminazione di una richiesta.
 * <p>
 * Coordina la view {@link WhPopUpDeleteRequestView}, interagisce con il modello
 * {@link Warehouseman} e delega alla {@link WarehousemanFacade} la cancellazione
 * sul livello di persistenza. Notifica inoltre la view principale tramite
 * {@link Observable} con il nuovo totale di richieste.
 * </p>
 *
 * @see WhPopUpDeleteRequestView
 * @see WarehousemanFacade
 * @see Observable
 */
public class WhPopUpDeleteRequestHandler {

    private final WhPopUpDeleteRequestView pdr;
    private final Warehouseman warehouseman;   // model utente corrente
    private final Observable observable;       // per notificare la view principale

    // Facade
    private final WarehousemanFacade facade;

    /**
     * Costruttore che inizializza la popup e registra i listener necessari.
     *
     * @param warehouseman il magazziniere corrente
     * @param observable   l'osservabile per notificare la view principale (nuovo totale richieste)
     * @param facade       facciata applicativa per la cancellazione della richiesta e il logging
     */
    public WhPopUpDeleteRequestHandler(Warehouseman warehouseman, Observable observable, WarehousemanFacade facade) {
        this.pdr = new WhPopUpDeleteRequestView();
        this.warehouseman = warehouseman;
        this.observable   = observable;
        this.facade       = facade;

        wireEvents();
    }

    /**
     * Collega gli handler degli eventi della view.
     * <p>
     * Il pulsante di invio è associato a {@link #onDeleteRequest()}.
     * </p>
     */
    private void wireEvents() {
        pdr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onDeleteRequest();
            }
        });
    }

    /**
     * Gestione dell'evento di eliminazione di una richiesta.
     * <p>
     * Flusso:
     * </p>
     * <ol>
     *   <li>Legge i dati dalla UI (id staff, id componente, MSN veicolo)</li>
     *   <li>Delega alla {@link WarehousemanFacade} la validazione e la cancellazione su DB</li>
     *   <li>Rimuove la richiesta dal modello locale {@link Warehouseman}</li>
     *   <li>Pulisce la form, mostra conferma e notifica l'{@link Observable} con il totale aggiornato</li>
     *   <li>Esegue il log dell'operazione tramite la Facade</li>
     * </ol>
     * In caso di richiesta inesistente, mostra un messaggio d'errore nella view e ripulisce i campi.
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

    /**
     * Mostra la finestra popup per l'eliminazione della richiesta.
     */
    public void showWindow() { 
    	pdr.show();
    }

    /**
     * Pulisce i campi dell'area di invio nella popup.
     */
    public void clear() { pdr.clearComponents(pdr.getSendPanel()); }
}
