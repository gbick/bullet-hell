package starter;

import acm.graphics.GRect;
import javafx.util.Pair;

public interface Obstacle {
	public GRect getSprite();
	public Path getPath();
	public Pair<Double, Double> getNextLoc();
	public double hit(Bullet bullet);
}
