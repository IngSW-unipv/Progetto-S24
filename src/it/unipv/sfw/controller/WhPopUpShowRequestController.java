package it.unipv.sfw.controller;

import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.view.WhPopUpShowRequestView;

public class WhPopUpShowRequestController {
	
	private Set<Request> request;
	private WhPopUpShowRequestView psr = new WhPopUpShowRequestView(request);

	public  WhPopUpShowRequestController(Set<Request> r) {
		request = new HashSet<>();
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		psr.show();
	}
	
}
