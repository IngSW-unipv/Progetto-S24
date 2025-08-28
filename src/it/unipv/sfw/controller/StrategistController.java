package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.StrategistDAO;
import it.unipv.sfw.dao.mysql.VehicleDAO;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.facade.StrategistFacade;
import it.unipv.sfw.facade.impl.DefaultStrategistFacade;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Strategist;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.StrategistView;

/**
 * Controller per la gestione dell'interfaccia dello Strategist.
 *  View -> Controller -> Model(+DAO)
 */
public class StrategistController extends AbsController {

    private Strategist st;          // model utente corrente
    private Vehicle currentVehicle; // vehicle associato allo stratega

    private StrategistView sv;
    private StrategistDAO sd;
    private VehicleDAO vd;

    // Facade
    private StrategistFacade facade;

    private int minT1 = 0, minT2 = 0, minT3 = 0;
    private int timeLap = 0;

    @Override
    public TypeController getType() {
        return TypeController.STRATEGIST;
    }

    @Override
    public void initialize() {
        // 1) Utente corrente dalla Session
        Staff user = Session.getIstance().getCurrentUser();
        if (!(user instanceof Strategist)) {
            throw new IllegalStateException("L'utente corrente non è uno Strategist");
        }
        st = (Strategist) user;

        // 2) View + DAO
        sv = new StrategistView();
        sd = new StrategistDAO();
        vd = new VehicleDAO();

        // inizializzo la Facade con i DAO (IStrategistDAO + IVehicleDAO)
        facade = new DefaultStrategistFacade(sd, vd);

        // 3) Log di login
        facade.log(st.getID(), "LOGIN");

        // 4) Stato UI iniziale (niente vehicle associato)
        setStrategyActionsEnabled(false);

        // 5) LISTENER

        // Associa veicolo allo stratega
        sv.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                String msn = sv.getVehicleField().getText();
                if (msn == null || msn.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Inserire un MSN veicolo.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                msn = msn.toUpperCase();
                try {
                    // validazione + persistenza associazione (DB) via Facade
                    facade.bindVehicleToStrategist(st.getID(), msn);

                    // model: creo/aggancio il veicolo allo stratega
                    currentVehicle = new Vehicle(msn);

                    // UI abilitata ora che il veicolo esiste/è associato
                    sv.showElement();
                    setStrategyActionsEnabled(true);

                    facade.log(st.getID(), "BIND VEHICLE: " + msn);
                } catch (VehicleNotFoundException ex) {
                    sv.mex(); // popup di errore
                    System.out.println(ex);
                }
            }
        });

        // Genera tempi settore sul model e aggiorna tabella
        sv.getGetTimeButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                if (currentVehicle == null) {
                    JOptionPane.showMessageDialog(null,
                        "Associare prima un veicolo.",
                        "Nessun veicolo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //  genera tempi sul Vehicle
                currentVehicle.setTimeSect();

                // persistenza dei tempi via Facade (DB)
                facade.persistSectorTimes(currentVehicle);

                // aggiorna tabella/etichette
                addLapRow(sv, currentVehicle);
            }
        });

        // Crea strategia (usa il timeLap calcolato nell’ultimo giro)
        sv.getCreateStrategyButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                int laps = sv.getTab().getRowCount();
                StPopUpCreateStrategyHandler scs =
                    new StPopUpCreateStrategyHandler(st, currentVehicle, laps == 0 ? 1 : 0, timeLap, facade);
                scs.showWindow();
            }
        });

        sv.setVisible(true);
        view = sv;
    }

    /**
     * Abilita/disabilita i pulsanti legati ad un vehicle associato.
     */
    private void setStrategyActionsEnabled(boolean enabled) {
        sv.getCreateStrategyButton().setEnabled(enabled);
        sv.getCreateStrategyButton().setVisible(enabled);

        sv.getGetTimeButton().setEnabled(enabled);
        sv.getGetTimeButton().setVisible(enabled);
    }

    /**
     * Calcola i tempi, aggiorna minimi e popola la tabella.
     */
    private void addLapRow(StrategistView sv, Vehicle v) {
        int t1 = v.getTimeSect1();
        int t2 = v.getTimeSect2();
        int t3 = v.getTimeSect3();
        timeLap = t1 + t2 + t3;

        // Memorizza il lap time anche nello Strategist
        st.setTimeLap(timeLap);

        // Inizializza i minimi al primo giro
        if (minT1 == 0 && minT2 == 0 && minT3 == 0) {
            minT1 = t1; minT2 = t2; minT3 = t3;
        }

        // Aggiorna minimi
        minT1 = Math.min(minT1, t1);
        minT2 = Math.min(minT2, t2);
        minT3 = Math.min(minT3, t3);

        String s1 = formatMs(t1);
        String s2 = formatMs(t2);
        String s3 = formatMs(t3);
        String lap = formatMs(timeLap);

        sv.addRow(s1, s2, s3, lap);
        sv.colorCell(minT1, minT2, minT3);
        sv.setCountLapLabel(sv.getCountLapLabel());
    }

    /**
     * Converte ms in "mm:ss.SSS".
     */
    private String formatMs(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
