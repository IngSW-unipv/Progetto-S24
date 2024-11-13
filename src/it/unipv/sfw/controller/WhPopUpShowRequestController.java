package it.unipv.sfw.controller;

import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.WhPopUpShowRequestView;

public class WhPopUpShowRequestController {
	protected Set<Request> request = Session.getIstance().getWh().getRequest();
	private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(	request);

	public  WhPopUpShowRequestController() {
	}
	
	public void initialie() {
		
		
		
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		psr.show();
	}
	
}
