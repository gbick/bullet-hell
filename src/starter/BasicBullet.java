package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRoundRect;

public class BasicBullet implements Bullet {
	private Path bulletPattern;
	private double damage;
	public GRoundRect bullet; // will change this when we have sprites for bullets
	// TODO make a sprite for the Bullet
	private MainApplication program;
	
	public BasicBullet(int damage, GImage shooter) {
		this.damage = damage;
		//bulletPattern.setMovementEquation(MovementEquation.STRAIGHT); TODO figure out what the bug is here
		//bulletPattern.setDirection();
		bullet = new GRoundRect(shooter.getX() + shooter.getWidth()/2, shooter.getY(), 3, 7); // TODO will need to adjust to position of player's ship
		bullet.setFillColor(Color.RED);
	}
	
	@Override
	public double getDamage() {
		return damage;
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
