package it.unipv.sfw.model.stratega;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unipv.sfw.model.staff.Strategist;

class StrategaTest {

	@Test
	void setTimeLap() {
		Strategist s = new Strategist("01MR","MRpwd123");
		
		s.setTimeLap(122333);
		
		assertTrue(s.getTimeLap()!=0);
	}

}
