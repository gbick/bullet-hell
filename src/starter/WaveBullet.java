package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import javafx.util.Pair;

/*
 * Basic bullet for enemies to fire. 
 * 
 * Default values:
 * Sprite: Small circle
 * Direction: Relative to shooter
 * Path: Straight
 * Damage: 5.0
 */
public class WaveBullet implements Bullet {
	private Path bulletPattern;
	private double damage;
	private double speed;
	public GOval bullet; // will change this when we have sprites for bullets
	// TODO make a sprite for the Bullet
	private boolean direction; //True: down, False: up
	
	public WaveBullet(double damage, GObject shooter, double speed, boolean dir) {
		if(dir) {			
			bullet = new GOval(shooter.getX() + shooter.getWidth()/2, shooter.getY() + shooter.getHeight(), 3, 3);
		}
		else {			
			bullet = new GOval(shooter.getX() + shooter.getWidth()/2, shooter.getY(), 3, 3);
		}
		bullet.setFillColor(Color.RED);
		bullet.setFilled(true);
		direction = dir;
		this.damage = damage;
		this.speed = speed;
		bulletPattern = new Path(bullet, MovementEquation.WAVE, this.speed, dir);
	}
	
	@Override
	public Pair<Double, Double> getNextLoc() {
		return bulletPattern.moveNextTick();
	}
	
	@Override
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public double getDamage() {
		return damage;
	}
	
	@Override
	public boolean getDirection() {
		return direction;
	}

	@Override
	public GOval getSprite() {
		return bullet;
	}


}
