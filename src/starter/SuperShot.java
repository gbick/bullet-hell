package starter;

import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.graphics.GRect;
import javafx.util.Pair;

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
	public double getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getDirection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GOval getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Double, Double> getNextLoc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
}
