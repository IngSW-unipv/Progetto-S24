package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpVehicleView;

public class McPopUpVehicleController {
	
	private McPopUpVehicleView vv;
	private MeccanicoDAO md;
	private Meccanico m;
	
	public McPopUpVehicleController() {
		
		vv = new McPopUpVehicleView();
		
		vv.getSendButton().addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String id_pilot= vv.getId_p().getText();
				
				int n = Integer.parseInt(id_pilot);
				
				String check = md.checkPilot(vv.getMsn().getText());
				
				if(check != null) {
					
					md.insertPilotOnVehicle(n);
					md.insertMeccOnVehicle(vv.getMsn().getText());
					
					m.setMSN(vv.getMsn().getText());
					Session.getIstance().setMsn(m.getMSN());
					m.addVehicle();
					vv.mex2();
				}else {
					
					vv.mex1();
					
				}
			
			}
					
		});
		
		
	}
	
	// Metodo per mostrare la finestra
    public void showWindow() {
        vv.show();
    }

}
