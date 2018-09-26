package edu.nd.sarec.railwaycrossing.model.vehicles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import edu.nd.sarec.railwaycrossing.model.infrastructure.Direction;
import edu.nd.sarec.railwaycrossing.model.infrastructure.Road;
import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;


/**
 * Very basic car factory.  Creates the car and registers it with the crossing gate and the car infront of it.
 * @author jane
 *
 */
public class CarFactory {

	private Collection<CrossingGate> gates = null;
	private Car previousCar = null;
	private ArrayList<Car> cars = new ArrayList<Car>();
	Direction direction;
	Point location;
	Road selfRoad = null;
	private boolean spawnCars;


	public CarFactory(){}

	//make the car factory take a reference to its road and a bool if it should build cars
	public CarFactory(Direction direction, Point location, Collection<CrossingGate> gates, Road r, boolean spawnCars){
		this.direction = direction;
		this.location = location;
		this.gates = gates;
		this.spawnCars = spawnCars;
		selfRoad = r;
	}


	// Most code here is to create random speeds
	public Car buildCar(){
		if (spawnCars) {
			if (previousCar == null || location.y < previousCar.getVehicleY()-100){
				Car car = new Car(location.x,location.y, direction);
				double speedVariable = (Math.random() * 10)/10;
				car.setSpeed((2-speedVariable)*1.5);

				// All cars created by this factory must be aware of crossing gates in the road
				for(CrossingGate gate: gates){
					gate.addObserver(car);
					if(gate != null && gate.getTrafficCommand()=="STOP")
						car.setGateDownFlag(true);
				}

				// Each car must observe the car infront of it so it doesn't collide with it.
				if (previousCar != null)
					previousCar.addObserver(car);
				previousCar = car;

				cars.add(car);
				return car;
			} else
				return null;
		}
		return null;
	}

	//insert a car into the arraylist of cars
	public void insertCar(Car c) {
		int addAt = -1;
		if (cars.size() > 0) {
			if (c.direction == Direction.WEST) {
				cars.get(cars.size()-1).deleteObservers();
				cars.get(cars.size()-1).addObserver(c);
			}
			//when southbound, add it roughly to where it belongs in the list
			else if (c.direction == Direction.SOUTH) {
				for (int i=0; i<cars.size(); i++) {
					if (cars.get(i).getVehicleY()+20 < c.getVehicleY()) {
						c.addObserver(cars.get(i));
						addAt = i;
						if (i > 0 && cars.get(i-1).getVehicleY() < 770) {
							cars.get(i-1).deleteObservers();
							cars.get(i-1).addObserver(c);
						}
						break;
					}
				}
			}
		}
		if (addAt > 0) {
			cars.add(addAt, c);
		}
		else {
			cars.add(c);
		}
	}

	// We will get a concurrency error if we try to delete cars whilst iterating through the array list
	// so we perform this in two stages.
	// 1.  Loop through the list and identify which cars are off the screen.  Add them to 'toDelete' array.
	// 2.  Iterate through toDelete and remove the cars from the original arrayList.
	public ArrayList<Car> removeOffScreenCars() {
		// Removing cars from the array list.
		ArrayList<Car> toDelete = new ArrayList<Car>();
		ArrayList<Car> hasLeftRoad = new ArrayList<Car>(); //cars that are no longer on the road being considered; that is they have turned
		for(Car car: cars){
			//if a car turns, first delete all of its observers, then safely extract it from the observer chain
			if (car.turn()) {
				car.deleteObservers();
				if (car.direction == Direction.WEST) {
					int i = cars.indexOf(car);
					if (i < cars.size()-1) {
						cars.get(i+1).removeLeadCar();
					}
					if (i!=0 && i<cars.size()-1) {
						cars.get(i-1).deleteObservers();
						(cars.get(i-1)).addObserver(cars.get(i+1));
					}
					for (Road r : selfRoad.intersections) {
						if (r.getStartY() == r.getEndY()) {
							r.getCarFactory().insertCar(car);
							hasLeftRoad.add(car);
						}
					}
					continue;
				}
				else if (car.direction == Direction.SOUTH) {
					int i = cars.indexOf(car);
					if (i < cars.size()-1) {
						cars.get(i+1).removeLeadCar();
					}
					if (i!=0 && i<cars.size()-1) {
						cars.get(i-1).deleteObservers();
						(cars.get(i-1)).addObserver(cars.get(i+1));
					}
					for (Road r : selfRoad.intersections) {
						if (r.getStartX() == r.getEndX()) {
							//r.getCarFactory().cars.add(car);
							r.getCarFactory().insertCar(car);
							hasLeftRoad.add(car);
						}
					}
					continue;
				}
			}
			car.move();
			if (car.offScreen())
				toDelete.add(car);

		}
		for (Car car: hasLeftRoad) {
			cars.remove(car);
		}
		for (Car car: toDelete)
			cars.remove(car);
		cars.trimToSize();
		return toDelete;
	}
}
