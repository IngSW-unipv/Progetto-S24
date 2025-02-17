package it.unipv.sfw.model.vehicle;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import  it.unipv.sfw.model.component.Components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

class VehicleTest {
	
	@Test
	void addComponentTest() {
		Vehicle v  = new Vehicle("SF24-001");
		
		Components c = new Components(1, "RUOTA ANTERIORE DX HARD");
		int res = -1;
		try {
			res = v.addComponent(c);
		} catch (DuplicateComponentException e) {
			fail("Exception thrown");
		}
			Set<Components> comps = v.getComponent();
			Iterator<Components> comps_iter = comps.iterator();
			if (!comps_iter.hasNext()) {
				if (res == 3)
					return;
				else 
					fail("Component not added");
			} else {
				Components v_comp = comps_iter.next();
				assertTrue(c.getIdComponent() ==  v_comp.getIdComponent());
			}
	
	}
	
	@Test
	void removeComponentTest() throws ComponentNotFoundException {
		Vehicle v  = new Vehicle("SF24-001");
		Components c = new Components(1, "RUOTA ANTERIORE DX HARD");
		
		Set<Components> comps = new HashSet<>();
		comps.add(c);
		
		v.setComponents(comps);
		
		v.removeComponent(c); 
	
		assertTrue(v.getComponent().isEmpty());
	}
	
	@Test
	void setTimeSectTest(){
		Vehicle v  = new Vehicle("SF24-001");
		
		v.setTimeSect();
		
		assertTrue(v.getTimeSect1() != 0  &&  v.getTimeSect2() != 0 && v.getTimeSect3() != 0 );
		
		
		
		
	}
}
