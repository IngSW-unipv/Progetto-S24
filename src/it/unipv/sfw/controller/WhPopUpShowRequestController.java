package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpShowRequestView;

public class WhPopUpShowRequestController {

	private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(Session.getIstance().getWh().getRequest());

	public  WhPopUpShowRequestController() {
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		psr.show();
	}
	
}
