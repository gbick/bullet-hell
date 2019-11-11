package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import javafx.util.Pair;
import acm.graphics.GRect;
import javafx.util.Pair;

public class SuperShot implements Bullet{
	private Path bulletPattern;
	private double damage;
	private double speed;
	private boolean direction;
	//private GImage sprite;
	private MainApplication program;
	private GOval sprite;
	public SuperShot(double damage, GObject shooter, double speed, boolean dir) 
	{
		if(dir)
		{
			sprite = new GOval(shooter.getX() + shooter.getY()/2, shooter.getY() + shooter.getHeight(), 10, program.getHeight());
			
		}
		else
		{
			sprite = new GOval(shooter.getX() + shooter.getWidth()/2, shooter.getY(), 10, 200);
		}
		sprite.setFillColor(Color.BLUE);
		sprite.setFilled(true);
		direction = dir;
		this.damage = damage;
		this.speed = speed;
		bulletPattern = new Path(sprite, MovementEquation.STRAIGHT, this.speed, dir);
	}

	@Override
	public double getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public boolean getDirection() {
		// TODO Auto-generated method stub
		return direction;
	}

	@Override
	public GOval getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public Pair<Double, Double> getNextLoc() {
		// TODO Auto-generated method stub
		return bulletPattern.moveNextTick();
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}
	public boolean checkEnemyBullet() {
		return false;
	}
}
