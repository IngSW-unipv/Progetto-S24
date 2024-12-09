package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpShowRequestView;

public class WhPopUpShowRequestHandler {

	private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(getRequest());

	public  WhPopUpShowRequestHandler() {
	}
	
	private Set<Request> getRequest() {
		return Session.getIstance().getWh().getRequest();
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		psr.show();
	}
	
}
