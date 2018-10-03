package chipsChallenge;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
//import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameGrid {
	final int dimensions = 25;
	int[][] gameMap = new int[dimensions][dimensions];
	Image tile = new Image("images\\textures\\textures\\BlankTile.png", 25, 25, true, true);
	Image wall = new Image("images\\textures\\textures\\WallTile.png", 25, 25, true, true);
	Image end = new Image("images\\textures\\textures\\endZone.png", 25, 25, true, true);
	//Image hint = new Image("images\\textures\\textures\\hintTile.png", 25, 25, true, true);
	//Image key = new Image("images\\textures\\textures\\yellowKey.png", 25, 25, true, true);
    //Image keyhole = new Image("images\\textures\\textures\\yellowKeyWall.png", 25, 25, true, true);
    Image tp = new Image("images\\textures\\textures\\portal.png", 25, 25, true, true);
    //Image crate = new Image("images\\textures\\textures\\crate.png", 25, 25, true, true);
	/*
	 * gameMap[x][y] == -1 --> Wall
	 * gamemap[x][y] ==  0 --> Floor
	 *     ''        ==  1 --> endZone
	 *     ''		 ==  2 --> Hint
	 *     ''        ==  3 --> Key
	 *     ''		 ==  4 --> Keyhole
	 *     ''        ==  5 --> Teleporter
	 */
	public void drawGrid(ObservableList<Node> root, int scale) {
		for (int x=0; x < dimensions; x++) {
			for (int y=0; y < dimensions; y++) {
				Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
				if (x==0 || x==dimensions-1 || y==0 || y==dimensions-1) {
					rect.setFill(new ImagePattern(wall));
					gameMap[x][y] = -1;
				}
				else if (gameMap[x][y] == -1) {
					rect.setFill(new ImagePattern(wall));
				}
				else if (gameMap[x][y] == 1) {
					rect.setFill(new ImagePattern(end));
					gameMap[x][y] = 1;
				}
				/*else if (gameMap[x][y] == 3) {
					rect.setFill(new ImagePattern(key));
				}
				else if (gameMap[x][y] == 4) {
					rect.setFill(new ImagePattern(keyhole));
				}*/
				else if (gameMap[x][y] == 5) {
					rect.setFill(new ImagePattern(tp));
				}
				else {
					rect.setFill(new ImagePattern(tile));
					gameMap[x][y] = 0;
				}
				root.add(rect);
			}
		}
	}

	public void setGridValue(int x, int y, int val) {
		gameMap[x][y] = val;
	}

	public int getGridValue(int x, int y) {
		return gameMap[x][y];
	}
}
