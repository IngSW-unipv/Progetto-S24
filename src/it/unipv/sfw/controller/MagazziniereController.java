package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.model.staff.Magazziniere;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.MagazziniereView;

public class MagazziniereController extends AbsController {
	private Staff user;
	
	protected Magazziniere m;

	@Override
	public TypeController getType() {
		// TODO Auto-generated method stub

		return TypeController.MAGAZZINIERE;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		MagazziniereView mv = new MagazziniereView();
		MagazziniereDAO md = new MagazziniereDAO();
		
		WhPopUpShowRequestController wsrc = new WhPopUpShowRequestController(m.getRequest());
		WhPopUpDeleteRequestController wdrc = new WhPopUpDeleteRequestController();
		WhPopUpUpdateComponentController wupc = new WhPopUpUpdateComponentController();
		
		// 
		try {

			user = Session.getIstance().getCurrentUser();

		} catch (Exception e) {
			System.out.println("Errore");
		}
		
		mv.getShowRequestButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				m.setRequest(md.selectAllRequest());
				wsrc.showWindow();
			}
			
		});
		
		mv.getDeleteRequestButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				wdrc.showWindow();
			}
			
		});
		
		mv.getUpdateCompoButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wupc.showWindow();
			}
			
		});

		mv.getCombobox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String select = (String) mv.getCombobox().getSelectedItem();
				
				if(select.equals("- ALL")) {
					md.countElement();
					
				}else {		
					md.countElementBySelect(select);
				}
				
			}
			
		});
		
		view = mv;
	}
	
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		view.onLoad();
	}

}
