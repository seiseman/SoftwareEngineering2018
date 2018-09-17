package ColumbusGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OceanExplorer extends Application{

	//members
	Pane root;
	int width = 625;
	int height = 625;
	int islandCount = 10;
	int scalingFactor = 25;
	Image shipImage;
	ImageView shipImageView;
	Image pirateImage1;
	ImageView pirateImageView1;
	Image pirateImage2;
	ImageView pirateImageView2;
	OceanMap map;
	Scene scene;
	Ship chris;
	PirateShip pirate1;
	PirateShip pirate2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage oceanStage) throws Exception {
		root = new AnchorPane();
		map = new OceanMap();
		map.drawMap(root.getChildren(), scalingFactor);

		//put the Columbus ship on the map
		shipImage = new Image("images\\ColumbusShip.png", 25, 25, true, true);
		shipImageView = new ImageView(shipImage);
		chris = new Ship(map);
		shipImageView.setX(chris.getShipLocation().x*scalingFactor);
		shipImageView.setY(chris.getShipLocation().y*scalingFactor);
		root.getChildren().add(shipImageView);

		//put pirate ships 1 and 2 on the map
		pirateImage1 = new Image("images\\pirateship.gif", 25, 25, true, true);
		pirateImage2 = new Image("images\\pirateship.gif", 25, 25, true, true);
		pirateImageView1 = new ImageView(pirateImage1);
		pirateImageView2 = new ImageView(pirateImage2);
		pirate1 = new PirateShip(map);
		pirate2 = new PirateShip(map);
		pirateImageView1.setX(pirate1.getShipLocation().x*scalingFactor);
		pirateImageView1.setY(pirate1.getShipLocation().y*scalingFactor);
		pirateImageView2.setX(pirate2.getShipLocation().x*scalingFactor);
		pirateImageView2.setY(pirate2.getShipLocation().y*scalingFactor);
		root.getChildren().add(pirateImageView1);
		root.getChildren().add(pirateImageView2);

		//make the pirate ships observers of the Columbus ship
		chris.addObserver(pirate1);
		chris.addObserver(pirate2);

		scene = new Scene(root, width, height);
		oceanStage.setScene(scene);
		oceanStage.setTitle("Homework 04");
		oceanStage.show();
		startSailing();
	}

	private void startSailing() {
		//listen for certain keypresses, and as they happen, handle them
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
					case RIGHT:
						chris.goEast();
						break;
					case LEFT:
						chris.goWest();
						break;
					case UP:
						chris.goNorth();
						break;
					case DOWN:
						chris.goSouth();
						break;
					default:
						break;
				}
				//update where the images are on the grid
				shipImageView.setX(chris.getShipLocation().x*scalingFactor);
				shipImageView.setY(chris.getShipLocation().y*scalingFactor);
				pirateImageView1.setX(pirate1.getShipLocation().x*scalingFactor);
				pirateImageView1.setY(pirate1.getShipLocation().y*scalingFactor);
				pirateImageView2.setX(pirate2.getShipLocation().x*scalingFactor);
				pirateImageView2.setY(pirate2.getShipLocation().y*scalingFactor);
			}
		});
	}

}
