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
 * Sprite: enemy_laser.png
 * Direction: Downwards
 * Path: Straight
 * Damage: 5.0
 */
public class BasicBullet implements Bullet {
	private Path bulletPattern;
	private double damage;
	private double speed;
	public GImage bullet;
	private boolean direction = true; //True: down, False: up
	private boolean enemyBullet = true; // True: belongs to enemy, False: belongs to player
	
	public BasicBullet(double damage, GObject shooter, double speed) {		
		bullet = new GImage("../sprites/obstacle/enemy_laser.png", shooter.getX() + shooter.getWidth()/2, shooter.getY() + shooter.getHeight());
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
