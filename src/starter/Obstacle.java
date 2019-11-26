package starter;

import acm.graphics.GImage;
import javafx.util.Pair;

public interface Obstacle {
	public GImage getSprite();
	public Path getPath();
	public Pair<Double, Double> getNextLoc();
	public double hit(Bullet bullet);
}
