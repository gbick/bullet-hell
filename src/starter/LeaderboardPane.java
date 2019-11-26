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
	private MainApplication program;
	private static final double LEADERBOARD_X = MainApplication.WINDOW_WIDTH*0.5 - 100;
	private static final double LEADERBOARD_Y = MainApplication.WINDOW_HEIGHT*.1;
	private static final int NUM_LEVELS = 3;
	private final static double FRAME_X_Y = 172.5;

	private int levelNumber;
	private int score;
	private boolean valid;
	
	private GLabel level;
	private GImage leaderboard;
	private GImage nextLevel;
	private GImage prevLevel;
	private GImage returnToMenu;
	private GImage background;
	private GImage frame;
	File save = new File("../media/data/levels/highscores.txt");
	File save2 = new File("../media/data/levels/highscores2.txt");
	File save3 = new File("../media/data/levels/highscores3.txt");
	private Scanner scan;
	
	private ArrayList<GLabel> levels = new ArrayList<GLabel>();
	private ArrayList<GLabel> currentScores = new ArrayList<GLabel>();
	private ArrayList<File> saves = new ArrayList<File>(Arrays.asList(save, save2, save3));
	public HashMap<Integer, ArrayList<Pair<String, Integer>>> scores = new HashMap<Integer, ArrayList<Pair<String, Integer>>>();
	//=====

	public LeaderboardPane(MainApplication app) {
		this.program = app;
		
		background = new GImage("../media/sprites/screen_images/title_back.png", 0, 0);
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		leaderboard = new GImage("../media/sprites/screen_images/title_button_leaderboards.png", LEADERBOARD_X, LEADERBOARD_Y);
		nextLevel = new GImage("../media/sprites/screen_images/lead_button_next.png", MainApplication.WINDOW_WIDTH - 200, 0);
		prevLevel = new GImage("../media/sprites/screen_images/lead_button_prev.png", 0, 0);
		returnToMenu = new GImage("../media/sprites/screen_images/lead_button_main.png", 0, MainApplication.WINDOW_HEIGHT-MainApplication.WINDOW_HEIGHT*((double)1/18));
		for(int i = 0; i < NUM_LEVELS; ++i) {
			level = new GLabel("Level " + (i+1), MainApplication.WINDOW_WIDTH*((double)4/9), MainApplication.WINDOW_HEIGHT*((double)1/3));
			level.setFont("Arial-Bold-24");
			levels.add(level);
		}
		importOldScores();
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		levelNumber = 1;
		program.add(background);
		program.add(frame);
		program.add(nextLevel);
		program.add(prevLevel);
		program.add(leaderboard);
		program.add(returnToMenu);
		if (!levels.isEmpty()) {
			program.add(levels.get(0));
		}
		writeToFile();
		printFile();
		//=====
	}

	@Override
	public void hideContents() {
		program.removeAll();
		//=====
	}
	
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
			currentScores.clear();
			levelNumber++;
			program.add(levels.get(levelNumber-1));
			printFile();
		}
		else if (obj == prevLevel && levelNumber != 1) {
			program.remove(levels.get(levelNumber - 1));
			for(GLabel label : currentScores) {
				program.remove(label);
			}
			currentScores.clear();
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
