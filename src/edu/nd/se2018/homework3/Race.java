package edu.nd.se2018.homework3;

import java.util.Set;
import java.util.HashSet;

public class Race {
	//list of unique horses in the race
	private Set<Horse> horseList = new HashSet<Horse>();

	//constructor
	public Race() {}

	//add a horse to the race, if it is already in the list, it will not be added twice
	public void addHorse(Horse horse) {
		horseList.add(horse);
	}

	//If a horse with the given name is in the list, remove the first occurrence of it
	public int removeHorseByName(String name) {
		for (Horse h : horseList) {
			if (h.getName() == name) {
				horseList.remove(h);
				return 1;
			}
		}
		return 0;
	}
	//If a horse with a given number is in the list, remove the first occurrence of it
	public int removeHorseByNumber(int number) {
		for (Horse h : horseList) {
			if (h.getNumber() == number) {
				horseList.remove(h);
				return 1;
			}
		}
		return 0;
	}

	//print the distance a horse has traveled
	private void printUpdate(Horse h) {
		System.out.printf(h.getName() + " has run %.2f miles.\n", h.getDistance());
	}

	//create and add 5 horses to the list, then begin the running of the race with the update method
	public void startRace() {
		Horse sprint1 = new Horse("Luke", 1, 42, new EarlySprintStrategy());
		Horse sprint2 = new Horse("Leia", 2, 40, new EarlySprintStrategy());
		Horse steady1 = new Horse("Han", 3, 45, new SteadyRunStrategy());
		Horse steady2 = new Horse("Chewy", 4, 39, new SteadyRunStrategy());
		Horse slow1 = new Horse("Vader", 5, 40, new SlowStartStrategy());

		this.addHorse(sprint1);
		this.addHorse(sprint2);
		this.addHorse(steady1);
		this.addHorse(steady2);
		this.addHorse(slow1);

		update();
	}

	//Loops until a horse runs 10 miles; every 2 minutes of "race time" print the current standings
	public void update() {
		double deltaT = 1.0;
		double periodicUpdate = 0.0; //amount of time since last update was printed. in seconds
		double raceTime = 0.0; //how long the race has been going, in seconds
		boolean raceRunning = true;
		while (raceRunning) {
			periodicUpdate += deltaT;
			raceTime += deltaT;
			if (periodicUpdate == 120.0) {
				System.out.println(raceTime/60.0 + " minutes into race:");
			}
			for (Horse h : horseList) {
				//calculate how far the horse travels during the time interval and add it to the current distance
				double d = h.getRaceStrategy().update(h.getMaxSpeed(), h.getDistance(), deltaT);
				h.setDistance(d);
				if (checkForWinner(h)) {
					System.out.println(h.getName() + " wins the race!");
					int minutes = (int)raceTime/60;
					int seconds = (int)raceTime%60;
					System.out.printf("It took " + minutes + ":%02d.", seconds);
					raceRunning = false;
					break;
				}
				//print each horses standing every 2 min
				if (periodicUpdate == 120.0) {
					printUpdate(h);
				}
			}
			if (periodicUpdate >= 120.0) {
				System.out.println(" ");
				periodicUpdate = 0.0;
			}
		}
	}

	//check if the horse has traveled 10 miles or more, if so return true; it is the winner
	public boolean checkForWinner(Horse h) {
		if (h.getDistance() >= 10) {
			return true;
		}
		return false;
	}
}
