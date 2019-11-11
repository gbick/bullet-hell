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
	private ArrayList<BasicBullet> bullets;
	//private Timer gameTimer;
	private ArrayList<Fighter> enemies; // TODO rewrite this using the actual enemy class type
	private int timerRuns;
	private int kills = 0, shot = 0;
	private RandomGenerator random;
	
	public GameScreen(MainApplication app)
	{
		this.program = app;
		
		gameSection = new GRect(GAME_SCREEN_MARGIN, GAME_SCREEN_MARGIN, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		
		healthBarLabel = new GLabel("Health:", 50, gameSection.getY() + gameSection.getHeight() + 15);
		
		superShotLabel = new GLabel("Super Shot:", 25, gameSection.getY() + gameSection.getHeight() + 35);
		
		livesLabel = new GLabel("Lives x", program.getWidth()-125, program.getHeight()-25);
		
		statsLabel = new GLabel("Stats: ", program.getWidth()-125, program.getHeight()/8);
		
		pointsLabel = new GLabel("Points: ", program.getWidth()-125, program.getHeight()/7);
		
		killsLabel = new GLabel("Kills: ", program.getWidth()-125, program.getHeight()/6);
		
		shotsLabel = new GLabel("Shots: ", program.getWidth()-125, program.getHeight()/5);
		
		//bossBarFrame = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-50, 10);
		
		//bossBar = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-45, 8);
		
		healthBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 5, 400, 10); // TODO refactor for flexibility
		
		superBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 25, 400, 10); // TODO refactor for flexibility
		healthBar.setFillColor(Color.RED); // TODO figure out why this isn't working?
		gameSection.setFillColor(Color.RED); // Or this?
		
		playerShip = new GImage("../media/sprites/player/ship1.png", 250, 543); // TODO refactor
		//gameTimer = new Timer(10, this);
		bullets = new ArrayList<BasicBullet>();
		enemies = new ArrayList<Fighter>();
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
			//add SuperShot methods
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
			BasicBullet temp = new BasicBullet(5, playerShip);
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
		ArrayList<BasicBullet> bulletsToRemove = new ArrayList<BasicBullet>();
		for(BasicBullet bullet : bullets) {
			if(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2) instanceof GRect) {
				if(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2) != gameSection) {		
					enemies.remove(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2));
					program.remove(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2));
					bulletsToRemove.add(bullet);
					kills++;
				}
			}
			bullet.bullet.move(0, -10);
		}
		bullets.removeAll(bulletsToRemove);
		for(BasicBullet bullet : bulletsToRemove) {
			program.remove(bullet.bullet);
		}
		killsLabel.setLabel("Kills : " + kills);
		timerRuns++;
		if(timerRuns % 20 == 0) {
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
			if(random.nextDouble(0, 2) < 1) {				
				Fighter temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.SEEK, playerShip);
				enemies.add(temp);
				program.add(temp.getSprite());
			}
			else {
				Fighter temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.CIRCLE);
				enemies.add(temp);
				program.add(temp.getSprite());
			}
			
		}
		if(timerRuns % 2 == 0) {
			for(Fighter enemy : enemies) {
				Pair<Double, Double> next = enemy.getNextLoc();
				enemy.getSprite().setLocation(next.getKey(), next.getValue());
			}
		}
	}
	
}