package it.unipv.sfw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.view.McGraphicTimePsView;

/**
 * Gestisce la visualizzazione grafica dei tempi di Pit Stop.
 * Dipende dal Mechanic passato dal Controller
 */
public class McGraphicTimePsHandler {

    private final Mechanic m;

    private final List<String> labelTime = new ArrayList<>();
    private final List<String> anomalyLabelTime = new ArrayList<>();

    private McGraphicTimePsView gtpv; // inizializzata in initialize()

    public McGraphicTimePsHandler(Mechanic mechanic) {
        this.m = mechanic;
    }

    /**
     * Inizializza la vista, crea etichette e gestisce eventuali anomalie.
     */
    public void initialize() {
        if (m == null) {
            throw new IllegalStateException("Mechanic nullo: passa il model al costruttore.");
        }

        // Log dei tempi in console (debug)
        for (Integer t : m.getAllTimePitStop()) {
            System.out.println("tempo: " + t);
        }

        createLabels();

        if (m.getAllTimePitStop().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Nessun tempo di pit stop disponibile.",
                "Informazione",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        // Crea la view solo ora che i dati sono pronti
        gtpv = new McGraphicTimePsView(new ArrayList<>(m.getAllTimePitStop()), new ArrayList<>(labelTime));
        gtpv.anomalyTime(new ArrayList<>(anomalyLabelTime));
        showWindow();
    }

    /**
     * Crea le etichette per i tempi di Pit Stop e per le anomalie.
     */
    private void createLabels() {
        labelTime.clear();
        anomalyLabelTime.clear();

        for (Integer time : m.getAllTimePitStop()) {
            labelTime.add(formatMs(time));
        }
        for (Integer time : m.getAnomalyTime()) {
            anomalyLabelTime.add(formatMs(time));
        }
    }

    /**
     * Converte millisecondi in "s.mmm" (es. 2.345).
     */
    private String formatMs(int millis) {
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%d.%03d", seconds, milliseconds);
    }

    /**
     * Mostra la finestra con i dati dei tempi di Pit Stop.
     */
    public void showWindow() {
        if (gtpv != null) {
            gtpv.show();
        }
    }
}
