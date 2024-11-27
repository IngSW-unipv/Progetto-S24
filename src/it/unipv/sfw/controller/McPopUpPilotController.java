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

				if (md.insertPilot(pv.getId().getText(), pv.getName().getText(), pv.getSurname().getText(), n)) {
					
					md.insertPilotOnVehicle(pv.getId().getText(), Session.getIstance().getV().getMSN());
					pv.mex1();
					pv.clearComponents(pv.getDataPanel());
					
					Session.getIstance().setId_pilot(pv.getId().getText());
					mv.setId_p();
					
				} else {
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
