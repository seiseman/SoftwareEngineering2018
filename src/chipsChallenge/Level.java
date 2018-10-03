package chipsChallenge;

public class Level {
	GameGrid grid;

	private static Level instance = null;
	protected Level() {};

	public static Level getInstance() {
		if (instance == null) {
			instance = new Level();
		}
		return instance;
	}

	public void setGrid(GameGrid g) {
		grid = g;
	}

	public void loadLevelOne(GameGrid g) {
		grid = g;
		for (int i=0; i<25; i++) {
			grid.setGridValue(8, i, -1);
		}
		//create teleporters
		grid.setGridValue(23, 13, 5);
		grid.setGridValue(1, 13, 5);

		//create end zone
		grid.setGridValue(4, 3, 1);
	}

	public void loadLevelTwo(GameGrid g) {

	}
}
