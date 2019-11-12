package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
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
	private GLabel accuracyLabel;
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
	double accuracy;
	private RandomGenerator random;
	private int health = 100;
	private GLabel healthLabel;
	private GRoundRect insideHealthBar;
	private GLabel superLabel;
	private double superShotPercent = 0;
	private GRoundRect insideSuperBar;
	private boolean mouseDown = false;
	
	public GameScreen(MainApplication app)
	{
		this.program = app;
		
		gameSection = new GRect(GAME_SCREEN_MARGIN, GAME_SCREEN_MARGIN, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		
		healthBarLabel = new GLabel("Health:", 50, gameSection.getY() + gameSection.getHeight() + 15);
		
		superShotLabel = new GLabel("Super Shot:", 25, gameSection.getY() + gameSection.getHeight() + 35);
		
		livesLabel = new GLabel("Lives x", program.getWidth()-125, program.getHeight()-25);
		
		statsLabel = new GLabel("Stats: ", program.getWidth()-125, GAME_SCREEN_MARGIN * 2);
		
		pointsLabel = new GLabel("Points: 0", program.getWidth()-125,GAME_SCREEN_MARGIN * 4);
		
		killsLabel = new GLabel("Kills: 0", program.getWidth()-125, GAME_SCREEN_MARGIN * 6);
		
		shotsLabel = new GLabel("Shots: 0", program.getWidth()-125, GAME_SCREEN_MARGIN * 8);
		
		accuracyLabel = new GLabel("Accuracy: 00.00%", program.getWidth()-125, GAME_SCREEN_MARGIN * 10);
		
		healthLabel = new GLabel("HP: ", program.getWidth()-125, program.getHeight()/4);
		
		superLabel = new GLabel("Supershot: " + superShotPercent + "%", program.getWidth()-125, program.getHeight()/3);
		
		//bossBarFrame = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-50, 10);
		
		//bossBar = new GRect(program.getWidth()/30, program.getHeight()/30, GAME_SCREEN_WIDTH-45, 8);
		
		healthBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 5, 400, 10); // TODO refactor for flexibility
		insideHealthBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 5, 400, 10);
		
		superBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 25, 400, 10); // TODO refactor for flexibility
		insideSuperBar = new GRoundRect(90, gameSection.getY() + gameSection.getHeight() + 25, 0, 10);
		insideSuperBar.setColor(Color.BLACK);
		insideSuperBar.setFillColor(Color.BLUE);
		insideSuperBar.setFilled(true);
		healthBar.setColor(Color.BLACK);
		healthBar.setFillColor(Color.RED);
		healthBar.setFilled(false);
		insideHealthBar.setColor(Color.BLACK);
		insideHealthBar.setFillColor(Color.RED);
		insideHealthBar.setFilled(true);
		gameSection.setColor(Color.BLACK);
		gameSection.setFilled(true);
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
		if(program.gameLost) {
			resetGame();
		}
		program.add(gameSection);
		program.add(healthBarLabel);
		program.add(superShotLabel);
		program.add(livesLabel);
		program.add(statsLabel);
		program.add(pointsLabel);
		program.add(killsLabel);
		program.add(shotsLabel);
		program.add(healthLabel);
		program.add(superLabel);
		program.add(accuracyLabel);
		//program.add(bossBarFrame);
		//program.add(bossBar);
		program.add(healthBar);
		program.add(insideHealthBar);
		program.add(superBar);
		program.add(insideSuperBar);
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
		program.remove(healthLabel);
		program.remove(superLabel);
		program.remove(accuracyLabel);
		//program.add(bossBarFrame);
		//program.add(bossBar);
		program.remove(healthBar);
		program.remove(insideHealthBar);
		program.remove(superBar);
		program.remove(insideSuperBar);
		program.remove(playerShip);
		
		if (enemies.size() > 0) {
			for(Obstacle enemy : enemies) {
				program.remove(enemy.getSprite());
			}
			enemies.removeAll(enemies);
		}
		
		if(bullets.size() > 0) {
			for(Bullet bullet : bullets) {
				program.remove(bullet.getSprite());
			}
			bullets.removeAll(bullets);
		}
		if(superShot.size() > 0) {
			for(SuperShot shot : superShot) {
				program.remove(shot.getSprite());
			}
			superShot.removeAll(superShot);
		}
		/*
		 *
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
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
			program.addPausePop();
			program.gameTimer.stop();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(program.getCurPop() != null) {
				e.consume();
				return;
			}
			else if (superShotPercent >= 100) {
				SuperShot temp = new SuperShot(50, playerShip, 10, false);
				bullets.add(temp);
				program.add(temp.getSprite());
				shot++;
				shotsLabel.setLabel("Shots: " + shot);
				superShotPercent = 0;
				program.remove(insideSuperBar);
				insideSuperBar.setSize(insideSuperBar.getWidth()+8, 10);
				program.add(insideSuperBar);
			}
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(program.getCurPop() == null) {
			if(e.getX() - playerShip.getWidth()/2 > gameSection.getX() && e.getX() + playerShip.getWidth()/2 < gameSection.getX() + gameSection.getWidth()) {
				playerShip.setLocation(e.getX() - playerShip.getWidth()/2, playerShip.getY());
			}
			else {
				if(e.getX() > gameSection.getX() + gameSection.getWidth()) {				
					playerShip.setLocation((gameSection.getX() + gameSection.getWidth()) - playerShip.getWidth(), playerShip.getY());
				}
				else if(e.getX() < gameSection.getX()) {
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
			mouseDown = true;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
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
		if (mouseDown && timerRuns % 10 == 0) {
			BasicBullet temp = new BasicBullet(5, playerShip, 10, false, false);
			bullets.add(temp);
			program.add(temp.bullet);
			shot++;
			shotsLabel.setLabel("Shots: " + shot);
		}
		if(superShotPercent < 100)
		{	
			if(timerRuns % 100 == 0)
			{
				superShotPercent += 0.5;
				superLabel.setLabel("Supershot: " + superShotPercent + "%");
				insideSuperBar.setSize(insideSuperBar.getWidth()+2, 10);
			}
		}
		if(health <= 0) {
			program.gameLost = true;
			program.addLosePop();
			program.gameTimer.stop();
		}
		
		/*
		 * Movement / Updating
		 */
		//Update accuracy
		if(shot > 0) {			
			accuracy = ((double)kills / (double)shot) * 100;
			DecimalFormat df = new DecimalFormat("#00.00");
			String accuracyFormated = df.format(accuracy);
			accuracyLabel.setLabel("Accuracy: " + accuracyFormated + "%");
		}
		//BULLETS
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Obstacle> obstaclesToRemove = new ArrayList<Obstacle>();
		ArrayList<SuperShot> superShotToRemove = new ArrayList<SuperShot>();
		for(Bullet bullet : bullets) {
			//Despawning
			GObject temp = program.getElementAt(bullet.getSprite().getX() + bullet.getSprite().getWidth() + 1, bullet.getSprite().getY() + bullet.getSprite().getHeight()/2);
			if(temp instanceof GRect && !(temp instanceof GRoundRect) && temp != gameSection) {

					if (!(bullet instanceof SuperShot || bullet.checkEnemyBullet())){
						bulletsToRemove.add(bullet);
					}
					GPoint tempPoint = new GPoint(bullet.getSprite().getX(), bullet.getSprite().getY());
					if (!(gameSection.contains(tempPoint))) {
						bulletsToRemove.add(bullet);
					}
					bulletsToRemove.add(bullet);
					for(Obstacle obstacle : enemies) {
						if(temp == obstacle.getSprite() && !bullet.checkEnemyBullet()) {
							obstaclesToRemove.add(obstacle);
							kills++;
							if(superShotPercent < 100) {
								superShotPercent += 2;
								superLabel.setLabel("Supershot: " + superShotPercent + "%");
								program.remove(insideSuperBar);
								insideSuperBar.setSize(insideSuperBar.getWidth()+8, 10);
								program.add(insideSuperBar);
							}
						}
					}
			}
			if(temp instanceof GImage && bullet.checkEnemyBullet()) {
				bulletsToRemove.add(bullet);
				if(health > 0) {
					health--;
					if(health <= 0) {
						program.remove(insideHealthBar);
						healthLabel.setLabel("HP: " + 0);
						for(Bullet remove : bullets) {
							bulletsToRemove.add(remove);
						}
						for(Obstacle remove : enemies) {
							obstaclesToRemove.add(remove);
						}
						program.gameLost = true;
						program.addLosePop();
						program.gameTimer.stop();
						//break;
						
					}
					else {
						if (health <= 0) {
							healthLabel.setLabel("HP: " + 0);
							for(Bullet remove : bullets) {
								bulletsToRemove.add(remove);
							}
							for(Obstacle remove : enemies) {
								obstaclesToRemove.add(remove);
							}
							program.gameLost = true;
							program.addLosePop();
							program.gameTimer.stop();
							//break;
						}
						else {
							healthLabel.setLabel("HP: " + health);
						}
							program.remove(insideHealthBar);
							insideHealthBar.setSize(insideHealthBar.getWidth()-4, 10);
							program.add(insideHealthBar);
					}
				}
			}
			//Movement
			Pair<Double, Double> next = bullet.getNextLoc();
			bullet.getSprite().setLocation(next.getKey(), next.getValue());
		}
