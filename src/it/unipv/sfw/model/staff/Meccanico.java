package it.unipv.sfw.model.staff;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.vehicle.Vehicle;

public class Meccanico extends Staff {

	/*
	 * MSN è il numero di serie dell'autovettura
	 */
	private String MSN;
	
	private Vehicle vehicles;

	protected ArrayList<Integer> allTimePitStop = new ArrayList<>();
	protected ArrayList<Integer> anomalyTime = new ArrayList<>();

	public Meccanico(String id, String pwd) {
		super(id, pwd);

	}

	public Vehicle addVehicle() {

		MSN = getMSN();
		Vehicle v = new Vehicle(MSN);
		
		return vehicles = v;
		
	}

	public int addComponent(Vehicle v, Components c) throws DuplicateComponentException {

		int mode = 0;

		mode = v.addComponent(c);

		v.showComponent();
		
		return mode;
	}

	public void removeComponent(Vehicle v, Components c) throws ComponentNotFoundException {
		
		 v.removeComponent(c);
		
	}

	public int setTimePS() {

		Random random = new Random();

		int  min = 2000, max = 4000, tmePs = 0;

		tmePs = random.nextInt((max - min) + 1) + min;

		checkPS(tmePs);
		
		allTimePitStop.add(tmePs);
		
		return tmePs;

	}

	private void checkPS(int timePS) {
		

		if (timePS == 2000 || timePS < 3000) {
			System.out.println("Tempo pit stop valido");
		} else {
			anomalyTime.add(timePS);
			System.out.println("Tempo pit stop eccessivo --> verificata anomalia");
		}

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

	public Vehicle getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle vehicles) {
		this.vehicles = vehicles;
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

	public ArrayList<Integer> getAnomalyTime() {
		return anomalyTime;
	}

	public void setAnomalyTime(ArrayList<Integer> anomalyTime) {
		this.anomalyTime = anomalyTime;
	}
	
}
