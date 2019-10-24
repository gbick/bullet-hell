package starter;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelSelectPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 50;
	//TODO Identify required objects here
	private GButton menuButton;
	private GButton startButton;
	//=====

	public LevelSelectPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		menuButton = new GButton("Return to Main Menu",0 ,program.getHeight() - BUTTON_HEIGHT ,BUTTON_WIDTH , BUTTON_HEIGHT);
		startButton = new GButton("Start Game", program.getWidth() - BUTTON_WIDTH, program.getHeight() - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
		//=====
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(menuButton);
		program.add(startButton);
		//=====
	}

	@Override
	public void hideContents() {
		//TODO program.remove(" ") all objects
		program.remove(menuButton);
		program.remove(startButton);
		//=====
	}

	//TODO Add all mouse/key events below here \/ \/ \/ 
	//Don't forget your @Override!
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject clicked = program.getElementAt(e.getX(), e.getY());
		if(clicked instanceof GButton) {
			if(clicked == menuButton) {
				program.switchToMenu();
			}
		}
	}
}
