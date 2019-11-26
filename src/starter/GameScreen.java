package starter;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

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
	private final static String SUPER_SOUND = "SuperShot_Sound.mp3";
	private final static String DEATH = "Enemy_Death.mp3";
	private final static String LEVEL_MUSIC = "Level_Music.mp3";
	private final static String BOSS_MUSIC = "Boss_Music.mp3";
	
	private int timerRuns;
	private int kills = 0, shot = 0, hits = 0;
	private int health = 100;
	private int points = 0;
	private int ticks;
	private double accuracy;
	private double superShotPercent = 0;
	private boolean mouseDown = false;
	private boolean spawnBoss = true;
	private boolean deleteSuper = false;
	private GRect gameSection;
	private GImage frame;
	private GImage background;
	private GLabel healthBarLabel;
	private GLabel superShotLabel;
	private GLabel livesLabel;
	private GLabel statsLabel;
	private GLabel pointsLabel;
	private GLabel killsLabel;
	private GLabel shotsLabel;
	private GLabel accuracyLabel;
	private GLabel healthLabel;
	private GLabel superLabel;
	private GRoundRect healthBar;
	private GRoundRect superBar;
	private GRoundRect insideHealthBar;
	private GRoundRect insideSuperBar;
	private GRoundRect bossBar;
	private GRoundRect insideBossBar;
	private LevelReader read;
	private Boss boss;
	private ArrayList<Bullet> bullets;
	private ArrayList<Obstacle> enemies;
	private ArrayList<GRoundRect> bars;
	private ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
	private ArrayList<Obstacle> obstaclesToRemove = new ArrayList<Obstacle>();
	private AudioPlayer player;
	private boolean hit;
	
	public GameScreen(MainApplication app)
	{
		/*
		 * Set up Screen objects
		 *  - Creation of on-screen objects
		 */
		
		//Creation of on-screen objects
		this.program = app;
		player = AudioPlayer.getInstance();
		
		frame = new GImage("../media/sprites/screen_images/game_frame.png", 0, 0);
		background = new GImage("../media/sprites/screen_images/game_back.png", 0, 0);
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
		bossBar = new GRoundRect(gameSection.getX() + ((gameSection.getWidth() - BAR_LENGTH)/2), gameSection.getY() - BAR_WIDTH, BAR_LENGTH, BAR_WIDTH);
		insideBossBar = new GRoundRect(gameSection.getX() + ((gameSection.getWidth() - BAR_LENGTH)/2), gameSection.getY() - BAR_WIDTH, BAR_LENGTH, BAR_WIDTH);
		bars = new ArrayList<GRoundRect>(Arrays.asList(insideSuperBar, insideHealthBar, healthBar, bossBar, insideBossBar));
		for(GRoundRect bar : bars) {
			bar.setColor(Color.BLACK);
		}
		insideSuperBar.setFillColor(Color.BLUE);
		insideSuperBar.setFilled(true);
		healthBar.setFillColor(Color.RED);
		healthBar.setFilled(false);
		insideHealthBar.setFillColor(Color.RED);
		insideHealthBar.setFilled(true);
		insideBossBar.setFillColor(Color.MAGENTA);
		insideBossBar.setFilled(true);
		bossBar.setVisible(false);
		insideBossBar.setVisible(false);
		playerShip = new GImage("../media/sprites/player/ship1_32x32.png", PLAYER_X, PLAYER_Y);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Obstacle>();
		timerRuns = 0;
		ticks = 0;
	}
	
	@Override
	public void showContents() {
		/*
		 * Show screen contents
		 */
		
		resetGame();
		program.add(background);
		program.add(frame);
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
		program.add(bossBar);
		program.add(insideBossBar);
		program.add(playerShip);
		program.gameTimer.start();
		player.playSound("sounds", LEVEL_MUSIC, true);
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
		program.removeAll();
		enemies.clear();
		bullets.clear();
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
				player.playSound("sounds", SUPER_SOUND);
				bullets.add(temp);
				program.add(temp.getSprite());
				shot++;
				shotsLabel.setLabel("Shots: " + shot);
				superShotPercent = 0;
				setSuper();
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
			addBullet();
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
		if(!spawnBoss) {
			checkCollision(boss);
		}
		//Rapid Fire
		if (mouseDown && timerRuns % 10 == 0) {
			addBullet();
		}
		
		//Super Shot
		if(superShotPercent < 100 && timerRuns % 100 == 0)
		{	
			superShotPercent += 0.5;
			setSuper();
		}
		
		//Check for loss
		if(health <= 0) {
			program.remove(insideHealthBar);
			program.gameLost = true;
			gameEnd();
		}

		//Update accuracy label
		if(shot > 0) {			
			accuracy = ((double)hits / (double)shot) * 100;
			DecimalFormat df = new DecimalFormat("#00.00");
			String accuracyFormated = df.format(accuracy);
			accuracyLabel.setLabel("Accuracy: " + accuracyFormated + "%");
		}
		
		//BULLETS
		for(Bullet bullet : bullets) {
			//Despawning
			GObject temp = program.getElementAt(bullet.getSprite().getX() + bullet.getSprite().getWidth() + 1, bullet.getSprite().getY() + bullet.getSprite().getHeight()/2);
			GObject temp2 = program.getElementAt(bullet.getSprite().getX(), bullet.getSprite().getY());
			if(bullet instanceof SuperShot) {
				try {
					if(temp2.getY() < gameSection.getY()) {
						bulletsToRemove.add(bullet);
					}
				}
				catch(NullPointerException n) {
					bulletsToRemove.add(bullet);
				}
				checkSuperShot(bullet);
				if (deleteSuper) {
					bulletsToRemove.add(bullet);
				}
				
			}
			try {
				if(!(bullet instanceof SuperShot) && temp.getY() < gameSection.getY()) {
					bulletsToRemove.add(bullet);
				}
			}
			catch(NullPointerException f) {
				bulletsToRemove.add(bullet);
			}
			if(temp instanceof GImage && !(temp instanceof Bullet) && temp != playerShip && !bullet.checkEnemyBullet()) {
					GPoint tempPoint = new GPoint(bullet.getSprite().getX(), bullet.getSprite().getY());
					bulletsToRemove.add(bullet);
					for(Obstacle obstacle : enemies) {
//						if(temp == obstacle.getSprite() && !bullet.checkEnemyBullet()) { // earlier version, can change back if this doesn't work
						if(tempPoint.getX() > obstacle.getSprite().getX() && tempPoint.getX() < obstacle.getSprite().getX() + obstacle.getSprite().getWidth() && 
								tempPoint.getY() > obstacle.getSprite().getY() && tempPoint.getY() < obstacle.getSprite().getY() + obstacle.getSprite().getHeight() &&
								!bullet.checkEnemyBullet()) {
							hit = true;
							if(obstacle instanceof Boss) {
								program.remove(insideBossBar);
								insideBossBar.setSize(((Boss) obstacle).getHealthPercentage() * BAR_LENGTH, BAR_WIDTH);
								program.add(insideBossBar);
							}
							if(obstacle.hit(bullet) <= 0) {	
								player.playSound("sounds", DEATH);
								if(obstacle instanceof Boss) {
									program.remove(insideBossBar);
									program.remove(bossBar);
									gameEnd();
									File save = program.getSave();
									Scanner scan = null;
									FileWriter writer = null;
									try {
										scan = new Scanner(save);
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									scan.skip("ID: ");
									String id = scan.next();
									scan.nextLine();
									scan.skip("Unlocked Levels: ");
									int currUnlocked = scan.nextInt();
									scan.close();
									if(currUnlocked == program.getLevel()) {
										try {
											currUnlocked++;
											String oldText = "Unlocked Levels: " + currUnlocked;
											writer = new FileWriter(save, false);
											writer.write("ID: " + id);
											writer.write("\r\n");
											writer.write(oldText);
											writer.close();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
								obstaclesToRemove.add(obstacle);
								kills++;
								points++;
							}
							if(superShotPercent <= 98) {
								superShotPercent += 2;
								setSuper();
							}
							else if(superShotPercent > 98 && superShotPercent < 100){
								superShotPercent = 100;
								setSuper();
							}
						}
					}
			}
			//Ship damaged
			else if(temp == playerShip && bullet.checkEnemyBullet()) {
				bulletsToRemove.add(bullet);
				if(health > 0) {
					health -= bullet.getDamage();
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
					player.stopSound("sounds", LEVEL_MUSIC);
					gameEnd();
				}
				setHealth();
			}
			
			
			//Movement
			Pair<Double, Double> next = bullet.getNextLoc();
			bullet.getSprite().setLocation(next.getKey(), next.getValue());
			if(hit) {
				hits++;
			}
			hit = false;
		}
		
		//Removal from arrays
		bullets.removeAll(bulletsToRemove);
		for(Bullet bullet : bulletsToRemove) {
			program.remove(bullet.getSprite());
		}
		deleteSuper = false;
		enemies.removeAll(obstaclesToRemove);
		for(Obstacle obstacle : obstaclesToRemove) {
			program.remove(obstacle.getSprite());
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
			if(enemy instanceof Boss) {
				if(((Boss) enemy).checkFireRate()) {
					if(((Boss) enemy).getBulletType() != null){						
						Bullet temp = ((Boss) enemy).getBulletType();
						temp.getSprite().setLocation(enemy.getSprite().getX() + enemy.getSprite().getWidth()/2, enemy.getSprite().getY() + enemy.getSprite().getHeight());
						bullets.add(temp);
						program.add(temp.getSprite());
					}
				}
			}
			GObject temp = program.getElementAt(enemy.getSprite().getX() + enemy.getSprite().getWidth() + 1, enemy.getSprite().getY() + enemy.getSprite().getHeight()/2);
			
			//Player collision
			if (temp == playerShip && !(enemy instanceof Boss)) {
				obstaclesToRemove.add(enemy);
				if(health > 0) {
					health -= 5;
					//healthLabel.setLabel("HP: " + health);
					if(health <= 0) {
						program.remove(insideHealthBar);
						healthLabel.setLabel("HP: " + 0);
						program.gameLost = true;
						gameEnd();
					}
					else {
						setHealth();
//						healthLabel.setLabel("HP: " + health);
//						program.remove(insideHealthBar);
//						insideHealthBar.setSize(insideHealthBar.getWidth()-20, 10);
//						program.add(insideHealthBar);
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
		
		//Update label and timerRuns
		killsLabel.setLabel("Kills : " + kills);
		timerRuns++;
		
		if(!(spawnBoss)) {			
			if(timerRuns % 300 == 0) {
				boss.changePhase();
			}
			if(boss.getMinionRate() != 0) {						
				if(timerRuns % boss.getMinionRate() == 0) {
					Enemy newEnemy = boss.getMinions();
					enemies.add(newEnemy.getEnemy());
					program.add(newEnemy.getSprite());
				}
			}
		}
		
		//Random Spawning
		if(timerRuns % TICK_RATE == 0) {
			if(read.readLine(ticks) != "BOSS") {
				String line = read.readLine(ticks);
				for(int i = 0; i < 10; i++) {
					Enemy newEnemy;
					char current = line.charAt(i * 2);
					if(current != '0') {
						newEnemy = new Enemy(current, GAME_SCREEN_MARGIN + (i * 50), playerShip);
						enemies.add(newEnemy.getEnemy());
						program.add(newEnemy.getSprite());
					}
				}
			}	
			else {			
				//SPAWN BOSS

				if(spawnBoss) {
					player.stopSound("sounds", LEVEL_MUSIC);
					player.playSound("sounds", BOSS_MUSIC, true);
				}
				if(spawnBoss) {
					boss = new Boss(GAME_SCREEN_WIDTH/4, GAME_SCREEN_MARGIN, playerShip);
					spawnBoss = false;
					enemies.add(boss);
					program.add(boss.getSprite());
					bossBar.setVisible(true);
					insideBossBar.setVisible(true);
				}
				/*
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
				*/
				
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
		setHealth();
		superShotPercent = 0;
		setSuper();
		program.remove(playerShip);
		program.delPop();
		program.gameTimer.restart();
		playerShip.setLocation(PLAYER_X, PLAYER_Y);
		shot = 0;
		hits = 0;
		kills = 0;
		points = 0;
		ticks = 0;
		accuracy = 0;
		timerRuns = 0;
		spawnBoss = true;
		insideBossBar.setVisible(false);
		bossBar.setVisible(false);
		shotsLabel.setLabel("Shots: " + shot);
		accuracyLabel.setLabel("Accuracy: 00.00%");
		for(Bullet bullet : bullets) {
			program.remove(bullet.getSprite());
		}
		bullets.clear();
		for(Obstacle enemy : enemies) {
			program.remove(enemy.getSprite());
		}
		enemies.clear();
		program.gameLost = false;
		deleteSuper = false;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void checkSuperShot(Bullet bullet) {
		ArrayList<GPoint> points = new ArrayList<GPoint>();
		GRectangle bounds = bullet.getSprite().getBounds();
		for(int i = (int)bounds.getX(); i < bounds.getWidth() + bounds.getX(); i++) {
			for(int j = (int)bounds.getY(); j < bounds.getHeight() + bounds.getY(); j++) {
				GPoint temp = new GPoint(i, j);
				points.add(temp);
			}
		}
		for(GPoint point : points) {
			for(Obstacle obstacle : enemies) {
				if(obstacle.getSprite().contains(point)){
					if (obstacle.hit(bullet) <= 0) {
						obstaclesToRemove.add(obstacle);
						kills++;
						if(obstacle instanceof Boss) {
							program.remove(insideBossBar);
							program.remove(bossBar);
							gameEnd();
						}
					}
					if(obstacle instanceof Boss) {
						program.remove(insideBossBar);
						insideBossBar.setSize(((Boss) obstacle).getHealthPercentage() * BAR_LENGTH, BAR_WIDTH);
						program.add(insideBossBar);
						deleteSuper = true;
						return;
					}
				}
			}
		}
	}
	
	public void checkCollision(Boss boss) {
		ArrayList<GPoint> points = new ArrayList<GPoint>();
		GRectangle bounds = playerShip.getBounds();
		for(int i = (int)bounds.getX(); i < bounds.getWidth() + bounds.getX(); i++) {
			for(int j = (int)bounds.getY(); j < bounds.getHeight() + bounds.getY(); j++) {
				GPoint temp = new GPoint(i, j);
				points.add(temp);
			}
		}
		for(GPoint point : points) {
			if(boss.getSprite().contains(point)) {
				program.gameLost = true;
				gameEnd();
				return;
			}
		}
	}
	
	public void gameEnd() {
		points += accuracy*10;
		program.addEndPop();
		program.gameTimer.stop();
	}
	
	public void addBullet() {
		PlayerBullet temp = new PlayerBullet(5, playerShip, 10);
		bullets.add(temp);
		program.add(temp.bullet);
		shot++;
		shotsLabel.setLabel("Shots: " + shot);
	}
	
	public void setSuper() {
		superLabel.setLabel("Supershot: " + superShotPercent + "%");
		insideSuperBar.setSize(superShotPercent*4, 10);
	}
	
	public void setHealth() {
		String newLabel = (health > 0) ? ("HP: " + health) : ("HP: 0");
		healthLabel.setLabel(newLabel);
		insideHealthBar.setSize(health*4, 10);
	}
}