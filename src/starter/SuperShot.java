package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import javafx.util.Pair;
import acm.graphics.GRect;
import javafx.util.Pair;

public class SuperShot implements Bullet {
	private Path bulletPattern;
	private double damage;
	private double speed;
	private boolean direction;
	//private GImage sprite;
	private MainApplication program;
	private GImage sprite;
	public SuperShot(double damage, GObject shooter, double speed, boolean dir) {
		if(dir) {
			sprite = new GImage("../media/sprites/player/ship_laser.png", shooter.getX() + shooter.getY()/2 - 10, shooter.getY() + shooter.getHeight());	
		}
		else {
			sprite = new GImage("../media/sprites/player/ship_laser.png", shooter.getX() + shooter.getWidth()/2 - 10, shooter.getY());
		}
		sprite.scale(10);
		direction = dir;
		this.damage = damage;
		this.speed = speed;
		bulletPattern = new Path(sprite, MovementEquation.STRAIGHT, this.speed, dir);
	}

	@Override
	public double getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public boolean getDirection() {
		// TODO Auto-generated method stub
		return direction;
	}

	@Override
	public GImage getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public Pair<Double, Double> getNextLoc() {
		// TODO Auto-generated method stub
		return bulletPattern.moveNextTick();
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}
	public boolean checkEnemyBullet() {
		return false;
	}
}
