package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.util.Pair;

public class Boss implements Obstacle{
	
	private final static double MAX_HEALTH = 500000;
	private double health;
	private Path flightPath;
	private Bullet shotType;
	private GRect sprite;
	
	public Boss(double x, double y, MovementEquation flightType)
	{
		sprite = new GRect(x,y,300, 50);
		sprite.setColor(Color.WHITE);
		sprite.setFillColor(Color.GRAY);
		sprite.setFilled(true);
		flightPath = new Path(sprite, flightType, 2, true);
		health = 500000;
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
	public Pair<Double, Double> getNextLoc() {
		return flightPath.moveNextTick();
	}
	@Override
	public void hit(Bullet bullet) {
		health -= bullet.getDamage();
	}
	public double getHealth() {
		return health/MAX_HEALTH;
	}
}