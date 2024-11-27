package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.WrongReplacementStatusException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpComponentController {

	private McPopUpComponentView pc;
	private McPopUpRequestView pr;
	private MeccanicoDAO md;

	private Components c;

	public McPopUpComponentController() {

		pc = new McPopUpComponentView();
		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();
		
		if (Session.getIstance().getOperation() == "ADD") {
			
			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					int n = 0;

					String id_compo = pc.getIdC().getText();

					int id = Integer.parseInt(id_compo);

					c = new Components(id, pc.getNameC().getText().toUpperCase(), pc.getStatusC().getText().toUpperCase());

					try {
						md.checkCompo(id);
						n = Session.getIstance().getM().addComponent(Session.getIstance().getV(), c);
					} catch (ComponentNotFoundException | WrongReplacementStatusException e1) {
						// TODO Auto-generated catch block
						
						e1.printStackTrace();
						pc.mex();
					}

					switch (n) {

					case 0:

						// componente già inserito
						pc.mex1();
						pc.clearComponents(pc.getDataPanel());
						break;

					case 1:
						
						// componente inserito con successo
						if (md.insertComponent(id, pc.getIdV().getText().toUpperCase())) {
							
							md.updateWear(c.getWear(), id);
							pc.mex2();
							pc.clearComponents(pc.getDataPanel());
						}

						break;

					case 2:

						if (md.insertComponent(id, pc.getIdV().getText().toUpperCase())) {
							md.updateWear(c.getWear(), id);
							pc.mex2();
							pc.clearComponents(pc.getDataPanel());
						}

						break;

					case 3:
						// Far apparire la window della richiesta per sostituire il componente
						pc.hide();
						pr.show();

						pr.setId_c(pc.getIdC());
						pr.setId_v(pc.getIdV());

						pr.getSendButton().addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub

								String idc = pr.getId_c().getText();

								int id_c = Integer.parseInt(idc);

								md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(), id_c,
										pr.getId_v().getText().toUpperCase());
								
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

					String id_comp = pc.getIdC().getText();

					int id = Integer.parseInt(id_comp);

					c = new Components(id, pc.getNameC().getText().toUpperCase(), pc.getStatusC().getText().toUpperCase());
					
					
					try {
						Session.getIstance().getM().removeComponent(Session.getIstance().getV(), c);
						
						md.removeComponent(id, pc.getIdV().getText().toUpperCase());
						
						pc.mex3();
					} catch (ComponentNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					pc.clearComponents(pc.getDataPanel());
				}

			});
		}

	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pc.show();
	}
	
	public void clear() {
		pc.clearComponents(pc.getSendPanel());
	}

}
