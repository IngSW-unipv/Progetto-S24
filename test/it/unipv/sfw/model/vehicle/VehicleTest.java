package it.unipv.sfw.model.vehicle;

import it.unipv.sfw.exceptions.DuplicateComponentException;
import  it.unipv.sfw.model.component.Components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

class VehicleTest {
	
	@Test
	void addComponentTest() {
		Vehicle v  = new Vehicle("SF24-001");
		
		Components c = new Components(1, "RUOTA ANTERIORE DX HARD");
		try {
			v.addComponent(c);
		} catch (DuplicateComponentException e) {
			fail("Exception thrown");
		}
		
		Set<Components> comps = v.getComponent();
		Components v_comp = comps.iterator().next();
		assertTrue(c.getIdComponent() ==  v_comp.getIdComponent());
	}

}
