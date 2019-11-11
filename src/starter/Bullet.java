package starter;

import acm.graphics.GOval;
import javafx.util.Pair;

public interface Bullet {
	public GOval getSprite();
	public Pair<Double, Double> getNextLoc();
	public double getSpeed();
	public double getDamage();
	public boolean getDirection();
}
