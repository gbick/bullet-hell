package starter;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import javafx.util.Pair;

public class LeaderboardPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	//TODO Identify required objects here
	private ArrayList<GLabel> levels = new ArrayList<GLabel>();
	private GLabel level;
	private int levelNumber;
	private GLabel leaderboard;
	private GLabel easy;
	private GLabel medium;
	private GLabel hard;
	private GButton nextLevel;
	private GButton prevLevel;
	private GButton returnToMenu;
	private static final double LEADERBOARD_X = MainApplication.WINDOW_WIDTH*((double)9/26);
	private static final double LEADERBOARD_Y = MainApplication.WINDOW_HEIGHT*.25;
	private static final int NUM_LEVELS = 3;
	private HashMap<Integer, ArrayList<Pair<String, Integer>>> scores = new HashMap<Integer, ArrayList<Pair<String, Integer>>>();
	File save = new File("../media/data/levels/highscores.txt");
	File save2 = new File("../media/data/levels/highscores2.txt");
	File save3 = new File("../media/data/levels/highscores3.txt");
	private ArrayList<File> saves = new ArrayList<File>(Arrays.asList(save, save2, save3));
	private Scanner scan;
	private boolean valid;
	private int score;
	private ArrayList<GLabel> currentScores = new ArrayList<GLabel>();
	
	//=====

	public LeaderboardPane(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		leaderboard = new GLabel("Leaderboard", LEADERBOARD_X, LEADERBOARD_Y);
		leaderboard.setFont("Arial-Bold-32");
		nextLevel = new GButton("Next Level", MainApplication.WINDOW_WIDTH*((double)7/9), 0, MainApplication.WINDOW_WIDTH*((double)2/9), MainApplication.WINDOW_HEIGHT*((double)1/18));
		prevLevel = new GButton("Previous Level", 0, 0, MainApplication.WINDOW_WIDTH*((double)2/9), MainApplication.WINDOW_HEIGHT*((double)1/18));
		returnToMenu = new GButton("Return to Main Menu", 0, MainApplication.WINDOW_HEIGHT-MainApplication.WINDOW_HEIGHT*((double)1/18), MainApplication.WINDOW_WIDTH*((double)2/9), MainApplication.WINDOW_HEIGHT*((double)1/18));
		for(int i = 0; i < NUM_LEVELS; ++i) {
			level = new GLabel("Level " + (i+1), MainApplication.WINDOW_WIDTH*((double)4/9), MainApplication.WINDOW_HEIGHT*((double)1/3));
			level.setFont("Arial-Bold-24");
			levels.add(level);
		}
		importOldScores();
//		easy = new GLabel("Easy Mode", level.getX(), 250);
//		easy.setFont("Arial-Bold-16");
//		medium = new GLabel("Medium Mode", level.getX(), 350);
//		medium.setFont("Arial-Bold-16");
//		hard = new GLabel("Hard Mode", level.getX(), 450);
//		hard.setFont("Arial-Bold-16");
		//=====
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		levelNumber = 1;
		program.add(nextLevel);
		program.add(prevLevel);
		program.add(leaderboard);
		program.add(returnToMenu);
		if (!levels.isEmpty()) {
			program.add(levels.get(0));
		}
//		program.add(easy);
//		program.add(medium);
//		program.add(hard);
		writeToFile();
		printFile();
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
		switchScores(obj);
		if(obj == returnToMenu) {
			program.switchToMenu();
		}
	}
	
	public void addElement(String id, int score, int level) {
		ArrayList<Pair<String, Integer>> scoreList = new ArrayList<Pair<String, Integer>>();
		Pair<String, Integer> tempPair = new Pair<>(id, score);
		if (scoreList.size() == 0) {
			scoreList.add(tempPair);
		}
		else {
			for(int i = 0; i < scoreList.size(); ++i) {
				if(scoreList.get(i).getValue() < tempPair.getValue()) {
					scoreList.add(i, tempPair);
					return;
				}
			}
			if (scoreList.size() < 5) {
				scoreList.add(tempPair);
			}
		}
		if (scores.get(level) != null) {
			for(Pair<String, Integer> appended : scoreList) {
				scores.get(level).add(appended);
			}
			selectionSort(scores.get(level));
		}
		else {
			scores.put(level, scoreList);
		}
	}
	
	public void writeToFile() {
		for(int i = 0; i < saves.size(); ++i) {
			try {
				saves.get(i).createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
		        FileWriter writer = new FileWriter(saves.get(i));
		        for(int j = 0; j < scores.get(i+1).size(); ++j) {
		        	if (j < 5) {
		        		writer.write("ID: " + scores.get(i+1).get(j).getKey());
		        		writer.write("\r\n");
		        		writer.write("Points: " + scores.get(i+1).get(j).getValue());
		        		writer.write("\r\n");
		        	}
		        	else {
		        		break;
		        	}
		        }
		        writer.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printFile() {
		if (scores.get(levelNumber) != null) {
			for(int i = 0; i < scores.get(levelNumber).size(); ++i) {
				if (i < 5) {
					GLabel tempId = new GLabel("ID: " + scores.get(levelNumber).get(i).getKey(), level.getX() - 100, level.getY() + 40*(i+1));
					tempId.setFont("Arial-18");
					GLabel tempScore = new GLabel("Points: " + scores.get(levelNumber).get(i).getValue(), tempId.getX() + 200, tempId.getY());
					tempScore.setFont("Arial-18");
					program.add(tempId);
					program.add(tempScore);
					currentScores.addAll(Arrays.asList(tempId, tempScore));
				}
				else {
					break;
				}
			}
		}
	}
	
	public void importOldScores() {
		for(int i = 0; i < NUM_LEVELS; ++i) {
			try {
				scan = new Scanner(saves.get(i));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!scan.hasNext()) {
				ArrayList<Pair<String, Integer>> tempScores = new ArrayList<Pair<String, Integer>>();
				scores.put(i+1, tempScores);
			}
			while(scan.hasNext()) {
				valid = true;
				scan.next();
				String id = scan.next();
				scan.next();
				try {
					score = Integer.parseInt(scan.next());
				}
				catch(NumberFormatException e) {
					valid = false;
				}
				if(valid) {
					addElement(id, score, i+1);
				}
			}
		}
	}
	
	public void switchScores(GObject obj) {
		if(obj == nextLevel && levelNumber < NUM_LEVELS) {
			program.remove(levels.get(levelNumber-1));
			for(GLabel label : currentScores) {
				program.remove(label);
			}
			currentScores.removeAll(currentScores);
			levelNumber++;
			program.add(levels.get(levelNumber-1));
			printFile();
		}
		else if (obj == prevLevel && levelNumber != 1) {
			program.remove(levels.get(levelNumber - 1));
			for(GLabel label : currentScores) {
				program.remove(label);
			}
			currentScores.removeAll(currentScores);
			levelNumber--;
			program.add(levels.get(levelNumber - 1));
			printFile();
		}
	}
	
	public void selectionSort(ArrayList<Pair<String, Integer>> a) {
		int i, j;
		for(i = 0; i < a.size()-1; ++i) {
			int max = i;
			for(j = i+1; j < a.size(); ++j) {
				if(a.get(j).getValue() > a.get(max).getValue()) {
					max = j;
				}
			}
			if(max != i) {
				Pair<String, Integer> temp = a.get(i);
				a.set(i, a.get(max));
				a.set(max, temp);
			}
		}
	}
}
