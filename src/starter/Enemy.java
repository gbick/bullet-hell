package starter;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Enemy{
	private Obstacle enemy;
	private double loc;
	private GImage target;
	private char type;
	
	public Enemy(char enemyType, double location, GImage seek) {
		Obstacle enemyShooter = null;
		Fighter enemyShip = null;
		Bullet shot;
		type = enemyType;
		loc = location;
		target = seek;
		switch(enemyType) {
		case '1':
			if(seek == null) {
				break;
			}
			enemyShip = new Fighter(location, 0, MovementEquation.SEEK, 1, seek);
			shot = new BasicBullet(5, enemyShip.getSprite(), 2);
			enemyShooter = new Shooter(enemyShip, shot, 50);
			break;
		case '2':
			enemyShip = new Fighter(location, 0, MovementEquation.WAVE, 2);
			break;
		case '3':
			enemyShip = new Fighter(location, 0 , MovementEquation.CIRCLE, 3);
			shot = new WaveBullet(5, enemyShip.getSprite(), 2, true, true);
			enemyShooter = new Shooter(enemyShip, shot, 100);
			break;
		case '4':
			enemyShip = new Fighter(location, 0, MovementEquation.RAND_MOVE, 4);
			break;
		default:
			break;
		}
		if(enemyShooter != null) {
			enemy = enemyShooter;
		}
		else if(enemyShip != null) {
			enemy = enemyShip;
		}
	}
	
	public Obstacle getEnemy() {
		return enemy;
	}
	
	public char getType() {
		return type;
	}
	
	public double getLocation() {
		return loc;
	}
	
	public GImage getTarget() {
		return target;
	}
	
	public GImage getSprite() {
		return enemy.getSprite();
	}
}
