package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Magazziniere;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.MagazziniereView;

public class MagazziniereController extends AbsController{
	private Staff user;
	
	private Magazziniere m;

	private Observable obs;
	
	@Override
	public TypeController getType() {
		// TODO Auto-generated method stub
		return TypeController.MAGAZZINIERE;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		// 
		try {

			user = Session.getIstance().getCurrentUser();
			m = (Magazziniere) user;
			
		} catch (Exception e) {
			System.out.println("Errore");
		}

		obs = new Observable();
		
		MagazziniereView mv = new MagazziniereView();
		MagazziniereDAO md = new MagazziniereDAO();
		
		WhPopUpDeleteRequestHandler wdrc = new WhPopUpDeleteRequestHandler(obs);
		WhPopUpUpdateComponentHandler wupc = new WhPopUpUpdateComponentHandler();
		
		Session.getIstance().getRequest();
		md.insertLogEvent(getID(), "LOGIN");
		
		mv.data(Session.getIstance().getName(), Session.getIstance().getSurname(), Session.getIstance().getWh().totalRequest());
		obs.addObserver(mv);
		
		mv.getShowRequestButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WhPopUpShowRequestHandler wsrc = new WhPopUpShowRequestHandler();
				md.insertLogEvent(getID(), "SHOW REQUEST");
				
				System.out.println(Session.getIstance().getWh().getRequest());
				
				for(Request r : m.getRequest()) {
					System.out.println(r);
					
				}

				wsrc.showWindow();
				mv.setMex();
			}
			
		});
		
		mv.getDeleteRequestButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				wdrc.showWindow();	
				wdrc.clear();
				mv.setMex();
			}
			
		});
		
		mv.getUpdateCompoButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wupc.showWindow();
				mv.setMex();
			}
			
		});

		mv.getCombobox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String select = (String) mv.getCombobox().getSelectedItem();
				md.insertLogEvent(getID(), "SHOW QUANTITY COMPONENT: " +select);
				
				if(select.equals("- ALL")) {
					mv.mexCombo(md.countElement());
					
				}else {	
					mv.mexCombo(md.countElementBySelect(select));
				}

			}
			
		});
		
		mv.setVisible(true);
		view = mv;
	}
	
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		this.onLoad();
	}
	
	private String getID() {
		return Session.getIstance().getId_staff();
	}

}
