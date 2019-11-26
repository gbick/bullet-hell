package starter;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.util.RandomGenerator;
import javafx.util.Pair;

public class Boss implements Obstacle{
	
	private final static double MAX_HEALTH = 1500;
	private double health;
	private Path flightPath;
	private Bullet shotType;
	private GImage sprite;
	private int curPhase;
	private GImage target;
	private double tick;
	private double fireRate;
	private int minionRate;
	private int currentLevel;
	private ArrayList<Pair<Enemy, Integer>> minion = new ArrayList<Pair<Enemy, Integer>>();
	private ArrayList<Pair<Pair<Integer, Path>, Bullet>> phase = new ArrayList<Pair<Pair<Integer, Path>, Bullet>>();
	private RandomGenerator rand;
	
	public Boss(double x, double y, GImage seek, int levelNum)
	{
		currentLevel = levelNum;
		target = seek;
		rand = RandomGenerator.getInstance();
		curPhase = -1;
		health = MAX_HEALTH;
		if(levelNum == 1) {			
			sprite = new GImage("../media/sprites/obstacle/boss_level_one.png",x, y);
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)),
					new BasicBullet(10, sprite, 3)));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(1, new Path(sprite, MovementEquation.STAY, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)),
					new WaveBullet(10, sprite, 1, true, true)));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)),
					new WaveBullet(10, sprite, 2, true, true)));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(2, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			minion.add(new Pair<Enemy, Integer>(new Enemy('2', sprite.getX() + sprite.getWidth()/2, target), 10));
			minion.add(new Pair<Enemy, Integer>(new Enemy('1', sprite.getX() + sprite.getWidth()/2, target), 10));
		}
		else if(levelNum == 2) {
			sprite = new GImage("../media/sprites/obstacle/boss_level_two.png", x, y);
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(1, new Path(sprite, MovementEquation.STAY_SEEK, target)), 
					new WaveBullet(10, sprite, 3, true, true)));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(0, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(2, new Path(sprite, MovementEquation.STAY_SEEK, target)), 
					new WaveBullet(10, sprite, 3, true, true)));
			minion.add(new Pair<Enemy, Integer>(new Enemy('1', sprite.getX() + sprite.getWidth()/2, target), 30));
			minion.add(new Pair<Enemy, Integer>(new Enemy('3', sprite.getX() + sprite.getWidth()/2, target), 30));	
		}
		else {
			sprite = new GImage("../media/sprites/obstacle/enemy_circler_ship.png", x, y);
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(2, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(1, new Path(sprite, MovementEquation.STAY, target)), 
					new BasicBullet(10, sprite, 5)));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(1, new Path(sprite, MovementEquation.STAY_SEEK, target)), null));
			phase.add(new Pair<Pair<Integer, Path>, Bullet>(new Pair<Integer, Path>(2, new Path(sprite, MovementEquation.STAY, target)), null));
			minion.add(new Pair<Enemy, Integer>(new Enemy('4', sprite.getX() + sprite.getWidth()/2, target), 20));
			minion.add(new Pair<Enemy, Integer>(new Enemy('4', sprite.getX() + sprite.getWidth()/2, target), 5));	
		}
		changePhase();
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
	public Pair<Double, Double> getNextLoc() {
		return flightPath.moveNextTick();
	}
	@Override
	public double hit(Bullet bullet) {
		health -= bullet.getDamage();
		return health;
	}
	
	public Enemy getMinions() {
		Enemy temp = minion.get(phase.get(curPhase).getKey().getKey() - 1).getKey();
		if(temp == null) {
			return temp;
		}
		else {
			if(currentLevel != 1) {
				double spawnLoc = rand.nextDouble(25, 525);
				temp = new Enemy(temp.getType(), spawnLoc, temp.getTarget());
				return temp;
			}
			double spawnLoc = rand.nextDouble(sprite.getX(), sprite.getX() + sprite.getWidth());
			temp = new Enemy(temp.getType(), spawnLoc, temp.getTarget());
			return temp;
		}
	}
	
	public boolean checkFireRate() {
		if(tick == fireRate) {
			tick = 0;
			return true;
		}
		else if(tick > fireRate) {
			tick = 0;
			return false;
		}
		tick++;
		return false;
	}
	
	public Bullet getBulletType() {
		if(shotType instanceof BasicBullet) {
			BasicBullet temp = new BasicBullet(shotType.getDamage(), shotType.getSprite(), shotType.getSpeed());
			return temp;
		}
		else if(shotType instanceof WaveBullet) {
			WaveBullet temp = new WaveBullet(shotType.getDamage(), shotType.getSprite(), shotType.getSpeed(), shotType.getDirection(), shotType.checkEnemyBullet());
			return temp;
		}
		return null;
	}
	
	public void changePhase() {
		curPhase++;
		if(curPhase == phase.size()) {	
			curPhase = 0;
		}
		flightPath = phase.get(curPhase).getKey().getValue();
		shotType = phase.get(curPhase).getValue();
		if(shotType != null) {
			fireRate = 10;
		}
		else {
			fireRate = 0;
		}
		if(phase.get(curPhase).getKey().getKey() != 0) {
			minionRate = minion.get(phase.get(curPhase).getKey().getKey() - 1).getValue();
		}
		else {
			minionRate = 0;
		}

	}
	
	public int getMinionRate() {
		
		return minionRate;
	}
	
	public double getHealthPercentage() {
		return health/MAX_HEALTH;
	}
}