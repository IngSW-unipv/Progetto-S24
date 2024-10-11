package it.unipv.sfw.controller;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import it.unipv.sfw.view.McGraphicTimePsView;

public class McGraphicTimePsController {
	private ArrayList<Double> tmPs;
	
	private McGraphicTimePsView gtpv  = new McGraphicTimePsView(tmPs);
	
	public McGraphicTimePsController(ArrayList<Double> tmPs) {
			this.tmPs = tmPs; 
	}
	
	
	public void showWindow() {
		
		/*
		 *  Posticipa l'esecuzione del codice specificato
		 *  fino a quando la GUI non è pronta. Assicura che qualsiasi modifica avvenga in modo sicuro
		 *  e al momento giusto 
		 */
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				gtpv.createAndShowGui(tmPs);
			}
			
		});

	}
}
