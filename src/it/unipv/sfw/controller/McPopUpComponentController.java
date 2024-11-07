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
import it.unipv.sfw.view.MeccanicoView;

public class McPopUpComponentController {

	private McPopUpComponentView pc;
	private McPopUpRequestView pr;
	private MeccanicoDAO md;

	private Meccanico m;
	private Components c;
	private Vehicle v;

	public McPopUpComponentController() {

		pc = new McPopUpComponentView();
		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();
		m = new Meccanico(Session.getIstance().getId_staff(), Session.getIstance().getPwd_staff());
//		v = new Vehicle(Session.getIstance().getMsn());
		
		if (Session.getIstance().getOperation() == "ADD") {
			
			System.out.println("il contenuto è: "+Session.getIstance().getOperation()+ " sono nell'if-@mcpcomponent");
			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					int n = 0, result = 0;

					String id_compo = pc.getIdC().getText();

					int id = Integer.parseInt(id_compo);

					c = new Components(id, pc.getNameC().getText(), pc.getStatusC().getText());

					result = md.checkCompo(id);

					// Se inserisco un valore sbagliato
					if (result == 0) {
						System.out.println("Componente inesistente");
						pc.mex();
						pc.clearComponents(pc.getSendPanel());
						return;
					}

					try {
						System.out.println("Componente TROVATO");
						n = m.addComponent(c);
						
					} catch (WrongReplacementStatusException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					switch (n) {

					case 0:

						// componente già inserito
						pc.mex1();
						pc.clearComponents(pc.getDataPanel());
						clear();
						break;

					case 1:
						
						// componente inserito con successo
						if (md.insertComponent(id, pc.getIdV().getText())) {
							System.out.println("WEAR = "+c.getWear()+"@mcpopupcomponent");
							md.updateWear(c.getWear(), id);
							pc.mex2();
							pc.clearComponents(pc.getDataPanel());
							clear();
						}

						break;

					case 2:

						if (md.insertComponent(id, pc.getIdV().getText())) {
							md.updateWear(c.getWear(), id);
							pc.mex2();
							pc.clearComponents(pc.getDataPanel());
							clear();
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

								md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText(), id_c,
										pr.getId_v().getText());
								
								clear();
							}
						});

						break;
					}
				}

			});

		} else {

			pc.hideField();
			System.out.println("il contenuto è: "+Session.getIstance().getOperation()+ " sono nell'else");

			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					String id_comp = pc.getIdC().getText();

					int id = Integer.parseInt(id_comp);

					c = new Components(id, pc.getNameC().getText(), pc.getStatusC().getText());
					
					
					try {
						m.removeComponent(c);
						
						md.removeComponent(id, pc.getIdV().getText());
						
						pc.mex3();
						clear();
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
