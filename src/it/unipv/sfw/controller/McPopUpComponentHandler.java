package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.facade.AddComponentOutcome;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

/**
 * Handler per la gestione delle operazioni sui componenti di un veicolo
 * tramite popup UI dedicati.
 * <p>
 * Supporta due operazioni principali:
 * <ul>
 *   <li>{@link Operation#ADD}: aggiunta di un componente</li>
 *   <li>{@link Operation#REMOVE}: rimozione di un componente</li>
 * </ul>
 * L'handler coordina le view {@link McPopUpComponentView} e
 * {@link McPopUpRequestView}, interagisce con il modello
 * ({@link Mechanic}, {@link Vehicle}, {@link Components}) e con la
 * {@link MechanicFacade} per l'aggiornamento lato persistenza.
 * </p>
 *
 * @see Mechanic
 * @see Vehicle
 * @see Components
 * @see MechanicFacade
 * @see McPopUpComponentView
 * @see McPopUpRequestView
 */
public class McPopUpComponentHandler {

    /**
     * Operazioni supportate dall'handler sui componenti.
     */
    public enum Operation { ADD, REMOVE }

    private final McPopUpComponentView pc;
    private final McPopUpRequestView pr;
    private final Mechanic m;
    private final Operation op;
    private final MechanicFacade facade;

    private Components c;

    /**
     * Costruttore che inizializza l'handler e registra i listener
     * in base all'operazione richiesta.
     *
     * @param m meccanico corrente (modello sorgente dei dati)
     * @param op operazione da eseguire ({@link Operation#ADD} o {@link Operation#REMOVE})
     * @param facade facciata applicativa per delegare le operazioni persistenti
     */
    public McPopUpComponentHandler(Mechanic m, Operation op, MechanicFacade facade) {
        this.pc = new McPopUpComponentView();
        this.pr = new McPopUpRequestView();
        this.m  = m;
        this.op = op;
        this.facade = facade;

        if (op == Operation.ADD) {
            setupAddComponentListener();
        } else {
            setupRemoveComponentListener();
        }
    }

    // ---------- ADD ----------

