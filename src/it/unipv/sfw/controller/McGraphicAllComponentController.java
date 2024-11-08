package it.unipv.sfw.controller;


import java.util.Set;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicAllComponentView;

public class McGraphicAllComponentController {
	
	protected Set<Components> components;
	private McGraphicAllComponentView gv;
	
	
	public McGraphicAllComponentController() {
		this.components = Session.getIstance().getV().getComponents();
		this.gv = new McGraphicAllComponentView(components);
			
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		gv.show();
	}

}
