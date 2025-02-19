package it.unipv.sfw.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import it.unipv.sfw.dao.mysql.StrategistDAO;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.StGraphicDetailsView;
import it.unipv.sfw.view.StPopUpCreateStrategyView;

/**
 * Controller per la finestra di pop-up di creazione/selezione strategia.
 * Gestisce le interazioni dell'utente con la {@link StPopUpCreateStrategyView}
 * e visualizza informazioni relative allo stato dei componenti e suggerimenti
 * di strategia.
 */
public class StPopUpCreateStrategyHandler {

    private StPopUpCreateStrategyView pcs;
    private StGraphicDetailsView gdv;
    private StrategistDAO sd;

    private final int setPoint = 81000;
    private int average = 0;

    /**
     * Costruttore per StPopUpCreateStrategyHandler.
     *
     * @param rc      Valore che indica se è stata selezionata una strategia
     *                precedente (0) o meno.
     * @param tmeLap Tempo sul giro corrente.
     */
    public StPopUpCreateStrategyHandler(int rc, int tmeLap) {

        pcs = new StPopUpCreateStrategyView();
        sd = new StrategistDAO(); // Inizializzazione del DAO

        // Calcolo della media di usura dei componenti
        for (Components c : getComponent()) {
            average += c.getWear();
        }

        average = average / getComponentSize();

        // Visualizzazione del livello di usura dei componenti
        String degradationMessage = "DEGRADATION OF COMPONENTS: " + average;
        pcs.getComponentLabel1().setText(degradationMessage);

        Color degradationColor;
        if (average > 70) {
            degradationColor = Color.GREEN;
        } else if (average >= 50 && average <= 70) {
            degradationColor = Color.ORANGE;
        } else {
            degradationColor = Color.RED;
        }
        pcs.getComponentLabel1().setForeground(degradationColor);

        // Visualizzazione del messaggio di strategia raccomandata
        String strategyMessage;
        Color strategyColor;

        if (rc == 0) {
            strategyMessage = "SELECTED A STRATEGY";
            strategyColor = Color.YELLOW;
        } else if ((tmeLap - setPoint) > 0) {
            strategyMessage = "RECOMMENDED STRATEGY : PUSH";
            strategyColor = Color.RED;
        } else {
            strategyMessage = "RECOMMENDED STRATEGY : HOLD THE POSITION";
            strategyColor = Color.GREEN;
        }

        pcs.getMexLabel().setText(strategyMessage);
        pcs.getMexLabel().setForeground(strategyColor);

        // Listener per il bottone "Details"
        pcs.getDetailsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gdv = new StGraphicDetailsView(getComponent());
                gdv.show();
                sd.insertLogEvent(getID(), "SHOW DETAILS COMPONENT");
            }
        });

        // Listener per la casella combinata di selezione della strategia
        pcs.getBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String select = (String) pcs.getBox().getSelectedItem();
                Session.getIstance().setStrategy(select);
                pcs.getStrategyLabel2().setText(select);
                pcs.getStrategyLabel2().setForeground(Color.YELLOW);
                sd.insertLogEvent(getID(), "SELECT NEW STRATEGY: " + select);
            }
        });
    }

    /**
     * Recupera l'insieme dei componenti del veicolo dalla sessione.
     * @return L'insieme dei componenti.
     */
    private Set<Components> getComponent() {
        return Session.getIstance().getV().getComponent();
    }

    /**
     * Restituisce il numero di componenti.
     * @return Il numero di componenti.
     */
    private int getComponentSize() {
        return getComponent().size();
    }

    /**
     * Mostra la finestra di pop-up.
     */
    public void showWindow() {
        pcs.show();
    }

    /**
     * Recupera l'ID del membro dello staff dalla sessione.
     * @return L'ID del membro dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }
}