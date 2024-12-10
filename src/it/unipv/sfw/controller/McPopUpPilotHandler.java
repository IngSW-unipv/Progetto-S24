package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MeccanicoView;

public class McPopUpPilotHandler {

	private McPopUpPilotView pv;
	private MeccanicoDAO md;

	public McPopUpPilotHandler(MeccanicoView mv) {

		pv = new McPopUpPilotView();
		md = new MeccanicoDAO();

		pv.getSendButton().addActionListener(new ActionListener() {
                    
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					md.selectP(pv.getId().getText(), pv.getName().getText().toUpperCase(), pv.getSurname().getText().toUpperCase(), pv.getNumber().getText());
					md.insertPilotOnVehicle(pv.getId().getText(), fetchMSN());

					Session.getIstance().setId_pilot(pv.getId().getText());
					pv.clearComponents(pv.getDataPanel());

					mv.setId_p();
					pv.close();
						
					md.insertLogEvent(getID(), "INSERT ID PILOT : " +pv.getId().getText());
					
				} catch (PilotNotFoundException err) {
					// TODO Auto-generated catch block
					System.out.println(err);
					pv.mex();
					pv.clearComponents(pv.getDataPanel());
				}

			}
		});

	}
	
	//metodo per information hiding
	private String getID() {
		return Session.getIstance().getId_staff();
	}
	
	private String fetchMSN() {
		return Session.getIstance().getV().getMSN().toUpperCase();
	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pv.show();
	}

	public void clear() {
		pv.clearComponents(pv.getSendPanel());
	}

}
