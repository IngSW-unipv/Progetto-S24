package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpRequestView;

/**
 * Handler popup per l'inserimento richieste di componenti.
 * Dipende dal Mechanic passato dal Controller
 */
public class McPopUpRequestHandler {

    private final McPopUpRequestView pr;
    private final MechanicDAO md;
    private final Mechanic m;

    public McPopUpRequestHandler(Mechanic m) {
        this.pr = new McPopUpRequestView();
        this.md = new MechanicDAO();
        this.m  = m;

        // Se il meccanico ha già un vehicle assegnato, nascondo il campo MSN nella UI
        boolean hasVehicle = (m.getVehicles() != null);
        if (hasVehicle) {
            pr.hide();   //UI: nasconde il campo id_v
            pr.title();  // UI: cambia titolo quando il veicolo è già presente
        }

        pr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                onSend(hasVehicle);
            }
        });
    }

    private void onSend(boolean hasVehicle) {
        String idS = pr.getId_s().getText();             // id staff digitato
        String idC = pr.getId_c().getText();             // id componente
        String desc = pr.getDesc().getText();            // descrizione richiesta

        // Se non c'è vehicle assegnato, prendo l'MSN dalla UI; altrimenti dal model
        String msn = null;
        if (!hasVehicle) {
            msn = pr.getId_v().getText();                // id_v dalla UI
            if (msn != null) msn = msn.toUpperCase();
        } else {
            Vehicle v = m.getVehicles();
            msn = (v != null && v.getMSN() != null) ? v.getMSN().toUpperCase() : null;
        }

        try {
            // 1) Validazione ID staff
            if (!m.getID().equals(idS)) {
                throw new WrongIDException();
            }

            // 2) Validazioni lato DB
            md.checkIdCompo(idC);
            md.checkRequest(idC);
            if (msn == null || msn.isEmpty()) {
                throw new VehicleNotFoundException("Missing or empty MSN");
            }
            md.checkVehicle(msn);

            // 3) Persistenza richiesta
            md.insertRequest(desc,
                             idS.toUpperCase(),   // standardizziamo in upper
                             idC,
                             msn);

            // 4) UI + log
            pr.clearComponents(pr.getDataPanel());
            pr.mex1();  // UI: messaggio di successo
            md.insertLogEvent(m.getID(), "INSERT REQUEST FOR COMPONENT: " + idC);

        } catch (WrongIDException | ComponentNotFoundException |
                 WrongRequestException | VehicleNotFoundException ex) {
            pr.mex();   // UI: messaggio di errore generico
            System.out.println(ex);
        }
    }

    public void showWindow() { pr.show(); }

    public void clear() { pr.clearComponents(pr.getSendPanel()); }
}
