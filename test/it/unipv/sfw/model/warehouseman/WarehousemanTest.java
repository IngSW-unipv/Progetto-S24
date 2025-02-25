package it.unipv.sfw.model.warehouseman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unipv.sfw.model.request.Request;
import it.unipv.sfw.model.staff.Warehouseman;

class WarehousemanTest {

	@Test
	void totalRequestTest() {
		Warehouseman mg = new Warehouseman("01RC", "RCpwd123");
		Request r = new Request(1,"desc", "staff",1,"vech");
		Set<Request> rs =new HashSet<>();
		rs.add(r);
		
		mg.setRequest(rs);
		
		assertFalse(mg.getRequest().isEmpty());
		
	}

}
