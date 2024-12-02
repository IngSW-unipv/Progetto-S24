package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.MagazziniereView;
import it.unipv.sfw.view.WhPopUpDeleteRequestView;

public class WhPopUpDeleteRequestController {

	private WhPopUpDeleteRequestView pdr;
	private MagazziniereDAO md;

	public WhPopUpDeleteRequestController(MagazziniereView wh) {
		pdr = new WhPopUpDeleteRequestView();
		md = new MagazziniereDAO();

		pdr.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					md.checkRequest(pdr.getId_s().getText(), pdr.getId_c().getText(), pdr.getId_v().getText().toUpperCase());
					pdr.clearComponents(pdr.getDataPanel());
				} catch (RequestNotFoundException err) {
					pdr.mex1();
					System.out.println(err);
				}

				try {
					md.removeRequest(pdr.getId_c().getText());
					pdr.mex2();
					pdr.clearComponents(pdr.getDataPanel());
					Session.getIstance().getRequest();
					wh.data(Session.getIstance().getName(), Session.getIstance().getSurname(),
							Session.getIstance().getWh().totalRequest());
				}catch(SQLException err) {
					pdr.mex1();
					err.printStackTrace();
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
