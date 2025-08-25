package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.MechanicView;

/**
 * Controller per la gestione delle azioni del meccanico.
 * View -> Controller -> Model(+DAO)
 */
public class MechanicController extends AbsController {

    private Mechanic m; // model corrente (unico riferimento)
    private MechanicView mv;
    private MechanicDAO md;

    @Override
    public TypeController getType() {
        return TypeController.MECHANIC;
    }

    @Override
    public void initialize() {
        // 1) Utente corrente dalla Session (solo questo dalla Session)
        Staff user = Session.getIstance().getCurrentUser();
        if (!(user instanceof Mechanic)) {
            // se non è un meccanico, interrompo (evito cast errati)
            throw new IllegalStateException("L'utente corrente non è un Mechanic");
        }
        m = (Mechanic) user;

        // 2) View + DAO
        mv = new MechanicView();
        md = new MechanicDAO();

        // 3) Handlers (passo il model per DI)
        McPopUpVehicleHandler pvc = new McPopUpVehicleHandler(m, mv);
        McPopUpPilotHandler   ppc = new McPopUpPilotHandler(m, mv);

        // 4) Log di login
        md.insertLogEvent(m.getID(), "LOGIN");

        // 5) Stato iniziale UI (niente vehicle assegnato)
        enableVehicleActions(false);

        // --- LISTENER BUTTONS ---

        // ADD VEHICLE (apre popup; il salvataggio avviene nell'handler)
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
                McPopUpRequestHandler prc = new McPopUpRequestHandler(m);
                prc.showWindow();
                prc.clear();
            }
        });

        // ADD COMPONENT
        mv.getAddComponentButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
            	McPopUpComponentHandler addHandler =
            		    new McPopUpComponentHandler(m, McPopUpComponentHandler.Operation.ADD);
            		addHandler.showWindow();
            		addHandler.clear();
            }
        });

        // REMOVE COMPONENT
        mv.getRemoveComponentButton().addActionListener(new ActionListener() {
            @Override 
             public void actionPerformed(ActionEvent e) {
            	McPopUpComponentHandler removeHandler =
            		    new McPopUpComponentHandler(m, McPopUpComponentHandler.Operation.REMOVE);
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
                md.removePilot(Session.getIstance().getId_pilot());
                md.insertLogEvent(m.getID(), "REMOVE PILOT");
                Session.getIstance().setId_pilot(null);
                mv.setId_p();
            }
        });

        // SHOW PIT STOP TIMES
        mv.getVisualTimePsButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McGraphicTimePsHandler gtpc = new McGraphicTimePsHandler(m);
                gtpc.initialize();
                md.insertLogEvent(m.getID(), "SHOW TIME PIT STOP");
            }
        });

        // SHOW COMPONENT STATUS
        mv.getVisualStatusComponentButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                McGraphicAllComponentHandler gacc = new McGraphicAllComponentHandler(m);
                gacc.showWindow();
                md.insertLogEvent(m.getID(), "SHOW STATUS COMPONENT");
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

    // true se NON ci sono componenti (o non esiste il vehicle)
    private boolean hasNoComponents() {
        Vehicle v = m.getVehicles();
        return v == null || v.getComponent().isEmpty();
    }
}
