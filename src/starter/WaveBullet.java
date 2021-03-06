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
	public GImage bullet; // will change this when we have sprites for bullets
	// TODO make a sprite for the Bullet
	private boolean direction; //True: down, False: up
	private boolean enemyBullet; // True: belongs to enemy, False: belongs to player
	
	public WaveBullet(double damage, GObject shooter, double speed, boolean dir, boolean enemyBullet) {
		bullet = new GImage("../sprites/obstacle/enemy_ball.png", shooter.getX() + shooter.getWidth()/2, shooter.getY() + shooter.getHeight());
		direction = dir;
		this.damage = damage;
		this.speed = speed;
		bulletPattern = new Path(bullet, MovementEquation.WAVE, this.speed, dir);
		this.enemyBullet = enemyBullet;
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
	public GImage getSprite() {
		return bullet;
	}
	@Override
	public boolean checkEnemyBullet() {
		return enemyBullet;
	}


}
