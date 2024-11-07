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
		md = new MeccanicoDAO();

		if (Session.getIstance().getOperation() == "ADD") {
			
			pv.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
			
					
					String number = pv.getNumber().getText();

					int n = Integer.parseInt(number);

					if (md.insertPilot(pv.getName().getText(), pv.getSurname().getText(), n)) {
						pv.mex1();
						pv.clearComponents(pv.getDataPanel());
					} else {
						pv.mex();
						pv.clearComponents(pv.getDataPanel());
						
					}

				}
			});

		} else {

			pv.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					if (md.checkPilot(Session.getIstance().getId_pilot())) {
						md.removePilot(Session.getIstance().getId_pilot());
						pv.mex2();
						pv.clearComponents(pv.getDataPanel());
					}else {
						pv.mex();
						pv.clearComponents(pv.getDataPanel());
					}

				}
			});
		}

	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pv.show();
	}
	
	public void clear() {
		pv.clearComponents(pv.getSendPanel());
	}

}
