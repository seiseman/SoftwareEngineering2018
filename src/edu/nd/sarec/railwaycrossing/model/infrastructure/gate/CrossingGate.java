package edu.nd.sarec.railwaycrossing.model.infrastructure.gate;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import edu.nd.sarec.railwaycrossing.model.vehicles.Train;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Context class for Crossing Gate
 * @author jane
 *
 */
public class CrossingGate extends Observable implements Observer{

	// Crossing Gate location and its trigger & exit points
	private int anchorX;
	private int anchorY;
	private int movingX;
	private int movingY;
	//there is a trigger and exit on the right and left of each gate
	//right trigger is for north track, right exit is for south track and vice versa
	private int triggerPointR;
	private int exitPointR;
	private int triggerPointL;
	private int exitPointL;
	//set to keep track of trains in the crossing
	private HashSet<Train> inCrossing = new HashSet<Train>();

	private IGateState gateClosed;
	private IGateState gateOpen;
	private IGateState gateClosing;
	private IGateState gateOpening;
	private IGateState currentGateState;
	private Line line;
	private Pane root;

	String gateName;

	public CrossingGate(){}

	public CrossingGate(int xPosition, int yPosition, String crossingGate){
		anchorX = xPosition;
		anchorY = yPosition;
		movingX = anchorX;
		movingY = anchorY-60;
		//triggerL and exitR are the same x position
		triggerPointR = anchorX+250;
		exitPointR = anchorX+250;
		//triggerR and exitL are the same x position
		exitPointL = anchorX-250;
		triggerPointL = anchorX-250;

		// Gate elements
		line = new Line(anchorX, anchorY,movingX,movingY);
		line.setStroke(Color.RED);
	    line.setStrokeWidth(10);

		// Gate States
		gateClosed = new GateClosed(this);
		gateOpen = new GateOpen(this);
		gateOpening = new GateOpening(this);
		gateClosing = new GateClosing(this);
		currentGateState = gateOpen;
		gateName = crossingGate;
	}

	public Line getGateLine(){
		return line;
	}

	public void operateGate(){
		currentGateState.operate();
	}

	public void close(){
		if (movingY<anchorY){
		    movingX+=1;
		    movingY+=1;
			line.setStartX(anchorX);
			line.setStartY(anchorY);
			line.setEndX(movingX);
			line.setEndY(movingY);
		} else {
			currentGateState.gateFinishedClosing();
		}
	}

	public void open(){
		if (movingX>anchorX){
			movingX-=1;
			movingY-=1;
			line.setStartX(anchorX);
			line.setStartY(anchorY);
			line.setEndX(movingX);
			line.setEndY(movingY);
		}  else {
			currentGateState.gateFinishedOpening();
		}
	}

	// State getters and setters
	public IGateState getGateClosedState(){
		return gateClosed;
	}
	public IGateState getGateOpenState(){
		return gateOpen;
	}
	public IGateState getGateClosingState(){
		return gateClosing;
	}
	public IGateState getGateOpeningState(){
		return gateOpening;
	}

	public void setGateState(IGateState newState){
		currentGateState = newState;
		setChanged();
		notifyObservers();
	}

	public String getTrafficCommand(){
		return currentGateState.getTrafficAction();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Train){
			Train train = (Train)o;
			if (train.getVehicleY() == 475) { //North train
				//if the train is in the crossing keep track of it
				if (train.getVehicleX() < triggerPointR && train.getVehicleX() > exitPointL) {
					inCrossing.add(train);
				}
				else { //remove it once it leaves
					inCrossing.remove(train);
				}

				//check if the train has left the station or is approaching it
				if (train.getVehicleX() < exitPointL) {
					if (inCrossing.isEmpty()) {
						currentGateState.leaveStation();
					}
				}
				else if(train.getVehicleX() < triggerPointR) {
					currentGateState.approachStation();
				}
			}
			if (train.getVehicleY() == 525) { //South train
				if (train.getVehicleX() > triggerPointL-35 && train.getVehicleX() < exitPointR) {
					inCrossing.add(train);
				}
				else {
					inCrossing.remove(train);
				}

				if (train.getVehicleX() > exitPointR) {
					if (inCrossing.isEmpty()) {
						currentGateState.leaveStation();
					}
				}
				else if (train.getVehicleX() > triggerPointL-35) {
					currentGateState.approachStation();
				}
			}
		}
	}
}
