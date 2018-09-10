package edu.nd.se2018.homework3;

import org.junit.Test;

public class StrategyUpdateTest {

	@Test
	public void test() {
		EarlySprintStrategy ess = new EarlySprintStrategy();
		SteadyRunStrategy srs = new SteadyRunStrategy();
		SlowStartStrategy sss = new SlowStartStrategy();
		assert(ess.update(40, 0, 120) == (double)4.0/(double)3.0);
		assert(srs.update(40, 0, 120) == (double)16.0/(double)15.0);
		assert(sss.update(40, 0, 120) == (double)1.0);
	}

}
