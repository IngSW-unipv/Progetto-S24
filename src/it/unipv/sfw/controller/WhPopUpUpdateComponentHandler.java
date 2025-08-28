package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

/**
 * Handler per il pop-up di aggiornamento componente.
 * Dipende dal Warehouseman passato dal Controller
 */
public class WhPopUpUpdateComponentHandler {

    private final WhPopUpUpdateComponentView puc;
    private final Warehouseman warehouseman; // per log/contesto

    // Facade
    private final WarehousemanFacade facade;

    public WhPopUpUpdateComponentHandler(Warehouseman warehouseman, WarehousemanFacade facade) {
        this.puc = new WhPopUpUpdateComponentView();
        this.warehouseman = warehouseman;
        this.facade = facade;

        wireEvents();
    }

    private void wireEvents() {
        puc.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                onUpdateComponent();
            }
        });
    }

    /**
     * Gestione dell'evento di aggiornamento componente.
     *
     * Flusso:
     *  1) Recupera input da UI (id componente, wear, status).
     *  2) Esegue validazioni (UI).
     *  3) Delega alla Facade le validazioni
     *  4) Mostra esito nella view e pulisce i campi.
     *
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

            // 1) Validazioni/persistenza/log via Facade
            facade.updateComponent(idComp, wear, status, warehouseman.getID());

            // 2) UI (successo)
            puc.mex2();

        } catch (ComponentNotFoundException err) {
            puc.mex1(); // errore componente non trovato
        } finally {
            // 3) Pulisci form
            puc.clearComponents(puc.getDataPanel());
        }
    }

    public void showWindow() {
    	puc.show();
    }

    public void clear() { puc.clearComponents(puc.getSendPanel()); }
}
