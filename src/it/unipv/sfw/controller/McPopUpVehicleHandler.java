package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.vehicle.Vehicle;
import it.unipv.sfw.view.McPopUpVehicleView;
import it.unipv.sfw.view.MeccanicoView;

public class McPopUpVehicleHandler {

	private McPopUpVehicleView vv;
	private MeccanicoDAO md;

	public McPopUpVehicleHandler(MeccanicoView mv) {

		vv = new McPopUpVehicleView();
		md = new MeccanicoDAO();

		vv.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String id_pilot = vv.getId_p().getText(), msn = vv.getMsn().getText().toUpperCase();

				try {
					md.checkPilot(id_pilot);
					md.checkVehicle(msn);

					md.insertPilotOnVehicle(id_pilot, msn);
					Session.getIstance().setId_pilot(id_pilot);

					mv.setId_p();

					String id = Session.getIstance().getId_staff();
					md.insertMeccOnVehicle(msn, id);

					Session.getIstance().getM().setMSN(msn);

					Session.getIstance().getV().setMSN(msn);
					Session.getIstance().setV(addVehicle());

					updateButtonStates(mv);
					md.insertLogEvent(getID(), "INSERT VEHICLE : " + vv.getMsn().getText().toUpperCase() );

					mv.getInsertVehicleButton().setEnabled(false);
					vv.close();
				} catch (PilotNotFoundException | VehicleNotFoundException ev) {
					System.out.println(ev);
					vv.mex();
					return;
				}

			}

		});

	}

	private void updateButtonStates(MeccanicoView mv) {
		boolean isVehiclePresent = (Session.getIstance().getV() != null);

		mv.getAddComponentButton().setEnabled(isVehiclePresent);
		mv.getAddComponentButton().setVisible(true);

		mv.getAddPilotButton().setEnabled(isVehiclePresent);
		mv.getAddPilotButton().setVisible(true);

		mv.getRemovePilotButton().setEnabled(isVehiclePresent);
		mv.getRemovePilotButton().setVisible(true);

		mv.getRemoveComponentButton().setEnabled(isVehiclePresent);
		mv.getRemoveComponentButton().setVisible(true);

		mv.getVisualTimePsButton().setEnabled(isVehiclePresent);
		mv.getVisualTimePsButton().setVisible(true);
	}

	//metodo per information hiding
	private Vehicle addVehicle() {
		return Session.getIstance().getM().addVehicle();
	}
	
	private String getID() {
		return Session.getIstance().getId_staff();
	}
	
	
	// Metodo per mostrare la finestra
	public void showWindow() {
		vv.show();
	}

	public void clear() {
		vv.clearComponents(vv.getSendPanel());
	}

}
