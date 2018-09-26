package edu.nd.sarec.railwaycrossing.model.vehicles;

import java.util.Observable;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the train entity object
 * @author jane
 *
 */
public class Train extends Observable implements IVehicle{
	private double currentX = 0;
	private double currentY = 0;
	private double originalX = 0;
	private Image img;
	private ImageView imgView;
	private int trainLength = 35;
	private int trainSpeed;

	public Train(int x, int y, int velocity, String image){
		this.currentX = x;
		this.currentY = y;
		originalX = x;
		trainSpeed = velocity;
		img = new Image(image,120,trainLength,false,false);
		imgView = new ImageView(img);
		imgView.setX(currentX);
		imgView.setY(currentY);
	}

	public double getVehicleX(){
		return currentX;
	}

	public double getVehicleY(){
		return currentY;
	}

	//use the trains speed to move it instead of a fixed speed and direction
	public void move(){
		currentX -= trainSpeed;
		imgView.setX(currentX);
		setChanged();
		notifyObservers();
	}

	public boolean offScreen(){
		if (currentX < -200 || currentX > 1500)
			return true;
		else
			return false;
	}

	public void reset(){
		currentX = originalX;
	}

	//@Override
	public Node getImageView() {
		return imgView;
	}
}