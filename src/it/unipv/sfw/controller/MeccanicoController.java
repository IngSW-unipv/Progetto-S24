package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.MeccanicoView;

public class MeccanicoController extends AbsController {

	private Staff user;

	protected Meccanico m;

	@Override
	public TypeController getType() {
		// TODO Auto-generated method stub
		return TypeController.MECCANICO;
	}

	@Override

	public void initialize() {
		// TODO Auto-generated method stub

		//
		try {

			user = Session.getIstance().getCurrentUser();
			m = (Meccanico) user;

		} catch (Exception e) {
			System.out.println("Errore");
		}

		MeccanicoView mv = new MeccanicoView();
		MeccanicoDAO md = new MeccanicoDAO();

		McPopUpRequestController prc = new McPopUpRequestController();
		McPopUpVehicleController pvc = new McPopUpVehicleController(mv);
		McPopUpPilotController ppc = new McPopUpPilotController(mv);
		McPopUpComponentController pcc = new McPopUpComponentController();

		// Controllo lo stato di V nella sessione
		boolean isVehiclePresent = (Session.getIstance().getV() != null);
		
		// Abilita o disabilita bottoni basati sul valore di V

		mv.getAddComponentButton().setEnabled(isVehiclePresent);
		mv.getAddComponentButton().setVisible(false);
		
		mv.getAddPilotButton().setEnabled(isVehiclePresent);
		mv.getAddPilotButton().setVisible(false);
		
		mv.getRemovePilotButton().setEnabled(isVehiclePresent);
		mv.getRemovePilotButton().setVisible(false);
		
		mv.getRemoveComponentButton().setEnabled(isVehiclePresent);
		mv.getRemoveComponentButton().setVisible(false);
		
		mv.getVisualTimePsButton().setEnabled(isVehiclePresent);
		mv.getVisualTimePsButton().setVisible(false);

		// ADD VEHICLE
		mv.getInsertVehicleButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				pvc.showWindow();

			}

		});

		// INSERT REQUEST
		mv.getInsertRequestButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				prc.showWindow();
				prc.clear();

			}

		});

		// ADD COMPONENT
		mv.getAddComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Session.getIstance().setOperation("ADD");
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-ADD COMPONENT");
				pcc.showWindow();
				pcc.clear();

			}

		});

		// REMOVE COMPONENT
		mv.getRemoveComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method

				Session.getIstance().setOperation("REMOVE");
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-REMOVE COMPONENT");
				pcc.showWindow();

			}

		});

		// ADD PILOT
		mv.getAddPilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				boolean isPilotPresent = (Session.getIstance().getId_pilot() != null);
				
				if(isPilotPresent == false) {
					
					Session.getIstance().setOperation("ADD");
					System.out.println(
							"il contenuto è: " + Session.getIstance().getOperation() + " @MECCANICO CONTROLLER-ADD PILOT");
					ppc.showWindow();
					ppc.clear();
				}else {
					// messaggio pop up che avverte di rimuovere prima di aggiungere
					JOptionPane.showMessageDialog(null, "REMOVE THE PILOT BEFORE TO ADD", "ERROR", JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		// REMOVE PILOT
		mv.getRemovePilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Session.getIstance().setOperation("REMOVE");
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-REMOVE PILOT");
				
				md.removePilot(Session.getIstance().getId_pilot());
				Session.getIstance().setId_pilot(null);
				mv.setId_p();
			}

		});

		mv.getVisualTimePsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Session.getIstance().getTps();
				McGraphicTimePsController gtpc = new McGraphicTimePsController();
				gtpc.initialize();

			}

		});

		mv.getVisualStatusComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				McGraphicAllComponentController gacc = new McGraphicAllComponentController();
				gacc.showWindow();

			}

		});

		mv.setVisible(true);
		view = mv;

	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		this.initialize();
	}

}
