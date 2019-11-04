package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class GameScreen extends GraphicsPane{

	private MainApplication program;
	
	private GImage playerShip;
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
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			//add SuperShot methods
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(program.getCurPop() != null) {
			program.getCurPop().mousePressed(e);
			return;
		}
	}
	
}