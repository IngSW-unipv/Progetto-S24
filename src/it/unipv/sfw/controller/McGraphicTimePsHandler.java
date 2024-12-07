package it.unipv.sfw.controller;

import java.util.ArrayList;

import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicTimePsView;

public class McGraphicTimePsHandler { 
	
	private ArrayList<String> labelTime = new ArrayList<>();
	private ArrayList<String> anomalyLabelTime = new ArrayList<>();
	
	private McGraphicTimePsView gtpv  = new McGraphicTimePsView(Session.getIstance().getM().getAllTimePitStop(), labelTime);
	
	public McGraphicTimePsHandler() {

	}
	
	public void initialize(){

		 for(Integer t : Session.getIstance().getM().getAllTimePitStop()) {
			 
			 System.out.println("tempo: " + t);
			 
		 }
		 
		createLabels();
		gtpv.anomalyTime(anomalyLabelTime);
		showWindow();
		
	}
	
	public void createLabels() {
		
		for(Integer time : Session.getIstance().getM().getAllTimePitStop()) {
			labelTime.add(convertTime(time));	
		}
		
		for(Integer  time : Session.getIstance().getM().getAnomalyTime()) {
			anomalyLabelTime.add(convertTime(time));
		}
	}
	
    // Metodo per convertire i millisecondi in un formato "secondi.millisecondi"
    private String convertTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;

        return String.format("%02d.%03d", seconds, milliseconds);
    }

	public void showWindow() {
		gtpv.show();
	}
}
