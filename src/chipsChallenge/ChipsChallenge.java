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
	CircuitStrategy circuitStrategy;
	Image csImage;
	ImageView csImageView;
	YellowKeyStrategy yellowKeyStrategy;
	Image yksImage;
	ImageView yksImageView;
	RedKeyStrategy redKeyStrategy;
	Image rksImage;
	ImageView rksImageView;
	AutoDoor ad;
	Image adImage;
	ImageView adImageView;
	RedDoor rd;
	Image rdImage;
	ImageView rdImageView;
	YellowDoor yd;
	Image ydImage;
	ImageView ydImageView;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public void start(Stage gameStage) throws Exception {
		root = new AnchorPane();
		grid = GameGrid.getInstance();
		grid.levelOne();
		grid.drawGrid(root.getChildren(), scalingFactor);

		playerImage = new Image("images\\textures\\textures\\chipDown.png", 25, 25, true, true);
		playerImageView = new ImageView(playerImage);
		player = new Chip();
		playerImageView.setX(player.getChipLocation().x*scalingFactor);
		playerImageView.setY(player.getChipLocation().y*scalingFactor);
		root.getChildren().add(playerImageView);

		csImage = new Image("images\\textures\\textures\\chipItem.png", 25, 25, true, true);
		csImageView = new ImageView(csImage);
		circuitStrategy = new CircuitStrategy();
		csImageView.setX(21*scalingFactor);
		csImageView.setY(21*scalingFactor);
		root.getChildren().add(csImageView);

		adImage = new Image("images\\textures\\textures\\crate.png", 25, 25, true, true);
		adImageView = new ImageView(adImage);
		ad = new AutoDoor(15, 22);
		adImageView.setX(15*scalingFactor);
		adImageView.setY(22*scalingFactor);
		root.getChildren().add(adImageView);

		yksImage = new Image("images\\textures\\textures\\yellowKey.png", 25, 25, true, true);
		yksImageView = new ImageView(yksImage);
		yellowKeyStrategy = new YellowKeyStrategy();
		yksImageView.setX(13*scalingFactor);
		yksImageView.setY(4*scalingFactor);

		rksImage = new Image("images\\textures\\textures\\redKey.png", 25, 25, true, true);
		rksImageView = new ImageView(rksImage);
		redKeyStrategy = new RedKeyStrategy();
		rksImageView.setX(10*scalingFactor);
		rksImageView.setY(10*scalingFactor);

		ydImage = new Image("images\\textures\\textures\\yellowKeyWall.png", 25, 25, true, true);
		ydImageView = new ImageView(ydImage);
		yd = new YellowDoor(19, 8);
		ydImageView.setX(19*scalingFactor);
		ydImageView.setY(8*scalingFactor);

		rdImage = new Image("images\\textures\\textures\\redKeyWall.png", 25, 25, true, true);
		rdImageView = new ImageView(rdImage);
		rd = new RedDoor(3, 16);
		rdImageView.setX(3*scalingFactor);
		rdImageView.setY(16*scalingFactor);

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

				//check to see if an item was grabbed
				if (player.allCircuits) {
					root.getChildren().remove(csImageView);
					root.getChildren().remove(adImageView);
				}

				if (player.redKey) {
					root.getChildren().remove(rksImageView);
					root.getChildren().remove(rdImageView);
				}

				if (player.yellowKey) {
					root.getChildren().remove(yksImageView);
					root.getChildren().remove(ydImageView);
				}

				//start level 2
				if (player.getOnEnd() == true) {
					grid.levelTwo();
					grid.drawGrid(root.getChildren(), scalingFactor);
					root.getChildren().add(rksImageView);
					root.getChildren().add(yksImageView);
					root.getChildren().add(ydImageView);
					root.getChildren().add(rdImageView);
					player.setOnEnd(false);

					player.setChipLocaion(2, 2);
					playerImageView.setX(player.getChipLocation().x*scalingFactor);
					playerImageView.setY(player.getChipLocation().y*scalingFactor);
					root.getChildren().remove(playerImageView);
					root.getChildren().add(playerImageView);
				}
			}
		});
	}

}
