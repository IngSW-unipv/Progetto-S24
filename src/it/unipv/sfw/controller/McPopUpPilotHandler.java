package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.model.staff.Session; // opzionale: solo per id_pilot UI
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MechanicView;

/**
 * Handler pop-up per assegnare un pilota a un veicolo.
 * Usa il Mechanic passato dal Controller
 */
public class McPopUpPilotHandler {

    private final McPopUpPilotView pv;
    private final MechanicDAO md;
    private final Mechanic m;      // stesso Mechanic del Controller
    private final MechanicView mv; // per aggiornare la UI principale

    public McPopUpPilotHandler(Mechanic m, MechanicView mv) {
        this.pv = new McPopUpPilotView();
        this.md = new MechanicDAO();
        this.m  = m;
        this.mv = mv;

        pv.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });
    }

    private void onSend() {
        String pilotId = pv.getId().getText();
        String name    = pv.getName().getText().toUpperCase();
        String surname = pv.getSurname().getText().toUpperCase();
        String number  = pv.getNumber().getText();

        // 0) Deve esistere un Vehicle con MSN
        Vehicle v = m.getVehicles();
        if (v == null || v.getMSN() == null || v.getMSN().isBlank()) {
            JOptionPane.showMessageDialog(null,
                "Assign or create a vehicle (with MSN) before linking a pilot.",
                "No vehicle", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String msn = v.getMSN().toUpperCase();

        try {
            // 1) Verifica esistenza pilota con i dati inseriti
            md.selectP(pilotId, name, surname, number);

            // 2) Associa pilota al veicolo
            md.insertPilotOnVehicle(pilotId, msn);

            // 3) (Opzionale) memorizza id pilota in Session come metadato leggero per UI altrove
            Session.getIstance().setId_pilot(pilotId);

            // 4) Aggiorna UI + log
            pv.clearComponents(pv.getDataPanel());
            mv.setId_p();
            pv.close();

            md.insertLogEvent(m.getID(), "INSERT ID PILOT : " + pilotId);

        } catch (PilotNotFoundException err) {
            pv.mex();                 //  messaggio di errore standard
            System.out.println(err);
            pv.clearComponents(pv.getDataPanel());
        }
    }

    public void showWindow() { pv.show(); }

    public void clear() { pv.clearComponents(pv.getSendPanel()); }
}
