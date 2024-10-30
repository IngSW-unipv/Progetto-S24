package it.unipv.sfw.model.staff;

import it.unipv.sfw.model.vehicle.Vehicle;

public class Stratega extends Staff{
	
	protected Vehicle vehicle;
	
	protected long timeLap;
	
	public Stratega(String id, String pwd) {
		super(id, pwd);
	}
	
	@Override
	public TypeController getType() {
		return Staff.TypeController.STRATEGA;
	}

}
