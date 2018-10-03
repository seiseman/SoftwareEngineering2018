package chipsChallenge;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChipsChallenge extends Application {

	Pane root;
	int width = 625;
	int height = 625;
	int scalingFactor = 25;
	GameGrid grid;
	Scene scene;
	Image playerImage;
	ImageView playerImageView;
	Chip player;
	Level level;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public void start(Stage gameStage) throws Exception {
		root = new AnchorPane();
		grid = new GameGrid();
		level = Level.getInstance();
		level.loadLevelOne(grid);
		grid.drawGrid(root.getChildren(), scalingFactor);

		playerImage = new Image("images\\textures\\textures\\chipDown.png", 25, 25, true, true);
		playerImageView = new ImageView(playerImage);
		player = new Chip(grid);
		playerImageView.setX(player.getChipLocation().x*scalingFactor);
		playerImageView.setY(player.getChipLocation().y*scalingFactor);
		root.getChildren().add(playerImageView);

		scene = new Scene(root, width, height);
		gameStage.setScene(scene);
		gameStage.setTitle("Chip's Challenge");
		gameStage.show();

		runGame();
	}

	public void runGame() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
					case RIGHT:
						player.goRight();
						break;
					case LEFT:
						player.goLeft();
						break;
					case UP:
						player.goUp();
						break;
					case DOWN:
						player.goDown();
						break;
					case ESCAPE:
						Platform.exit();
					default:
						break;
				}
				//update player
				playerImageView.setX(player.getChipLocation().x*scalingFactor);
				playerImageView.setY(player.getChipLocation().y*scalingFactor);
			}
		});
	}

}
