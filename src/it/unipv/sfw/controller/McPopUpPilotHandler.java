package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MechanicView;

/**
 * Handler che gestisce il collegamento di un pilota a un {@link Vehicle}
 * attraverso una popup dedicata ({@link McPopUpPilotView}).
 * <p>
 * Raccoglie i dati inseriti dall'utente (id, nome, cognome, numero),
 * verifica la presenza di un veicolo con MSN valido e delega alla
 * {@link MechanicFacade} l'operazione di associazione del pilota.
 * In caso di successo aggiorna la {@link Session} e la {@link MechanicView}.
 * </p>
 *
 * @see McPopUpPilotView
 * @see MechanicView
 * @see MechanicFacade
 * @see Mechanic
 * @see Vehicle
 */
public class McPopUpPilotHandler {

    private final McPopUpPilotView pv;
    private final Mechanic m;
    private final MechanicView mv;
    private final MechanicFacade facade;

    /**
     * Costruttore che inizializza la popup e registra il listener
     * sul pulsante di invio per avviare il flusso di collegamento.
     *
     * @param m meccanico corrente (contesto applicativo)
     * @param mv vista principale del meccanico da aggiornare al termine
     * @param facade facciata per l'operazione di link pilota
     */
    public McPopUpPilotHandler(Mechanic m, MechanicView mv, MechanicFacade facade) {
        this.pv = new McPopUpPilotView();
        this.m  = m;
        this.mv = mv;
        this.facade = facade;

        pv.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) { 
            	onSend();
            }
        });
    }

    /**
     * Gestisce il flusso di invio dalla popup:
     * <ol>
     *   <li>Legge i dati del pilota dalla {@link McPopUpPilotView}</li>
     *   <li>Verifica la presenza di un veicolo con MSN valido</li>
     *   <li>Invoca {@link MechanicFacade#linkPilotToVehicle(String, String, String, String, String, String)}</li>
     *   <li>Aggiorna la {@link Session} e la {@link MechanicView} in caso di successo</li>
     *   <li>Gestisce errori di dominio mostrando messaggi nella UI</li>
     * </ol>
     */
    private void onSend() {
        String pilotId = pv.getId().getText();
        String name    = pv.getName().getText().toUpperCase();
        String surname = pv.getSurname().getText().toUpperCase();
        String number  = pv.getNumber().getText();

        Vehicle v = m.getVehicle();
        if (v == null || !v.hasValidMsn()) {
            JOptionPane.showMessageDialog(null,
                "Assign or create a vehicle (with MSN) before linking a pilot.",
                "No vehicle", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String msn = v.getMSN().toUpperCase();

        try {
            facade.linkPilotToVehicle(m.getID(), msn, pilotId, name, surname, number);
            Session.getIstance().setId_pilot(pilotId);
            pv.clearComponents(pv.getDataPanel());
            mv.setId_p();
            pv.close();
        } catch (PilotNotFoundException err) {
            pv.mex();
            pv.clearComponents(pv.getDataPanel());
        }
    }

    /**
     * Mostra la popup per l'inserimento dei dati del pilota.
     */
    public void showWindow() {
    	pv.show();
    }
    
    /**
     * Pulisce i campi della sezione di invio della popup.
     */
    public void clear() { pv.clearComponents(pv.getSendPanel()); }
}
