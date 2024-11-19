package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.controller.AbsController.TypeController;
import it.unipv.sfw.dao.mysql.StrategaDAO;
import it.unipv.sfw.dao.mysql.VehicleDAO;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Stratega;
import it.unipv.sfw.view.StrategistView;

public class StrategistController extends AbsController {

	private Staff user;

	protected Stratega st;

	@Override
	public TypeController getType() {
		// TODO Auto-generated method stub
		return TypeController.STRATEGA;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

		//
		try {

			user = Session.getIstance().getCurrentUser();
			st = (Stratega) user;

		} catch (Exception e) {
			System.out.println("Errore");
		}

		StrategistView sv = new StrategistView();
		StrategaDAO sd = new StrategaDAO();
		VehicleDAO vd = new VehicleDAO();

		sv.getGetTimeButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Session.getIstance().getTS();
				createLabels(sv);
				vd.timeSector(Session.getIstance().getV());
			}

		});

		sv.setVisible(true);
		view = sv;
	}

	public void createLabels(StrategistView sv) {

		String t1 = convertTime(Session.getIstance().getV().getTimeSect1());
		String t2 = convertTime(Session.getIstance().getV().getTimeSect2());
		String t3 = convertTime(Session.getIstance().getV().getTimeSect3());
		int timeLap = Session.getIstance().getV().getTimeSect1()+Session.getIstance().getV().getTimeSect2()+Session.getIstance().getV().getTimeSect3();
		String t4 = convertTime(timeLap);
		
		sv.addRow(t1,t2,t3,t4);
	}

// Metodo per convertire i millisecondi in un formato "secondi.millisecondi"
	private String convertTime(int millis) {
		int seconds = (millis / 1000) % 60;
		int milliseconds = millis % 1000;

		return String.format("%02d.%03d", seconds, milliseconds);
	}
}
