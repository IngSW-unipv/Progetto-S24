package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpRequestHandler {

    private final McPopUpRequestView pr;
    private final Mechanic m;
    private final MechanicFacade facade;

    public McPopUpRequestHandler(Mechanic m, MechanicFacade facade) {
        this.pr = new McPopUpRequestView();
        this.m  = m;
        this.facade = facade;

        boolean hasVehicle = (m.getVehicles() != null);
        if (hasVehicle) { pr.hide(); pr.title(); }

        pr.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                onSend(hasVehicle);
            }
        });
    }

    private void onSend(boolean hasVehicle) {
        String idS = pr.getId_s().getText();
        String idC = pr.getId_c().getText();
        String desc = pr.getDesc().getText();

        String msn = null;
        if (!hasVehicle) {
            msn = pr.getId_v().getText();
            if (msn != null) msn = msn.toUpperCase();
        } else {
            Vehicle v = m.getVehicles();
            msn = (v != null && v.getMSN() != null) ? v.getMSN().toUpperCase() : null;
        }

        try {
            facade.createComponentRequest(
                m.getID(),
                idS,
                idC,
                msn,
                desc.toUpperCase()
            );
            pr.clearComponents(pr.getDataPanel());
            pr.mex1(); // successo

        } catch (WrongIDException | ComponentNotFoundException |
                 WrongRequestException | VehicleNotFoundException ex) {
            pr.mex();   // errore generico view
            System.out.println(ex);
        }
    }

    public void showWindow() { 
    	pr.show();
    }
    
    public void clear() { pr.clearComponents(pr.getSendPanel()); }
}
