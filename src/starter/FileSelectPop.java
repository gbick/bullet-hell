package starter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class FileSelectPop extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	//TODO Identify required objects here
	GButton frame;
	GButton file1;
	GButton file2;
	GButton file3;
	String display;
	
	File save;
	Scanner scan;
	
	//=====

	public FileSelectPop(MainApplication app) {
		this.program = app;
		//TODO Declare object properties here
		frame = new GButton("", program.getWidth()/4, program.getHeight()/4, program.getWidth()/2, program.getHeight()/2);
		
		//Read saves
		for(int i = 1; i <= 3; i++) {
			save = new File("data\\saves\\save" + (i) + ".txt");
			display = "Save " + i;
			try {
				scan = new Scanner(save);
			} catch (FileNotFoundException e) {
				display = "New File";
			}
			switch (i) {
			case 1:
				file1 = new GButton(display, frame.getX()  + frame.getWidth()/65, frame.getY()  + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
				break;
			case 2:
				file2 = new GButton(display, frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
				break;
			case 3:
				file3 = new GButton(display, frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));	
				break;
			default:
				break;
			}
		}
		
	//	while (scan.hasNextLine()) {
	//			
	//	} 
		//=====
	}

	@Override
	public void showContents() {
		//TODO program.add(" ") all objects that should be immediately visible on load
		program.add(frame);
		program.add(file1);
		program.add(file2);
		program.add(file3);
		//=====
	}

	@Override
	public void hideContents() {
		//TODO program.remove(" ") all objects
		program.remove(frame);
		program.remove(file1);
		program.remove(file2);
		program.remove(file3);
		//=====
	}

	//TODO Add all mouse/key events below here \/ \/ \/ 
	//Don't forget your @Override!
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX() < frame.getX() || e.getX() > frame.getX() + frame.getWidth() || 
				e.getY() < frame.getY() || e.getY() > frame.getY() + frame.getHeight()) {
			program.delPop();
			return;
		}
	}
}
