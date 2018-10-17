package chipsChallenge;

import java.awt.Point;

import javafx.application.Platform;

public class Chip {
	private Point position = new Point();
	GameGrid grid;
	private boolean onEnd = false;
	Teleporter tp;
	boolean allCircuits = false;
	boolean redKey = false;
	boolean yellowKey = false;

	Chip() {
		grid = GameGrid.getInstance();
		tp = new Teleporter();
		position.x = 15;
		position.y = 21;
	}

	public Point getChipLocation() {
		return position;
	}

	public void setChipLocaion(int x, int y) {
		position.x = x;
		position.y = y;
	}

	private void handleCollisions(int targetX, int targetY) {
		switch(grid.getGridValue(targetX, targetY)) {
			case -1:
				//do nothing, player is not allowed to move here
				break;
			case 1:
				//End tile, reset board and set up level 2
				position.x = targetX;
				position.y = targetY;
				//call next level function
				if (grid.getLevel() != 2) {
					onEnd = true;
				}
				else {
					Platform.exit();
				}
				break;
			case 2:
				//circuit, remove the item
				grid.circuits.get(0).pickup(targetX, targetY);
				position.x = targetX;
				position.y = targetY;
				if (grid.circuits.isEmpty()) {
					allCircuits = true;
				}
				break;
			case 3:
				//tile has a red key on it. pick it up
				//Call some function that "picks up" the key and notifies doors to open
				grid.rks.get(0).pickup(targetX, targetY);
				position.x = targetX;
				position.y = targetY;
				if (grid.rks.isEmpty()) {
					redKey = true;
				}
				break;
			case 4:
				//tile is a yellow key
				grid.yks.get(0).pickup(targetX, targetY);
				position.x = targetX;
				position.y = targetY;
				if (grid.yks.isEmpty()) {
					yellowKey = true;
				}
				break;
			case 5:
				//teleporter
				Point newCoord = tp.teleport(targetX, targetY);
				position.x = newCoord.x;
				position.y = newCoord.y;
			case 6:
				//some type of door
				break;
		}
	}

	public void goRight() {
		// TODO Auto-generated method stub
		if (grid.getGridValue(position.x+1, position.y) != 0) {
			this.handleCollisions(position.x+1, position.y);
		}
		else {
			position.x += 1;
		}
	}

	public void goLeft() {
		// TODO Auto-generated method stub
		if (grid.getGridValue(position.x-1, position.y) != 0) {
			this.handleCollisions(position.x-1, position.y);
		}
		else {
			position.x -= 1;
		}
	}

	public void goUp() {
		// TODO Auto-generated method stub
		if (grid.getGridValue(position.x, position.y-1) != 0) {
			this.handleCollisions(position.x, position.y-1);
		}
		else {
			position.y -= 1;
		}
	}

	public void goDown() {
		// TODO Auto-generated method stub
		if (grid.getGridValue(position.x, position.y+1) != 0) {
			this.handleCollisions(position.x, position.y+1);
		}
		else {
			position.y += 1;
		}
	}

	public boolean getOnEnd() {
		return onEnd;
	}
	public void setOnEnd(boolean set) {
		onEnd = set;
	}
}
