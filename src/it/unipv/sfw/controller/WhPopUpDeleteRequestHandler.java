package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

/**
 * Controller per la finestra di pop-up di eliminazione richiesta.
 * Gestisce le interazioni dell'utente con la {@link WhPopUpDeleteRequestView}
 * e coordina le azioni con il {@link MagazziniereDAO}.
 * Implementa il pattern Observer per notificare eventuali cambiamenti
 * nel numero di richieste.
 */
public class WhPopUpDeleteRequestHandler {

    private WhPopUpDeleteRequestView pdr;
    private MagazziniereDAO md;

    /**
     * Costruttore per WhPopUpDeleteRequestHandler.
     *
     * @param observable L'oggetto {@link Observable} a cui questo handler
     *                   si registrerà per notificare eventuali cambiamenti.
     */
    public WhPopUpDeleteRequestHandler(Observable observable) {
        pdr = new WhPopUpDeleteRequestView();
        md = new MagazziniereDAO();

        pdr.getSendButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ID STAFF: " + pdr.getId_s().getText());
                System.out.println("ID COMPONENT: " + pdr.getId_c().getText());
                System.out.println("ID VEHICLE: " + pdr.getId_v().getText());

                try {
                    md.checkRequest(pdr.getId_s().getText(), pdr.getId_c().getText(),
                            pdr.getId_v().getText().toUpperCase());
                    md.removeRequest(pdr.getId_c().getText());

                    Session.getIstance().getRequest(); //Aggiorna la lista di richieste nella sessione

                    pdr.clearComponents(pdr.getDataPanel());

                    int totalRequest = setTotalRequest();

                    // Notifica gli osservatori del cambiamento nel numero di richieste
                    observable.notifyObservers(totalRequest);

                    pdr.mex2(); // Mostra messaggio di successo
                    md.insertLogEvent(getID(), "DELETE REQUEST ID COMPONENT: " + pdr.getId_c().getText());
                } catch (RequestNotFoundException err) {
                    System.out.println(err);
                    pdr.mex1(); // Mostra messaggio di errore
                    pdr.clearComponents(pdr.getDataPanel());
                    return;

                }

            }

        });

    }

    /**
     * Recupera e aggiorna il numero totale di richieste dal magazzino nella sessione.
     * @return Il numero totale di richieste.
     */
    private int setTotalRequest() {
        return Session.getIstance().getWh().totalRequest();
    }

    /**
     * Recupera l'ID del membro dello staff dalla sessione.
     * @return L'ID del membro dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }

    /**
     * Mostra la finestra di pop-up.
     */
    public void showWindow() {
        pdr.show();
    }

    /**
     * Pulisce i componenti del pannello di invio.
     */
    public void clear() {
        pdr.clearComponents(pdr.getSendPanel());
    }
}