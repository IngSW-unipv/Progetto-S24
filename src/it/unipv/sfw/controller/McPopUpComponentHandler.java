package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

/**
 * Gestisce la finestra di popup per l'aggiunta o la rimozione di componenti.
 * Consente l'inserimento e la gestione delle richieste di sostituzione componenti.
 */
public class McPopUpComponentHandler {

    private McPopUpComponentView pc;
    private McPopUpRequestView pr;
    private MeccanicoDAO md;
    private Components c;
    
    /**
     * Costruttore della classe McPopUpComponentHandler.
     * Inizializza le viste e il DAO per la gestione dei componenti.
     */
    public McPopUpComponentHandler() {
        pc = new McPopUpComponentView();
        pr = new McPopUpRequestView();
        md = new MeccanicoDAO();
        
        if (Session.getIstance().getOperation().equals("ADD")) {
            setupAddComponentListener();
        } else {
            setupRemoveComponentListener();
        }
    }

    /**
     * Imposta il listener per l'aggiunta di un componente.
     */
    private void setupAddComponentListener() {
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddComponent();
            }
        });
    }

    /**
     * Gestisce l'aggiunta di un componente.
     */
    private void handleAddComponent() {
        int n = 0, idc = 0;
        try {
            md.checkCompo(pc.getIdC().getText(), pc.getNameC().getText().toUpperCase(), pc.getStatusC().getText().toUpperCase());
            idc = Integer.parseInt(pc.getIdC().getText());
            c = new Components(idc, pc.getNameC().getText().toUpperCase());
            c.setReplacementStatus(pc.getStatusC().getText().toUpperCase());
            n = addCompo();
            handleAddComponentResult(n);
        } catch (ComponentNotFoundException err) {
            System.out.println(err);
            pc.mex();
        } catch (DuplicateComponentException err) {
            System.out.println(err);
            pc.mex1();
        }
    }

    /**
     * Gestisce i risultati dell'aggiunta di un componente.
     */
    private void handleAddComponentResult(int result) {
        switch (result) {
            case 0:
                pc.mex1();
                pc.clearComponents(pc.getDataPanel());
                break;
            case 1:
            case 2:
                if (md.insertComponent(pc.getIdC().getText(), fetchMSN())) {
                    md.updateWear(c.getWear(), pc.getIdC().getText());
                    pc.mex2();
                    pc.clearComponents(pc.getDataPanel());
                }
                break;
            case 3:
                handleComponentReplacementRequest();
                break;
        }
    }

    /**
     * Gestisce la richiesta di sostituzione di un componente.
     */
    private void handleComponentReplacementRequest() {
        pc.hide();
        pr.show();
        pr.setId_c(pc.getIdC());
        pr.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(), pc.getIdC().getText(), pr.getId_v().getText().toUpperCase());
            }
        });
        md.insertLogEvent(getID(), "INSERT COMPONENT ID: " + pc.getIdC().getText());
    }

    /**
     * Imposta il listener per la rimozione di un componente.
     */
    private void setupRemoveComponentListener() {
        pc.hideField();
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveComponent();
            }
        });
    }

    /**
     * Gestisce la rimozione di un componente.
     */
    private void handleRemoveComponent() {
        try {
            md.checkCompo(pc.getIdC().getText().toUpperCase(), pc.getNameC().getText().toUpperCase(), "USED");
            md.removeComponent(pc.getIdC().getText().toUpperCase(), fetchMSN());
            int id = Integer.parseInt(pc.getIdC().getText());
            c = new Components(id, pc.getNameC().getText().toUpperCase());
            Session.getIstance().getM().removeComponent(Session.getIstance().getV(), c);
            pc.mex3();
            md.insertLogEvent(getID(), "REMOVE COMPONENT ID: " + pc.getIdC().getText());
        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        }
        pc.clearComponents(pc.getDataPanel());
    }

    /**
     * Recupera il numero di serie del veicolo.
     */
    private String fetchMSN() {
        return Session.getIstance().getV().getMSN().toUpperCase();
    }

    /**
     * Recupera l'ID dello staff attualmente loggato.
     */
    private String getID() {
        return Session.getIstance().getId_staff();
    }

    /**
     * Aggiunge un componente al veicolo.
     * 
     * @return Il codice del risultato dell'operazione.
     */
    private int addCompo() throws DuplicateComponentException {
        return Session.getIstance().getM().addComponent(Session.getIstance().getV(), c);
    }

    /**
     * Mostra la finestra di gestione componenti.
     */
    public void showWindow() {
        pc.show();
    }

    /**
     * Pulisce la vista dai dati precedenti.
     */
    public void clear() {
        pc.clearComponents(pc.getSendPanel());
    }
}
