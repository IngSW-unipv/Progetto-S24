package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpRequestView;

/**
 * Controller per la finestra di pop-up per le richieste di componenti.
 * Questa classe gestisce le interazioni dell'utente con la
 * {@link McPopUpRequestView} e interagisce con il {@link MechanicDAO}
 * per inserire le richieste nel database.
 */
public class McPopUpRequestHandler {

    private McPopUpRequestView pr;
    private MechanicDAO md;

    /**
     * Costruttore per McPopUpRequestHandler.
     */
    public McPopUpRequestHandler() {

        pr = new McPopUpRequestView();
        md = new MechanicDAO();

        System.out.println("OPERATION: " + Session.getIstance().getOperation());

        if (Session.getIstance().getOperation().equals("NO_V")) {

            pr.getSendButton().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        if (!pr.getId_s().getText().equals(Session.getIstance().getId_staff()))
                            throw new WrongIDException();

                    } catch (WrongIDException err) {
                        pr.mex();
                        System.out.println(err);
                        return;

                    }

                    try {
                        md.checkIdCompo(pr.getId_c().getText());
                        md.checkRequest(pr.getId_c().getText());

                        md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(),
                                pr.getId_c().getText(), pr.getId_v().getText().toUpperCase());
                        pr.clearComponents(pr.getDataPanel());
                        pr.mex1();

                        md.insertLogEvent(getID(), "INSERT REQUEST FOR COMPONENT: " + pr.getId_c().getText());

                    } catch (ComponentNotFoundException | WrongRequestException err) {

                        pr.mex();
                        System.out.println(err);

                    }

                    try {

                        md.checkVehicle(pr.getId_v().getText().toUpperCase());

                    } catch (VehicleNotFoundException err) {
                        pr.mex();
                        System.out.println(err);
                        return;

                    }

                    pr.clearComponents(pr.getDataPanel());

                }
            });

        } else {

            pr.hide();
            pr.title();

            pr.getSendButton().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        if (!pr.getId_s().getText().equals(Session.getIstance().getId_staff()))
                            throw new WrongIDException();

                    } catch (WrongIDException err) {
                        pr.mex();
                        System.out.println(err);
                        return;

                    }

                    try {
                        md.checkIdCompo(pr.getId_c().getText());
                        md.checkRequest(pr.getId_c().getText());

                        md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(),
                                pr.getId_c().getText(), fetchMSN());
                        pr.clearComponents(pr.getDataPanel());
                        pr.mex1();
                        md.insertLogEvent(getID(), "INSERT REQUEST FOR COMPONENT: " + pr.getId_c().getText());

                    } catch (ComponentNotFoundException | WrongRequestException err) {

                        pr.mex();
                        System.out.println(err);

                    }
                }
            });
        }

    }

    /**
     * Recupera l'MSN del veicolo dalla sessione.
     * @return L'MSN del veicolo.
     */
    private String fetchMSN() {
        return Session.getIstance().getV().getMSN().toUpperCase();
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
        pr.show();
    }

    /**
     * Pulisce i componenti del pannello di invio.
     */
    public void clear() {
        pr.clearComponents(pr.getSendPanel());
    }

}