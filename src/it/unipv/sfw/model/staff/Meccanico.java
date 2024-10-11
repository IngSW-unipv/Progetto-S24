package it.unipv.sfw.model.staff;

import java.util.ArrayList;
import java.util.HashSet;
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
	protected Set<Vehicle> vehicles;
	
	protected String tmePitStop;
	protected ArrayList<String> allTimePitStop;

	public Meccanico(String name, String surname, String id, String pwd, String tmePitStop) {
		super(name, surname, id, pwd);

		vehicles = new HashSet<>();
		this.tmePitStop = tmePitStop;
		allTimePitStop = new ArrayList<>();
	}

	public void addVehicle() {

		MSN = getMSN();
		// String msn = MSN + vehicles.size() +1;
		Vehicle v = new Vehicle(MSN);

		vehicles.add(v);
	}

	public int addComponent(Components c, String MSN) throws WrongReplacementStatusException {

		int mode = 0;
		Vehicle v = new Vehicle(MSN);

		return mode = v.addComponent(c);
	}

	public void removeComponent(Components c, String MSN) throws ComponentNotFoundException {
		Vehicle v = new Vehicle(MSN);

		v.removeComponent(c);

	}

	public int wear(Components c, String MSN) {

		Vehicle v = new Vehicle(MSN);
		int w = 0;

		return w = v.calcWear(c);

	}

	public String setTimePS() {

		Random random = new Random();

		int min = 2000, max = 60000, tmePs = 0;

		tmePs = random.nextInt((min - max) + 1) + min;

		long minutes = tmePs / 60000;

		long seconds = (tmePs % 60000) / 1000;

		long milliseconds = tmePs % 1000;

		checkPS(tmePs);

		allTimePitStop.add(tmePitStop);
		return tmePitStop = minutes + ":" + seconds + "." + milliseconds;

	}

	private void checkPS(int timePS) {

		if (timePS == 2000 || timePS < 3000) {
			System.out.println("Tempo pit stop valido");
		} else {
			System.out.println("Tempo pit stop eccessivo --> verificata anomalia");
		}

	}

	// Ottenere tutti i tempi di pit stop convertiti in secondi
	public ArrayList<Double> getTime() throws NumberFormatException {
		ArrayList<Double> time = new ArrayList<>();

		for (String tm : allTimePitStop) {
			time.add(convertTime(tm));
		}

		return time;
	}

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

	public ArrayList<String> getAllTimePitStop() {
		return allTimePitStop;
	}

	public void setAllTimePitStop(ArrayList<String> allTimePitStop) {
		this.allTimePitStop = allTimePitStop;
	}

	public String getMSN() {
		return MSN;
	}

	public void setMSN(String mSN) {
		MSN = mSN;
	}
}
