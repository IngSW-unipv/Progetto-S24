package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpRequestController {

	private McPopUpRequestView pr;
	private MeccanicoDAO md;

	public McPopUpRequestController() {

		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();

		System.out.println("OPERATION: " + Session.getIstance().getOperation());

		if (Session.getIstance().getOperation().equals("NO_V")) {

			pr.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					try {
						if (!pr.getId_s().getText().equals(Session.getIstance().getId_staff()))
							throw new WrongIDException();

					} catch (WrongIDException err) {
						pr.mex();
						System.out.println(err);
						return;

					}

					try {
						md.checkIdCompo(pr.getId_c().getText());
						md.checkRequest(pr.getId_c().getText());

						md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(),
								pr.getId_c().getText(), pr.getId_v().getText().toUpperCase());
						pr.clearComponents(pr.getDataPanel());
						pr.mex1();

					} catch (ComponentNotFoundException | WrongRequestException err) {
						// TODO Auto-generated catch block
						pr.mex();
						System.out.println(err);

					}
					
					try {

						md.checkVehicle(pr.getId_v().getText().toUpperCase());

					} catch (VehicleNotFoundException err) {
						pr.mex();
						System.out.println(err);
						return;

					}

					pr.clearComponents(pr.getDataPanel());

				}
			});

		} else {

			pr.hide();
			pr.title();
			
			pr.getSendButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					try {
						if (!pr.getId_s().getText().equals(Session.getIstance().getId_staff()))
							throw new WrongIDException();

					} catch (WrongIDException err) {
						pr.mex();
						System.out.println(err);
						return;

					}

					try {
						md.checkIdCompo(pr.getId_c().getText());
						md.checkRequest(pr.getId_c().getText());
						
						md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText().toUpperCase(),
								pr.getId_c().getText(), Session.getIstance().getV().getMSN());
						pr.clearComponents(pr.getDataPanel());
						pr.mex1();

					} catch (ComponentNotFoundException | WrongRequestException err) {
						// TODO Auto-generated catch block
						pr.mex();
						System.out.println(err);

					}
				}
			});
		}

	}

	// Metodo per mostrare la finestra
	public void showWindow() {
		pr.show();
	}

	public void clear() {
		pr.clearComponents(pr.getSendPanel());
	}

}
