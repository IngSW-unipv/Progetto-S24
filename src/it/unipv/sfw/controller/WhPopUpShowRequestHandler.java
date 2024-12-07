package it.unipv.sfw.controller;

import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpShowRequestView;

public class WhPopUpShowRequestHandler {

	private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(Session.getIstance().getWh().getRequest());

	public  WhPopUpShowRequestHandler() {
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		psr.show();
	}
	
}
