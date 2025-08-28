package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.facade.impl.DefaultMechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.MechanicView;

public class MechanicController extends AbsController {

    private Mechanic m;
    private MechanicView mv;
    private MechanicFacade facade; // Facade

    @Override
    public TypeController getType() {
        return TypeController.MECHANIC;
    }

    @Override
    public void initialize() {
        Staff user = Session.getIstance().getCurrentUser();
        if (!(user instanceof Mechanic)) {
            throw new IllegalStateException("L'utente corrente non è un Mechanic");
        }
        m = (Mechanic) user;

        mv = new MechanicView();
        facade = new DefaultMechanicFacade(new MechanicDAO());

        McPopUpVehicleHandler pvc = new McPopUpVehicleHandler(m, mv, facade);
        McPopUpPilotHandler   ppc = new McPopUpPilotHandler(m, mv, facade);

        facade.log(m.getID(), "LOGIN");
        enableVehicleActions(false);

        // ADD VEHICLE
        mv.getInsertVehicleButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                pvc.showWindow();
                pvc.clear();
            }
        });

        // INSERT REQUEST
        mv.getInsertRequestButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                boolean hasVehicle = (m.getVehicles() != null);
                Session.getIstance().setOperation(hasVehicle ? "YES_V" : "NO_V");
                McPopUpRequestHandler prc = new McPopUpRequestHandler(m, facade);
                prc.showWindow();
                prc.clear();
            }
        });

        // ADD COMPONENT
        mv.getAddComponentButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McPopUpComponentHandler addHandler =
                        new McPopUpComponentHandler(m, McPopUpComponentHandler.Operation.ADD, facade);
                addHandler.showWindow();
                addHandler.clear();
            }
        });

        // REMOVE COMPONENT
        mv.getRemoveComponentButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McPopUpComponentHandler removeHandler =
                        new McPopUpComponentHandler(m, McPopUpComponentHandler.Operation.REMOVE, facade);
                removeHandler.showWindow();
                removeHandler.clear();
            }
        });

        // ADD PILOT
        mv.getAddPilotButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                boolean isPilotPresent = (Session.getIstance().getId_pilot() != null);
                if (!isPilotPresent) {
                    Session.getIstance().setOperation("ADD");
                    ppc.showWindow();
                    ppc.clear();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "REMOVE THE PILOT BEFORE TO ADD",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // REMOVE PILOT
        mv.getRemovePilotButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Session.getIstance().setOperation("REMOVE");
                String pilotId = Session.getIstance().getId_pilot();
                if (pilotId != null) {
                    facade.unlinkPilotFromVehicle(m.getID(), pilotId);
                    Session.getIstance().setId_pilot(null);
                    mv.setId_p();
                }
            }
        });

        // SHOW PIT STOP TIMES
        mv.getVisualTimePsButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McGraphicTimePsHandler gtpc = new McGraphicTimePsHandler(m);
                gtpc.initialize();
                facade.log(m.getID(), "SHOW TIME PIT STOP");
            }
        });

        // SHOW COMPONENT STATUS
        mv.getVisualStatusComponentButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McGraphicAllComponentHandler gacc = new McGraphicAllComponentHandler(m);
                gacc.showWindow();
                facade.log(m.getID(), "SHOW STATUS COMPONENT");
            }
        });

        mv.setVisible(true);
        view = mv;
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

 //		DA VERIFICARE
//    private boolean hasNoComponents() {
//        Vehicle v = m.getVehicles();
//        return v == null || v.getComponent().isEmpty();
//    }
}
