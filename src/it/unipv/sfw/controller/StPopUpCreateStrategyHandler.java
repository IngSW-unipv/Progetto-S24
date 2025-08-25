package it.unipv.sfw.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import it.unipv.sfw.dao.mysql.StrategistDAO;
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
    private final StrategistDAO sd;
    private final Strategist strategist;
    private final Vehicle vehicle;

    // Soglia di riferimento per decidere PUSH/HOLD (ms)
    private static final int SET_POINT_MS = 81_000;

    private StGraphicDetailsView gdv;

    /**
     * @param strategist stratega corrente (dal controller)
     * @param vehicle    veicolo associato (dal controller)
     * @param selectedStrategyCount 0 se era già stata selezionata una strategia in precedenza
     * @param currentLapTimeMs      tempo giro corrente (ms)
     */
    public StPopUpCreateStrategyHandler(Strategist strategist, Vehicle vehicle,
                                        int selectedStrategyCount, int currentLapTimeMs) {
        this.pcs = new StPopUpCreateStrategyView();
        this.sd  = new StrategistDAO();
        this.strategist = strategist;
        this.vehicle    = vehicle;

        // 1) Calcolo media usura componenti
        int averageWear = computeAverageWear(vehicle.getComponent());

        // 2) Mostra degradazione componenti con colore
        pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS: " + averageWear);
        pcs.getComponentLabel1().setForeground(colorForWear(averageWear));

        // 3) Messaggio strategia raccomandata
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

        // 4) Listener: dettagli componenti
        pcs.getDetailsButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                gdv = new StGraphicDetailsView(vehicle.getComponent());
                gdv.show();
                sd.insertLogEvent(strategist.getID(), "SHOW DETAILS COMPONENT");
            }
        });

        // 5) Listener: scelta strategia dalla combo
        pcs.getBox().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                String select = (String) pcs.getBox().getSelectedItem();
                
                pcs.getStrategyLabel2().setText(select);
                pcs.getStrategyLabel2().setForeground(Color.YELLOW);
                sd.insertLogEvent(strategist.getID(), "SELECT NEW STRATEGY: " + select);
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
