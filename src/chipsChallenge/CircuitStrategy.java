package chipsChallenge;

import java.awt.Point;
import java.util.Observable;

public class CircuitStrategy extends Observable implements CollectibleStrategy {
	GameGrid grid;
	Point loc;
	boolean clearImage = false;

	public CircuitStrategy() {
		grid = GameGrid.getInstance();
	}

	public CircuitStrategy(Point loc) {
		grid = GameGrid.getInstance();
		this.loc = loc;
	}

	@Override
	public void pickup(int x, int y) {
		// TODO Auto-generated method stub
		grid.setGridValue(x, y, 0);
		clearImage = true;
		grid.circuits.clear();
		setChanged();
		notifyObservers();
	}

	public boolean getClearImage() {
		return clearImage;
	}

	public Point getCircuitLoc() {
		return loc;
	}

	public void setCircuitLoc(int x, int y) {
		loc.x = x;
		loc.y = y;
	}

}
