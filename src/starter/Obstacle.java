package starter;

public interface Obstacle {
	public void spawn();
	public void despawn();
	public void start();
	public void stop();
	public void hit(Bullet bullet);
}
