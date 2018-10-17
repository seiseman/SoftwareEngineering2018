package chipsChallenge;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class AutoDoor implements Observer{
	GameGrid grid;
	Point loc = new Point();
	boolean openDoor = false;

	public AutoDoor(int x, int y) {
		grid = GameGrid.getInstance();
		loc.x = x;
		loc.y = y;
	}

	public void setLoc(int x, int y) {
		this.loc.x = x;
		this.loc.y = y;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		grid.setGridValue(loc.x, loc.y, 0);
		openDoor = true;
	}


}
