package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpVehicleView;
import it.unipv.sfw.view.MeccanicoView;

/**
 * Controller per la finestra di pop-up per l'inserimento di un veicolo.
 * Gestisce le interazioni dell'utente con la {@link McPopUpVehicleView} e
 * interagisce con il {@link MeccanicoDAO} per aggiornare il database.
 */
public class McPopUpVehicleHandler {

    private McPopUpVehicleView vv;
    private MeccanicoDAO md;

    /**
     * Costruttore per McPopUpVehicleHandler.
     *
     * @param mv La {@link MeccanicoView} associata a questo handler.
     */
    public McPopUpVehicleHandler(MeccanicoView mv) {

        vv = new McPopUpVehicleView();
        md = new MeccanicoDAO();

        vv.getSendButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String id_pilot = vv.getId_p().getText(), msn = vv.getMsn().getText().toUpperCase();

                try {
                    md.checkPilot(id_pilot);
                    md.checkVehicle(msn);

                    md.insertPilotOnVehicle(id_pilot, msn);
                    Session.getIstance().setId_pilot(id_pilot);

                    mv.setId_p();

                    String id = Session.getIstance().getId_staff();
                    md.insertMeccOnVehicle(msn, id);

                    Session.getIstance().getM().setMSN(msn);

                    Session.getIstance().getV().setMSN(msn);
                    Session.getIstance().setV(addVehicle());

                    updateButtonStates(mv);
                    md.insertLogEvent(getID(), "INSERT VEHICLE : " + vv.getMsn().getText().toUpperCase());

                    mv.getInsertVehicleButton().setEnabled(false);
                    vv.close();
                } catch (PilotNotFoundException | VehicleNotFoundException ev) {
                    System.out.println(ev);
                    vv.mex();
                    return;
                }

            }

        });

    }

    /**
     * Aggiorna lo stato (abilitato/disabilitato) e la visibilità di alcuni
     * pulsanti nell'interfaccia {@link MeccanicoView}, in base alla presenza
     * di un veicolo nella sessione.
     *
     * @param mv La {@link MeccanicoView} da aggiornare.
     */
    private void updateButtonStates(MeccanicoView mv) {
        boolean isVehiclePresent = (Session.getIstance().getV() != null);

        mv.getAddComponentButton().setEnabled(isVehiclePresent);
        mv.getAddComponentButton().setVisible(true);

        mv.getAddPilotButton().setEnabled(isVehiclePresent);
        mv.getAddPilotButton().setVisible(true);

        mv.getRemovePilotButton().setEnabled(isVehiclePresent);
        mv.getRemovePilotButton().setVisible(true);

        mv.getRemoveComponentButton().setEnabled(isVehiclePresent);
        mv.getRemoveComponentButton().setVisible(true);

        mv.getVisualTimePsButton().setEnabled(isVehiclePresent);
        mv.getVisualTimePsButton().setVisible(true);
    }

    /**
     * Aggiunge un veicolo alla sessione.
     * @return Il veicolo aggiunto.
     */
    private Vehicle addVehicle() {
        return Session.getIstance().getM().addVehicle();
    }

    /**
     * Recupera l'ID del membro dello staff dalla sessione.
     * @return L'ID del membro dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }

    /**
     * Mostra la finestra di pop-up.
     */
    public void showWindow() {
        vv.show();
    }

    /**
     * Pulisce i componenti del pannello di invio.
     */
    public void clear() {
        vv.clearComponents(vv.getSendPanel());
    }

}