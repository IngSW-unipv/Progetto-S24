package it.unipv.sfw.controller;

import java.util.ArrayList;

import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicTimePsView;

/**
 * Gestisce la visualizzazione grafica dei tempi di Pit Stop.
 * Recupera i dati relativi ai tempi di Pit Stop, li elabora e li visualizza nella finestra corrispondente.
 */
public class McGraphicTimePsHandler {
    
    private ArrayList<String> labelTime = new ArrayList<>();
    private ArrayList<String> anomalyLabelTime = new ArrayList<>();
    private McGraphicTimePsView gtpv = new McGraphicTimePsView(fetchTimePitStop(), labelTime);

    /**
     * Inizializza la vista McGraphicTimePsView, stampando informazioni sui tempi di Pit Stop,
     * creando etichette e gestendo eventuali anomalie.
     */
    public void initialize() {
        for (Integer t : Session.getIstance().getM().getAllTimePitStop()) {
            System.out.println("tempo: " + t);
        }
        createLabels();
        gtpv.anomalyTime(anomalyLabelTime);
        showWindow();
    }

    /**
     * Crea le etichette per i tempi di Pit Stop e per eventuali anomalie.
     */
    public void createLabels() {
        for (Integer time : Session.getIstance().getM().getAllTimePitStop()) {
            labelTime.add(convertTime(time));    
        }
        for (Integer time : Session.getIstance().getM().getAnomalyTime()) {
            anomalyLabelTime.add(convertTime(time));
        }
    }
    
    /**
     * Converte i millisecondi in un formato "secondi.millisecondi".
     * 
     * @param millis Il tempo in millisecondi.
     * @return Il tempo formattato come stringa.
     */
    private String convertTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%02d.%03d", seconds, milliseconds);
    }

    /**
     * Mostra la finestra con i dati dei tempi di Pit Stop.
     */
    public void showWindow() {
        gtpv.show();
    }
    
    /**
     * Recupera la lista dei tempi di Pit Stop dal sistema.
     * 
     * @return Una lista di tempi di Pit Stop in millisecondi.
     */
    private ArrayList<Integer> fetchTimePitStop() {
        return Session.getIstance().getM().getAllTimePitStop();
    }
}
