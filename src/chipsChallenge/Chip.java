package chipsChallenge;

import java.awt.Point;

public class Chip {
	private Point position = new Point();
	GameGrid grid;

	Chip(GameGrid gameGrid) {
		grid = gameGrid;
		position.x = 12;
		position.y = 12;
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
			case 2:
				//hint block, pop up a display message
				position.x = targetX;
				position.y = targetY;
				break;
			case 3:
				//tile has a key on it. pick it up
				position.x = targetX;
				position.y = targetY;
				//Call some function taht "picks up" the key and notifies doors to open
				grid.setGridValue(targetX, targetY, 0);
				break;
			case 4:
				//tile is a door, check for key
				break;
			case 5:
				//teleporter
				//get the landing pads position and move chip to it.
				if (position.x >13) {
					position.x = 1;
					position.y = 13;
				}
				else {
					position.x = 23;
					position.y = 13;
				}
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
}
