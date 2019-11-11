package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GOval;
import javafx.util.Pair;
import acm.graphics.GRect;
import javafx.util.Pair;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private int damage;
	//private GImage sprite;
	private MainApplication program;
	private GOval sprite;
	public SuperShot(int damage, Path bulletPattern) {
		this.damage = damage;
		//this.bulletPattern = bulletPattern;
		sprite = new GOval(100, program.getHeight());
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
	public boolean checkEnemyBullet() {
		return false;
	}
}
