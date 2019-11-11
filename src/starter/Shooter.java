package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.util.Pair;

public class Shooter implements Obstacle {
	private double health;
	private Path flightPath;
	private Bullet shotType;
	private GRect sprite; //TODO change to type GImage in all instances
	private int tick;
	private int fireRate;

	public Shooter(Fighter ship, Bullet shot, int rate) {
		//sprite = new GImage("../media/sprites/player/ship1.png", x, y);
		sprite = ship.getSprite();
		sprite.setFillColor(Color.YELLOW);
		sprite.setFilled(true);
		flightPath = ship.getPath();
		health = 5.0;
		shotType = shot;
		tick = 0;
		fireRate = rate;
	}
	
	public Shooter(Fighter ship, Bullet shot, int rate, GImage target) {
		//sprite = new GImage("../media/sprites/player/ship1.png", x, y);
		sprite = ship.getSprite();
		flightPath = ship.getPath();
		health = 5.0;
		tick = 0;
		fireRate = rate;
	}
	
	@Override
	public Pair<Double, Double> getNextLoc() {
		return flightPath.moveNextTick();
	}
	
	public boolean checkFireRate() {
		if(tick == fireRate) {
			tick = 0;
			return true;
		}
		tick++;
		return false;
	}
	
	public Bullet getBulletType() {
		if(shotType instanceof BasicBullet) {
			BasicBullet temp = new BasicBullet(shotType.getDamage(), shotType.getSprite(), shotType.getSpeed(), shotType.getDirection());
			return temp;
		}
		else if(shotType instanceof WaveBullet) {
			WaveBullet temp = new WaveBullet(shotType.getDamage(), shotType.getSprite(), shotType.getSpeed(), shotType.getDirection());
			return temp;
		}
		return null;
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
