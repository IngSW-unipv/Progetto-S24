package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

/**
 * Gestisce il popup per Aggiungere o Rimuovere componenti.
 * Dipende dal Mechanic passato dal Controller (no Model in Session).
 */
public class McPopUpComponentHandler {

    public enum Operation { ADD, REMOVE }

    private final McPopUpComponentView pc;
    private final McPopUpRequestView pr;   // usata solo quando serve una richiesta
    private final MechanicDAO md;
    private final Mechanic m;
    private final Operation op;

    private Components c; // componente corrente (costruito dalla UI)

    public McPopUpComponentHandler(Mechanic m, Operation op) {
        this.pc = new McPopUpComponentView();
        this.pr = new McPopUpRequestView();
        this.md = new MechanicDAO();
        this.m  = m;
        this.op = op;

        if (op == Operation.ADD) {
            setupAddComponentListener();
        } else {
            setupRemoveComponentListener();
        }
    }

    // ---------- ADD ----------

    private void setupAddComponentListener() {
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                handleAddComponent();
            }
        });
    }

    private void handleAddComponent() {
        Vehicle v = ensureVehicleWithMSN();
        if (v == null) return; // messaggio già mostrato

        try {
            // Validazioni lato DB su id/nome/stato dichiarato nella UI
            String idCStr = pc.getIdC().getText();
            String name   = pc.getNameC().getText().toUpperCase();
            String status = pc.getStatusC().getText().toUpperCase();

            md.checkCompo(idCStr, name, status);

            int idc = Integer.parseInt(idCStr);
            c = new Components(idc, name);
            c.setReplacementStatus(status);

            // Update MODEL: prova ad aggiungere il componente al Vehicle
            int result = m.addComponent(v, c);

            // Persist/UX in base all’esito
            handleAddComponentResult(result, v);

        } catch (ComponentNotFoundException err) {
            pc.mex(); // tuo errore generico
            System.out.println(err);
        } catch (DuplicateComponentException err) {
            pc.mex1(); // tuo messaggio "duplicato"
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddComponentResult(int result, Vehicle v) {
        // Convenzione: 1=ottime, 2=buone, 3=usurato (da tua Vehicle.addComponent corretta)
        switch (result) {
            case 1:
            case 2:
                // Persist nel DB + wear aggiornato
                if (md.insertComponent(String.valueOf(c.getIdComponent()), v.getMSN().toUpperCase())) {
                    md.updateWear(c.getWear(), String.valueOf(c.getIdComponent()));
                    pc.mex2(); // tuo "inserito con successo"
                    pc.clearComponents(pc.getDataPanel());
                }
                md.insertLogEvent(m.getID(), "INSERT COMPONENT ID: " + c.getIdComponent());
                break;

            case 3:
                // componente usurato -> avvia richiesta sostituzione
                handleComponentReplacementRequest(v);
                md.insertLogEvent(m.getID(), "REQUEST REPLACEMENT FOR COMPONENT ID: " + c.getIdComponent());
                break;

            default:
                // fallback (in caso il tuo Vehicle ritorni 0 in qualche condizione)
                pc.mex1(); // messaggio neutro
                pc.clearComponents(pc.getDataPanel());
                break;
        }
    }

    private void handleComponentReplacementRequest(Vehicle v) {
        pc.hide();
        pr.show();
        pr.setId_c(pc.getIdC()); // precompila id componente

        pr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // Inserisci richiesta usando direttamente l’MSN del Vehicle corrente
                md.insertRequest(
                    pr.getDesc().getText(),
                    pr.getId_s().getText().toUpperCase(),
                    pc.getIdC().getText(),
                    v.getMSN().toUpperCase()
                );
                // opzionale: feedback UI
                // pr.mex1(); // se hai un "success" per la request
            }
        });
    }

    // ---------- REMOVE ----------

    private void setupRemoveComponentListener() {
        pc.hideField(); // nasconde campi non necessari per remove (coerente con tua UI)
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                handleRemoveComponent();
            }
        });
    }

    private void handleRemoveComponent() {
        Vehicle v = ensureVehicleWithMSN();
        if (v == null) return;

        try {
            String idCStr = pc.getIdC().getText();
            String name   = pc.getNameC().getText().toUpperCase();

            // Validazione: il componente esiste ed è USED
            md.checkCompo(idCStr.toUpperCase(), name, "USED");

            // Persist: rimozione dal DB
            md.removeComponent(idCStr.toUpperCase(), v.getMSN().toUpperCase());

            // Update MODEL: rimozione dal Vehicle
            int id = Integer.parseInt(idCStr);
            c = new Components(id, name);
            v.removeComponent(c); // Vehicle rimuove per name o equals/hashCode

            pc.mex3(); // tuo "rimosso con successo"
            md.insertLogEvent(m.getID(), "REMOVE COMPONENT ID: " + idCStr);

        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        pc.clearComponents(pc.getDataPanel());
    }

    // ---------- Helpers ----------

    private Vehicle ensureVehicleWithMSN() {
        Vehicle v = m.getVehicles();
        if (v == null || v.getMSN() == null || v.getMSN().isBlank()) {
            JOptionPane.showMessageDialog(
                null,
                "Assign/create a vehicle (with MSN) before managing components.",
                "No vehicle", JOptionPane.WARNING_MESSAGE
            );
            return null;
        }
        return v;
    }

    public void showWindow() { pc.show(); }

    public void clear() { pc.clearComponents(pc.getSendPanel()); }
}
