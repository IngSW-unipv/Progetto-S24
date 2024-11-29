package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpRequestController {
	
	private McPopUpRequestView pr; 
	private MeccanicoDAO md;
	
	public McPopUpRequestController() {
		
		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();
		
		pr.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 
				 try {
					md.checkIdCompo(pr.getId_c().getText());
					md.checkStaff(Session.getIstance().getId_staff());
					md.checkVehicle(Session.getIstance().getV().getMSN());
					
					md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(), pr.getId_c().getText(), pr.getId_v().getText().toUpperCase());
					pr.clearComponents(pr.getDataPanel());
					pr.mex1();
					
				} catch (ComponentNotFoundException |  WrongIDException | VehicleNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

					pr.clearComponents(pr.getDataPanel());
					pr.mex();
				
			}
		});
	
	}
	
	// Metodo per mostrare la finestra
    public void showWindow() {
        pr.show();
    }
    
    public void clear() {
    	pr.clearComponents(pr.getSendPanel());
    }
    
}
