package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
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
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run(){
		initialize();
		
	}
	
	public void initialize() {
		add(export);
		add(restart);
		add(del);
		add(newLine);
		add(tool);
		add(pageNum);
		add(pageUp);
		add(pageDown);
		add(menuButtons);
		add(gridBounds);
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
			}
		}
		GLabel msg = new GLabel("> " + message, 25, 650);
		consoleMessages.add(msg);
		add(msg);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		consoleMessage("Click");
	}

}