    /**
     * Registra il listener per il bottone di invio nella modalità aggiunta componente.
     * <p>
     * Alla pressione, viene invocata {@link #handleAddComponent()} che
     * gestisce l'intero flusso di validazione, aggiornamento modello e persistenza.
     * </p>
     */
    private void setupAddComponentListener() {
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
					handleAddComponent();
				} catch (DuplicateComponentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    /**
     * Gestisce l'aggiunta di un componente al veicolo del meccanico:
     * <ol>
     *   <li>Verifica la presenza di un veicolo con MSN valido</li>
     *   <li> input UI (id, nome, stato) e costruisce {@link Components}</li>
     *   <li>Aggiorna il modello (calcolo wear/esito) tramite {@link Mechanic#addComponent(Vehicle, Components)}</li>
     *   <li>Invoca la {@link MechanicFacade} per la persistenza</li>
     *   <li>Gestisce gli esiti ({@link AddComponentOutcome}) e le anomalie</li>
     * </ol>
     *
     * @throws DuplicateComponentException se viene rilevato un duplicato
     */
    private void handleAddComponent() throws DuplicateComponentException {
        Vehicle v = ensureVehicleWithMSN();
        if (v == null) return;

        try {
            String idCStr = pc.getIdC().getText();
            String name   = pc.getNameC().getText().toUpperCase();
            String status = pc.getStatusC().getText().toUpperCase();

            int idc = Integer.parseInt(idCStr);
            c = new Components(idc, name);
            c.setReplacementStatus(status);

            // calcola wear/esito
            int result = m.addComponent(v, c); // 1/2 ok, 3 worn

            var res = facade.addComponent(
                m.getID(),
                v.getMSN().toUpperCase(),
                c.getIdComponent(),
                c.getName().toUpperCase(),
                c.getReplacementStatus().toUpperCase(),
                c.getWear()
            );

            switch (res.getOutcome()) {
                case INSERTED_OK -> {
                    pc.mex2();
                    pc.clearComponents(pc.getDataPanel());
                }
                case NEEDS_REPLACEMENT -> {
                    if (result == 3) {
                        handleComponentReplacementRequest(v);
                    } else {
                        pc.mex1();
                        pc.clearComponents(pc.getDataPanel());
                    }
                }
                default -> {
                    pc.mex1();
                    pc.clearComponents(pc.getDataPanel());
                }
            }

        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Avvia richiesta sostituzione componente:
     * <ol>
     *   <li>Nasconde la popup di inserimento</li>
     *   <li>Mostra la popup di richiesta sostituzione</li>
     *   <li>Precompila l'id componente</li>
     *   <li>Registra il listener per inviare la richiesta via {@link MechanicFacade}</li>
     * </ol>
     *
     * @param v veicolo corrente, già validato
     */
    private void handleComponentReplacementRequest(Vehicle v) {
        pc.hide();
        pr.show();
        pr.setId_c(pc.getIdC()); // precompila id componente

        pr.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    facade.createComponentRequest(
                        m.getID(),
                        pr.getId_s().getText(),
                        pc.getIdC().getText(),
                        v.getMSN().toUpperCase(),
                        pr.getDesc().getText().toUpperCase()
                    );
                    // opzionale
                    // pr.mex1();
                } catch (Exception ex) {
                    pr.mex();
                    System.out.println(ex);
                }
            }
        });
    }

    // ---------- REMOVE ----------

    /**
     * Configura la UI per la rimozione del componente:
     * <ul>
     *   <li>Nasconde i campi non necessari</li>
     *   <li>Registra il listener che invoca {@link #handleRemoveComponent()}</li>
     * </ul>
     */
    private void setupRemoveComponentListener() {
        pc.hideField();
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveComponent();
            }
        });
    }

    /**
     * Gestisce la rimozione di un componente dal veicolo:
     * <ol>
     *   <li>Verifica la presenza di veicolo con MSN valido</li>
     *   <li> input (id, nome)</li>
     *   <li>Invoca la {@link MechanicFacade#removeComponent(String, String, int, String)}</li>
     *   <li>Aggiorna il modello locale e notifica la UI</li>
     * </ol>
     * Gestisce errori di conversione (ID non numerico) e componenti non trovati.
     */
    private void handleRemoveComponent() {
        Vehicle v = ensureVehicleWithMSN();
        if (v == null) return;

        try {
            String idCStr = pc.getIdC().getText();
            String name   = pc.getNameC().getText().toUpperCase();

            int id = Integer.parseInt(idCStr);

            facade.removeComponent(
                m.getID(),
                v.getMSN().toUpperCase(),
                id,
                name
            );

            // model update
            c = new Components(id, name);
            v.removeComponent(c);

            pc.mex3();
        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        pc.clearComponents(pc.getDataPanel());
    }

    // ---------- Helpers SOLO UI ----------

    /**
     * Verifica che esista un veicolo associato al meccanico con MSN valido.
     * In caso contrario mostra un messaggio
     *
     * @return il {@link Vehicle} valido oppure null se assente/non valido e mostra messaggio warning
     */
    private Vehicle ensureVehicleWithMSN() {
        Vehicle v = m.getVehicles();
        if (v == null || !v.hasValidMsn()) {
            JOptionPane.showMessageDialog(
                null,
                "Assign/create a vehicle (with MSN) before managing components.",
                "No vehicle", JOptionPane.WARNING_MESSAGE
            );
            return null;
        }
        return v;
    }

    /**
     * Mostra la popup principale di gestione componenti.
     */
    public void showWindow() { 
    	pc.show();
    }

    /**
     * Pulisce i campi della popup di invio.
     */
    public void clear() { pc.clearComponents(pc.getSendPanel()); }
}
