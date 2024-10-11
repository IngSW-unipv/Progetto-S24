package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

public class WhPopUpUpdateComponentController {
	
	private WhPopUpUpdateComponentView puc;
	private MagazziniereDAO md;
	
	public WhPopUpUpdateComponentController() {
		puc = new WhPopUpUpdateComponentView();
		
		puc.getSendButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int res = 0;
				
				String id_c = puc.getId_c().getText(),
							wear = puc.getWear().getText();
				
				int id = Integer.parseInt(id_c),
					 nw = Integer.parseInt(wear);
				
				res = md.checkCompo(id);
				
				if(res == 0) {
					puc.mex1();
				}else {
					md.updateComponent(id, nw, puc.getStatus().getText());
					puc.mex2();
				}
				
			}
			
		});
		
	}
	
	public void showWindow() {
		// TODO Auto-generated method stub
		puc.show();
	}
	
}
