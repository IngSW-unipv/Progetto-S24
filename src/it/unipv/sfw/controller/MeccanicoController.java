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

	// creo un SET per contenere tutti i componenti di cui è composta la vettura
	//protected Set<Component> c = new HashSet<>();

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

		McPopUpComponentController pcc = new McPopUpComponentController();
		McPopUpPilotController ppc = new McPopUpPilotController();
		McPopUpRequestController prc = new McPopUpRequestController();
		McPopUpVehicleController pvc = new McPopUpVehicleController();

		McGraphicTimePsController gtpc;
		McGraphicAllComponentController gacc;

		gtpc = new McGraphicTimePsController(m.getTime());
		
		Session.getIstance().setC();
		gacc = new McGraphicAllComponentController();
		

		mv.getAddComponentButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("ADD");
				pcc.showWindow();
			}

		});

		mv.getRemoveComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("REMOVE");
				pcc.showWindow();
			}

		});

		mv.getAddPilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("ADD");
				ppc.showWindow();
				
				// query per recuperare id pilota
				Session.getIstance().setId_pilot(md.selectIdP());
				mv.setId_p();
			}

		});

		mv.getRemovePilotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Session.getIstance().setOperation("REMOVE");
				ppc.showWindow();
			}

		});

		mv.getInsertRequestButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				prc.showWindow();
			}

		});

		mv.getInsertVehicleButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				pvc.showWindow();
			}

		});

		mv.getVisualTimePsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gtpc.showWindow();
			}

		});

		mv.getVisualStatusComponentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
