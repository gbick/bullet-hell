package starter;

import acm.graphics.GObject;
import acm.graphics.GOval;
import javafx.util.Pair;

public interface Bullet {
	public GObject getSprite();
	public Pair<Double, Double> getNextLoc();
	public double getSpeed();
	public double getDamage();
	public boolean getDirection();
	public boolean checkEnemyBullet();
}
