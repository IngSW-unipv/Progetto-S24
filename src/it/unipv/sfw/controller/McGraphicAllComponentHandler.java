package it.unipv.sfw.controller;

import java.util.Set;
import javax.swing.JOptionPane;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McGraphicAllComponentView;

/**
 * Handler che gestisce la visualizzazione grafica di tutti i componenti
 * del {@link Vehicle} associato a un {@link Mechanic}.
 * <p>
 * Riceve in input un meccanico e, a partire dal suo veicolo, 
 * costruisce la view {@link McGraphicAllComponentView} con l’elenco dei componenti.
 * </p>
 * <p>
 * Se il meccanico non ha ancora un veicolo assegnato o il MSN non è valido,
 * mostra un messaggio di avviso all’utente.
 * </p>
 *
 * @see Mechanic
 * @see Vehicle
 * @see McGraphicAllComponentView
 */
public class McGraphicAllComponentHandler {

    /**
     * Meccanico di riferimento, passato dal controller.
     */
    private final Mechanic m;

    /**
     * View che mostra i componenti.
     */
    private McGraphicAllComponentView gv;

    /**
     * Costruttore che inizializza l’handler con il meccanico associato.
     *
     * @param mechanic il {@link Mechanic} da cui recuperare il veicolo e i componenti
     */
    public McGraphicAllComponentHandler(Mechanic mechanic) {
        this.m = mechanic;
    }

    /**
     * Mostra la finestra contenente la lista di tutti i componenti del veicolo
     * associato al meccanico.
     * <p>
     * Se il veicolo non è presente, non ha un MSN valido o non è stato assegnato,
     * viene mostrato un messaggio di avviso tramite {@link JOptionPane}.
     * </p>
     */
    public void showWindow() {
        Vehicle v = m.getVehicle();
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
