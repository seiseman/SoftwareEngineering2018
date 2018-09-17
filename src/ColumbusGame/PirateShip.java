package ColumbusGame;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class PirateShip implements Observer {
	private Point position = new Point();
	Point observed = new Point();
	OceanMap map;
	Random random = new Random();

	//randomly generate coordinates until a free square is found for the pirate ship
	//to start in
	PirateShip(OceanMap oceanMap) {
		map = oceanMap;
		boolean placed = false;
		while (!placed) {
			position.x = random.nextInt(map.getDimension());
			position.y = random.nextInt(map.getDimension());
			//starting position of the player; pirates can't start here
			if (position.x == 24 && position.y == 0) {
				continue;
			}
			if (map.getGridValue(position.x, position.y) == 0) {
				map.setGridValue(position.x, position.y, 2);
				placed = true;
			}
		}
	}

	//getters and setters
	public Point getShipLocation() {
			return position;
		}

	public void setShipLocation(int x, int y) {
		position.x = x;
		position.y = y;
	}

	//if the space is available, more the ship 1 square in the specified
	//direction. Also, mark on the grid that the square is occupied
	public void goEast() {
		//check for edge of map
		if (position.x < 24) {
			//check that the target square is empty
			//this was broken up to make the logic more understandable
			if (map.getGridValue(position.x+1, position.y) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.x += 1;
				map.setGridValue(position.x, position.y, 2);
			}
		}
	}

	public void goWest() {
		if(position.x > 0) {
			if (map.getGridValue(position.x-1, position.y) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.x -= 1;
				map.setGridValue(position.x, position.y, 2);
			}
		}
	}

	public void goNorth() {
		if (position.y > 0) {
			if (map.getGridValue(position.x, position.y-1) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.y -= 1;
				map.setGridValue(position.x, position.y, 2);
			}
		}
	}

	public void goSouth() {
		if (position.y < 24) {
			if (map.getGridValue(position.x, position.y+1) == 0) {
				map.setGridValue(position.x, position.y, 0);
				position.y += 1;
				map.setGridValue(position.x, position.y, 2);;
			}
		}
	}

	//move function for then the pirate observes that the player has moved
	public void movePirate() {
		//differences between the player's and pirates' x and y coordinates
		int xDiff = position.x - observed.x;
		int yDiff = position.y - observed.y;
		//only move, up/down or left/right based on which difference is bigger
		if (Math.abs(xDiff) > Math.abs(yDiff)) {
			//before committing to a move, make sure that the space is available
			if (position.x < 24 && map.getGridValue(position.x+1, position.y) == 0 && xDiff < 0) {
				this.goEast();
			}
			else if(position.x > 0 && map.getGridValue(position.x-1, position.y) == 0 && xDiff > 0) {
				this.goWest();
			}
		}
		else {
			if (position.y > 0 && map.getGridValue(position.x, position.y-1) == 0 && yDiff > 0) {
				this.goNorth();
			}
			else if (position.y < 24 && map.getGridValue(position.x, position.y+1) == 0 && yDiff < 0) {
				this.goSouth();
			}
		}
	}

	//update is the observer method that gets called when the observable, Ship, notifies
	//that it has been updated;
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Ship) {
			observed = ((Ship)o).getShipLocation();
			movePirate();
		}
	}

}
