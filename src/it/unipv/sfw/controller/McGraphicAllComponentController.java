package it.unipv.sfw.controller;


import java.util.Set;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.view.McGraphicAllComponentView;

public class McGraphicAllComponentController {
	
	protected Set<Components> components;
	private McGraphicAllComponentView gv  = new McGraphicAllComponentView(components);
	
	
	public McGraphicAllComponentController(Set<Components> set) {
		this.components = set;
			
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		gv.show();
	}

}
