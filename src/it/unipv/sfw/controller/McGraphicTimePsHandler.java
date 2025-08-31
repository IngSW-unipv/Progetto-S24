package it.unipv.sfw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.view.McGraphicTimePsView;

/**
 * Handler che gestisce la visualizzazione grafica dei tempi di Pit Stop
 * per il {@link Mechanic} passato dal controller.
 * <p>
 * Si occupa di:
 * <ul>
 *   <li>Recuperare i tempi e le anomalie dal modello</li>
 *   <li>Creare etichette leggibili per i dati</li>
 *   <li>Mostrare la vista {@link McGraphicTimePsView}</li>
 *   <li>Gestire casi in cui non ci siano tempi disponibili</li>
 * </ul>
 * </p>
 *
 * @see Mechanic
 * @see McGraphicTimePsView
 */
public class McGraphicTimePsHandler {

    /**
     * Meccanico da cui recuperare i tempi di Pit Stop.
     */
    private final Mechanic m;

    /**
     * Etichette testuali dei tempi di Pit Stop.
     */
    private final List<String> labelTime = new ArrayList<>();

    /**
     * Etichette testuali dei tempi anomali.
     */
    private final List<String> anomalyLabelTime = new ArrayList<>();

    /**
     * Vista grafica dei tempi di Pit Stop.
     * Inizializzata durante {@link #initialize()}.
     */
    private McGraphicTimePsView gtpv;

    /**
     * Costruttore che inizializza l’handler con il meccanico di riferimento.
     *
     * @param mechanic {@link Mechanic} da cui recuperare i dati
     */
    public McGraphicTimePsHandler(Mechanic mechanic) {
        this.m = mechanic;
    }

    /**
     * Inizializza la vista grafica e prepara i dati:
     * <ol>
     *   <li>Controlla la validità del {@link Mechanic}</li>
     *   <li>Logga i tempi in console (debug)</li>
     *   <li>Crea le etichette testuali dei tempi e delle anomalie</li>
     *   <li>Mostra un messaggio se non sono disponibili dati</li>
     *   <li>Costruisce la {@link McGraphicTimePsView} con i dati</li>
     *   <li>Visualizza la finestra</li>
     * </ol>
     *
     * @throws IllegalStateException se il {@link Mechanic} è {@code null}
     */
    public void initialize() {
        if (m == null) {
            throw new IllegalStateException("Mechanic nullo");
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
     * Crea le etichette testuali dei tempi di Pit Stop
     * e delle eventuali anomalie.
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
     * Converte un tempo in millisecondi nel formato {@code "s.mmm"}.
     * <p>Esempio: 2345 → "2.345".</p>
     *
     * @param millis tempo in millisecondi
     * @return tempo formattato come stringa
     */
    private String formatMs(int millis) {
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%d.%03d", seconds, milliseconds);
    }

    /**
     * Mostra la finestra con i dati dei tempi di Pit Stop,
     * se la vista è stata inizializzata.
     */
    public void showWindow() {
        if (gtpv != null) {
            gtpv.show();
        }
    }
}
