package it.unipv.sfw.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import it.unipv.sfw.facade.StrategistFacade;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Strategist;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.StGraphicDetailsView;
import it.unipv.sfw.view.StPopUpCreateStrategyView;

/**
 * Handler per la finestra pop-up di creazione/selezione strategia di gara.
 * <p>
 * Dipende da {@link Strategist} e {@link Vehicle} passati dal {@link StrategistController},
 * senza utilizzare la {@link it.unipv.sfw.model.staff.Session}.
 * </p>
 * <p>
 * Responsabilità principali:
 * <ul>
 *   <li>Mostrare il livello medio di degrado dei componenti del veicolo</li>
 *   <li>Determinare una strategia consigliata (PUSH/HOLD) in base al tempo sul giro</li>
 *   <li>Permettere allo strategist di selezionare manualmente una strategia</li>
 *   <li>Mostrare i dettagli grafici dei componenti</li>
 *   <li>Loggare le azioni tramite {@link StrategistFacade}</li>
 * </ul>
 * </p>
 *
 * @see Strategist
 * @see Vehicle
 * @see StrategistFacade
 * @see StPopUpCreateStrategyView
 */
public class StPopUpCreateStrategyHandler {

    private final StPopUpCreateStrategyView pcs;
    private final Strategist strategist;
    private final Vehicle vehicle;

    // Facade per logging
    private final StrategistFacade facade;

    // Soglia di riferimento per decidere PUSH/HOLD (ms)
    private static final int SET_POINT_MS = 81_000;

    private StGraphicDetailsView gdv;

    /**
     * Costruttore: inizializza la popup di creazione strategia e registra i listener.
     *
     * @param strategist             strategist corrente
     * @param vehicle                veicolo associato
     * @param selectedStrategyCount  numero di strategie già selezionate (0 : prima strategia)
     * @param currentLapTimeMs       tempo dell'ultimo giro (ms)
     * @param facade                 {@link StrategistFacade} usata per logging
     */
    public StPopUpCreateStrategyHandler(Strategist strategist, Vehicle vehicle,
                                        int selectedStrategyCount, int currentLapTimeMs,
                                        StrategistFacade facade) {
        this.pcs = new StPopUpCreateStrategyView();
        this.strategist = strategist;
        this.vehicle    = vehicle;
        this.facade     = facade;

        // Mostra degrado medio componenti
        int averageWear = computeAverageWear(vehicle.getComponent());
        pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS: " + averageWear);
        pcs.getComponentLabel1().setForeground(colorForWear(averageWear));

        // Determina messaggio strategia consigliata
        String strategyMsg;
        Color strategyColor;
        if (selectedStrategyCount == 0) {
            strategyMsg = "SELECTED A STRATEGY";
            strategyColor = Color.YELLOW;
        } else if ((currentLapTimeMs - SET_POINT_MS) > 0) {
            strategyMsg = "RECOMMENDED STRATEGY : PUSH";
            strategyColor = Color.RED;
        } else {
            strategyMsg = "RECOMMENDED STRATEGY : HOLD THE POSITION";
            strategyColor = Color.GREEN;
        }
        pcs.getMexLabel().setText(strategyMsg);
        pcs.getMexLabel().setForeground(strategyColor);

        // Listener per mostrare dettagli componenti
        pcs.getDetailsButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                gdv = new StGraphicDetailsView(vehicle.getComponent());
                gdv.show();
                facade.log(strategist.getID(), "SHOW DETAILS COMPONENT");
            }
        });

        // Listener per selezione manuale strategia
        pcs.getBox().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                String select = (String) pcs.getBox().getSelectedItem();
                pcs.getStrategyLabel2().setText(select);
                pcs.getStrategyLabel2().setForeground(Color.YELLOW);
                facade.log(strategist.getID(), "SELECT NEW STRATEGY: " + select);
            }
        });
    }

    /**
     * Mostra la finestra pop-up per la creazione della strategia.
     */
    public void showWindow() { pcs.show(); }

    // ---------- Helpers ----------

    /**
     * Calcola l'usura media dei componenti.
     *
     * @param comps insieme di {@link Components}
     * @return usura media, oppure 0 se la collezione è nulla o vuota
     */
    private static int computeAverageWear(Set<Components> comps) {
        if (comps == null || comps.isEmpty()) return 0;
        int sum = 0;
        for (Components c : comps) sum += c.getWear();
        return sum / comps.size();
    }

    /**
     * Restituisce un colore rappresentativo del livello di usura.
     *
     * @param avg usura media (0-100)
     * @return {@link Color#GREEN} se usura : 70, {@link Color#ORANGE} se compresa tra 50-70,
     *         {@link Color#RED} altrimenti
     */
    private static Color colorForWear(int avg) {
        if (avg > 70) return Color.GREEN;
        if (avg >= 50) return Color.ORANGE;
        return Color.RED;
    }
}
