package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpComponentView;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpComponentHandler {

	private McPopUpComponentView pc;
	private McPopUpRequestView pr;
	private MeccanicoDAO md;

	private Components c;

	public McPopUpComponentHandler() {

		pc = new McPopUpComponentView();
		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();
		
		if (Session.getIstance().getOperation().equals("ADD")) {
			
			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					int n = 0, idc = 0;

					try {
						
						md.checkCompo(pc.getIdC().getText(), pc.getNameC().getText().toUpperCase(), pc.getStatusC().getText().toUpperCase());
						idc = Integer.parseInt(pc.getIdC().getText());
						c = new Components(idc, pc.getNameC().getText().toUpperCase());
						c.setReplacementStatus(pc.getStatusC().getText().toUpperCase());
						n = addCompo();

						switch (n) {

						case 0:

							// componente già inserito
							pc.mex1();
							pc.clearComponents(pc.getDataPanel());
							break;

						case 1:
							
							// componente inserito con successo
							if (md.insertComponent(pc.getIdC().getText(), fetchMSN())) {
								
								md.updateWear(c.getWear(),pc.getIdC().getText() );
								pc.mex2();
								pc.clearComponents(pc.getDataPanel());
							}

							break; 

						case 2:

							if (md.insertComponent(pc.getIdC().getText(), fetchMSN())) {
								md.updateWear(c.getWear(), pc.getIdC().getText());
								pc.mex2();
								pc.clearComponents(pc.getDataPanel());
							}

							break;

						case 3:
							// Far apparire la window della richiesta per sostituire il componente
							pc.hide();
							pr.show();

							pr.setId_c(pc.getIdC());

							pr.getSendButton().addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub

									md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(), pc.getIdC().getText(),
											pr.getId_v().getText().toUpperCase());
									
								}
							});
							
							md.insertLogEvent(getID(), "INSERT COMPONENT ID: " +pc.getIdC().getText());
							break;
						}
					} catch (ComponentNotFoundException err) {
						// TODO Auto-generated catch block
		
						System.out.println(err);
						pc.mex();
						
					}catch (DuplicateComponentException err) {
						
						System.out.println(err);
						pc.mex1();
					}

				}

			});

		} else {

			pc.hideField();

			pc.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
				
					try {
						md.checkCompo(pc.getIdC().getText().toUpperCase(), pc.getNameC().getText().toUpperCase(), "USED");				
						md.removeComponent(pc.getIdC().getText().toUpperCase(), fetchMSN());
						
						int id = Integer.parseInt( pc.getIdC().getText());
						
						c = new Components(id, pc.getNameC().getText().toUpperCase());
						
						Session.getIstance().getM().removeComponent(Session.getIstance().getV(), c);
						
						pc.mex3();
						md.insertLogEvent(getID(), "REMOVE COMPONENT ID: " +pc.getIdC().getText());
					} catch (ComponentNotFoundException err) {
						// TODO Auto-generated catch block
						pc.mex();
						System.out.println(err);
					}
					
					pc.clearComponents(pc.getDataPanel());
				}

			});
		}

	}
	
	//metodo per information hiding
	private String fetchMSN() {
		return Session.getIstance().getV().getMSN().toUpperCase();
	}
	
	private int addCompo() throws DuplicateComponentException {
		return Session.getIstance().getM().addComponent(Session.getIstance().getV(), c);
	}
	
	private String getID() {
		return Session.getIstance().getId_staff();
	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pc.show();
	}
	
	public void clear() {
		pc.clearComponents(pc.getSendPanel());
	}

}
