package starter;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class LevelSelectPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private static final int BUTTON_WIDTH = 200; //Width of bottom buttons
	private static final int BUTTON_HEIGHT = 50; //Height of bottom buttons
	private static final int NUM_LEVELS = 3; //Number of level selection buttons (total number of levels)
	private static final int LEVEL_GRID_HEIGHT = 250; //The height at which the level button grid is located
	private static final int LEVEL_BUTTON_SIZE = 150; //Height and width of level buttons
	private static final int MARGIN = 100; //The amount of space on the left and right of the level select buttons
	//TODO Identify required objects here
	private GButton menuButton;
	private GButton startButton;
	private GLabel title;
	private ArrayList<GButton> levels;
	private ArrayList<Integer> lockedStatus;
	private int unlocked;
	
	// private GImage ship = new GImage("sprites//player//ship1.png", 0, 0);
	Scanner scan;
	//=====

	public LevelSelectPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		
		title = new GLabel("Choose a level", program.getWidth()/2 - 80, 50);
		title.setFont("Arial-25");
		
		menuButton = new GButton("Return to Main Menu",0 ,program.getHeight() - BUTTON_HEIGHT ,BUTTON_WIDTH , BUTTON_HEIGHT);
		startButton = new GButton("Start Game", program.getWidth() - BUTTON_WIDTH, program.getHeight() - BUTTON_HEIGHT, 
				BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFillColor(Color.MAGENTA);
		startButton.setFillColor(Color.MAGENTA);
		//=====
	}
	
	public void scanLevelStatus() {
		//Read level unlocks
		lockedStatus = new ArrayList<Integer>();
		try {
			scan = new Scanner(program.getSave());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		scan.nextLine();
		scan.skip("Unlocked Levels: ");
		unlocked = scan.nextInt() - 1;
		scan.close();
		
		
		//Set up number of levels for display
		levels = new ArrayList<GButton>();
		int spaceBetween = ((program.getWidth() - (MARGIN * 2) - (LEVEL_BUTTON_SIZE * NUM_LEVELS)) / NUM_LEVELS);
		GButton temp;
		for(int i = 0; i < NUM_LEVELS; i++) {
			if(i == 0) {				
				temp = new GButton("Level " + (i + 1), MARGIN, LEVEL_GRID_HEIGHT, LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
			}
			else {
				if(unlocked >= i) {
				temp = new GButton("Level " + (i + 1), MARGIN + (spaceBetween * i) + (LEVEL_BUTTON_SIZE * i), LEVEL_GRID_HEIGHT, 
						LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
				}
				else {
				temp = new GButton("Level " + (i + 1), MARGIN + (spaceBetween * i) + (LEVEL_BUTTON_SIZE * i), LEVEL_GRID_HEIGHT, 
						LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE, Color.GRAY);
				}
			}
			levels.add(temp);
		}
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(menuButton);
		program.add(startButton);
		program.add(title);
		
		//Add grid of levels
		scanLevelStatus();
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
		program.remove(title);
		
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
			if(clicked == startButton) {
				program.switchToGame();
			}
		}
	}
}
