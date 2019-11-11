package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.util.Pair;

public class Fighter implements Obstacle {
	private double health;
	private Path flightPath;
	private Bullet shotType;
	private GRect sprite; //TODO change to type GImage in all instances

	public Fighter(double x, double y, MovementEquation flightType) {
		//sprite = new GImage("../media/sprites/player/ship1.png", x, y);
		sprite = new GRect(x, y, 20, 20);
		sprite.setFillColor(Color.BLUE);
		sprite.setFilled(true);
		flightPath = new Path(sprite, flightType, 1, true);
		health = 5.0;
	}
	
	public Fighter(double x, double y, MovementEquation flightType, GImage target) {
		//sprite = new GImage("../media/sprites/player/ship1.png", x, y);
		sprite = new GRect(x, y, 20, 20);
		sprite.setFillColor(Color.RED);
		sprite.setFilled(true);
		flightPath = new Path(sprite, flightType, target);
		health = 5.0;
	}
	
	@Override
	public Pair<Double, Double> getNextLoc() {
		return flightPath.moveNextTick();
	}
	
	@Override
	public GRect getSprite() {
		return sprite;
	}
	
	@Override 
	public Path getPath() {
		return flightPath;
	}

	@Override
	public void hit(Bullet bullet) {
		health -= bullet.getDamage();

	}

}
