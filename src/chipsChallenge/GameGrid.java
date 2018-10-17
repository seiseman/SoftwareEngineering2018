package chipsChallenge;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
//import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.Point;
import java.util.ArrayList;

public class GameGrid {
	private static GameGrid instance = null;
	protected GameGrid() {};
	public ArrayList<Teleporter> teleporters = new ArrayList<Teleporter>();
	public ArrayList<CircuitStrategy> circuits = new ArrayList<CircuitStrategy>();
	public ArrayList<YellowKeyStrategy> yks = new ArrayList<YellowKeyStrategy>();
	public ArrayList<RedKeyStrategy> rks = new ArrayList<RedKeyStrategy>();
	final int dimensions = 25;
	private int level = 0;
	AutoDoor ad;
	RedDoor rd;
	YellowDoor yd;
	int[][] gameMap = new int[dimensions][dimensions];
	Image tile = new Image("images\\textures\\textures\\BlankTile.png", 25, 25, true, true);
	Image wall = new Image("images\\textures\\textures\\WallTile.png", 25, 25, true, true);
	Image end = new Image("images\\textures\\textures\\endZone.png", 25, 25, true, true);
    Image tp = new Image("images\\textures\\textures\\portal.png", 25, 25, true, true);

    public static GameGrid getInstance() {
		if (instance == null) {
			instance = new GameGrid();
		}
		return instance;
	}

    public void levelOne() {
    	this.level = 1;
    	//build the partitions for level 1
    	for (int i=0; i<25; i++) {
    		this.setGridValue(6, i, -1);
    		this.setGridValue(12, i, -1);
    		this.setGridValue(18, i, -1);
    		this.setGridValue(i, 6, -1);
    		this.setGridValue(i, 12, -1);
    		this.setGridValue(i, 18, -1);
    	}

    	//add the teleporters
    	teleporters.add(new Teleporter(new Point(15, 19), new Point(9,13)));
    	teleporters.add(new Teleporter(new Point(11, 15), new Point(1,21)));
    	teleporters.add(new Teleporter(new Point(3, 23), new Point(21,1)));
    	teleporters.add(new Teleporter(new Point(19, 3), new Point(7,9)));
    	teleporters.add(new Teleporter(new Point(9, 7), new Point(21,23)));
    	teleporters.add(new Teleporter(new Point(21, 19), new Point(15,1)));
    	teleporters.add(new Teleporter(new Point(13, 3), new Point(17,15)));
    	teleporters.add(new Teleporter(new Point(13, 15), new Point(21,17)));
    	teleporters.add(new Teleporter(new Point(21, 13), new Point(17,21)));
    	for (Teleporter t: teleporters) {
    		this.setGridValue(t.src.x, t.src.y, 5);
    		this.setGridValue(t.dst.x, t.dst.y, 5);
    	}

    	//add the circuit to grab and wall to go down
    	circuits.add(new CircuitStrategy(new Point(21, 21)));
    	this.setGridValue(21, 21, 2);
    	ad = new AutoDoor(15, 22);
    	circuits.get(0).addObserver(ad);
    	this.setGridValue(15, 22, 6);



    	//add end gate, teleporter to end gate and block it off
    	teleporters.add(new Teleporter(new Point(15,23), new Point(3,5)));
    	this.setGridValue(15, 23, 5);
    	this.setGridValue(3, 5, 5);
    	this.setGridValue(14, 23, -1);
    	this.setGridValue(14, 22, -1);
    	this.setGridValue(16, 23, -1);
    	this.setGridValue(16, 22, -1);
    	this.setGridValue(3, 1, 1);
    }

    public void levelTwo() {
    	this.level = 2;
    	this.resetLevel();
    	for (int i=0; i<25; i++) {
    		this.setGridValue(i, 8, -1);
    		this.setGridValue(i, 16, -1);
    	}
    	rks.add(new RedKeyStrategy(new Point(10, 10)));
    	yks.add(new YellowKeyStrategy(new Point(13, 4)));
    	rd = new RedDoor(3, 16);
    	yd = new YellowDoor(19, 8);
    	rks.get(0).addObserver(rd);
    	yks.get(0).addObserver(yd);
    	this.setGridValue(10, 10, 3);
    	this.setGridValue(13, 4, 4);
    	this.setGridValue(19, 8, 6);
    	this.setGridValue(3, 16, 6);
    	this.setGridValue(20, 21, 1);
    }

	/*
	 * gameMap[x][y] == -1 --> Wall
	 * gamemap[x][y] ==  0 --> Floor
	 *     ''        ==  1 --> endZone
	 *     ''		 ==  2 --> chipItem
	 *     ''        ==  3 --> Red Key
	 *     ''		 ==  4 --> Yellow Key
	 *     ''        ==  5 --> Teleporter
	 *     ''        ==  6 --> Door
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
				else if (gameMap[x][y] == 2) {
					rect.setFill(new ImagePattern(tile));
				}
				else if (gameMap[x][y] == 3) {
					rect.setFill(new ImagePattern(tile));
				}
				else if (gameMap[x][y] == 4) {
					rect.setFill(new ImagePattern(tile));
				}
				else if (gameMap[x][y] == 5) {
					rect.setFill(new ImagePattern(tp));
				}
				else if (gameMap[x][y] == 6) {
					rect.setFill(new ImagePattern(tile));
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

	public int getLevel() {
		return level;
	}

	private void resetLevel() {
		for (int i=0; i<25; i++) {
			for (int j=0; j<25; j++) {
				this.gameMap[i][j] = 0;
			}
		}
		teleporters.clear();
		circuits.clear();
	}
}
