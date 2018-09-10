package edu.nd.se2018.homework3;

public class SteadyRunStrategy extends RaceStrategy{

	public SteadyRunStrategy(){}

	@Override
	public double update(int maxSpeed, double currentDistance, double time) {
		double deltaD = maxSpeed*0.8*(time/3600.0);
		return currentDistance + deltaD;
	}

}
