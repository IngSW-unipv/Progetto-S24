package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MechanicView;

public class McPopUpPilotHandler {

    private final McPopUpPilotView pv;
    private final Mechanic m;
    private final MechanicView mv;
    private final MechanicFacade facade;

    public McPopUpPilotHandler(Mechanic m, MechanicView mv, MechanicFacade facade) {
        this.pv = new McPopUpPilotView();
        this.m  = m;
        this.mv = mv;
        this.facade = facade;

        pv.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) { 
            	onSend();
            }
        });
    }

    private void onSend() {
        String pilotId = pv.getId().getText();
        String name    = pv.getName().getText().toUpperCase();
        String surname = pv.getSurname().getText().toUpperCase();
        String number  = pv.getNumber().getText();

        Vehicle v = m.getVehicles();
        if (v == null || !v.hasValidMsn()) {
            JOptionPane.showMessageDialog(null,
                "Assign or create a vehicle (with MSN) before linking a pilot.",
                "No vehicle", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String msn = v.getMSN().toUpperCase();

        try {
            facade.linkPilotToVehicle(m.getID(), msn, pilotId, name, surname, number);
            Session.getIstance().setId_pilot(pilotId);
            pv.clearComponents(pv.getDataPanel());
            mv.setId_p();
            pv.close();
        } catch (PilotNotFoundException err) {
            pv.mex();
            pv.clearComponents(pv.getDataPanel());
        }
    }

    public void showWindow() {
    	pv.show();
    }
    
    public void clear() { pv.clearComponents(pv.getSendPanel()); }
}
