package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;

public interface IMechanicDAO {
	 void checkStaff(String id) throws WrongIDException;
	 void checkVehicle(String msn) throws VehicleNotFoundException;
	 boolean insertMeccOnVehicle(String msn, String id);
	 void checkPilot(String id_p) throws PilotNotFoundException;
	 boolean insertPilotOnVehicle(String id_p, String msn);
	 boolean removePilot(String idp);
	 void selectP(String id, String name, String surname, String number) throws PilotNotFoundException;
	 boolean checkPilotOnVehicle(String idp);
	 void checkCompo(String id_c, String name, String status)  throws ComponentNotFoundException;
	 void checkIdCompo(String id_c) throws ComponentNotFoundException;
	 boolean insertComponent(String id, String msn);
	 boolean updateWear(int wear, String id);
	 void removeComponent(String id_c, String id_v);
	 boolean insertRequest(String desc, String id_s, String id_c, String id_v);
	 void checkRequest(String id_c) throws WrongRequestException;
	 void  insertLogEvent(String id_staff, String desc);
	 
}
