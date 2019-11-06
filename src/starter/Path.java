package starter;

import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import javafx.util.Pair;

public class Path {
	private MovementEquation movement;
	private double direction; // slope of movement direction for bullet
	private Pair<Double, Double> coords;
	private GRect object;
	
	public Path(GRect enemy, MovementEquation movementType) {
		object = enemy;
		movement = movementType;
		coords = new Pair<Double, Double>(enemy.getX(), enemy.getY());
	}
	
	public Pair<Double, Double> moveNextTick() {
		switch(movement) {
		case STRAIGHT:
			coords = new Pair<Double, Double>(coords.getKey(), object.getY() + 1);
			break;
		case DIAGONAL:
			coords = new Pair<Double, Double>(object.getX() + 1, object.getY() + 1);
			break;
		case WAVE:
			coords = new Pair<Double, Double>(object.getX() + (Math.sin(object.getY() * 0.1) * 5),object.getY() + 1);
			break;
		default:
			break;
		}
		return coords;
	}
	
	public void setDirection() {
		switch(movement) {
			case STRAIGHT:
				direction = 0;
				break;
			case DIAGONAL:
				direction = .1;
				break;
			case WAVE:
				//TODO write this case - not entirely sure how we wanted to implement this
				break;
		}
	}
}
