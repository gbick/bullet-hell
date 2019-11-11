package starter;

import acm.graphics.GRect;
import javafx.util.Pair;

public interface Bullet {
	public GRect getSprite();
	public Pair<Double, Double> getNextLoc();
	public double getDamage();
	public boolean getDirection();
}
