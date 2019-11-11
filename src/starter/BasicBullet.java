package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Basic bullet for enemies to fire. 
 * 
 * Default values:
 * Sprite: Small circle
 * Direction: Relative to shooter
 * Path: Straight
 * Damage: 5.0
 */
public class BasicBullet implements Bullet {
	private Path bulletPattern;
	private double damage;
	public GRect bullet; // will change this when we have sprites for bullets
	// TODO make a sprite for the Bullet
	private MainApplication program;
	private boolean direction; //True: down, False: up
	
	public BasicBullet(int damage, GImage shooter, boolean dir) {
		bullet = new GRect(shooter.getX() + shooter.getWidth()/2, shooter.getY(), 3, 10);
		bullet.setFillColor(Color.RED);
		bullet.setFilled(true);
		direction = dir;
		this.damage = damage;
		bulletPattern = new Path(bullet, MovementEquation.STRAIGHT);
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
	public void spawn() {
		// TODO Auto-generated method stub
		program.add(bullet);
	}

	@Override
	public void despawn() {
		// TODO Auto-generated method stub
		program.remove(bullet);
	}

}
