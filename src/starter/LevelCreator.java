package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class LevelCreator extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 650;
	public static final int WINDOW_HEIGHT = 650;
	
	GButton restart = new GButton("Restart", 0, 0, 50, 50, Color.RED);
	GButton export = new GButton("Export", 50, 0, 50, 50, Color.GREEN);
	GButton del = new GButton("Delete", 100, 0, 50, 50);
	GButton newLine = new GButton("New Line", 150, 0, 50, 50);
	GButton tool = new GButton("Empty", 200, 0, 50, 50, Color.YELLOW);
	GButton pageNum = new GButton("1/1", 500, 0, 50, 50);
	GButton pageDown = new GButton("DOWN", 550, 0, 50, 50, Color.GRAY);
	GButton pageUp = new GButton(" UP ", 600, 0, 50, 50, Color.GRAY);
	GRect menuButtons = new GRect(0, 0, 650, 50);
	GRect gridBounds = new GRect(25, 50, 600, 550);
	GRect consoleBounds = new GRect (25, 600, 600, 50);
	
	ArrayList<GLabel> consoleMessages = new ArrayList<GLabel>();
	HashMap<Integer, ArrayList<GButton>> dataLines = new HashMap<Integer, ArrayList<GButton>>();
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run(){
		initialize();
		
	}
	
	public void initialize() {
		add(menuButtons);
		add(gridBounds);
		add(export);
		add(restart);
		add(del);
		add(newLine);
		add(tool);
		add(pageNum);
		add(pageUp);
		add(pageDown);
		consoleBounds.setFillColor(Color.LIGHT_GRAY);
		consoleBounds.setFilled(true);
		add(consoleBounds);
		consoleMessage("Initialization complete.");
	}
	
	public void consoleMessage(String message) {
		for(int i = 0; i < consoleMessages.size(); i++) {
			consoleMessages.get(i).move(0, -10);
			if(consoleMessages.get(i).getY() <= 600){
				remove(consoleMessages.get(i));
				consoleMessages.remove(i);
			}
		}
		GLabel msg = new GLabel("> " + message, 25, 650);
		consoleMessages.add(msg);
		add(msg);
	}
	
	public void createNewLine() {
		ArrayList<GButton> line = new ArrayList<GButton>();
		for(int i = 0; i < 10; i++) {
			GButton temp = new GButton("", 25 + (50 * i), 0, 60, 60);
			line.add(temp);
		}
		dataLines.put(dataLines.size(), line);
		consoleMessage("Line " + dataLines.size() + " created.");
	}
	
	public void addNewLineVis() {
		
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject clicked = getElementAt(e.getX(), e.getY());
		if(clicked instanceof GButton) {
			if(clicked == newLine) {
				//Create new line
				createNewLine();
			}
		}
	}

}
