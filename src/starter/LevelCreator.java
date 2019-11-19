package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class LevelCreator extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 650;
	public static final int WINDOW_HEIGHT = 650;
	
	private GButton restart = new GButton("Restart", 0, 0, 50, 50, Color.RED);
	private GButton export = new GButton("Export", 50, 0, 50, 50, Color.GREEN);
	private GButton newLine = new GButton("New Line", 150, 0, 50, 50);
	private GButton tool = new GButton("Empty", 200, 0, 50, 50, Color.WHITE);
	private GButton pageNum = new GButton(" / ", 500, 0, 50, 50);
	private GButton pagePrev = new GButton("PREV", 550, 0, 50, 50, Color.GRAY);
	private GButton pageNext = new GButton("NEXT", 600, 0, 50, 50, Color.GRAY);
	private GRect menuButtons = new GRect(0, 0, 650, 50);
	private GRect gridBounds = new GRect(25, 50, 600, 550);
	private GRect consoleBounds = new GRect (25, 600, 600, 50);
	
	private ArrayList<GLabel> consoleMessages = new ArrayList<GLabel>();
	private ArrayList<GButton> gridLabels = new ArrayList<GButton>();
	private HashMap<Integer, ArrayList<GButton>> displayData = new HashMap<Integer, ArrayList<GButton>>();
	private HashMap<Integer, ArrayList<GButton>> dataLines = new HashMap<Integer, ArrayList<GButton>>();
	private int page = 0;
	private int numPages = 1;
	private int numLines = 0;
	private int curTool = 0;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run(){
		initialize();
		
	}
	
	public void initialize() {
		//add(menuButtons);
		add(gridBounds);
		add(export);
		add(restart);
		add(newLine);
		add(tool);
		add(pageNum);
		add(pageNext);
		add(pagePrev);
		consoleBounds.setFillColor(Color.LIGHT_GRAY);
		consoleBounds.setFilled(true);
		add(consoleBounds);
		consoleMessage("Initialization complete.");
	}
	
	private void consoleMessage(String message) {
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
	
	private void createNewLine() {
		ArrayList<GButton> line = new ArrayList<GButton>();
		for(int i = 0; i < 10; i++) {
			GButton temp = new GButton("", 25 + (60 * i), 0, 60, 60);
			line.add(temp);
		}
		numLines++;
		if(numLines % 8 == 0) {
			numLines = 0;
			numPages++;
		}
		dataLines.put(dataLines.size(), line);
		consoleMessage("Line " + dataLines.size() + " created.");
		updateGrid();
		updateMenuButtons();
	}
	
	private void updateGrid() {
		//Remove current objects
		if(gridLabels.size() > 0) {
			for(int i = 0; i < gridLabels.size(); i++) {
				remove(gridLabels.get(i));
				gridLabels.remove(i);
			}
		}
		if(displayData.size() > 0) {
			for(int i = displayData.size() - 1; i >= 0; i--) {
				for(int j = 0; j < 10; j++) {
					try {						
						remove(displayData.get(i).get(j));
					}
					catch(NullPointerException e) {
						break;
					}
				}
				displayData.remove(i);
			}
		}
		
		//Add new objects
		GButton lineLabel;
		int label = 1 * (page);
		for(int i = 0; i < 8; i++) {
			lineLabel = new GButton("" + ((label * 8) + i + 1), 0, 50 + (60 * i), 25, 60);
			gridLabels.add(lineLabel);
			add(lineLabel);
		}
		if(page != numPages - 1) {
			//Full page
			for(int i = 0; i < 8; i++) {
				displayData.put(i, dataLines.get(i + (page * 8)));
				for(int j = 0; j < 10; j++) {
					dataLines.get(i + (page * 8)).get(j).setLocation(dataLines.get(i + (page * 8)).get(j).getX(), 50 + (i * 60));
					add(dataLines.get(i + (page * 8)).get(j));
				}
			}
		}
		else {
			//Not full page
			for(int i = 0; i < 8; i++) {
				displayData.put(i, dataLines.get(i + (page * 8)));
				for(int j = 0; j < 10; j++) {
					try {						
						dataLines.get(i + (page * 8)).get(j);
					}
					catch(NullPointerException e){
						return;
					}
					dataLines.get(i + (page * 8)).get(j).setLocation(dataLines.get(i + (page * 8)).get(j).getX(), 50 + (i * 60));
					add(dataLines.get(i + (page * 8)).get(j));
				}
			}
		}
	}
	
	private void setData(GButton square) {
		square.setFillColor(tool.getFillColor());
		if(tool.getLabelText() == "Empty") {
			square.setLabelText("");
		}
		else {			
			square.setLabelText(tool.getLabelText());
		}
	}
	
	private void reset() {
		dataLines = new HashMap<Integer, ArrayList<GButton>>();
		page = 0;
		numPages = 1;
		numLines = 0;
		updateMenuButtons();
		updateGrid();
		consoleMessage("FULL RESET. ALL DATA ERASED.");
	}
	
	public void updateMenuButtons() {
		//Page display
		pageNum.setLabelText((page + 1) + "/" + numPages);
		
		//Tool
		
		//TODO CHANGE THIS SWITCH STATEMENT TO ADD/REMOVE ENEMY TYPES
		switch(curTool) {
			case 0:
				tool.setFillColor(Color.WHITE);
				tool.setLabelText("Empty");
				break;
			case 1:
				tool.setFillColor(Color.RED);
				tool.setLabelText("Fighter");
				break;
			case 2:
				tool.setFillColor(Color.BLUE);
				tool.setLabelText("Shooter");
				break;
			case 3:
				tool.setFillColor(Color.YELLOW);
				tool.setLabelText("Fighter 2");
				break;
			case 4:
				tool.setFillColor(Color.DARK_GRAY);
				tool.setLabelText("Circler");
			default:
				break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject clicked = getElementAt(e.getX(), e.getY());
		if(clicked instanceof GButton) {
			if(clicked == newLine) {
				//Create new line
				createNewLine();
			}
			if(clicked == pageNext && (page + 1) < numPages) {
				page++;
				updateGrid();
				updateMenuButtons();
			}
			if(clicked == pagePrev && page > 0) {
				page--;
				updateGrid();
				updateMenuButtons();
			}
			if(clicked == restart) {
				reset();
			}
			for(int i = 0; i < displayData.size(); i++) {
				for(int j = 0; j < 10; j++) {
					try {
						displayData.get(i).get(j);
					}
					catch(NullPointerException n) {
						break;
					}
					if(clicked == displayData.get(i).get(j)) {
						setData(displayData.get(i).get(j));
					}
				}
			}
			if(clicked == tool) {
				//TODO CHANGE THIS IF STATEMENT TO SUPPORT MORE ENEMY TYPES
				if(curTool < 4) {
					curTool++;
				}
				else {
					curTool = 0;
				}
				updateMenuButtons();
			}
			if(clicked == export) {
				int num = 0;
				boolean cantCreate = true;
				File file;
				FileWriter writer = null;
				do {
					file = new File("../media/data/levelCreatorOutput/unamedLevel" + num + ".txt");
					if(file.exists()) {
						num++;
					}
					else {
						cantCreate = false;
					}
				}while(cantCreate);
				
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				num = 0;
				
				try {
					writer = new FileWriter(file, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//WRITE TO FILE
				for(int i = 0; i < dataLines.size(); i++) {
					for(int j = 0; j < 10; j++){
						Color dataColor = dataLines.get(i).get(j).getFillColor();
						
						//TODO ADD COLOR CASES TO THIS IF ELSE STATEMENT WHEN ADDING NEW OBJECTS
						if(dataColor == Color.WHITE) {
							try {
								writer.write("0 ");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if(dataColor == Color.RED) {
							try {
								writer.write("1 ");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if(dataColor == Color.BLUE){
							try {
								writer.write("2 ");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if(dataColor == Color.YELLOW) {
							try {
								writer.write("3 ");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if(dataColor == Color.DARK_GRAY) {
							try {
								writer.write("4 ");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					try {
						writer.write("\n");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				try {
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				consoleMessage("Exported level schematic as 'unamedLevel" + num + ".txt'");
			}
		}
	}

}
