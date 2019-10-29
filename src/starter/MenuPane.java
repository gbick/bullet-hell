package starter;

import java.awt.Color;
import java.awt.Font;
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
		title = new GLabel("Celestial Combat",program.getWidth()/5,program.getHeight()/6);
		title.setFont("Arial-50");
		program.add(title);
		
		start = new GButton("Start Game", program.getWidth()/2 - 100 , program.getHeight()/8 + 100, 200, 50);
		start.setFillColor(Color.MAGENTA);
		
		lead = new GButton("Leaderboards", program.getWidth()/2 - 100,program.getHeight()/8 + 200 ,200,50);
		lead.setFillColor(Color.MAGENTA);
		
		controls = new GButton("Controls",program.getWidth()/2 - 100, program.getHeight()/8 + 300, 200, 50);
		controls.setFillColor(Color.MAGENTA);
		
		exit = new GButton("Exit", program.getWidth()/2 - 100,program.getHeight()/8 + 400,200,50);
		exit.setFillColor(Color.MAGENTA);
		
	}

	@Override
	public void showContents() {
		program.add(start);
		program.add(lead);
		program.add(controls);
		program.add(exit);
		program.add(title);
	}

	@Override
	public void hideContents() {
		program.remove(start);
		program.remove(lead);
		program.remove(controls);
		program.remove(exit);
		program.remove(title);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//ICheck for popups
		if(program.getCurPop() != null) {
			program.getCurPop().mousePressed(e);
		}
		
		//No popups
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == start)
		{
			program.addPopFileSelect();
		}
		else if(obj==lead)
		{
			program.switchToLeaderboard();
		}
		else if(obj==controls)
		{
			//to add the function later
		}
		else if(obj==exit)
		{
			Runtime.getRuntime().exit(0);
		}
	}
}
