package it.unipv.sfw.model.stratega;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unipv.sfw.model.staff.Stratega;

class StrategaTest {

	@Test
	void setTimeLap() {
		Stratega s = new Stratega("01MR","MRpwd123");
		
		s.setTimeLap(122333);
		
		assertTrue(s.getTimeLap()!=0);
	}

}
