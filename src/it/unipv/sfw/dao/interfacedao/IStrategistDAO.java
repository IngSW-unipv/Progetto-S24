package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.exceptions.VehicleNotFoundException;

public interface IStrategistDAO {
	 public void insertLogEvent(String id_staff, String desc);
	 public void checkVehicle(String msn) throws VehicleNotFoundException;
	 public void insertStrategistOnVehicle(String msn, String id);

}
