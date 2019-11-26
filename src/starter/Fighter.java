package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.util.Pair;

public class Fighter implements Obstacle {
	private double health;
	private Path flightPath;
	private Bullet shotType;
	private GImage sprite; //TODO change to type GImage in all instances

	public Fighter(double x, double y, MovementEquation flightType, int enemyType) {
		//sprite = new GImage("../media/sprites/player/ship1.png", x, y);
		switch(enemyType) {
		case 1:
			sprite = new GImage("../media/sprites/obstacle/enemy_shooter_ship.png", x, y);
			break;
		case 2:
			sprite = new GImage("../media/sprites/obstacle/enemy_rammer_ship.png", x, y);
			break;
		case 3:
			sprite = new GImage("../media/sprites/obstacle/enemy_circler_ship.png", x, y);
			break;
		case 4:
			sprite = new GImage("../media/sprites/obstacle/obstacle_asteroid.png", x, y);
			break;
		default:
			break;
		}
		flightPath = new Path(sprite, flightType, 1, true);
		health = 5.0;
	}
	
	public Fighter(double x, double y, MovementEquation flightType, int enemyType, GImage target) {
		switch(enemyType) {
		case 1:
			sprite = new GImage("../media/sprites/obstacle/enemy_shooter_ship.png", x, y);
			break;
		case 2:
			sprite = new GImage("../media/sprites/obstacle/enemy_rammer_ship.png", x, y);
			break;
		case 3:
			sprite = new GImage("../media/sprites/obstacle/enemy_circler_ship.png", x, y);
			break;
		case 4:
			sprite = new GImage("../media/sprites/obstacle/obstacle_asteroid.png", x, y);
			break;
		default:
			break;
		}
		flightPath = new Path(sprite, flightType, target);
		health = 5.0;
	}
	
	@Override
	public Pair<Double, Double> getNextLoc() {
		return flightPath.moveNextTick();
	}
	
	@Override
	public GImage getSprite() {
		return sprite;
	}
	
	@Override 
	public Path getPath() {
		return flightPath;
	}

	@Override
	public double hit(Bullet bullet) {
		health -= bullet.getDamage();
		return health;
	}

}
