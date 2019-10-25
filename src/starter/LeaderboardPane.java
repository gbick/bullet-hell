package starter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import javafx.util.Pair;

public class LeaderboardPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	//TODO Identify required objects here
	private ArrayList<GLabel> levels;
	private int levelNumber;
	private GLabel leaderboard;
	private GLabel easy;
	private GLabel medium;
	private GLabel hard;
	private GButton nextLevel;
	private GButton prevLevel;
	private static final int LEADERBOARD_X = MainApplication.WINDOW_HEIGHT/5;
	private HashMap<Integer, Pair<String, Integer>> scores; // first element is a 3-digit int - 
	
	//=====

	public LeaderboardPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		leaderboard = new GLabel("Leaderboard", 100, 100);
		nextLevel = new GButton("Next Level", 700, 0, 200, 50);
		prevLevel = new GButton("Previous Level", 0, 0, 200, 50);
		levelNumber = 1;
		levels.add(new GLabel("Level " + levelNumber, 100, 200));
		//=====
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(nextLevel);
		program.add(prevLevel);
		program.add(leaderboard);
		if (!levels.isEmpty()) {
			program.add(levels.get(0));
		}
		//=====
	}

	@Override
	public void hideContents() {
		//TODO program.remove(" ") all objects
		program.removeAll();
		//=====
	}

	//TODO Add all mouse/key events below here \/ \/ \/ 
	
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == nextLevel && levelNumber < 3) {
			program.remove(levels.get(levelNumber - 1));
			levelNumber++;
			levels.add(new GLabel("Level " + levelNumber, 100, 200));
			program.add(levels.get(levelNumber - 1));
		}
		else if(obj == prevLevel) {
			program.switchToMenu();
		}
	}
}
