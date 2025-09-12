package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpVehicleView;
import it.unipv.sfw.view.MechanicView;

/**
 * Handler che gestisce l'assegnazione/creazione del {@link Vehicle} per un {@link Mechanic}
 * e il collegamento al pilota tramite la popup {@link McPopUpVehicleView}.
 * <p>
 * Raccoglie i dati inseriti dall'utente (id pilota, MSN), delega alla
 * {@link MechanicFacade} l'operazione applicativa e, in caso di successo,
 * aggiorna lo stato del modello, la {@link Session} e abilita le azioni
 * relative al veicolo nella {@link MechanicView}.
 * </p>
 *
 * @see McPopUpVehicleView
 * @see MechanicView
 * @see MechanicFacade
 * @see Mechanic
 * @see Vehicle
 */
public class McPopUpVehicleHandler {

    private final McPopUpVehicleView vv;
    private final Mechanic m;
    private final MechanicView mv;
    private final MechanicFacade facade;
    
    /**
     * Gestisce l'inserimento di un nuovo veicolo a partire dall'MSN fornito dalla view.
     *
     * <p>Il metodo delega alla {@code facade} la creazione del {@link it.unipv.sfw.model.vehicle.Vehicle}
     * (validazioni, normalizzazione e persistenza/log a carico della facade).<br>
     * Se la facade restituisce {@code null}, viene mostrato un messaggio utente tramite {@code vv.mex()}
     * e l'operazione termina senza modificare il model. 
     * In caso di successo il veicolo viene assegnato al model del meccanico tramite {@code m.addVehicle(v)}.</p>
     *
     * @param msnFromView MSN inserito nella view
     *
     * @see facade.MechanicFacade#createVehicle(String)
     * @see staff.Mechanic#addVehicle(it.unipv.sfw.model.vehicle.Vehicle)
     */
    void onInsertVehicle(String msnFromView) {
        Vehicle v = facade.createVehicle(msnFromView);

        if (v == null) {
            vv.mex(); // feedback utente (es. errore/avviso)
            return;
        }

        m.addVehicle(v);
    }

    
    /**
     * Costruttore che inizializza la popup e registra il listener
     * per avviare il flusso di assegnazione veicolo/pilota.
     *
     * @param m meccanico corrente
     * @param mv vista del meccanico da aggiornare
     * @param facade facciata applicativa per l'assegnazione veicolo
     */
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

    /**
     * Gestisce l'invio dalla popup:
     * <ol>
     *   <li>Legge id pilota e MSN dalla {@link McPopUpVehicleView}</li>
     *   <li>Invoca {@link MechanicFacade#assignVehicleToMechanicAndPilot(String, String, String)}</li>
     *   <li>Aggiorna il modello locale ({@link Mechanic} e {@link Vehicle})</li>
     *   <li>Aggiorna la {@link Session} e la {@link MechanicView}, abilitando le azioni veicolo</li>
     *   <li>Gestisce gli errori di dominio mostrando messaggi nella UI</li>
     * </ol>
     * @throws WrongIDException 
     */
    private void onSend()  {
        String idPilot = vv.getId_p().getText();
        String msn    = vv.getMsn().getText().toUpperCase();

        try {
        	
        	onInsertVehicle(msn);
        	
            facade.assignVehicleToMechanicAndPilot(m.getID(), idPilot, msn );

            Session.getIstance().setId_pilot(idPilot);

            mv.setId_p();
            enableVehicleActions(true);
            mv.getInsertVehicleButton().setEnabled(false);
            vv.close();

        } catch (PilotNotFoundException | VehicleNotFoundException ex) {
            vv.mex();
        }
    }

    /**
     * Abilita/Disabilita le azioni della {@link MechanicView} legate al veicolo.
     *
     * @param on {@code true} per abilitare e rendere visibili i controlli, {@code false} per disabilitarli
     */
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

    /**
     * Mostra la popup per l'inserimento dei dati del veicolo.
     */
    public void showWindow() {
    	vv.show();
    }
    
    /**
     * Pulisce i campi dell'area di invio della popup.
     */
    public void clear() { vv.clearComponents(vv.getSendPanel()); }
}
