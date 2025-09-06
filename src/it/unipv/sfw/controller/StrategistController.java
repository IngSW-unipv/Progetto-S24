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
 * Controller per la gestione dell'interfaccia e del flusso operativo dello {@link Strategist}.
 * <p>
 * Responsabilità principali:
 * <ul>
 *   <li>Associare un {@link Vehicle} allo strategist</li>
 *   <li>Richiedere la generazione dei tempi di settore al modello veicolo e persisterli</li>
 *   <li>Delegare il calcolo/aggiornamento dei tempi sul giro e dei minimi di settore al modello {@link Strategist}</li>
 *   <li>Abilitare le azioni di strategia sulla {@link StrategistView}</li>
 * </ul>
 * Architettura MVC: <i>View -> Controller -> Model (+DAO)</i>, con delega
 * delle operazioni applicative/persistenza alla {@link StrategistFacade}.
 * </p>
 */
public class StrategistController extends AbsController {

    private Strategist st;          // model utente corrente
    private Vehicle currentVehicle; // vehicle associato allo strategist
    
    private StrategistView sv;
    // Facade
    private StrategistFacade facade;

    /**
     * Restituisce il tipo di controller gestito.
     *
     * @return {@link AbsController.TypeController#STRATEGIST}
     */
    @Override
    public TypeController getType() {
        return TypeController.STRATEGIST;
    }

    /**
     * Inizializza il controller dello Strategist:
     * <ol>
     *   <li>Recupera e valida l'utente corrente dalla {@link Session}</li>
     *   <li>Istanzia {@link StrategistView}, {@link StrategistDAO}, {@link VehicleDAO} e la {@link StrategistFacade}</li>
     *   <li>Esegue il log di login</li>
     *   <li>Imposta lo stato iniziale della UI disabilitando le azioni di strategia</li>
     *   <li>Registra i listener dei pulsanti:
     *     <ul>
     *       <li>Associazione veicolo allo strategist</li>
     *       <li>Generazione tempi di settore e persistenza</li>
     *       <li>Creazione strategia (apre popup dedicato)</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @throws IllegalStateException se l'utente corrente non è uno {@link Strategist}
     */
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
        StrategistDAO sd = new StrategistDAO();
        VehicleDAO vd = new VehicleDAO();

        // inizializzo la Facade con i DAO (IStrategistDAO + IVehicleDAO)
        facade = new DefaultStrategistFacade(sd, vd);

        // 3) Log di login
        facade.log(st.getID(), "LOGIN");

        // 4) Stato UI iniziale (niente vehicle associato)
        setStrategyActionsEnabled(false);

        // 5) LISTENER

        // Associa veicolo allo strategist
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

                    // model: creo/aggancio il veicolo allo strategist
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

                // genera tempi sul Vehicle (modello)
                currentVehicle.setTimeSect();

                // persistenza dei tempi via Facade (DB)
                facade.persistSectorTimes(currentVehicle);

                // aggiorna tabella/etichette delegando il calcolo al modello Strategist
                addLapRow(sv, currentVehicle);
            }
        });

        // Crea strategia (usa l'ultimo lap time registrato nel modello)
        sv.getCreateStrategyButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                int laps = sv.getTab().getRowCount();
                StPopUpCreateStrategyHandler scs =
                    new StPopUpCreateStrategyHandler(st, currentVehicle, laps == 0 ? 1 : 0, st.getTimeLap(), facade);
                scs.showWindow();
            }
        });

        sv.setVisible(true);
        view = sv;
    }

    /**
     * Abilita/disabilita i pulsanti della {@link StrategistView} che richiedono
     * la presenza di un veicolo associato.
     *
     * @param enabled {@code true} per abilitare i controlli; {@code false} per disabilitarli
     */
    private void setStrategyActionsEnabled(boolean enabled) {
        sv.getCreateStrategyButton().setEnabled(enabled);
        sv.getCreateStrategyButton().setVisible(enabled);

        sv.getGetTimeButton().setEnabled(enabled);
        sv.getGetTimeButton().setVisible(enabled);
    }
    /**
     * Legge i tempi di settore dal {@link Vehicle}, aggiorna il modello {@link Strategist}
     * registrando un nuovo giro e aggiorna la view con i dati formattati.
     *
     * @param sv vista dello strategist da aggiornare
     * @param v  veicolo da cui leggere i tempi di settore
     */
    private void addLapRow(StrategistView sv, Vehicle v) {
        int t1 = v.getTimeSect1();
        int t2 = v.getTimeSect2();
        int t3 = v.getTimeSect3();

        // aggiorna stato interno dello Strategist
        st.registerLapTimes(t1, t2, t3);

        // lettura dei valori aggiornati dal model
        String s1  = formatMs(t1);
        String s2  = formatMs(t2);
        String s3  = formatMs(t3);
        String lap = formatMs(st.getTimeLap());

        sv.addRow(s1, s2, s3, lap);
        sv.colorCell(st.getMinT1(), st.getMinT2(), st.getMinT3());
        sv.setCountLapLabel(sv.getCountLapLabel());
    }


    /**
     * Converte millisecondi nel formato {@code "mm:ss.SSS"}.
     *
     * @param millis tempo in millisecondi
     * @return stringa formattata nel formato minuti:secondi.millisecondi
     */
    private String formatMs(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
