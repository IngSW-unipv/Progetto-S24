package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpPilotView;


public class McPopUpPilotController {
	
	private McPopUpPilotView pv;
	private MeccanicoDAO md;
	
	public McPopUpPilotController() {
		
		pv = new McPopUpPilotView();
		
		if(Session.getIstance().getOperation() == "ADD") {
			
			pv.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					String number = pv.getNumber().getText();
					
					int n = Integer.parseInt(number);
					
					md.insertPilot(pv.getName().getText(), pv.getSurname().getText(), n);
					
				}
			});
			
		}else {
			
			pv.hide();
			pv.setName();
			pv.setNumber();
			
			pv.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					String number = pv.getNumber().getText();
					
					int n = Integer.parseInt(number);
					
					md.removePilot(pv.getName().getText(), n);
								
				}
			});
		}
				
	}

	// Metodo per mostrare la finestra
    public void showWindow() {
        pv.show();
    }

}
