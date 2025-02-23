package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.MagazziniereView;

/**
 * Controller che gestisce il processo di gestione del magazziniere nella {@link MagazziniereView}.
 * Si occupa della gestione delle richieste e della visualizzazione delle informazioni relative al magazzino.
 * 
 * @see AbsController
 * @see it.unipv.sfw.view.MagazziniereView
 */
public class MagazziniereController extends AbsController {
    private Staff user;
    private Warehouseman m;
    private Observable obs;

    /**
     * Restituisce il tipo di controller.
     * 
     * @return Il tipo di controller, in questo caso {@link TypeController#MAGAZZINIERE}.
     */
    @Override
    public TypeController getType() {
        return TypeController.MAGAZZINIERE;
    }

    /**
     * Inizializza il controller creando la vista del magazziniere e impostando i listener
     * per la gestione dei bottoni.
     */
    @Override
    public void initialize() {
        try {
            user = Session.getIstance().getCurrentUser();
            m = (Warehouseman) user;
        } catch (Exception e) {
            System.out.println("Errore");
        }

        obs = new Observable();

        MagazziniereView mv = new MagazziniereView();
        WarehousemanDAO md = new WarehousemanDAO();

        WhPopUpDeleteRequestHandler wdrc = new WhPopUpDeleteRequestHandler(obs);
        WhPopUpUpdateComponentHandler wupc = new WhPopUpUpdateComponentHandler();

        Session.getIstance().getRequest();
        md.insertLogEvent(getID(), "LOGIN");

        mv.data(Session.getIstance().getName(), Session.getIstance().getSurname(),
                Session.getIstance().getWh().totalRequest());
        obs.addObserver(mv);

        mv.getShowRequestButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WhPopUpShowRequestHandler wsrc = new WhPopUpShowRequestHandler();
                md.insertLogEvent(getID(), "SHOW REQUEST");

                System.out.println(Session.getIstance().getWh().getRequest());

                for (Request r : m.getRequest()) {
                    System.out.println(r);
                }

                wsrc.showWindow();
                mv.setMex();
            }
        });

        mv.getDeleteRequestButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wdrc.showWindow();
                wdrc.clear();
                mv.setMex();
            }
        });

        mv.getUpdateCompoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wupc.showWindow();
                mv.setMex();
            }
        });

        mv.getCombobox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String select = (String) mv.getCombobox().getSelectedItem();
                md.insertLogEvent(getID(), "SHOW QUANTITY COMPONENT: " + select);

                if (select.equals("- ALL")) {
                    mv.mexCombo(md.countElement());
                } else {
                    mv.mexCombo(md.countElementBySelect(select));
                }
            }
        });

        mv.setVisible(true);
        view = mv;
    }
    
    /**
     * Restituisce l'ID dello staff attualmente autenticato.
     * 
     * @return Una stringa contenente l'ID dello staff.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }
}
