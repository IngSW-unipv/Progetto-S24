package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpVehicleView;
import it.unipv.sfw.view.MechanicView;

/**
 * Handler per il pop-up di associazione veicolo.
 * Riceve il Mechanic dal Controller, opera sul Model, poi aggiorna DB e UI.
 */
public class McPopUpVehicleHandler {

    private final McPopUpVehicleView vv;
    private final MechanicDAO md;
    private final Mechanic m;      // lo stesso oggetto passato dal Controller
    private final MechanicView mv; // per aggiornare la UI principale

    public McPopUpVehicleHandler(Mechanic m, MechanicView mv) {
        this.vv = new McPopUpVehicleView();
        this.md = new MechanicDAO();
        this.m  = m;
        this.mv = mv;

        vv.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });
    }

    private void onSend() {
        String idPilot = vv.getId_p().getText();
        String msn     = vv.getMsn().getText().toUpperCase();

        try {
            // 1) Validazioni DB
            md.checkPilot(idPilot);
            md.checkVehicle(msn);

            // 2) MODEL FIRST: creo/ottengo il Vehicle e imposto MSN, sintassi equivalente a un if/else
            Vehicle v = (m.getVehicles() != null) ? m.getVehicles() : m.addVehicle();
            v.setMSN(msn);

            // 3) Persisto le associazioni
            md.insertPilotOnVehicle(idPilot, msn);
            md.insertMeccOnVehicle(msn, m.getID());

            // 4) Metadato UI leggero
            Session.getIstance().setId_pilot(idPilot);

            // 5) UI + Log
            mv.setId_p(); // aggiorna label del pilota
            enableVehicleActions(true);
            md.insertLogEvent(m.getID(), "INSERT VEHICLE : " + msn);

            mv.getInsertVehicleButton().setEnabled(false);
            vv.close();

        } catch (PilotNotFoundException | VehicleNotFoundException ex) {
            // messaggio standard della view
            vv.mex();
        }
    }

    private void enableVehicleActions(boolean on) {
        mv.getAddComponentButton().setEnabled(on);
        mv.getAddComponentButton().setVisible(true);

        mv.getAddPilotButton().setEnabled(on);
        mv.getAddPilotButton().setVisible(true);

        mv.getRemovePilotButton().setEnabled(on);
        mv.getRemovePilotButton().setVisible(true);

        mv.getRemoveComponentButton().setEnabled(on);
        mv.getRemoveComponentButton().setVisible(true);

        mv.getVisualTimePsButton().setEnabled(on);
        mv.getVisualTimePsButton().setVisible(true);
    }

    public void showWindow() { vv.show(); }

    public void clear() { vv.clearComponents(vv.getSendPanel()); }
}
