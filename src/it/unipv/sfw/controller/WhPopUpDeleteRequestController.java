package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.MagazziniereView;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

public class WhPopUpDeleteRequestController {
	
	private WhPopUpDeleteRequestView pdr;
	private MagazziniereDAO md	;
	
	
	public WhPopUpDeleteRequestController(MagazziniereView wh) {
		pdr = new WhPopUpDeleteRequestView();
		md = new MagazziniereDAO();
		
		pdr.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = 0;
				
				String id_c = pdr.getId_c().getText();

				int idc = Integer.parseInt(id_c);
				
				res = md.checkRequest(pdr.getId_s().getText(), idc,  pdr.getId_v().getText());
				
				if(res == 0) {
					pdr.mex1();
					pdr.clearComponents(pdr.getDataPanel());
				}else {
					md.removeRequest(idc);
					pdr.mex2();
					pdr.clearComponents(pdr.getDataPanel());
					Session.getIstance().getRequest();
					wh.data(Session.getIstance().getName(), Session.getIstance().getSurname(), Session.getIstance().getWh().totalRequest());
				}
				
			}
			
		});
		
	}
	
	public void showWindow() {
		// TODO Auto-generated method stub
		pdr.show();
	}
	
	public void clear() {
		pdr.clearComponents(pdr.getSendPanel());
	}
}
