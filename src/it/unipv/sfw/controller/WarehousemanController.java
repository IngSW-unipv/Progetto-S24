package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.facade.FacadeFactory;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.WarehousemanView;

/**
 * Controller che gestisce il flusso del magazziniere ({@link Warehouseman}).
 * <p>
 * Responsabilità principali:
 * <ul>
 *   <li>Recuperare l'utente corrente dalla {@link Session} e validarne il ruolo</li>
 *   <li>Inizializzare la {@link WarehousemanView} e l'{@link Observable}</li>
 *   <li>Delegare alla {@link WarehousemanFacade} il caricamento delle richieste e le operazioni applicative</li>
 *   <li>Collegare i listener dei controlli UI e notificare la view tramite l'Observable</li>
 * </ul>
 * Il flusso segue lo schema MVC: <i>View -> Controller -> Model</i>.
 * </p>
 *
 * @see WarehousemanView
 * @see WarehousemanFacade
 * @see Observable
 */
public class WarehousemanController extends AbsController {

    private Warehouseman m;     // model utente corrente
    private Observable obs;     // Observable

    //Facade
    private WarehousemanFacade facade;

    /**
     * Restituisce il tipo di controller gestito da questa classe.
     *
     * @return {@link AbsController.TypeController#WAREHOUSEMAN}
     */
    @Override
    public TypeController getType() {
        return TypeController.WAREHOUSEMAN;
    }

    /**
     * Inizializza il controller del magazziniere.
     * <ol>
     *   <li>Recupera e valida l'utente corrente dalla {@link Session}</li>
     *   <li>Crea la {@link WarehousemanView} e l'{@link Observable}</li>
     *   <li>Istanzia la {@link WarehousemanFacade} e carica le richieste dal DB nel modello</li>
     *   <li>Imposta i dati di intestazione nella view e registra quest'ultima come {@link Observer}</li>
     *   <li>Configura gli handler e i listener dei pulsanti della UI</li>
     *   <li>Rende visibile la view e associa la {@link #view} al controller</li>
     * </ol>
     *
     * @throws IllegalStateException se l'utente corrente non è un {@link Warehouseman}
     */
    @Override
    public void initialize() {
        // 1) Utente corrente dalla Session
        Staff user = Session.getIstance().getCurrentUser();
        if (!(user instanceof Warehouseman)) {
            throw new IllegalStateException("L'utente corrente non è un Warehouseman");
        }
        m = (Warehouseman) user;

        // 2) View + Observable
        WarehousemanView mv = new WarehousemanView();
        obs = new Observable();

        // inizializzo la Facade
        facade = FacadeFactory.warehouseman();

        // 3) Carica le richieste dal DB nel Model
        // la Facade NON tocca il Model; aggiorna il controller
        Set<Request> reqs = facade.loadRequests();
        m.setRequest(new HashSet<>(reqs)); 

        // 4) Log di login
        facade.log(m.getID(), "LOGIN");

        // 5) Dati intestazione nella view
        mv.data(
            Session.getIstance().getName(),
            Session.getIstance().getSurname(),
            m.totalRequest()
        );

        // 6) Osservazione della view
        obs.addObserver(mv);

        // 7) Creazione Handler
        WhPopUpDeleteRequestHandler wdrc = new WhPopUpDeleteRequestHandler(m, obs, facade);
        WhPopUpUpdateComponentHandler wupc = new WhPopUpUpdateComponentHandler(m, facade);

        // --- LISTENER BUTTONS ---

        // Mostra richieste
        mv.getShowRequestButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                WhPopUpShowRequestHandler wsrc = new WhPopUpShowRequestHandler(m);
                facade.log(m.getID(), "SHOW REQUEST");

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
                facade.log(m.getID(), "SHOW QUANTITY COMPONENT: " + select);

                if ("- ALL".equals(select)) {
                    mv.mexCombo(facade.countAllComponentsInWarehouse());
                } else {
                    mv.mexCombo(facade.countComponentsInWarehouseByName(select));
                }
            }
        });

        mv.setVisible(true);
        view = mv;
    }
}
