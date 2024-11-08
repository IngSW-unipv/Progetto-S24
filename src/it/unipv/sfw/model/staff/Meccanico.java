package it.unipv.sfw.model.staff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.WrongReplacementStatusException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.vehicle.Vehicle;

public class Meccanico extends Staff {

	/*
	 * MSN è il numero di serie dell'autovettura
	 */

	private String MSN;
	/*
	 * il team dei meccanici si suddividono in 2 squadre (1 per pilota), ma nel caso
	 * un pilota non potesse gareggiare per un imprevisto il meccanico aggiunge il
	 * veicolo del sostituto. non si utilizza una macchina unica per via delle
	 * caratteristiche di guida diverse.
	 */
	
	// è un set -> no duplicati, linked -> posso simulare un ordinamento
	protected Set<Vehicle> vehicles = new LinkedHashSet<>();

	protected String tmePitStop;
	protected ArrayList<Integer> allTimePitStop = new ArrayList<>();

	public Meccanico(String id, String pwd) {
		super(id, pwd);

	}

	public Vehicle addVehicle() {

		MSN = getMSN();
		// String msn = MSN + vehicles.size() +1;
		Vehicle v = new Vehicle(MSN);
		
		vehicles.add(v);
		
		Vehicle vec = null;
		for (Vehicle vcl : vehicles) {
			vec = vcl; // Alla fine del ciclo, avremo l'ultimo elemento
			System.out.println(vcl);
		}
		
		return vec;
	}

	public int addComponent(Components c) throws WrongReplacementStatusException {

		int mode = 0;

		mode = Session.getIstance().getV().addComponent(c);

		Session.getIstance().getV().showComponent();
		
		return mode;
	}

	public void removeComponent(Components c) throws ComponentNotFoundException {
		
		 Session.getIstance().getV().showComponent();
		 Session.getIstance().getV().removeComponent(c);
		

	}

	public int wear(Components c, String MSN) {

		int w = 0;
		Vehicle v = Session.getIstance().getV();
		
		return w = v.calcWear(c);

	}

	public int setTimePS() {

		Random random = new Random();

		int  min = 2000, max = 60000, tmePs = 0;

		tmePs = random.nextInt((max - min) + 1) + min;

		checkPS(tmePs);
		
		// tmePitStop = minutes + ":" + seconds + "." + milliseconds;
		allTimePitStop.add(tmePs);
		
		return tmePs;

	}

	private void checkPS(int timePS) {

		if (timePS == 2000 || timePS < 3000) {
			System.out.println("Tempo pit stop valido");
		} else {
			System.out.println("Tempo pit stop eccessivo --> verificata anomalia");
		}

	}

	// Ottenere tutti i tempi di pit stop convertiti in secondi
//	public ArrayList<Double> getTime() throws NumberFormatException {
//		ArrayList<Double> time = new ArrayList<>();
//
//		for (String tm : allTimePitStop) {
//			time.add(convertTime(tm));
//		}
//
//		return time;
//	}

	// Metodo per convertire una stringa di tempo (mm:ss.SSS) in secondi decimali
	public double convertTime(String tps) throws NumberFormatException {

		String[] minSec = tps.split(":"), secMilli = tps.split(".");

		int minutes = Integer.parseInt(minSec[0]);
		int seconds = Integer.parseInt(secMilli[0]);
		int milliseconds = Integer.parseInt(secMilli[1]);

		return (minutes * 60) + seconds + (milliseconds / 1000.0);

	}

	@Override
	public TypeController getType() {

		return Staff.TypeController.MECCANICO;
	}

	public Set<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public String getTmePitStop() {
		return tmePitStop;
	}

	public void setTmePitStop(String tmePitStop) {
		this.tmePitStop = tmePitStop;
	}

	public ArrayList<Integer> getAllTimePitStop() {
		return allTimePitStop;
	}

	public void setAllTimePitStop(ArrayList<Integer> allTimePitStop) {
		this.allTimePitStop = allTimePitStop;
	}

	public String getMSN() {
		return MSN;
	}

	public void setMSN(String mSN) {
		MSN = mSN;
	}
}
