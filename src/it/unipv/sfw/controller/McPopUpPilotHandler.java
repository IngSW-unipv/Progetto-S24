package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MeccanicoView;

/**
 * Controller per la finestra di pop-up utilizzata dai meccanici per assegnare
 * piloti ai veicoli.
 * Questa classe gestisce le interazioni dell'utente con la
 * {@link McPopUpPilotView} e interagisce
 * con il {@link MechanicDAO} per aggiornare il database.
 */
public class McPopUpPilotHandler {

    private McPopUpPilotView pv;
    private MechanicDAO md;

    /**
     * Costruttore per il McPopUpPilotHandler.
     *
     * @param mv La {@link MeccanicoView} associata a questo handler.
     */
    public McPopUpPilotHandler(MeccanicoView mv) {

        pv = new McPopUpPilotView();
        md = new MechanicDAO();

        pv.getSendButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    md.selectP(pv.getId().getText(), pv.getName().getText().toUpperCase(),
                            pv.getSurname().getText().toUpperCase(), pv.getNumber().getText());
                    md.insertPilotOnVehicle(pv.getId().getText(), fetchMSN());

                    Session.getIstance().setId_pilot(pv.getId().getText());
                    pv.clearComponents(pv.getDataPanel());

                    mv.setId_p();
                    pv.close();

                    md.insertLogEvent(getID(), "INSERT ID PILOT : " + pv.getId().getText());

                } catch (PilotNotFoundException err) {
                    System.out.println(err);
                    pv.mex();
                    pv.clearComponents(pv.getDataPanel());
                }

            }
        });

    }

    /**
     * Recupera l'ID del membro dello staff attualmente loggato.
     * Questo metodo fornisce information hiding accedendo all'istanza di Sessione.
     *
     * @return L'ID del membro dello staff loggato.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }

    /**
     * Recupera l'MSN del veicolo.
     * Questo metodo fornisce information hiding accedendo all'istanza di Sessione.
     *
     * @return L'MSN del veicolo.
     */
    private String fetchMSN() {
        return Session.getIstance().getV().getMSN().toUpperCase();
    }

    /**
     * Mostra la finestra di pop-up per l'assegnazione dei piloti.
     */
    public void showWindow() {
        pv.show();
    }

    /**
     * Cancella i dati nella finestra di pop-up.
     */
    public void clear() {
        pv.clearComponents(pv.getSendPanel());
    }

}