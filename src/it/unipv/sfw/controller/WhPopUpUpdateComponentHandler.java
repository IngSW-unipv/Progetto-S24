package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

/**
 * Handler per il pop-up di aggiornamento componente.
 * Dipende dal Warehouseman passato dal Controller
 */
public class WhPopUpUpdateComponentHandler {

    private final WhPopUpUpdateComponentView puc;
    private final WarehousemanDAO md;
    private final Warehouseman warehouseman; // per log/contesto

    public WhPopUpUpdateComponentHandler(Warehouseman warehouseman) {
        this.puc = new WhPopUpUpdateComponentView();
        this.md  = new WarehousemanDAO();
        this.warehouseman = warehouseman;

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

    private void onUpdateComponent() {
        String idComp = puc.getId_c().getText();
        String wearStr = puc.getWear().getText();
        String status  = puc.getStatus().getText() != null ? puc.getStatus().getText().toUpperCase().trim(): "";

        try {
            // Validazioni input base
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

            // 1) Verifica esistenza componente
            md.checkCompo(idComp);

            // 2) Aggiorna componente nel DB
            md.updateComponent(idComp, String.valueOf(wear), status);

            // 3) UI + Log
            puc.mex2(); // successo
            md.insertLogEvent(warehouseman.getID(), "UPDATE COMPONENT: " + idComp);

        } catch (ComponentNotFoundException err) {
            puc.mex1(); // errore componente non trovato
        } finally {
            // 4) Pulisci form
            puc.clearComponents(puc.getDataPanel());
        }
    }

    public void showWindow() { puc.show(); }

    public void clear() { puc.clearComponents(puc.getSendPanel()); }
}
