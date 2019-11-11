package starter;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import javafx.util.Pair;

public class Path {
	private MovementEquation movement;
	private double direction; // slope of movement direction for bullet
	private Pair<Double, Double> coords;
	private Pair<Double, Double> center;
	private GRect object;
	private GImage target;
	private double num = 0;
	
	public Path(GRect enemy, MovementEquation movementType) {
		object = enemy;
		movement = movementType;
		coords = new Pair<Double, Double>(enemy.getX(), enemy.getY());
	}
	
	public Path(GRect enemy, MovementEquation movementType, GImage headTowards) {
		object = enemy;
		movement = movementType;
		coords = new Pair<Double, Double>(enemy.getX(), enemy.getY());
		target = headTowards;
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
		case SEEK:
			if(target != null) {
				Pair<Double, Double> temp = new Pair<Double, Double>(object.getX(), object.getY());
				//If target is to the right/left of object
				if((target.getX() + target.getWidth()/2) > coords.getKey() + object.getWidth()/2) {
					temp = new Pair<Double, Double>(temp.getKey() + 1, temp.getValue());
				}
				else if((target.getX() + target.getWidth()/2) < coords.getKey() + object.getWidth()/2){
					temp = new Pair<Double, Double>(temp.getKey() - 1, temp.getValue());
				}
				//If target is above/below object
				if((target.getY() + target.getHeight()/2) > coords.getValue() + object.getHeight()/2) {
					temp = new Pair<Double, Double>(temp.getKey(), temp.getValue() + 1);
				}
				else if((target.getY() + target.getHeight()/2) < coords.getValue() + object.getHeight()/2) {
					temp = new Pair<Double, Double>(temp.getKey(), temp.getValue() - 1);
				}
				coords = temp;
			}
			else {
				coords = new Pair<Double, Double>(object.getX(), object.getY());	
			}
			break;
		case CIRCLE:
			if(center == null) {
				center = new Pair<Double, Double>(object.getX(), object.getY());
			}
			if(num == 360) {
				num = 0;
			}
			center = new Pair<Double, Double>(center.getKey(), center.getValue() + 1);
			coords = new Pair<Double, Double>((Math.cos(num) * 30) + center.getKey(), (Math.sin(num) * 30) + center.getValue());
			num += 0.1;
			
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
