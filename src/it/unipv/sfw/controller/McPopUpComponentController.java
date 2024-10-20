package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.WrongReplacementStatusException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpComponentController {

	private McPopUpComponentView pc;
	private McPopUpRequestView pr;
	private MeccanicoDAO md;

	private Meccanico m;
	private Components c;
	private Vehicle v;

	public McPopUpComponentController() {

		pc = new McPopUpComponentView();

		if (Session.getIstance().getOperation() == "ADD") {

			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int n = 0,
						 result = 0;

					String id_compo = pc.getInsertID_C().getText();

					int id = Integer.parseInt(id_compo);

					c = new Components(id, pc.getNameC().getText(), pc.getStatusC().getText());

					result = md.checkCompo(id, pc.getNameC().getText());

					//Se inserisco un valore sbagliato
					if (result == 0) {
						pc.mex();
						
						return;
					}
					

					try {
						n = m.addComponent(c, m.getMSN());
					} catch (WrongReplacementStatusException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					switch (n) {

					case 0:

						// componente già inserito
						pc.mex1();

						break;

					case 1:

						// componente inserito con successo
						if (md.insertComponent(id, pc.getInsertID_V().getText())) {
							v.calcWear(c);
							md.updateWear(n, id);
							pc.mex2();
						}

						break;

					case 2:

						if (md.insertComponent(id, pc.getInsertID_V().getText())) {
							v.calcWear(c);
							md.updateWear(n, id);
							pc.mex2();
						}

						break;

					case 3:
						// Far apparire la window della richiesta per sostituire il componente
						pc.hide();
						pr.show();

						pr.setId_c(pc.getInsertID_C());
						pr.setId_v(pc.getInsertID_V());

						pr.getSendButton().addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub

								String idc = pr.getId_c().getText();

								int id_c = Integer.parseInt(idc);

								md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText(), id_c,
										pr.getId_v().getText());

							}
						});

						break;
					}
				}

			});

		} else {
			
			pc.hideField();

			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					String id_comp = pc.getInsertID_C().getText();

					int id = Integer.parseInt(id_comp);

					c = new Components(id, pc.getNameC().getText(), pc.getStatusC().getText());

					try {
						m.removeComponent(c, m.getMSN());
					} catch (ComponentNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});

		}

	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pc.show();
	}

}
