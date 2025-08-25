package it.unipv.sfw.dao.interfacedao;

import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.request.Request;

public interface IWarehousemanDAO {
	public Set<Request> selectAllRequest();
	public void removeRequest(String idc);
	public boolean updateComponent(String id, String wear, String status);
	public int countElement();
	public int countElementBySelect(String select);
	public void checkRequest(String id_s, String id_c, String id_v) throws RequestNotFoundException;
	public void checkCompo(String id_c) throws ComponentNotFoundException;
	public void  insertLogEvent(String id_staff, String desc);
}
