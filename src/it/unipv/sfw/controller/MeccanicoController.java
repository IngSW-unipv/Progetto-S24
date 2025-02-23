package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.MeccanicoView;

/**
 * Controller per la gestione delle azioni del meccanico.
 * Gestisce le interazioni tra la vista del meccanico e il modello dei dati.
 */
public class MeccanicoController extends AbsController {

    private Staff user;

    private Mechanic m;

    @Override
    public TypeController getType() {
        return TypeController.MECCANICO;
    }

    @Override
    public void initialize() {

        try {
            user = Session.getIstance().getCurrentUser();
            m = (Mechanic) user;
        } catch (Exception e) {
            System.out.println("Errore");
        }

        MeccanicoView mv = new MeccanicoView();
        MechanicDAO md = new MechanicDAO();

        McPopUpVehicleHandler pvc = new McPopUpVehicleHandler(mv);
        McPopUpPilotHandler ppc = new McPopUpPilotHandler(mv);

        // Inizializzazione dei tempi di PIT STOP
        Session.getIstance().getTps();

        // Inserimento del login nella tabella degli eventi
        md.insertLogEvent(getID(), "LOGIN");

        // Abilita o disabilita bottoni basati sul valore di V (Veicolo assegnato)
        mv.getAddComponentButton().setEnabled(false);
        mv.getAddComponentButton().setVisible(false);

        mv.getAddPilotButton().setEnabled(false);
        mv.getAddPilotButton().setVisible(false);

        mv.getRemovePilotButton().setEnabled(false);
        mv.getRemovePilotButton().setVisible(false);

        mv.getRemoveComponentButton().setEnabled(false);
        mv.getRemoveComponentButton().setVisible(false);

        mv.getVisualTimePsButton().setEnabled(false);
        mv.getVisualTimePsButton().setVisible(false);

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
                boolean isVehiclePresent = (Session.getIstance().getId_pilot() != null);

                if (!isVehiclePresent) {
                    Session.getIstance().setOperation("NO_V");
                } else {
                    Session.getIstance().setOperation("YES_V");
                }
                McPopUpRequestHandler prc = new McPopUpRequestHandler();
                prc.showWindow();
                prc.clear();
            }
        });

        // ADD COMPONENT
        mv.getAddComponentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.getIstance().setOperation("ADD");
                McPopUpComponentHandler pcc = new McPopUpComponentHandler();
                System.out.println("il contenuto è: " + Session.getIstance().getOperation()
                        + " @MECCANICO CONTROLLER-ADD COMPONENT");
                pcc.showWindow();
                pcc.clear();
            }
        });

        // REMOVE COMPONENT
        mv.getRemoveComponentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isComponentPresent = getComponent();

                if (isComponentPresent) {
                    // messaggio pop up che avverte di rimuovere prima di aggiungere
                    JOptionPane.showMessageDialog(null, "INSERT A COMPONENT BEFORE TO REMOVING", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Session.getIstance().setOperation("REMOVE");
                    McPopUpComponentHandler pcc = new McPopUpComponentHandler();
                    System.out.println("il contenuto è: " + Session.getIstance().getOperation()
                            + " @MECCANICO CONTROLLER-REMOVE COMPONENT");
                    pcc.showWindow();
                    pcc.clear();
                }
            }
        });

        // ADD PILOT
        mv.getAddPilotButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isPilotPresent = (Session.getIstance().getId_pilot() != null);

                if (!isPilotPresent) { 
                    Session.getIstance().setOperation("ADD");
                    System.out.println("il contenuto è: " + Session.getIstance().getOperation()
                            + " @MECCANICO CONTROLLER-ADD PILOT");
                    ppc.showWindow();
                    ppc.clear();
                } else {
                    // messaggio pop up che avverte di rimuovere prima di aggiungere
                    JOptionPane.showMessageDialog(null, "REMOVE THE PILOT BEFORE TO ADD", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // REMOVE PILOT
        mv.getRemovePilotButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.getIstance().setOperation("REMOVE");
                System.out.println("il contenuto è: " + Session.getIstance().getOperation()
                        + " @MECCANICO CONTROLLER-REMOVE PILOT");

                md.removePilot(Session.getIstance().getId_pilot());
                md.insertLogEvent(getID(), "REMOVE PILOT");
                Session.getIstance().setId_pilot(null);
                mv.setId_p();
            }
        });

        mv.getVisualTimePsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                McGraphicTimePsHandler gtpc = new McGraphicTimePsHandler();
                gtpc.initialize();
                md.insertLogEvent(getID(), "SHOW TIME PIT STOP");
            }
        });

        mv.getVisualStatusComponentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                McGraphicAllComponentHandler gacc = new McGraphicAllComponentHandler();
                gacc.showWindow();
                md.insertLogEvent(getID(), "SHOW STATUS COMPONENT");
            }
        });

        mv.setVisible(true);
        view = mv;
    }

    // Metodo per information hiding
    private boolean getComponent() {
        return Session.getIstance().getV().getComponent().isEmpty();
    }

    private String getID() {
        return Session.getIstance().getId_staff();
    }
}