 package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.MagazziniereView;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

public class WhPopUpDeleteRequestHandler {

	private WhPopUpDeleteRequestView pdr;
	private MagazziniereDAO md;

	public WhPopUpDeleteRequestHandler(Observable observable) {
		pdr = new WhPopUpDeleteRequestView();
		md = new MagazziniereDAO();
		

		pdr.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ID STAFF: "+pdr.getId_s().getText());
				System.out.println("ID COMPONENT: "+pdr.getId_c().getText());
				System.out.println("ID VEHICLE: "+pdr.getId_v().getText());
				
				try {
					md.checkRequest(pdr.getId_s().getText(), pdr.getId_c().getText(), pdr.getId_v().getText().toUpperCase());
					md.removeRequest(pdr.getId_c().getText());
					
					Session.getIstance().getRequest();

					pdr.clearComponents(pdr.getDataPanel());
					
					System.out.println("total req: " +  Session.getIstance().getWh().totalRequest());

					int totalRequest = Session.getIstance().getWh().totalRequest();
					
					 // Notifica gli osservatori del cambiamento
                    observable.notifyObservers(totalRequest);
					
					pdr.mex2();
				} catch (RequestNotFoundException err) {
					System.out.println(err);
					pdr.mex1();
					pdr.clearComponents(pdr.getDataPanel());
					return;
					
				}
					

			}

		});

	}

	public void showWindow() {
		// TODO Auto-generated method stub
		pdr.show();
	}

	public void clear() {
		pdr.clearComponents(pdr.getSendPanel());
	}
}
