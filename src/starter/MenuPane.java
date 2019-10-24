package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GLabel title;
	private GButton start;
	private GButton lead;
	private GButton controls;
	private GButton exit;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("Celestial Combat",program.getWidth()/2,program.getHeight()/4);
		program.add(title);
		
		start = new GButton("Start Game", 200, 200, 200, 50);
		start.setFillColor(Color.MAGENTA);
		
		lead = new GButton("Leaderboards", 300,300,200,50);
		lead.setFillColor(Color.MAGENTA);
		
		controls = new GButton("Controls",400, 400, 200, 50);
		controls.setFillColor(Color.MAGENTA);
		
		exit = new GButton("Exit", 500,500,200,50);
		exit.setFillColor(Color.MAGENTA);
		
	}

	@Override
	public void showContents() {
		program.add(start);
		program.add(lead);
		program.add(controls);
	}

	@Override
	public void hideContents() {
		program.remove(start);
		program.remove(lead);
		program.remove(controls);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == start)
		{
		
		}
		else if(obj==lead)
		{
			
		}
		else if(obj==controls)
		{
			
		}
		else if(obj==exit)
		{
			
		}
	}
}
