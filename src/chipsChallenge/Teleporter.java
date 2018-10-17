package chipsChallenge;

import java.awt.Point;

public class Teleporter {
	GameGrid grid;
	Point src;
	Point dst;

	public Teleporter(Point source, Point dest) {
		grid = GameGrid.getInstance();
		src = source;
		dst = dest;
	}

	public Teleporter() {
		grid = GameGrid.getInstance();
	};

	public Point teleport(int x, int y) {
		for (Teleporter t : grid.teleporters) {
			if ((t.src.x == x && t.src.y == y)) {
				return t.dst;
			}
			else if (t.dst.x == x && t.dst.y == y) {
				return t.src;
			}
		}
		return new Point(-1,-1);
	}

}
