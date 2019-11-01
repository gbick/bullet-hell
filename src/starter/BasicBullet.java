package starter;

import acm.graphics.GImage;

public class BasicBullet implements Bullet {
	private Path bulletPattern;
	private int damage;
	private GImage sprite;
	// TODO make a sprite for the Bullet
	private MainApplication program;
	
	public BasicBullet(int damage) {
		this.damage = damage;
		bulletPattern.setMovementEquation(MovementEquation.STRAIGHT);
		bulletPattern.setDirection();
	}
		
	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		program.add(sprite);
	}

	@Override
	public void despawn() {
		// TODO Auto-generated method stub
		program.remove(sprite);
	}

}
