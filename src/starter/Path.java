package starter;

public abstract class Path {
	private MovementEquation movement;
	private double direction; // slope of movement direction for bullet
	
	public void setMovementEquation(MovementEquation movement) {
		this.movement = movement;
	}
	
	public void setDirection() {
		switch(movement) {
			case STRAIGHT:
				direction = 0;
				break;
			case DIAGONAL:
				direction = .1;
				break;
			case WAVE:
				//TODO write this case - not entirely sure how we wanted to implement this
				break;
		}
	}
}
