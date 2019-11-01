package starter;

import acm.graphics.GImage;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private int damage;
	private GImage sprite;
	private MainApplication program;
	
	@Override
	public void spawn() {
		program.add(sprite);
	}
	@Override
	public void despawn() {
		program.remove(sprite);
	}
}
