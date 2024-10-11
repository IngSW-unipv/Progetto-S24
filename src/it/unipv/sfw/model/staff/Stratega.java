package it.unipv.sfw.model.staff;

import it.unipv.sfw.model.vehicle.Vehicle;

public class Stratega extends Staff{
	
	protected Vehicle vehicle;
	
	protected long timeLap;
	
	public Stratega(String name, String surname, String id, String pwd) {
		super(name, surname, id, pwd);
	}
	
	@Override
	public TypeController getType() {
		return Staff.TypeController.STRATEGA;
	}

}
