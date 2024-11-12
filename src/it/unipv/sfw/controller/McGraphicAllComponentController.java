package it.unipv.sfw.controller;


import java.util.Set;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicAllComponentView;

public class McGraphicAllComponentController {
	
	protected Set<Components> components = Session.getIstance().getV().getComponents();
	private McGraphicAllComponentView gv = new McGraphicAllComponentView(components);
	
	
	public McGraphicAllComponentController() {
		
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		
		gv.show();
	}

}
