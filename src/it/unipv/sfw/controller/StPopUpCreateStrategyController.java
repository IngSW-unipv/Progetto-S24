package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.StrategaDAO;
import it.unipv.sfw.view.StPopUpCreateStrategyView;

public class StPopUpCreateStrategyController {
	
	private StPopUpCreateStrategyView pcs;
	private StrategaDAO sd;
	
	public StPopUpCreateStrategyController(){
		
		pcs = new StPopUpCreateStrategyView();
		sd = new StrategaDAO();
		
		pcs.getDetailsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	public void showWindow() {
		// TODO Auto-generated method stub
		pcs.show();
	}
}
