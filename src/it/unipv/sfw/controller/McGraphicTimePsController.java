package it.unipv.sfw.controller;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicTimePsView;

public class McGraphicTimePsController { 
	
	private ArrayList<String> labelTime = new ArrayList<>();
	
	private McGraphicTimePsView gtpv  = new McGraphicTimePsView(Session.getIstance().getM().getAllTimePitStop(), labelTime);
	
	public McGraphicTimePsController() {

	}
	
	public void initialize(){

		 for(Integer t : Session.getIstance().getM().getAllTimePitStop()) {
			 
			 System.out.println("tempo: " + t);
			 
		 }
		 
		createLabels();
		showWindow();
		
	}
	
	public void createLabels() {
		
		for(Integer time : Session.getIstance().getM().getAllTimePitStop()) {
			labelTime.add(convertTime(time));	
		}
	}
	
    // Metodo per convertire i millisecondi in un formato "minuti:secondi.millisecondi"
    private String convertTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;

        return String.format("%02d.%03d", seconds, milliseconds);
    }

	public void showWindow() {
		gtpv.show();
	}
}
