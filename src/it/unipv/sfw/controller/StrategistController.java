package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.StrategistDAO;
import it.unipv.sfw.dao.mysql.VehicleDAO;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Stratega;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.StrategistView;

/**
 * Controller per la gestione dell'interfaccia dello stratega.
 * Gestisce le interazioni dell'utente con la {@link StrategistView} e
 * coordina le azioni con il {@link VehicleDAO}.
 */
public class StrategistController extends AbsController {

    private Staff user;
    private Stratega st;

    private int minT1, minT2, minT3, timeLap = getTimeLap();

    /**
     * Restituisce il tipo di controller.
     * @return Il tipo di controller (STRATEGA).
     */
    @Override
    public TypeController getType() {
        return TypeController.STRATEGA;
    }

    /**
     * Inizializza il controller, creando la vista, impostando i listener
     * per i bottoni e recuperando i dati dell'utente dalla sessione.
     */
    @Override
    public void initialize() {

        // Inizializzazione di un veicolo di esempio (da rimuovere in produzione)
        Vehicle v = new Vehicle("SF24-001");
        Components c1 = new Components(1, "MOTORE TERMICO");
        Components c2 = new Components(2, "ERS");
        Components c3 = new Components(3, "ALA ANTERIORE");

        Session.getIstance().setV(v);

        c1.setReplacementStatus("USED");
        c2.setReplacementStatus("USED");
        c3.setReplacementStatus("USED");

        try {
            v.addComponent(c1);
            v.addComponent(c2);
            v.addComponent(c3);
        } catch (DuplicateComponentException e) {
            e.printStackTrace();
        }

        try {
            user = Session.getIstance().getCurrentUser();
            st = (Stratega) user;
        } catch (Exception e) {
            System.out.println("Errore");
        }

        StrategistView sv = new StrategistView();
        VehicleDAO vd = new VehicleDAO();
        StrategistDAO sd = new StrategistDAO();
        
        sd.insertLogEvent(getID(), "LOGIN");

//        sv.getTab().setEnabled(false);
//        sv.getTab().setVisible(false);
        
       sv.getCreateStrategyButton().setEnabled(false);
       sv.getCreateStrategyButton().setVisible(false);       
        
       sv.getGetTimeButton().setEnabled(false);
       sv.getGetTimeButton().setVisible(false);
       
       
       sv.getSendButton().addActionListener(new ActionListener() {
    	   @Override
           public void actionPerformed(ActionEvent e) {
              String msn = sv.getVehicleField().getText().toUpperCase();
              
              try {
            	  
            	  sd.insertStrategistOnVehicle(msn, getID());
            	  sv.showElement();
            	  
              } catch (VehicleNotFoundException ev) {
                  System.out.println(ev);
                  sv.mex();
                  return;
              }
              
           }
       });
       
        sv.getGetTimeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.getIstance().getTS();
                createTable(sv);
                vd.timeSector(Session.getIstance().getV());
            }
        });

        sv.getCreateStrategyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StPopUpCreateStrategyHandler scs = new StPopUpCreateStrategyHandler(sv.getTab().getRowCount(), timeLap);
                scs.showWindow();
            }
        });

        this.minT1 = 0;
        this.minT2 = 0;
        this.minT3 = 0;

        sv.setVisible(true);
        view = sv;
    }

    /**
     * Crea e popola la tabella dei tempi sul giro nella vista.
     * @param sv La vista {@link StrategistView} da cui recuperare la tabella.
     */
    public void createTable(StrategistView sv) {

        // Recupero tempi dai settori (valori generati casualmente)
        int app1 = getVehicle().getTimeSect1();
        int app2 = getVehicle().getTimeSect2();
        int app3 = getVehicle().getTimeSect3();
        timeLap = app1 + app2 + app3;

        setTimeLap();

        // Inizializzazione minimi (solo al primo giro)
        if (minT1 == 0 && minT2 == 0 && minT3 == 0) {
            minT1 = app1;
            minT2 = app2;
            minT3 = app3;
        }

        // Aggiornamento minimi
        minT1 = Math.min(minT1, app1);
        minT2 = Math.min(minT2, app2);
        minT3 = Math.min(minT3, app3);


        String t1 = convertTime(app1);
        String t2 = convertTime(app2);
        String t3 = convertTime(app3);
        String t4 = convertTime(timeLap);

        sv.addRow(t1, t2, t3, t4);
        sv.colorCell(minT1, minT2, minT3);
        sv.setCountLapLabel(sv.getCountLapLabel());
    }

    /**
     * Converte un tempo in millisecondi in una stringa formattata
     * come "minuti:secondi.millisecondi".
     * @param millis Il tempo in millisecondi.
     * @return Il tempo formattato come stringa.
     */
    private String convertTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }

    /**
     * Recupera il tempo sul giro dalla sessione.
     * @return Il tempo sul giro.
     */
    private int getTimeLap() {
        return Session.getIstance().getS().getTimeLap();
    }

    /**
     * Recupera il veicolo dalla sessione.
     * @return Il veicolo.
     */
    private Vehicle getVehicle() {
        return Session.getIstance().getV();
    }

    /**
     * Imposta il tempo sul giro nella sessione.
     */
    private void setTimeLap() {
        Session.getIstance().getS().setTimeLap(timeLap);
    }
    
    private String getID() {
        return Session.getIstance().getId_staff();
    }
}