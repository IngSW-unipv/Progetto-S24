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
 * Popup per creare/selezionare la strategia di gara.
 * Dipende da Strategist e Vehicle passati dal Controller (no Model letto da Session).
 */
public class StPopUpCreateStrategyHandler {

    private final StPopUpCreateStrategyView pcs;
    private final Strategist strategist;
    private final Vehicle vehicle;

    // Facade per il solo logging 
    private final StrategistFacade facade;

    // Soglia di riferimento per decidere PUSH/HOLD (ms)
    private static final int SET_POINT_MS = 81_000;

    private StGraphicDetailsView gdv;

    public StPopUpCreateStrategyHandler(Strategist strategist, Vehicle vehicle,
                                        int selectedStrategyCount, int currentLapTimeMs,
                                        StrategistFacade facade) {
        this.pcs = new StPopUpCreateStrategyView();
        this.strategist = strategist;
        this.vehicle    = vehicle;
        this.facade     = facade;

        int averageWear = computeAverageWear(vehicle.getComponent());
        pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS: " + averageWear);
        pcs.getComponentLabel1().setForeground(colorForWear(averageWear));

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

        pcs.getDetailsButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                gdv = new StGraphicDetailsView(vehicle.getComponent());
                gdv.show();
                facade.log(strategist.getID(), "SHOW DETAILS COMPONENT");
            }
        });

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

    public void showWindow() { pcs.show(); }

    // ---------- Helpers ----------

    private static int computeAverageWear(Set<Components> comps) {
        if (comps == null || comps.isEmpty()) return 0;
        int sum = 0;
        for (Components c : comps) sum += c.getWear();
        return sum / comps.size();
    }

    private static Color colorForWear(int avg) {
        if (avg > 70) return Color.GREEN;
        if (avg >= 50) return Color.ORANGE;
        return Color.RED;
    }
}
