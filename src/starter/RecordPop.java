package starter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.KeyListener;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class RecordPop extends GraphicsPane implements KeyListener {
	private MainApplication program; // you will use program to get access to
	private GameScreen game;									// all of the GraphicsProgram calls
	//TODO Identify required objects here
	GButton frame;
	GButton file1;
	GButton insert;
	GButton newFile;
	GLabel instructions;
	String display;
	int charNum = 0;
	int num;
	boolean confirmed = false;
	private final static double FRAME_X = 135;
	private final static double FRAME_Y = 281;
	private final static int FRAME_HEIGHT = 50;
	private final static int FRAME_WIDTH = 315;
	
	
	ArrayList<Character> id = new ArrayList<Character>();
	File save;
	Scanner scan;
	
	//=====

	public RecordPop(MainApplication app, GameScreen game) {
		this.program = app;
		this.game = game;
		//TODO Declare object properties here
		frame = new GButton("_ _ _", FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		instructions = new GLabel("Please enter a 3-character ID: ", 138, 292);
		id.add('_');
		id.add('_');
		id.add('_');
		frame.message.setFont("Arial-64");
		frame.message.setLocation(frame.message.getX() - 40, frame.message.getY());
		save = new File("../media/data/levels/highscores.txt");
		//=====
	}
	
	public void createSave(KeyEvent e) {
		if(charNum != 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			id.set(charNum - 1, '_');
			charNum--;
		}
		else if(charNum < 3 && !e.isActionKey() && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED &&
				e.getKeyCode() != KeyEvent.VK_DELETE && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
			id.set(charNum, e.getKeyChar());
			charNum++;
		}
		else {
			if(charNum == 3 && e.getKeyCode() == KeyEvent.VK_ENTER) {
				program.delPop();
				charNum = 0;
				
				try {
					save.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        try {
		            FileWriter writer = new FileWriter(save, true);
		            writer.write("ID: " + id.get(0) + id.get(1) + id.get(2));
		            writer.write("\r\n");
		            writer.write("Points: " + game.getPoints());
		            writer.write("\r\n");
		            writer.close();
		            System.exit(0);
		        } catch (IOException e2) {
		            e2.printStackTrace();
		        }
		        id.set(0, '_');
		        id.set(1,  '_');
		        id.set(2, '_');
		        program.setSave(save);
		        //program.switchToSel();
				return;
			}
		}
		frame.setLabelText(id.get(0) + " " + id.get(1) + " " + id.get(2));
		program.remove(instructions);
		program.add(instructions);
	}
	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(frame);
		program.add(instructions);
		//=====
	}

	@Override
	public void hideContents() {
		//TODO program.remove(" ") all objects
		program.remove(frame);
		program.remove(instructions);
		//=====
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.remove(instructions);
			program.delPop();
			return;
		}
		if (!confirmed) {
			createSave(e);
		}
	}
}
