package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.MeccanicoView;

public class MeccanicoController extends AbsController {

	private Staff user;

	protected Meccanico m;
	protected Vehicle v;

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

		mv.getAddComponentButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("ADD");
				McPopUpComponentController pcc = new McPopUpComponentController();
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-ADD COMPONENT");
				pcc.showWindow();
				pcc.clear();
			}

		});

		mv.getRemoveComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("REMOVE");
				McPopUpComponentController pcc = new McPopUpComponentController();
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-REMOVE COMPONENT");
				pcc.showWindow();
			}

		});

		mv.getAddPilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("ADD");
				McPopUpPilotController ppc = new McPopUpPilotController();
				System.out.println(
						"il contenuto è: " + Session.getIstance().getOperation() + " @MECCANICO CONTROLLER-ADD PILOT");
				ppc.showWindow();
				ppc.clear();
			}

		});

		mv.getRemovePilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				McPopUpPilotController ppc = new McPopUpPilotController();
				Session.getIstance().setOperation("REMOVE");
				System.out.println("il contenuto è: " + Session.getIstance().getOperation()
						+ " @MECCANICO CONTROLLER-REMOVE PILOT");
				ppc.showWindow();
			}

		});

		mv.getInsertRequestButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				prc.showWindow();
				prc.clear();
			}

		});

		mv.getInsertVehicleButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				pvc.showWindow();
				pvc.clear();

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

				McGraphicAllComponentController gacc  = new McGraphicAllComponentController();
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
