package starter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class InstructPop extends GraphicsPane {
	
	private MainApplication program;
	private final static double FRAME_X_Y = 162.5;
	private final static int FRAME_WIDTH_HEIGHT = 325;
	private final static int LABELS_WIDTH = 315;
	private final static double LABELS_HEIGHT = 98.33;
	private final static double LABELS_X_Y = 167.5;
	private final static double LABEL_2_Y = 275.83;
	private final static double LABEL_3_Y = 384.17;
	
	GButton frame;
	GButton label1;
	GButton label2;
	GButton label3;
	
	public InstructPop(MainApplication app)
	{
		this.program = app;
		frame = new GButton("", FRAME_X_Y, FRAME_X_Y, FRAME_WIDTH_HEIGHT, FRAME_WIDTH_HEIGHT);
		
		label1 = new GButton("Mouse - Move Ship", LABELS_X_Y, LABELS_X_Y, LABELS_WIDTH, LABELS_HEIGHT);
		
		label2 = new GButton("Shoot - Left Click", LABELS_X_Y, LABEL_2_Y, LABELS_WIDTH, LABELS_HEIGHT);
		
		label3 = new GButton("Space - Super Shot", LABELS_X_Y, LABEL_3_Y, LABELS_WIDTH, LABELS_HEIGHT);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(frame);
		program.add(label1);
		program.add(label2);
		program.add(label3);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(frame);
		program.remove(label1);
		program.remove(label2);
		program.remove(label3);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX() < frame.getX() || e.getX() > frame.getX() + frame.getWidth() || 
				e.getY() < frame.getY() || e.getY() > frame.getY() + frame.getHeight()) {
			program.delPop();
			return;
		}
	}
}