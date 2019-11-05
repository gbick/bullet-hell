package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.util.RandomGenerator;

public class GameScreen extends GraphicsPane implements ActionListener {

	private MainApplication program;
	
	public GImage playerShip;
	private final static int GAME_SCREEN_HEIGHT = 600;
	private final static int GAME_SCREEN_WIDTH = 500 ;
	
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
	private ArrayList<GRoundRect> enemies; // TODO rewrite this using the actual enemy class type
	private int timerRuns;
	private RandomGenerator random;
	
	public GameScreen(MainApplication app)
	{
		this.program = app;
		
		gameSection = new GRect(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		
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
		bullets = new ArrayList<BasicBullet>();
		enemies = new ArrayList<GRoundRect>();
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
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
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
		if(program.getCurPop() != null) {
			program.getCurPop().mouseMoved(e);
			return;
		} 
		if(e.getX() > gameSection.getX() && e.getX() + playerShip.getWidth() < gameSection.getX() + gameSection.getWidth()) {
			playerShip.setLocation(e.getX() - playerShip.getWidth()/2, playerShip.getY());
		}
		if(e.getY() > gameSection.getY() && e.getY() + playerShip.getHeight() < gameSection.getY() + gameSection.getHeight()) {
			playerShip.setLocation(playerShip.getX(), e.getY() - playerShip.getHeight()/2);
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
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(program.getCurPop() != null) {
			program.getCurPop().mouseDragged(e);
			return;
		}
		if(e.getX() > gameSection.getX() && e.getX() + playerShip.getWidth() < gameSection.getX() + gameSection.getWidth()) {
			playerShip.setLocation(e.getX() - playerShip.getWidth()/2, playerShip.getY());
		}
		if(e.getY() > gameSection.getY() && e.getY() + playerShip.getHeight() < gameSection.getY() + gameSection.getHeight()) {
			playerShip.setLocation(playerShip.getX(), e.getY() - playerShip.getHeight()/2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(BasicBullet bullet : bullets) {
			if(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2) instanceof GRoundRect) {
				enemies.remove(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2));
				program.remove(program.getElementAt(bullet.bullet.getX() + bullet.bullet.getWidth() + 1, bullet.bullet.getY() + bullet.bullet.getHeight()/2));
			}
			bullet.bullet.move(0, -10);
		}
		timerRuns++;
		if(timerRuns % 200 == 0) {
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
			GRoundRect temp = new GRoundRect(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, 20, 20);
			temp.setColor(Color.RED);		
			temp.setFilled(true);
			enemies.add(temp);
			program.add(temp);
			
		}
		if(timerRuns % 2 == 0) {
			for(GRoundRect enemy : enemies) {
				enemy.move(0, 10);
			}
		}
	}
	
}