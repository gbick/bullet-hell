package starter;

import acm.graphics.GImage;

public class BasicBullet implements Bullet {
	private Path bulletPattern;
	private int damage;
	private GImage sprite;
	// TODO make a sprite for the Bullet
	private MainApplication program;
	
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
