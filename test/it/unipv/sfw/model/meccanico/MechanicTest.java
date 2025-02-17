package it.unipv.sfw.model.meccanico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.vehicle.Vehicle;

class MechanicTest {


	@Test
	void addVehicleTest() {
		Meccanico m = new Meccanico("MM01", "MMpwd123");

		Vehicle v = m.addVehicle();
		
		assertTrue(v != null);
		
	}

	@Test
	void addComponentTest() throws DuplicateComponentException {
		Meccanico m = new Meccanico("MM01", "MMpwd123");
		String MSN = "SF24-001";
		Vehicle v = new Vehicle(MSN);
	
		Components c = new Components(1, "MOTORE TERMICO"); 
		int  mode = m.addComponent(v, c);
		
		assertTrue(mode != 0 );
		
	}
	
	@Test
	void setTimePsTest() {
		Meccanico m = new Meccanico("MM01", "MMpwd123");
		
		int time = m.setTimePS();
		
		assertTrue(time != 0);
	}
}
