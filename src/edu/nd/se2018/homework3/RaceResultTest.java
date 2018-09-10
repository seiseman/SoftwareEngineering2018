package edu.nd.se2018.homework3;

import org.junit.Test;

public class RaceResultTest {

	@Test
	public void test() {
		Race race = new Race();
		Horse ace = new Horse("Ace", 1, 35, new EarlySprintStrategy());
		Horse bid = new Horse("Bid", 2, 35, new SteadyRunStrategy());
		Horse can = new Horse("Can", 3, 35, new SlowStartStrategy());
		Horse dug = new Horse("Dug", 4, 34, new SlowStartStrategy());
		Horse ego = new Horse("Ego", 5, 34, new SteadyRunStrategy());

		race.addHorse(ace);
		race.addHorse(bid);
		race.addHorse(can);
		race.addHorse(dug);
		race.addHorse(ego);
		race.update();
		assert(race.checkForWinner(can) == true);
		assert(race.checkForWinner(dug) == false);

	}

}
