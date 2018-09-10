package edu.nd.se2018.homework3;

public class Horse {
	private String name;
	private int number;
	private int maxSpeed;
	private RaceStrategy strat;
	private double distance;

	//constructor
	public Horse(String name, int num, int max, RaceStrategy strat) {
		this.name = name;
		this.number = num;
		this.maxSpeed = max;
		this.strat = strat;
		this.distance = 0;
	}

	/* Getters and setters for all of the member variables */

	public String getName() {
		return this.name;
	}
	public void setName(String n) {
		this.name = n;
	}

	public int getNumber() {
		return this.number;
	}
	public void setName(int n) {
		this.number = n;
	}

	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	public void setMaxSpeed(int s) {
		this.maxSpeed = s;
	}

	public RaceStrategy getRaceStrategy() {
		return this.strat;
	}
	public void setRaceStrategy(RaceStrategy s) {
		this.strat = s;
	}

	public double getDistance() {
		return this.distance;
	}
	public void setDistance(double d) {
		this.distance = d;
	}
}
