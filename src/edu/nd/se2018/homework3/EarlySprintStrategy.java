package edu.nd.se2018.homework3;

public class EarlySprintStrategy extends RaceStrategy{

	public EarlySprintStrategy(){}

	@Override
	public double update(int maxSpeed, double currentDistance, double time) {
		double deltaD = 0;
		if (currentDistance < 2.0) {
			deltaD = maxSpeed*1.0*(time/3600.0);
		}
		else {
			deltaD = maxSpeed*0.75*(time/3600.0);
		}
		return currentDistance + deltaD;
	}

}
