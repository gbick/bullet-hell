package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	private final static int LABEL_DIFFERENTIAL = 125;
	private final static int BAR_LENGTH = 400;
	private final static int BAR_X = 90;
	private final static int SUPER_DIFFERENTIAL = 25;
	private final static int HEALTH_DIFFERENTIAL = 5;
	private final static int BAR_WIDTH = 10;
	private final static int PLAYER_X = 250;
	private final static int PLAYER_Y = 543;
	private final static int TICK_RATE = 50;
	
	private GRect gameSection;
	private GLabel healthBarLabel;
	private GLabel superShotLabel;
	private GLabel livesLabel;
	private GLabel statsLabel;
	private GLabel pointsLabel;
	private GLabel killsLabel;
	private GLabel shotsLabel;
	private GLabel accuracyLabel;
	private GRoundRect healthBar;
	private GRoundRect superBar;
	private ArrayList<Bullet> bullets;
	private ArrayList<Obstacle> enemies;
	private ArrayList<SuperShot> superShot;
	private int timerRuns;
	private int kills = 0, shot = 0;
	private double accuracy;
	private RandomGenerator random;
	private int health = 100;
	private GLabel healthLabel;
	private GRoundRect insideHealthBar;
	private GLabel superLabel;
	private double superShotPercent = 0;
	private GRoundRect insideSuperBar;
	private boolean mouseDown = false;
	private ArrayList<GRoundRect> bars;
	private int points = 0;
	private LevelReader read;
	private int ticks;
	private boolean spawnBoss = true;
	
	public GameScreen(MainApplication app)
	{
		/*
		 * Set up Screen objects
		 *  - Creation of on-screen objects
		 */
		
		//Creation of on-screen objects
		this.program = app;
		
		gameSection = new GRect(GAME_SCREEN_MARGIN, GAME_SCREEN_MARGIN, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		healthBarLabel = new GLabel("Health:", 50, gameSection.getY() + gameSection.getHeight() + 15);
		superShotLabel = new GLabel("Super Shot:", 25, gameSection.getY() + gameSection.getHeight() + 35);
		livesLabel = new GLabel("Lives x", program.getWidth()-LABEL_DIFFERENTIAL, program.getHeight()-25);
		statsLabel = new GLabel("Stats: ", program.getWidth()-LABEL_DIFFERENTIAL, GAME_SCREEN_MARGIN * 2);
		pointsLabel = new GLabel("Points: 0", program.getWidth()-LABEL_DIFFERENTIAL,GAME_SCREEN_MARGIN * 4);
		killsLabel = new GLabel("Kills: 0", program.getWidth()-LABEL_DIFFERENTIAL, GAME_SCREEN_MARGIN * 6);
		shotsLabel = new GLabel("Shots: 0", program.getWidth()-LABEL_DIFFERENTIAL, GAME_SCREEN_MARGIN * 8);
		accuracyLabel = new GLabel("Accuracy: 00.00%", program.getWidth()-LABEL_DIFFERENTIAL, GAME_SCREEN_MARGIN * 10);
		healthLabel = new GLabel("HP: 100", program.getWidth()-LABEL_DIFFERENTIAL, program.getHeight()/4);
		superLabel = new GLabel("Supershot: 0%" + superShotPercent + "%", program.getWidth()-LABEL_DIFFERENTIAL, program.getHeight()/3);
		healthBar = new GRoundRect(BAR_X, gameSection.getY() + gameSection.getHeight() + HEALTH_DIFFERENTIAL, BAR_LENGTH, BAR_WIDTH);
		insideHealthBar = new GRoundRect(BAR_X, gameSection.getY() + gameSection.getHeight() + HEALTH_DIFFERENTIAL, BAR_LENGTH, BAR_WIDTH);
		superBar = new GRoundRect(BAR_X, gameSection.getY() + gameSection.getHeight() + SUPER_DIFFERENTIAL, BAR_LENGTH, BAR_WIDTH);
		insideSuperBar = new GRoundRect(BAR_X, gameSection.getY() + gameSection.getHeight() + SUPER_DIFFERENTIAL, 0, BAR_WIDTH);
		bars = new ArrayList<GRoundRect>(Arrays.asList(insideSuperBar, insideHealthBar, healthBar));
		for(GRoundRect bar : bars) {
			bar.setColor(Color.BLACK);
		}
		insideSuperBar.setFillColor(Color.BLUE);
		insideSuperBar.setFilled(true);
		healthBar.setFillColor(Color.RED);
		healthBar.setFilled(false);
		insideHealthBar.setFillColor(Color.RED);
		insideHealthBar.setFilled(true);
		gameSection.setColor(Color.BLACK);
		gameSection.setFilled(true);
		playerShip = new GImage("../media/sprites/player/ship1_32x32.png", PLAYER_X, PLAYER_Y);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Obstacle>();
		superShot = new ArrayList<SuperShot>();
		timerRuns = 0;
		ticks = 0;
		random = RandomGenerator.getInstance();
	}
	
	@Override
	public void showContents() {
		/*
		 * Show screen contents
		 */
		
		resetGame();
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
		program.add(healthBar);
		program.add(insideHealthBar);
		program.add(superBar);
		program.add(insideSuperBar);
		program.add(playerShip);
		program.gameTimer.start();
	}

	@Override
	public void hideContents() {
		/*
		 * Hide screen content (Leaving game screen entirely, reset everything!!)
		 *  - Remove objects
		 *  - Clear enemy array
		 *  - Clear bullets array
		 *  - Clear super shot array
		 */
		
		//Remove objects
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
		program.remove(healthBar);
		program.remove(insideHealthBar);
		program.remove(superBar);
		program.remove(insideSuperBar);
		program.remove(playerShip);
		
		//Clear enemy array
		if (enemies.size() > 0) {
			for(Obstacle enemy : enemies) {
				program.remove(enemy.getSprite());
			}
			enemies.removeAll(enemies);
		}
		
		//Clear bullets array
		if(bullets.size() > 0) {
			for(Bullet bullet : bullets) {
				program.remove(bullet.getSprite());
			}
			bullets.removeAll(bullets);
		}
		
		//Clear super shot array
		if(superShot.size() > 0) {
			for(SuperShot shot : superShot) {
				program.remove(shot.getSprite());
			}
			superShot.removeAll(superShot);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		/*
		 * Key pressed event
		 *  -Pausing
		 *  -Super shot
		 */
		
		//Pausing
		
		if(program.getCurPop() != null) {
			program.getCurPop().keyPressed(e);
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
			program.addPausePop();
			program.gameTimer.stop();
		}
		//Super shot
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
				insideSuperBar.setSize(0, 10);
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		/*
		 * Mouse movement event
		 *  -Player Ship movement
		 */
		
		//Player Ship movement
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
		/*
		 * Mouse pressed event
		 *  -Player Ship shooting
		 */
		
		//Ship shooting
		if(program.getCurPop() != null) {
			program.getCurPop().mousePressed(e);
			return;
		}
		else {
			PlayerBullet temp = new PlayerBullet(5, playerShip, 10);
			bullets.add(temp);
			program.add(temp.bullet);
			shot++;
			shotsLabel.setLabel("Shots: " + shot);
			mouseDown = true;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		/*
		 * Mouse released event
		 *  -Hold shooting
		 */
		
		//Hold shooting
		mouseDown = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		/*
		 * Mouse dragged event
		 *  -Player Ship movement
		 */
		
		//Player Ship movement
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
		 * Action performed event (Triggered every iteration of the main game timer)
		 *  - Rapid Fire
		 *  - Super Shot
		 *  - Check for loss
		 *  - Update accuracy label
		 *  - Bullets
		 *   > Despawning
		 *   > Ship damaged
		 *   > Movement
		 *   > Removal from arrays
		 *   - Obstacles
		 *    > Shooting
		 *    > Player collision
		 *    > Removal from array
		 *   - Update label and timerRuns
		 *   - Random Spawning
		 */
		
		//Rapid Fire
		if (mouseDown && timerRuns % 10 == 0) {
			PlayerBullet temp = new PlayerBullet(5, playerShip, 10);
			bullets.add(temp);
			program.add(temp.bullet);
			shot++;
			shotsLabel.setLabel("Shots: " + shot);
		}
		
		//Super Shot
		if(superShotPercent < 100 && timerRuns % 100 == 0)
		{	
			superShotPercent += 0.5;
			superLabel.setLabel("Supershot: " + superShotPercent + "%");
			insideSuperBar.setSize(insideSuperBar.getWidth()+2, 10);
		}
		
		//Check for loss
		if(health <= 0) {
			program.gameLost = true;
			points += accuracy*10;
			program.addLosePop();
			program.gameTimer.stop();
		}

		//Update accuracy label
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
			try {
				if(temp.getY() < gameSection.getY()) {
					bulletsToRemove.add(bullet);
				}
			}
			catch(NullPointerException f) {
				bulletsToRemove.add(bullet);
			}
			if(temp instanceof GRect && !(temp instanceof GRoundRect) && temp != gameSection && !bullet.checkEnemyBullet()) {
					GPoint tempPoint = new GPoint(bullet.getSprite().getX(), bullet.getSprite().getY());
					bulletsToRemove.add(bullet);
					for(Obstacle obstacle : enemies) {
//						if(temp == obstacle.getSprite() && !bullet.checkEnemyBullet()) { // earlier version, can change back if this doesn't work
						if(tempPoint.getX() > obstacle.getSprite().getX() && tempPoint.getX() < obstacle.getSprite().getX() + obstacle.getSprite().getWidth() && 
								tempPoint.getY() > obstacle.getSprite().getY() && tempPoint.getY() < obstacle.getSprite().getY() + obstacle.getSprite().getHeight() &&
								!bullet.checkEnemyBullet()) {
							if(obstacle.hit(bullet) <= 0) {								
								obstaclesToRemove.add(obstacle);
								kills++;
								points++;
							}
							if(superShotPercent <= 98) {
								superShotPercent += 2;
								superLabel.setLabel("Supershot: " + superShotPercent + "%");
								insideSuperBar.setSize(insideSuperBar.getWidth()+8, 10);
							}
						}
					}
			}
			
			//Ship damaged
			else if(temp == playerShip && bullet.checkEnemyBullet()) {
				bulletsToRemove.add(bullet);
				if(health > 0) {
					health--;
				}
				else if (health <= 0) {
					program.remove(insideHealthBar);
					healthLabel.setLabel("HP: " + 0);
					for(Bullet remove : bullets) {
						bulletsToRemove.add(remove);
					}
					for(Obstacle remove : enemies) {
						obstaclesToRemove.add(remove);
					}
					program.gameLost = true;
					points += accuracy*10;
					program.addLosePop();
					program.gameTimer.stop();
				}
				healthLabel.setLabel("HP: " + health);
				insideHealthBar.setSize(insideHealthBar.getWidth()-4, 10);
			}
			
			//Movement
			Pair<Double, Double> next = bullet.getNextLoc();
			bullet.getSprite().setLocation(next.getKey(), next.getValue());
		}
		
		//Removal from arrays
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
			//Shooting
			if(enemy instanceof Shooter) {
				if(((Shooter) enemy).checkFireRate()) {
					Bullet temp = ((Shooter) enemy).getBulletType();
					temp.getSprite().setLocation(enemy.getSprite().getX() + enemy.getSprite().getWidth()/2, enemy.getSprite().getY() + enemy.getSprite().getHeight());
					bullets.add(temp);
					program.add(temp.getSprite());
				}
			}
			GObject temp = program.getElementAt(enemy.getSprite().getX() + enemy.getSprite().getWidth() + 1, enemy.getSprite().getY() + enemy.getSprite().getHeight()/2);
			
			//Player collision
			if (temp == playerShip) {
				obstaclesToRemove.add(enemy);
				if(health > 0) {
					health -= 5;
					healthLabel.setLabel("HP: " + health);
					if(health <= 0) {
						program.remove(insideHealthBar);
						healthLabel.setLabel("HP: " + 0);
						points += accuracy*10;
						program.addLosePop();
						program.gameTimer.stop();
					}
					else {
						if (health <= 0) {
							healthLabel.setLabel("HP: " + 0);
							program.gameLost = true;
							points += accuracy*10;
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
			
			//Despawning
			else if(enemy.getSprite().getY() + enemy.getSprite().getHeight() > gameSection.getHeight() + gameSection.getY()) {
				obstaclesToRemove.add(enemy);
			}
			Pair<Double, Double> next = enemy.getNextLoc();
			enemy.getSprite().setLocation(next.getKey(), next.getValue());
		}
		
		//Remove from array
		enemies.removeAll(obstaclesToRemove);
		for(Obstacle obstacle : obstaclesToRemove) {
			program.remove(obstacle.getSprite());
		}
		
		//Update label and timerRuns
		killsLabel.setLabel("Kills : " + kills);
		timerRuns++;
		
		//Random Spawning
		if(timerRuns % TICK_RATE == 0) {
			if(read.readLine(ticks) != "BOSS") {
				String line = read.readLine(ticks);
				for(int i = 0; i < 10; i++) {
					Obstacle temp;
					switch(line.charAt(i * 2)) {
					case '1':
						Fighter enemyShip1 = new Fighter(GAME_SCREEN_MARGIN + (i * 50), 0, MovementEquation.SEEK, playerShip);
						BasicBullet shot1 = new BasicBullet(5, enemyShip1.getSprite(), 2);
						temp = new Shooter(enemyShip1, shot1, 50);
						temp.getSprite().setColor(Color.WHITE);
						temp.getSprite().setFillColor(Color.RED);
						enemies.add(temp);
						program.add(temp.getSprite());
						break;
					case '2':
						temp = new Fighter(GAME_SCREEN_MARGIN + (i * 50), 0, MovementEquation.WAVE);
						temp.getSprite().setColor(Color.WHITE);
						temp.getSprite().setFillColor(Color.BLACK);
						enemies.add(temp);
						program.add(temp.getSprite());
						break;
					case '3':
						Fighter enemyShip2 = new Fighter(GAME_SCREEN_MARGIN + (i * 50), 0 , MovementEquation.STRAIGHT);
						WaveBullet shot2 = new WaveBullet(5, enemyShip2.getSprite(), 2, true, true);
						temp = new Shooter(enemyShip2, shot2, 100);
						enemies.add(temp);
						program.add(temp.getSprite());
						break;
					case '4':
						temp = new Fighter(GAME_SCREEN_MARGIN + (i * 50), 0, MovementEquation.CIRCLE);
						enemies.add(temp);
						program.add(temp.getSprite());
						break;
					default:
						break;
					}
				}
			}
			else {			
				double rand = random.nextDouble(0,4);
				Obstacle temp;
				if(spawnBoss) {
					Boss boss = new Boss(GAME_SCREEN_WIDTH/4, GAME_SCREEN_MARGIN, MovementEquation.STAY_SEEK);
					boss.getSprite().setColor(Color.GRAY);
					boss.getSprite().setFillColor(Color.WHITE);
					spawnBoss = false;
					enemies.add(boss);
					program.add(boss.getSprite());
				}
				if(rand < 1 && rand > 0) {				
					Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.SEEK, playerShip);
					BasicBullet shot = new BasicBullet(5, enemyShip.getSprite(), 2);
					temp = new Shooter(enemyShip, shot, 50);
					temp.getSprite().setColor(Color.WHITE);
					temp.getSprite().setFillColor(Color.RED);
				}
				else if(rand > 1 && rand < 2) {
					temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.WAVE);
					temp.getSprite().setColor(Color.WHITE);
					temp.getSprite().setFillColor(Color.BLACK);
				}
				else if(rand > 2 && rand < 3) {
					Fighter enemyShip = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0 , MovementEquation.STRAIGHT);
					WaveBullet shot = new WaveBullet(5, enemyShip.getSprite(), 2, true, true);
					temp = new Shooter(enemyShip, shot, 100);
				}
				else {
					temp = new Fighter(random.nextDouble(0, GAME_SCREEN_WIDTH - 20), 0, MovementEquation.CIRCLE);
				}
				enemies.add(temp);
				program.add(temp.getSprite());
				
			}
			ticks++;
		}
	}
	
	public void resetGame() {
		/*
		 * Reset game (Resets all stats and objects)
		 *  - Reset to default values
		 */
		
		//Reset to default values
		read = new LevelReader(program.getLevel());
		health = 100;
		healthLabel.setLabel("HP: " + health);
		superShotPercent = 0;
		superLabel.setLabel("Supershot: " + superShotPercent + "%");
		program.remove(playerShip);
		program.delPop();
		program.gameTimer.restart();
		insideHealthBar.setSize(400,10);
		insideSuperBar.setSize(0, 10);
		playerShip.setLocation(PLAYER_X, PLAYER_Y);
		shot = 0;
		kills = 0;
		points = 0;
		ticks = 0;
		accuracy = 0;
		shotsLabel.setLabel("Shots: " + shot);
		accuracyLabel.setLabel("Accuracy: 00.00%");
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
	
	public int getPoints() {
		return points;
	}
	
}