package it.unipv.sfw.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.StrategaDAO;
import it.unipv.sfw.view.StPopUpCreateStrategyView;

public class StPopUpCreateStrategyController {

	private StPopUpCreateStrategyView pcs;
	private StrategaDAO sd;

	public StPopUpCreateStrategyController() {

		pcs = new StPopUpCreateStrategyView();
		sd = new StrategaDAO();

		pcs.getDetailsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		pcs.getBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String select = (String) pcs.getBox().getSelectedItem();
				pcs.getStrategyLabel2().setText(select);
				pcs.getStrategyLabel2().setForeground(Color.YELLOW);
				
			}

		});
	}

	public void showWindow() {
		// TODO Auto-generated method stub
		pcs.show();
	}
}
