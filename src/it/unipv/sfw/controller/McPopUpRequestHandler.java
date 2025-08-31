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

/**
 * Handler che gestisce l'invio di una richiesta di sostituzione componente
 * tramite la popup {@link McPopUpRequestView}.
 * <p>
 * Il flusso supporta due casi:
 * <ul>
 *   <li><b>Meccanico con veicolo associato</b>: l'MSN è ricavato dal {@link Mechanic}</li>
 *   <li><b>Meccanico senza veicolo associato</b>: l'MSN è inserito manualmente nella popup</li>
 * </ul>
 * La richiesta viene delegata alla {@link MechanicFacade} e gli esiti sono
 * notificati alla view.
 * </p>
 *
 * @see MechanicFacade
 * @see McPopUpRequestView
 * @see Mechanic
 * @see Vehicle
 */
public class McPopUpRequestHandler {

    private final McPopUpRequestView pr;
    private final Mechanic m;
    private final MechanicFacade facade;

    /**
     * Costruttore che inizializza la popup e configura il comportamento
     * in base alla presenza o meno di un veicolo associato al meccanico.
     * <p>
     * Se esiste già un veicolo, la popup viene adattata (titolo/campi) per il caso
     * "veicolo presente".
     * </p>
     *
     * @param m meccanico corrente
     * @param facade facciata per la creazione della richiesta
     */
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

    /**
     * Gestisce l'invio della richiesta dalla popup:
     * <ol>
     *   <li>Legge i campi (id_s, id_c, descrizione)</li>
     *   <li>Determina l'MSN: da UI se non c'è veicolo, altrimenti dal modello</li>
     *   <li>Invoca {@link MechanicFacade#createComponentRequest(String, String, String, String, String)}</li>
     *   <li>Pulisce i campi e mostra messaggio di successo; gestisce errori di dominio</li>
     * </ol>
     *
     * @param hasVehicle {@code true} se il meccanico ha un veicolo associato (MSN noto), altrimenti {@code false}
     */
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

    /**
     * Mostra la popup per la creazione della richiesta.
     */
    public void showWindow() { 
    	pr.show();
    }
    
    /**
     * Pulisce i campi dell'area di invio della popup.
     */
    public void clear() { pr.clearComponents(pr.getSendPanel()); }
}
