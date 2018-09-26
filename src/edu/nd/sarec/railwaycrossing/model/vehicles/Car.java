package edu.nd.sarec.railwaycrossing.model.vehicles;

import java.util.Observable;
import java.util.Observer;

import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
import edu.nd.sarec.railwaycrossing.model.infrastructure.Direction;
import edu.nd.sarec.railwaycrossing.view.CarImageSelector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Represents Car object
 * @author jane
 *
 */
public class Car extends Observable implements IVehicle, Observer{
	private ImageView ivCar;
	private double currentX = 0;
	private double currentY = 0;
	private double originalY = 0;
	private boolean gateDown = false;
	private double leadCarY = -1;  // Current Y position of car directly infront of this one
	private double leadCarX = -1;
	private double speed = 0.5;
	Direction direction;

	/**
	 * Constructor
	 * @param x initial x coordinate of car
	 * @param y initial y coordinate of car
	 * @param direction TODO
	 */
	public Car(int x, int y, Direction direction){
		this.currentX = x;
		this.currentY = y;
		this.direction = direction;
		originalY = y;
		ivCar = new ImageView(CarImageSelector.getImage());
		ivCar.setX(getVehicleX());
		ivCar.setY(getVehicleY());
	}

	@Override
	public Node getImageView() {
		return ivCar;
	}

	public boolean gateIsClosed(){
		return gateDown;
	}

	public double getVehicleX(){
		return currentX;
	}
	public double getVehicleY(){
		return currentY;
	}

	public void move(){
		boolean canMove = true;

		// First case.  Car is at the front of the stopping line.
		if (gateDown && getVehicleY() < 430 && getVehicleY()> 390)
			canMove = false;

		// Second case. Car is too close too other car.
		if (direction == Direction.NORTH || direction == Direction.SOUTH) {
			if (leadCarY != -1  && getDistanceToLeadCarY() < 50)
				canMove = false;
		}
		else if (direction == Direction.EAST || direction == Direction.WEST) {
			if (leadCarX != -1  && getDistanceToLeadCarX() < 50)
				canMove = false;
		}

		//utilize the direction that cars now have
		if (canMove){
			if (direction == Direction.NORTH) {
				currentY-=speed;
				ivCar.setY(currentY);
			}
			else if (direction == Direction.SOUTH) {
				currentY+=speed;
				ivCar.setY(currentY);
			}
			else if (direction == Direction.EAST) {
				currentX+=speed;
				ivCar.setX(currentX);
			}
			else if (direction == Direction.WEST) {
				currentX-=speed;
				ivCar.setX(currentX);
			}
		}
		setChanged();
		notifyObservers();
	}

	//if a car is within the window to turn and on the right road, see if it turns
	public boolean turn() {
		if (currentY < 694 && currentY > 690 && direction == Direction.SOUTH && currentX == 791) {
			double turnChance = (Math.random());
			if (turnChance <= .2) {
				direction = Direction.WEST;
				removeLeadCar();
				currentY = 691;
				return true;
			}
		}
		else if (currentX < 390 && direction == Direction.WEST) {
			direction = Direction.SOUTH;
			currentX = 391;
			return true;
		}
		return false;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public void setGateDownFlag(boolean gateDown){
		this.gateDown = gateDown;
	}

	public boolean offScreen(){
		if (currentY > 1020)
			return true;
		else
			return false;
	}

	public void reset(){
		currentY = originalY;
	}

	public double getDistanceToLeadCarY(){
		return Math.abs(leadCarY-getVehicleY());
	}

	public double getDistanceToLeadCarX() {
		return Math.abs(leadCarX-getVehicleX());
	}

	public void removeLeadCar(){
		leadCarY = -1;
		leadCarX = -1;
	}

	@Override
	public void update(Observable o, Object arg1) {
		if (o instanceof Car){
			leadCarY = (((Car)o).getVehicleY());
			leadCarX = (((Car)o).getVehicleX());
			if (leadCarY > 1020 || ((Car)o).direction != direction) {
				leadCarY = -1;
				leadCarX = -1;
			}
		}

		if (o instanceof CrossingGate){
			CrossingGate gate = (CrossingGate)o;
			if(gate.getTrafficCommand()=="STOP")
				gateDown = true;
			else
				gateDown = false;

		}
	}
}
