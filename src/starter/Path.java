package starter;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import javafx.util.Pair;

public class Path {
	private MovementEquation movement;
	private double direction; // slope of movement direction for bullet
	private Pair<Double, Double> coords;
	private Pair<Double, Double> center;
	private GObject object; //TODO change to GImage for sprites when implemented
	private GImage target;
	private double num = 0;
	private double tick = 1; //1 = down, -1 = up
	
	public Path(GObject enemy, MovementEquation movementType, double speed,  boolean dir) {
		if(dir) {
			tick = speed;
		}
		else {
			tick = -1 * speed;
		}
		object = enemy;
		movement = movementType;
		coords = new Pair<Double, Double>(enemy.getX(), enemy.getY());
	}
	
	public Path(GObject enemy, MovementEquation movementType, GImage headTowards) {
		//For seeking shots only
		object = enemy;
		movement = movementType;
		coords = new Pair<Double, Double>(enemy.getX(), enemy.getY());
		target = headTowards;
	}
	
	public Pair<Double, Double> moveNextTick() {
		switch(movement) {
		case STRAIGHT:
			coords = new Pair<Double, Double>(object.getX(), object.getY() + tick);
			break;
		case DIAGONAL:
			coords = new Pair<Double, Double>(object.getX() + tick, object.getY() + tick);
			break;
		case WAVE:
			coords = new Pair<Double, Double>(object.getX() + tick * (Math.sin(object.getY() * 0.1) * 2),object.getY() + tick);
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
			center = new Pair<Double, Double>(center.getKey(), center.getValue() + tick);
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
