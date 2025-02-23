package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

/**
 * Controller per la finestra di pop-up di aggiornamento componente.
 * Gestisce le interazioni dell'utente con la {@link WhPopUpUpdateComponentView}
 * e coordina le azioni con il {@link WarehousemanDAO}.
 */
public class WhPopUpUpdateComponentHandler {

    private WhPopUpUpdateComponentView puc;
    private WarehousemanDAO md;

    /**
     * Costruttore per WhPopUpUpdateComponentHandler.
     */
    public WhPopUpUpdateComponentHandler() {
        puc = new WhPopUpUpdateComponentView();
        md = new WarehousemanDAO();

        puc.getSendButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    md.checkCompo(puc.getId_c().getText());
                    md.updateComponent(puc.getId_c().getText(), puc.getWear().getText(),
                            puc.getStatus().getText().toUpperCase());
                    puc.mex2(); // Mostra messaggio di successo
                    md.insertLogEvent(getID(), "UPDATE COMPONENT: " + puc.getId_c().getText());

                } catch (ComponentNotFoundException err) {
                    System.out.println(err);
                    puc.mex1(); // Mostra messaggio di errore
                }

                puc.clearComponents(puc.getDataPanel()); // Pulisce i campi del form
            }

        });

    }

    /**
     * Mostra la finestra di pop-up per l'aggiornamento del componente.
     */
    public void showWindow() {
        puc.show();
    }

    /**
     * Recupera l'ID del membro dello staff dalla sessione.
     * @return L'ID del membro dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }

}