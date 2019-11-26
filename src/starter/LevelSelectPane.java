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
	private final static String MENU_MUSIC = "Menu_Music.mp3";
	//TODO Identify required objects here
	
	private GImage background;
	private GImage menuButton;
	private GImage startButton;
	private GImage title;
	private int unlocked;
	private int level = 0;
	
	private ArrayList<GButton> levels;
	private AudioPlayer player;
	Scanner scan;
	//=====

	public LevelSelectPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		player = AudioPlayer.getInstance();
		title = new GImage("../media/sprites/screen_images/levelsel_title.png", program.getWidth()/2 - 100, 0);
		background = new GImage("../media/sprites/screen_images/title_back.png", 0, 0);
		menuButton = new GImage("../media/sprites/screen_images/lead_button_main.png",0 ,program.getHeight() - BUTTON_HEIGHT);
		startButton = new GImage("../media/sprites/screen_images/title_button_start.png", program.getWidth() - BUTTON_WIDTH, program.getHeight() - BUTTON_HEIGHT);
		//=====
	}
	
	public void scanLevelStatus() {
		//Read level unlocks
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
		program.add(background);
		program.add(title);
		program.add(menuButton);
		program.add(startButton);
		
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
		program.remove(background);
		
		//Remove grid of levels
		for(int i = 0; i < levels.size(); i++) {
			program.remove(levels.get(i));
		}
		level = 0;
		//=====
	}

	//TODO Add all mouse/key events below here \/ \/ \/ 
	//Don't forget your @Override!
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject clicked = program.getElementAt(e.getX(), e.getY());
		if(clicked == menuButton) {
			level = 0;
			program.switchToMenu();
		}
		if(clicked == startButton && level != 0) {
			player.stopSound("sounds", MENU_MUSIC);
			program.setLevel(level);
			program.switchToGame();
		}
		for(int i = 0; i < levels.size(); i++) {
			if(clicked == levels.get(i) && unlocked >= i && level != i + 1) {
				if(level != 0) {	
					levels.get(level - 1).setFillColor(Color.WHITE);
					program.remove(levels.get(level - 1));
					program.add(levels.get(level - 1));
				}
				level = i + 1;
				levels.get(i).setFillColor(Color.LIGHT_GRAY);
				program.remove(levels.get(level - 1));
				program.add(levels.get(level - 1));
			}
		}
	}
}
