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
 * Sprite: ship_laser.png
 * Direction: Upwards
 * Path: Straight
 * Damage: 5.0
 */
public class PlayerBullet implements Bullet {
	private Path bulletPattern;
	private double damage;
	private double speed;
	public GImage bullet;
	private boolean direction = false; //True: down, False: up
	private boolean enemyBullet = false; // True: belongs to enemy, False: belongs to player
	
	public PlayerBullet(double damage, GObject shooter, double speed) {		
		bullet = new GImage("../sprites/player/ship_laser.png", shooter.getX() + shooter.getWidth()/2, shooter.getY());
		this.damage = damage;
		this.speed = speed;
		bulletPattern = new Path(bullet, MovementEquation.STRAIGHT, this.speed, direction);
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
	
	public GImage getBullet() {
		return bullet;
	}
	@Override
	public boolean checkEnemyBullet() {
		return enemyBullet;
	}
}
