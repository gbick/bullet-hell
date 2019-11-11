package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.util.Pair;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private int damage;
	//private GImage sprite;
	private MainApplication program;
	private GRect sprite;
	public SuperShot(int damage, Path bulletPattern) {
		this.damage = damage;
		bulletPattern = new Path(sprite, MovementEquation.STRAIGHT, 20, true);
		sprite = new GRect(100, program.getHeight());
		sprite.setFillColor(Color.BLUE);
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
	public GRect getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Double, Double> getNextLoc() {
		// TODO Auto-generated method stub
		return null;
	}
}
