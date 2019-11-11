package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private int damage;
	//private GImage sprite;
	private MainApplication program;
	private GRect sprite;
	public SuperShot(int damage, Path bulletPattern) {
		this.damage = damage;
		//this.bulletPattern = bulletPattern;
		sprite = new GRect(100, program.getHeight());
		sprite.setFillColor(Color.BLUE);
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
}
