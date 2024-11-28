package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpPilotView;
import it.unipv.sfw.view.MeccanicoView;

public class McPopUpPilotController {

	private McPopUpPilotView pv;
	private MeccanicoDAO md;

	public McPopUpPilotController(MeccanicoView mv) {

		pv = new McPopUpPilotView();
		md = new MeccanicoDAO();

		pv.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String number = pv.getNumber().getText();
				
				int n = Integer.parseInt(number);
				
				
				try {
					md.checkPilot(pv.getId().getText());
					md.insertPilotOnVehicle(pv.getId().getText(), Session.getIstance().getV().getMSN().toUpperCase());

					Session.getIstance().setId_pilot(pv.getId().getText());
					pv.clearComponents(pv.getDataPanel());

					mv.setId_p();
					pv.close();
					
				} catch (PilotNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					pv.mex();
					pv.clearComponents(pv.getDataPanel());
				}

			}
		});

	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pv.show();
	}

	public void clear() {
		pv.clearComponents(pv.getSendPanel());
	}

}
