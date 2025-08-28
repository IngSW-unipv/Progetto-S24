package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpVehicleView;
import it.unipv.sfw.view.MechanicView;

public class McPopUpVehicleHandler {

    private final McPopUpVehicleView vv;
    private final Mechanic m;
    private final MechanicView mv;
    private final MechanicFacade facade;

    public McPopUpVehicleHandler(Mechanic m, MechanicView mv, MechanicFacade facade) {
        this.vv = new McPopUpVehicleView();
        this.m  = m;
        this.mv = mv;
        this.facade = facade;

        vv.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) { 
            	onSend(); 
            }
        });
    }

    private void onSend() {
        String idPilot = vv.getId_p().getText();
        String msn     = vv.getMsn().getText().toUpperCase();

        try {
            facade.assignVehicleToMechanicAndPilot(m.getID(), idPilot, msn);

            Vehicle v = (m.getVehicles() != null) ? m.getVehicles() : m.addVehicle();
            v.setMSN(msn);

            Session.getIstance().setId_pilot(idPilot);

            mv.setId_p();
            enableVehicleActions(true);
            mv.getInsertVehicleButton().setEnabled(false);
            vv.close();

        } catch (PilotNotFoundException | VehicleNotFoundException ex) {
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

    public void showWindow() {
    	vv.show();
    }
    
    public void clear() { vv.clearComponents(vv.getSendPanel()); }
}
