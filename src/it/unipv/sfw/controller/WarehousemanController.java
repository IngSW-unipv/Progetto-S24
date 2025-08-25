package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.WarehousemanView;

/**
 * Controller che gestisce il flusso del magazziniere (Warehouseman).
 * View -> Controller -> Model(+DAO)
 */
public class WarehousemanController extends AbsController {

    private Warehouseman m;     // model utente corrente
    private Observable obs;     // Observable

    @Override
    public TypeController getType() {
        return TypeController.WAREHOUSEMAN;
    }

    @Override
    public void initialize() {
        // 1) Utente corrente dalla Session
        Staff user = Session.getIstance().getCurrentUser();
        if (!(user instanceof Warehouseman)) {
            throw new IllegalStateException("L'utente corrente non è un Warehouseman");
        }
        m = (Warehouseman) user;

        // 2) View + DAO + Observable
        WarehousemanView mv = new WarehousemanView();
        WarehousemanDAO md = new WarehousemanDAO();
        obs = new Observable();

        // 3) Carica le richieste dal DB nel Model
        Set<Request> reqs = md.selectAllRequest();
        m.setRequest(reqs);

        // 4) Log di login
        md.insertLogEvent(m.getID(), "LOGIN");

        // 5) Dati intestazione nella view (name/surname ancora in Session come metadati UI)
        mv.data(
            Session.getIstance().getName(),
            Session.getIstance().getSurname(),
            m.totalRequest()
        );

        // 6) Osservazione della view
        obs.addObserver(mv);

        // 7) Handlers con DI del model/observable
        WhPopUpDeleteRequestHandler wdrc = new WhPopUpDeleteRequestHandler(m, obs);
        WhPopUpUpdateComponentHandler wupc = new WhPopUpUpdateComponentHandler(m); 

        // --- LISTENER BUTTONS ---

        // Mostra richieste
        mv.getShowRequestButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                WhPopUpShowRequestHandler wsrc = new WhPopUpShowRequestHandler(m);
                md.insertLogEvent(m.getID(), "SHOW REQUEST");

                wsrc.showWindow();
                mv.setMex();
            }
        });

        // Elimina richiesta
        mv.getDeleteRequestButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                wdrc.showWindow();
                wdrc.clear();
                mv.setMex();
            }
        });

        // Aggiorna componente (stato/usura)
        mv.getUpdateCompoButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                wupc.showWindow();
                mv.setMex();
            }
        });

        // Combo quantità componenti
        mv.getCombobox().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                String select = (String) mv.getCombobox().getSelectedItem();
                md.insertLogEvent(m.getID(), "SHOW QUANTITY COMPONENT: " + select);

                if ("- ALL".equals(select)) {
                    mv.mexCombo(md.countElement());
                } else {
                    mv.mexCombo(md.countElementBySelect(select));
                }
            }
        });

        mv.setVisible(true);
        view = mv;
    }
}
