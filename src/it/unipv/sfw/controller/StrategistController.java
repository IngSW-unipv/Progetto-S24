package it.unipv.sfw.controller;

import it.unipv.sfw.controller.AbsController.TypeController;
import it.unipv.sfw.dao.mysql.StrategaDAO;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Stratega;
import it.unipv.sfw.view.StrategistView;

public class StrategistController extends AbsController{

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
		StrategaDAO md = new StrategaDAO();
		
		sv.setVisible(true);
		view = sv;
	}
}
