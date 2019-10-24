package starter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelSelectPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 50;
	private static final int NUM_LEVELS = 3;
	private static final int LEVEL_GRID_HEIGHT = 350;
	//TODO Identify required objects here
	private GButton menuButton;
	private GButton startButton;
	private ArrayList<GButton> levels;
	//=====

	public LevelSelectPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		
		//Set up number of levels for display
		levels = new ArrayList<GButton>();
		int levelStartX = 0;
		for(int i = 0; i < NUM_LEVELS; i++) {
			GButton temp = new GButton("Level " + (i + 1), levelStartX + (100 * i), LEVEL_GRID_HEIGHT, 100, 100);
			levels.add(temp);
		}
		
		menuButton = new GButton("Return to Main Menu",0 ,program.getHeight() - BUTTON_HEIGHT ,BUTTON_WIDTH , BUTTON_HEIGHT);
		startButton = new GButton("Start Game", program.getWidth() - BUTTON_WIDTH, program.getHeight() - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
		//=====
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(menuButton);
		program.add(startButton);
		
		//Add grid of levels
		for(int i = 0; i < levels.size(); i++) {
			program.add(levels.get(i));
		}
		//=====
	}

	@Override
	public void hideContents() {
		//TODO program.remove(" ") all objects
		program.remove(menuButton);
		program.remove(startButton);
		
		//Remove grid of levels
		for(int i = 0; i < levels.size(); i++) {
			program.remove(levels.get(i));
		}
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
