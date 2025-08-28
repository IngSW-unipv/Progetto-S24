package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.facade.AddComponentOutcome;
import it.unipv.sfw.facade.MechanicFacade;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpComponentHandler {

    public enum Operation { ADD, REMOVE }

    private final McPopUpComponentView pc;
    private final McPopUpRequestView pr;
    private final Mechanic m;
    private final Operation op;
    private final MechanicFacade facade;

    private Components c;

    public McPopUpComponentHandler(Mechanic m, Operation op, MechanicFacade facade) {
        this.pc = new McPopUpComponentView();
        this.pr = new McPopUpRequestView();
        this.m  = m;
        this.op = op;
        this.facade = facade;

        if (op == Operation.ADD) {
            setupAddComponentListener();
        } else {
            setupRemoveComponentListener();
        }
    }

    // ---------- ADD ----------

    private void setupAddComponentListener() {
        pc.getSendButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
					handleAddComponent();
				} catch (DuplicateComponentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    private void handleAddComponent() throws DuplicateComponentException {
        Vehicle v = ensureVehicleWithMSN();
        if (v == null) return;

        try {
            String idCStr = pc.getIdC().getText();
            String name   = pc.getNameC().getText().toUpperCase();
            String status = pc.getStatusC().getText().toUpperCase();

            int idc = Integer.parseInt(idCStr);
            c = new Components(idc, name);
            c.setReplacementStatus(status);

            // model-first: calcola wear/esito
            int result = m.addComponent(v, c); // 1/2 ok, 3 worn

            var res = facade.addComponent(
                m.getID(),
                v.getMSN().toUpperCase(),
                c.getIdComponent(),
                c.getName().toUpperCase(),
                c.getReplacementStatus().toUpperCase(),
                c.getWear()
            );

            switch (res.getOutcome()) {
                case INSERTED_OK -> {
                    pc.mex2();
                    pc.clearComponents(pc.getDataPanel());
                }
                case NEEDS_REPLACEMENT -> {
                    if (result == 3) {
                        handleComponentReplacementRequest(v);
                    } else {
                        pc.mex1();
                        pc.clearComponents(pc.getDataPanel());
                    }
                }
                default -> {
                    pc.mex1();
                    pc.clearComponents(pc.getDataPanel());
                }
            }

        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleComponentReplacementRequest(Vehicle v) {
        pc.hide();
        pr.show();
        pr.setId_c(pc.getIdC()); // precompila id componente

        pr.getSendButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    facade.createComponentRequest(
                        m.getID(),
                        pr.getId_s().getText(),
                        pc.getIdC().getText(),
                        v.getMSN().toUpperCase(),
                        pr.getDesc().getText().toUpperCase()
                    );
                    // opzionale
                    // pr.mex1();
                } catch (Exception ex) {
                    pr.mex();
                    System.out.println(ex);
                }
            }
        });
    }

    // ---------- REMOVE ----------

    private void setupRemoveComponentListener() {
        pc.hideField();
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

            int id = Integer.parseInt(idCStr);

            facade.removeComponent(
                m.getID(),
                v.getMSN().toUpperCase(),
                id,
                name
            );

            // model update
            c = new Components(id, name);
            v.removeComponent(c);

            pc.mex3();
        } catch (ComponentNotFoundException err) {
            pc.mex();
            System.out.println(err);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID componente non numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        pc.clearComponents(pc.getDataPanel());
    }

    // ---------- Helpers SOLO UI ----------

    private Vehicle ensureVehicleWithMSN() {
        Vehicle v = m.getVehicles();
        if (v == null || !v.hasValidMsn()) {
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
