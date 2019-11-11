package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.graphics.GRoundRect;
import acm.util.RandomGenerator;
import javafx.util.Pair;

public class GameScreen extends GraphicsPane implements ActionListener {

	private MainApplication program;
	
	public GImage playerShip;
	private final static int GAME_SCREEN_HEIGHT = 600;
	private final static int GAME_SCREEN_WIDTH = 500 ;
	private final static int GAME_SCREEN_MARGIN = 10;
	
	private GRect gameSection;
	private GLabel healthBarLabel;
	private GLabel superShotLabel;
	private GLabel livesLabel;
	private GLabel statsLabel;
	private GLabel pointsLabel;
	private GLabel killsLabel;
	private GLabel shotsLabel;
	private GRect bossBarFrame;
	private GRect bossBar;
	private GRoundRect healthBar;
	private GRoundRect superBar;
	private ArrayList<Bullet> bullets;
	//private Timer gameTimer;
	private ArrayList<Obstacle> enemies; // TODO rewrite this using the actual enemy class type
	private ArrayList<SuperShot> superShot;
	private int timerRuns;
	private int kills = 0, shot = 0;
	private RandomGenerator random;
	
	public GameScreen(MainApplication app)
	{
		this.program = app;
		
		gameSection = new GRect(GAME_SCREEN_MARGIN, GAME_SCREEN_MARGIN, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		
		healthBarLabel = new GLabel("Health:", 50, program.getHeight()-35);
		
		superShotLabel = new GLabel("Super Shot:", 50, program.getHeight()-10);
		
		livesLabel = new GLabel("Lives x", program.getWidth()-125, program.getHeight()-25);
		
		statsLabel = new GLabel("Stats: ", program.getWidth()-125, program.getHeight()/8);
		
		pointsLabel = new GLabel("Points: ", program.getWidth()-125, program.getHeight()/7);
		
		killsLabel = new GLabel("Kills: ", program.getWidth()-125, program.getHeight()/6);
		
		shotsLabel = new GLabel("Shots: ", program.getWidth()-125, program.getHeight()/5);
		
		//bossBarFrame = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-50, 10);
		
		//bossBar = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-45, 8);
		
		healthBar = new GRoundRect(90, 605, 400, 10); // TODO refactor for flexibility
		
		superBar = new GRoundRect(114, 630, 400, 10); // TODO refactor for flexibility
		healthBar.setFillColor(Color.MAGENTA); // TODO figure out why this isn't working?
		gameSection.setFillColor(Color.RED); // Or this?
		
		playerShip = new GImage("../media/sprites/player/ship1.png", 250, 543); // TODO refactor
		//gameTimer = new Timer(10, this);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Obstacle>();
		superShot = new ArrayList<SuperShot>();
		timerRuns = 0;
		random = RandomGenerator.getInstance();
	}
	
	@Override
	public void showContents() {
		
		program.add(gameSection);
		program.add(healthBarLabel);
		program.add(superShotLabel);
		program.add(livesLabel);
		program.add(statsLabel);
		program.add(pointsLabel);
		program.add(killsLabel);
		program.add(shotsLabel);
		//program.add(bossBarFrame);
		//program.add(bossBar);
		program.add(healthBar);
		program.add(superBar);
		program.add(playerShip);
		program.gameTimer.start();
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(gameSection);
		program.remove(healthBarLabel);
		program.remove(superShotLabel);
		program.remove(livesLabel);
		program.remove(statsLabel);
		program.remove(pointsLabel);
		program.remove(killsLabel);
		program.remove(shotsLabel);
		//program.add(bossBarFrame);
		//program.add(bossBar);
		program.remove(healthBar);
		program.remove(superBar);
		program.remove(playerShip);
		
		/*
		for(GRoundRect i: enemies.keySet())
		{
			program.remove(i);
		}
		for(BasicBullet i: bullets)
		{
			//add remove function for BasicBullet
		}
		*/
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P)
		{
			program.addPausePop();
			program.gameTimer.stop();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(program.getCurPop() != null) {
				e.consume();
				return;
			}
			else {
				SuperShot temp = new SuperShot(50, playerShip, 50, false);
				superShot.add(temp);
				program.add(temp.getSprite());
				shot++;
				shotsLabel.setLabel("Shots: " + shot);
			}
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(program.getCurPop() == null)
		{
			if(e.getX() - playerShip.getWidth()/2 > gameSection.getX() && e.getX() + playerShip.getWidth()/2 < gameSection.getX() + gameSection.getWidth()) {
				playerShip.setLocation(e.getX() - playerShip.getWidth()/2, playerShip.getY());
			}
			else {
				if(e.getX() > gameSection.getX() + gameSection.getWidth()) {				
					playerShip.setLocation((gameSection.getX() + gameSection.getWidth()) - playerShip.getWidth(), playerShip.getY());
				}
				else if(e.getX() < gameSection.getX()){
					playerShip.setLocation(gameSection.getX(), playerShip.getY());
				}
			}
			if(e.getY() - playerShip.getHeight()/2 > gameSection.getY() && e.getY() + playerShip.getHeight()/2 < gameSection.getY() + gameSection.getHeight()) {
				playerShip.setLocation(playerShip.getX(), e.getY() - playerShip.getHeight()/2);
			}
			else {
				if(e.getY() > gameSection.getY() + gameSection.getHeight()) {
					playerShip.setLocation(playerShip.getX(), (gameSection.getY() + gameSection.getHeight()) - playerShip.getHeight());
				}
				else if(e.getY() < gameSection.getY()) {
					playerShip.setLocation(playerShip.getX(), gameSection.getY());
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(program.getCurPop() != null) {
			program.getCurPop().mousePressed(e);
			return;
		}
		else {
			BasicBullet temp = new BasicBullet(5, playerShip, 10, false);
			bullets.add(temp);
			program.add(temp.bullet);
			shot++;
			shotsLabel.setLabel("Shots: " + shot);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(program.getCurPop() != null) {
			program.getCurPop().mouseDragged(e);
			return;
		}
		if(e.getX() > gameSection.getX() && e.getX() + playerShip.getWidth()/2 < gameSection.getX() + gameSection.getWidth()) {
			playerShip.setLocation(e.getX() - playerShip.getWidth()/2, playerShip.getY());
		}
		if(e.getY() > gameSection.getY() && e.getY() + playerShip.getHeight()/2 < gameSection.getY() + gameSection.getHeight()) {
			playerShip.setLocation(playerShip.getX(), e.getY() - playerShip.getHeight()/2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Movement / Updating
		 */
		//BULLETS
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Obstacle> obstaclesToRemove = new ArrayList<Obstacle>();
		ArrayList<SuperShot> superShotToRemove = new ArrayList<SuperShot>();
		for(Bullet bullet : bullets) {
			//Despawning
			GObject temp = program.getElementAt(bullet.getSprite().getX() + bullet.getSprite().getWidth() + 1, bullet.getSprite().getY() + bullet.getSprite().getHeight()/2);
			if(temp instanceof GRect) {
				if(temp != gameSection) {
					bulletsToRemove.add(bullet);
					for(Obstacle obstacle : enemies) {
						if(temp == obstacle.getSprite()) {
							obstaclesToRemove.add(obstacle);
						}
					}
					kills++;
				}
			}
			//Movement
			Pair<Double, Double> next = bullet.getNextLoc();
			bullet.getSprite().setLocation(next.getKey(), next.getValue());
		}
		
		//Removal
		bullets.removeAll(bulletsToRemove);
		for(Bullet bullet : bulletsToRemove) {
			program.remove(bullet.getSprite());
		}
		enemies.removeAll(obstaclesToRemove);
		for(Obstacle obstacle : obstaclesToRemove) {
			program.remove(obstacle.getSprite());
		}
		//OBSTACLES
		for(Obstacle enemy : enemies) {
			if(enemy instanceof Shooter) {
				if(((Shooter) enemy).checkFireRate()) {
					Bullet temp = ((Shooter) enemy).getBulletType();
					temp.getSprite().setLocation(enemy.getSprite().getX() + enemy.getSprite().getWidth()/2, enemy.getSprite().getY() + enemy.getSprite().getHeight());
					bullets.add(temp);
					program.add(temp.getSprite());
				}
			}
			Pair<Double, Double> next = enemy.getNextLoc();
			enemy.getSprite().setLocation(next.getKey(), next.getValue());
		}
		killsLabel.setLabel("Kills : " + kills);
		timerRuns++;
		
		/*
		 * Random spawning
		 */
		if(timerRuns % 100 == 0) {
//			GPoint[] temp = new GPoint[3];
//			GPoint temp1 = new GPoint(random.nextDouble(0, GAME_SCREEN_WIDTH), 0);
//			if(temp1.getX() > GAME_SCREEN_WIDTH/2) {
//				GPoint temp2 = new GPoint(temp1.getX() - 10, 0);
//				GPoint temp3 = new GPoint((temp1.getX() + temp2.getX() / 2), 10);
//				temp[0] = temp1;
//				temp[1] = temp2;
//				temp[2] = temp3;
//			}
//			else {
//				GPoint temp2 = new GPoint(temp1.getX() + 10, 0);
//				GPoint temp3 = new GPoint((temp1.getX() + temp2.getX() / 2), 10);
//				temp[0] = temp1;
//				temp[1] = temp2;
//				temp[2] = temp3;
//			}
			double rand = random.nextDouble(0,4);
			if(rand < 1 && rand > 0) {				
				Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.SEEK, playerShip);
				BasicBullet shot = new BasicBullet(5, enemyShip.getSprite(), 2, true);
				Shooter temp = new Shooter(enemyShip, shot, 50);
				enemies.add(temp);
				temp.getSprite().setFillColor(Color.RED);
				program.add(temp.getSprite());
			}
			else if(rand > 1 && rand < 2) {
				Fighter temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.WAVE);
				enemies.add(temp);
				temp.getSprite().setFillColor(Color.BLACK);
				program.add(temp.getSprite());
			}
			else if(rand > 2 && rand < 3) {
				Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0 , MovementEquation.STRAIGHT);
				WaveBullet shot = new WaveBullet(5, enemyShip.getSprite(), 2, true);
				Shooter temp = new Shooter(enemyShip, shot, 50);
				enemies.add(temp);
				program.add(temp.getSprite());
			}
			else {
				Fighter temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.CIRCLE);
				enemies.add(temp);
				program.add(temp.getSprite());
			}
			
		}
	}
	
}