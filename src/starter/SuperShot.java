package starter;

import acm.graphics.GImage;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private int damage;
	private GImage sprite;
	private MainApplication program;
	
	public SuperShot(int damage, Path bulletPattern) {
		this.damage = damage;
		this.bulletPattern = bulletPattern;
	}
	
	@Override
	public void spawn() {
		program.add(sprite);
	}
	@Override
	public void despawn() {
		program.remove(sprite);
	}

	@Override
	public double getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getDirection() {
		// TODO Auto-generated method stub
		return false;
	}
}
