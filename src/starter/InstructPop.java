package starter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class InstructPop extends GraphicsPane {
	
	private MainApplication program;
	private final static double FRAME_X_Y = 162.5;
	private final static double LABELS_X_Y = 162.5;
	private final static double LABEL_2_Y = 272.83;
	private final static double LABEL_3_Y = 381.17;
	
	GImage frame;
	GImage label1;
	GImage label2;
	GImage label3;
	
	public InstructPop(MainApplication app)
	{
		this.program = app;
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		
		label1 = new GImage("../media/sprites/screen_images/popup_option_instruct_move.png", LABELS_X_Y, LABELS_X_Y);
		
		label2 = new GImage("../media/sprites/screen_images/popup_option_instruct_shoot.png", LABELS_X_Y, LABEL_2_Y);
		
		label3 = new GImage("../media/sprites/screen_images/popup_option_instruct_super.png", LABELS_X_Y, LABEL_3_Y);
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
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.delPop();
		}
	}
}