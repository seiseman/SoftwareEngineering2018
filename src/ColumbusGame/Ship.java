package ColumbusGame;

import java.util.Observable;
import java.awt.Point;

public class Ship extends Observable {
	private Point position = new Point();
	OceanMap map;

	Ship(OceanMap oceanMap) {
		map = oceanMap;
		position.x = 24;
		position.y = 0;
		map.setGridValue(position.x, position.y, 3);
	}

	//getters and setters
	public Point getShipLocation() {
			return position;
		}

	public void setShipLocation(int x, int y) {
		position.x = x;
		position.y = y;
	}

	//if the space is available, move the ship 1 square in the specified direction
	public void goEast() {
		//check for edge of map
		if (position.x < 24) {
			//check that the target square is empty
			//this was broken up to make the logic more readable
			if (map.getGridValue(position.x+1, position.y) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.x += 1;
				map.setGridValue(position.x, position.y, 3);
				setChanged();
				notifyObservers();
			}
		}
	}

	public void goWest() {
		if(position.x > 0) {
			if (map.getGridValue(position.x-1, position.y) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.x -= 1;
				map.setGridValue(position.x, position.y, 3);
				setChanged();
				notifyObservers();
			}
		}
	}

	public void goNorth() {
		if (position.y > 0) {
			if (map.getGridValue(position.x, position.y-1) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.y -= 1;
				map.setGridValue(position.x, position.y, 3);
				setChanged();
				notifyObservers();
			}
		}
	}

	public void goSouth() {
		if (position.y < 24) {
			if (map.getGridValue(position.x, position.y+1) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.y += 1;
				map.setGridValue(position.x, position.y, 3);
				setChanged();
				notifyObservers();
			}
		}
	}

}
