package it.unipv.sfw.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.StGraphicDetailsView;
import it.unipv.sfw.view.StPopUpCreateStrategyView;

public class StPopUpCreateStrategyController {

	private StPopUpCreateStrategyView pcs;
	private StGraphicDetailsView gdv;
	
	private final int setPoint = 81000;
	private int average = 0;
	
	public StPopUpCreateStrategyController(int rc, int tmeLap) {

		pcs = new StPopUpCreateStrategyView();
		
		for(Components c : Session.getIstance().getV().getComponent()) {
			average += c.getWear();
		}
		
		average = average/Session.getIstance().getV().getComponent().size();
		
		if(average > 70) {
			pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS:              " + average);
			pcs.getComponentLabel1().setForeground(Color.GREEN);
		}else if(average >= 50 && average <= 70){
			pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS:              " + average);
			pcs.getComponentLabel1().setForeground(Color.ORANGE);
		}else {
			pcs.getComponentLabel1().setText("DEGRADATION OF COMPONENTS:              " + average);
			pcs.getComponentLabel1().setForeground(Color.RED);
		}
		 
		if(rc == 0) {
			pcs.getMexLabel().setText("SELECTED A STRATEGY");
			pcs.getMexLabel().setForeground(Color.YELLOW);
			
		}else if((tmeLap - setPoint)>0){
			pcs.getMexLabel().setText("RECOMMENDED STRATEGY : PUSH");
			pcs.getMexLabel().setForeground(Color.RED);
		}else {
			pcs.getMexLabel().setText("RECOMMENDED STRATEGY : HOLD THE POSITION");
			pcs.getMexLabel().setForeground(Color.GREEN);
		}

		pcs.getDetailsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Session.getIstance().getV().getComponents();
				gdv = new StGraphicDetailsView(Session.getIstance().getV().getComponent());
				gdv.show();
			}

		});

		pcs.getBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String select = (String) pcs.getBox().getSelectedItem();
				Session.getIstance().setStrategy(select);
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