//		for(SuperShot superShots : superShot) {
//			GObject temp = program.getElementAt(superShots.getSprite().getX() + superShots.getSprite().getWidth()+1,superShots.getSprite().getY() - superShots.getSprite().getHeight()/2);
//			if(temp instanceof GRect && temp != gameSection) {
//					superShotToRemove.add(superShots);
//					for(Obstacle obstacle : enemies) {
//						if(temp==obstacle.getSprite()) {
//							obstaclesToRemove.add(obstacle);
//						}
//					}
//					kills++;
//			}
//			Pair<Double, Double> next = superShots.getNextLoc();
//			superShots.getSprite().setLocation(next.getKey(), next.getValue());
//		}
		
		//Removal
		bullets.removeAll(bulletsToRemove);
		for(Bullet bullet : bulletsToRemove) {
			program.remove(bullet.getSprite());
		}
		enemies.removeAll(obstaclesToRemove);
		for(Obstacle obstacle : obstaclesToRemove) {
			program.remove(obstacle.getSprite());
		}
		
		superShot.removeAll(superShotToRemove);
		for(SuperShot superShots : superShot) {
			program.remove(superShots.getSprite());
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
			GObject temp = program.getElementAt(enemy.getSprite().getX() + enemy.getSprite().getWidth() + 1, enemy.getSprite().getY() + enemy.getSprite().getHeight()/2);
			if (temp instanceof GImage) {
				obstaclesToRemove.add(enemy);
				if(health > 0) {
					health -= 5;
					healthLabel.setLabel("HP: " + health);
					if(health <= 0) {
						program.remove(insideHealthBar);
						healthLabel.setLabel("HP: " + 0);
						program.addLosePop();
						program.gameTimer.stop();
						// break;
					}
					else {
						if (health <= 0) {
							healthLabel.setLabel("HP: " + 0);
							program.gameLost = true;
							program.addLosePop();
							program.gameTimer.stop();
							// break;
						}
						else {
							healthLabel.setLabel("HP: " + health);
						}
						program.remove(insideHealthBar);
						insideHealthBar.setSize(insideHealthBar.getWidth()-20, 10);
						program.add(insideHealthBar);
					}
					
				}
			}
			else if(enemy.getSprite().getY() + enemy.getSprite().getHeight() > gameSection.getHeight() + gameSection.getY()) {
				obstaclesToRemove.add(enemy);
			}
			Pair<Double, Double> next = enemy.getNextLoc();
			enemy.getSprite().setLocation(next.getKey(), next.getValue());
		}
		enemies.removeAll(obstaclesToRemove);
		for(Obstacle obstacle : obstaclesToRemove) {
			program.remove(obstacle.getSprite());
		}
		killsLabel.setLabel("Kills : " + kills);
		timerRuns++;
		
		/*
		 * Random spawning
		 */
		if(timerRuns % 100 == 0) {
			double rand = random.nextDouble(0,4);
			if(rand < 1 && rand > 0) {				
				Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.SEEK, playerShip);
				BasicBullet shot = new BasicBullet(5, enemyShip.getSprite(), 2, true, true);
				Shooter temp = new Shooter(enemyShip, shot, 50);
				enemies.add(temp);
				temp.getSprite().setColor(Color.WHITE);
				temp.getSprite().setFillColor(Color.RED);
				program.add(temp.getSprite());
			}
			else if(rand > 1 && rand < 2) {
				Fighter temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.WAVE);
				enemies.add(temp);
				temp.getSprite().setColor(Color.WHITE);
				temp.getSprite().setFillColor(Color.BLACK);
				program.add(temp.getSprite());
			}
			else if(rand > 2 && rand < 3) {
				Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0 , MovementEquation.STRAIGHT);
				WaveBullet shot = new WaveBullet(5, enemyShip.getSprite(), 2, true, true);
				Shooter temp = new Shooter(enemyShip, shot, 100);
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
	public void resetGame() {
		health = 100;
		healthLabel.setLabel("HP: " + health);
		superShotPercent = 0;
		superLabel.setLabel("Supershot: " + superShotPercent + "%");
		program.remove(playerShip);
		program.delPop();
		program.gameTimer.restart();
		insideHealthBar.setSize(400,10);
		for(Bullet bullet : bullets) {
			program.remove(bullet.getSprite());
		}
		bullets.removeAll(bullets);
		for(Obstacle enemy : enemies) {
			program.remove(enemy.getSprite());
		}
		enemies.removeAll(enemies);
		program.gameLost = false;
	}
	
}