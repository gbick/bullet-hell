package starter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ControlsPop extends GraphicsPane {
	
	private MainApplication program;
	
	GButton frame;
	GLabel label1;
	GLabel label2;
	GLabel label3;
	
	public ControlsPop(MainApplication app)
	{
		this.program = app;
		frame = new GButton("", program.getWidth()/4, program.getHeight()/4, program.getWidth()/2, program.getHeight()/2);
		label1 = new GLabel("Mouse - Move Ship", frame.getX()  + frame.getWidth()/65, frame.getY());
		label2 = new GLabel("Shoot - Left Click", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65);
		label3 = new GLabel("Space - Super Shot", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65);
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