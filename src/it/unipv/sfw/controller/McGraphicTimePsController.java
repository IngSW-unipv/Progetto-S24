package it.unipv.sfw.controller;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicTimePsView;

public class McGraphicTimePsController { 
	private ArrayList<String> labelTime;
	
	private McGraphicTimePsView gtpv  = new McGraphicTimePsView(Session.getIstance().getM().getAllTimePitStop(), labelTime);
	
	public McGraphicTimePsController() {
	}
	
	public void initialize(){
		
		labelTime = new ArrayList<>();
		
		 for(Integer t : Session.getIstance().getM().getAllTimePitStop()) {
			 
			 System.out.println("tempo: " + t);
			 
		 }
		createLabels();
		showWindow();
		
	}
	
	public void createLabels() {
		
		 for(Integer t : Session.getIstance().getM().getAllTimePitStop()) {
			 
			 System.out.println("tempo @createLabels: " + t);
			 
		 }
		
		for(Integer time : Session.getIstance().getM().getAllTimePitStop()) {
			labelTime.add(convertTime(time));	
		}
	}
	
    // Metodo per convertire i millisecondi in un formato "minuti:secondi.millisecondi"
    private String convertTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;

        return String.format("%d:%02d.%03d", minutes, seconds, milliseconds);
    }

	public void showWindow() {
		
		/*
		 *  Posticipa l'esecuzione del codice specificato
		 *  fino a quando la GUI non è pronta. 
		 *  Assicura che qualsiasi modifica avvenga in modo sicuro e al momento giusto 
		 */
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gtpv.createAndShowGui(Session.getIstance().getM().getAllTimePitStop(), labelTime);
			}
			
		});

	}
}
