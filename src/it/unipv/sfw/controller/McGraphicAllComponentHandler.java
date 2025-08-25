package it.unipv.sfw.controller;

import java.util.Set;
import javax.swing.JOptionPane;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McGraphicAllComponentView;

/**
 * Gestisce la visualizzazione grafica di tutti i componenti del vehicle del meccanico.
 * Dipende dal Mechanic passato dal Controller
 */
public class McGraphicAllComponentHandler {

    private final Mechanic m;
    private McGraphicAllComponentView gv;

    public McGraphicAllComponentHandler(Mechanic mechanic) {
        this.m = mechanic;
    }

    /** Mostra la finestra con la lista di tutti i componenti. */
    public void showWindow() {
        Vehicle v = m.getVehicles();
        if (v == null || v.getMSN() == null || v.getMSN().isBlank()) {
            JOptionPane.showMessageDialog(
                null,
                "Assign/create a vehicle (with MSN) before viewing components.",
                "No vehicle", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Set<Components> components = v.getComponent();
        gv = new McGraphicAllComponentView(components);
        gv.show();
    }
}
