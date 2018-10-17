package chipsChallenge;

import java.awt.Point;
import java.util.Observable;

public class RedKeyStrategy extends Observable implements CollectibleStrategy {
	GameGrid grid;
	Point loc = new Point();
	boolean clearImage = false;

	public RedKeyStrategy() {
		grid = GameGrid.getInstance();
	}

	public RedKeyStrategy(Point loc) {
		grid = GameGrid.getInstance();
		this.loc = loc;
	}

	@Override
	public void pickup(int x, int y) {
		// TODO Auto-generated method stub
		grid.setGridValue(x, y, 0);
		clearImage = true;
		grid.rks.clear();
		setChanged();
		notifyObservers();
	}

}
