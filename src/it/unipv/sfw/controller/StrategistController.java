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

	private int minT1, minT2, minT3, timeLap;

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
		StPopUpCreateStrategyController scs = new StPopUpCreateStrategyController();
		VehicleDAO vd = new VehicleDAO();

		sv.getGetTimeButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Session.getIstance().getTS();
				createTable(sv);

				vd.timeSector(Session.getIstance().getV());
			}

		});
		
		sv.getCreateStrategyButton().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				scs.showWindow();
			}
			
		});

		this.minT1 = 0;
		this.minT2 = 0;
		this.minT3 = 0;
		this.timeLap = 0;
		
		sv.setVisible(true);
		view = sv;
	}

	public void createTable(StrategistView sv) {

		// Valori generati casualmente provenienti da Session
		int app1 = Session.getIstance().getV().getTimeSect1(); // Settore 1
		int app2 = Session.getIstance().getV().getTimeSect2(); // Settore 2
		int app3 = Session.getIstance().getV().getTimeSect3(); // Settore 3
		int app4 = app1 + app2 + app3; // Tempo totale del giro

		// Inizializza i minimi solo se sono 0 (primo set di valori)
		if (minT1 == 0 && minT2 == 0 && minT3 == 0 && timeLap == 0) {
			minT1 = app1;
			minT2 = app2;
			minT3 = app3;
			timeLap = app4;
		}

		// Confronta e aggiorna i minimi
		if (app1 < minT1) {
			minT1 = app1;
		}
		if (app2 < minT2) {
			minT2 = app2;
		}
		if (app3 < minT3) {
			minT3 = app3;
		}
		if (app4 < timeLap) {
			timeLap = app4;
		}

		String t1 = convertTime(app1);
		String t2 = convertTime(app2);
		String t3 = convertTime(app3);
		String t4 = convertTime(app4);

		sv.addRow(t1, t2, t3, t4);
		sv.colorCell(minT1, minT2, minT3);
		sv.setCountLapLabel(sv.getCountLapLabel());
	}

	// Metodo per convertire i millisecondi in un formato
	// "minuti:secondi.millisecondi"
	private String convertTime(int millis) {
		int minutes = (millis / 1000) / 60; // Calcolo dei minuti
		int seconds = (millis / 1000) % 60; // Calcolo dei secondi
		int milliseconds = millis % 1000; // Calcolo dei millisecondi

		// Formatta il tempo in "mm:ss.SSS"
		return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
	}

}
