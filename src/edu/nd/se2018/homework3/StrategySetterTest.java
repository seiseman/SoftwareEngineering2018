package edu.nd.se2018.homework3;

import org.junit.Test;

public class StrategySetterTest {

	@Test
	public void test() {
		SteadyRunStrategy srs = new SteadyRunStrategy();
		SlowStartStrategy sss = new SlowStartStrategy();
		Horse h = new Horse("Test", 1, 35, new SteadyRunStrategy());
		assert(h.getRaceStrategy().getClass().equals(srs.getClass()));

		h.setRaceStrategy(sss);
		assert(h.getRaceStrategy().getClass().equals(sss.getClass()));
	}

}
