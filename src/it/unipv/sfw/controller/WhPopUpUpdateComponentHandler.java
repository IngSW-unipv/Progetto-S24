package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

/**
 * Handler che gestisce il pop-up di aggiornamento di un {@link it.unipv.sfw.model.component.Components}.
 * <p>
 * Si occupa di:
 * <ul>
 *   <li>Recuperare gli input dalla view {@link WhPopUpUpdateComponentView}</li>
 *   <li>Validare i dati lato UI (id componente, wear, status)</li>
 *   <li>Delegare le operazioni di dominio/persistenza alla {@link WarehousemanFacade}</li>
 *   <li>Mostrare messaggi di esito e pulire i campi della UI</li>
 * </ul>
 * </p>
 *
 * @see Warehouseman
 * @see WarehousemanFacade
 * @see WhPopUpUpdateComponentView
 */
public class WhPopUpUpdateComponentHandler {

    private final WhPopUpUpdateComponentView puc;
    private final Warehouseman warehouseman; // contesto per log e ID

    /**
     * Facade che incapsula la logica applicativa e la persistenza.
     */
    private final WarehousemanFacade facade;

    /**
     * Costruttore che inizializza la popup e registra i listener sugli eventi UI.
     *
     * @param warehouseman il magazziniere corrente
     * @param facade la facciata per gestire l'aggiornamento dei componenti
     */
    public WhPopUpUpdateComponentHandler(Warehouseman warehouseman, WarehousemanFacade facade) {
        this.puc = new WhPopUpUpdateComponentView();
        this.warehouseman = warehouseman;
        this.facade = facade;

        wireEvents();
    }

    /**
     * Registra i listener sugli elementi della view.
     * In particolare, il pulsante di invio è associato a {@link #onUpdateComponent()}.
     */
    private void wireEvents() {
        puc.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                onUpdateComponent();
            }
        });
    }

    /**
     * Gestisce l'evento di aggiornamento componente.
     * <ol>
     *   <li>Recupera input da UI (id, wear, status)</li>
     *   <li>Esegue validazioni lato UI (campi obbligatori, range wear, valori status ammessi)</li>
     *   <li>Invoca {@link WarehousemanFacade#updateComponent(String, int, String, String)} per validazione e persistenza</li>
     *   <li>Mostra messaggi di esito nella view</li>
     *   <li>Pulisce il form</li>
     * </ol>
     */
    private void onUpdateComponent() {
        String idComp = puc.getId_c().getText();
        String wearStr = puc.getWear().getText();
        String status  = puc.getStatus().getText() != null ? puc.getStatus().getText().toUpperCase().trim(): "";

        try {
            // Validazioni input base (UI)
            if (idComp == null || idComp.isBlank()) {
                JOptionPane.showMessageDialog(null, "Inserire ID componente.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int wear;
            try {
                wear = Integer.parseInt(wearStr);
                if (wear < 0 || wear > 100) {
                    JOptionPane.showMessageDialog(null, "Wear deve essere tra 0 e 100.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Wear non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!status.equals("NEW") && !status.equals("USED")) {
                JOptionPane.showMessageDialog(null, "Status deve essere NEW o USED.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validazioni e persistenza via Facade
            facade.updateComponent(idComp, wear, status, warehouseman.getID());

            // Successo
            puc.mex2();

        } catch (ComponentNotFoundException err) {
            puc.mex1(); // errore: componente non trovato
        } finally {
            // Pulizia form
            puc.clearComponents(puc.getDataPanel());
        }
    }

    /**
     * Mostra la finestra popup.
     */
    public void showWindow() {
    	puc.show();
    }

    /**
     * Pulisce i campi dell'area di invio della popup.
     */
    public void clear() { puc.clearComponents(puc.getSendPanel()); }
}
