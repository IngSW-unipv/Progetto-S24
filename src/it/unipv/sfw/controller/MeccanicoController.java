package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.MeccanicoView;

/**
 * Controller per la gestione dell'interfaccia del meccanico.
 * Questa classe gestisce le interazioni dell'utente con la {@link MeccanicoView}
 * e coordina le azioni con il {@link MeccanicoDAO} e altri controller.
 */
public class MeccanicoController extends AbsController {

    private Staff user;
    private Meccanico m;

    /**
     * Restituisce il tipo di controller.
     * @return Il tipo di controller (MECCANICO).
     */
    @Override
    public TypeController getType() {
        return TypeController.MECCANICO;
    }

    /**
     * Inizializza il controller, creando la vista, impostando i listener
     * per i bottoni e recuperando i dati dell'utente dalla sessione.
     */
    @Override
    public void initialize() {

        try {
            user = Session.getIstance().getCurrentUser();
            m = (Meccanico) user;
        } catch (Exception e) {
            System.out.println("Errore");
        }

        MeccanicoView mv = new MeccanicoView();
        MeccanicoDAO md = new MeccanicoDAO();

        McPopUpVehicleHandler pvc = new McPopUpVehicleHandler(mv);
        McPopUpPilotHandler ppc = new McPopUpPilotHandler(mv);

        // Inizializzazione dei tempi di PIT STOP
        Session.getIstance().getTps();

        // Inserimento del login nella tabella degli eventi
        md.insertLogEvent(getID(), "LOGIN");

        // Inizializzazione stato bottoni
        setButtonStates(mv);


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
                boolean isComponentPresent = !getComponent(); //Invertito per logica
                
                if(!isComponentPresent) { //Invertito per logica
                    // messaggio pop up che avverte di rimuovere prima di aggiungere
                    JOptionPane.showMessageDialog(null, "INSERT A COMPONENT BEFORE TO REMOVING", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                }else {
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

    /**
     * Imposta lo stato iniziale dei bottoni dell'interfaccia.
     * @param mv La vista {@link MeccanicoView} da cui recuperare i bottoni.
     */
    private void setButtonStates(MeccanicoView mv) {
        boolean isVehiclePresent = (Session.getIstance().getV() != null);

        mv.getAddComponentButton().setEnabled(isVehiclePresent);
        mv.getAddComponentButton().setVisible(isVehiclePresent);

        mv.getAddPilotButton().setEnabled(isVehiclePresent);
        mv.getAddPilotButton().setVisible(isVehiclePresent);

        mv.getRemovePilotButton().setEnabled(isVehiclePresent);
        mv.getRemovePilotButton().setVisible(isVehiclePresent);

        mv.getRemoveComponentButton().setEnabled(isVehiclePresent);
        mv.getRemoveComponentButton().setVisible(isVehiclePresent);

        mv.getVisualTimePsButton().setEnabled(isVehiclePresent);
        mv.getVisualTimePsButton().setVisible(isVehiclePresent);
    }



    /**
     * Verifica se ci sono componenti nel veicolo.
     * @return `true` se il veicolo *non* ha componenti, `false` altrimenti.
     */
    private boolean getComponent() {
        return Session.getIstance().getV().getComponent().isEmpty();
    }

    /**
     * Recupera l'ID del membro dello staff dalla sessione.
     * @return L'ID del membro dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }
}