package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.view.WhPopUpUpdateComponentView;

public class WhPopUpUpdateComponentHandler {

	private WhPopUpUpdateComponentView puc;
	private MagazziniereDAO md;

	public WhPopUpUpdateComponentHandler() {
		puc = new WhPopUpUpdateComponentView();
		md = new MagazziniereDAO();
		
		puc.getSendButton().addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					md.checkCompo(puc.getId_c().getText());
					md.updateComponent(puc.getId_c().getText(), puc.getWear().getText() ,puc.getStatus().getText().toUpperCase());
					puc.mex2();
					
				} catch (ComponentNotFoundException err) {
					// TODO Auto-generated catch block
					System.out.println(err);
					puc.mex1();
				}
				
				puc.clearComponents(puc.getDataPanel());
				}
				
			
			
		});

}

	public void showWindow() {
		// TODO Auto-generated method stub
		puc.show();
	}

}
