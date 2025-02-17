package it.unipv.sfw.controller;


import java.util.Set;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicAllComponentView;

public class McGraphicAllComponentHandler {
	
	private Set<Components> components = fetchComponent();
	private McGraphicAllComponentView gv = new McGraphicAllComponentView(components);

	public void showWindow() {
		// TODO Auto-generated method stub
		
		gv.show();
	}
	
	
	//metodo per information hiding
	private Set<Components> fetchComponent(){
		return Session.getIstance().getV().getComponent();
	}

}
