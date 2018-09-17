package ColumbusGame;

import java.util.Random;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OceanMap {
	final int dimensions = 25;
	int[][] oceanGrid = new int[dimensions][dimensions];
	Random rand;

	OceanMap() {
		generateIslands();
	}

	//getters and setters
	public int getGridValue(int x, int y) {
		return oceanGrid[x][y];
	}

	public void setGridValue(int x, int y, int val) {
		oceanGrid[x][y] = val;
	}

	public int getDimension() {
		return dimensions;
	}

	//generate 10 random "islands"
	//islands are not allowed to occupy the same space as another island, or the starting
	//position of the player's boat
	private void generateIslands() {
		rand = new Random();
		int islandsPlaced = 0;
		while (islandsPlaced < 10) {
			int x = rand.nextInt(dimensions);
			int y = rand.nextInt(dimensions);
			if (x == 24 && y == 0) {
				continue;
			}
			if (oceanGrid[x][y] != 1) {
				oceanGrid[x][y] = 1;
				islandsPlaced += 1;
			}
		}
	}

	//go through the board filling in each square with the appropriate color
	//for water and islands
	public void drawMap(ObservableList<Node> root, int scale) {
		for (int x=0; x < dimensions; x++) {
			for (int y=0; y < dimensions; y++) {
				Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
				rect.setStroke(Color.BLACK);
				if (oceanGrid[x][y] == 1) {
					rect.setFill(Color.GREEN);
				}
				else if (oceanGrid[x][y] == 2) {
					rect.setFill(Color.PALETURQUOISE);
				}
				else {
					rect.setFill(Color.PALETURQUOISE);
					oceanGrid[x][y] = 0;
				}
				root.add(rect);
			}
		}
	}
}
