package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class PausePop extends GraphicsPane {
	private MainApplication program;
	
	GButton frame;
	GLabel message;
	GButton quit;
	GButton resume;
	GButton resumeShip;
	
	public PausePop(MainApplication app)
	{
		this.program = app;
		frame = new GButton("", program.getWidth()/5, program.getHeight()/3, program.getWidth()/2, program.getHeight()*((double)4/13));
		frame.setFillColor(Color.MAGENTA);
		message = new GLabel("The game has been paused", frame.getX()  + frame.getWidth()/30, frame.getHeight()+60);
		message.setFont("Arial-24");
		quit = new GButton("Quit Game", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		quit.setFillColor(Color.MAGENTA);
		quit.setEdgeColor(Color.MAGENTA);
		resume = new GButton("Resume Game", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		resume.setFillColor(Color.MAGENTA);
		resume.setEdgeColor(Color.MAGENTA);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(frame);
		program.add(message);
		program.add(quit);
		program.add(resume);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(frame);
		program.remove(message);
		program.remove(quit);
		program.remove(resume);
		program.remove(resumeShip);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == quit)
		{
			program.addExitPop();
		}
		else if(obj == resume)
		{
			resumeShip = new GButton("Click on Ship to Resume", program.game.playerShip.getX() - 25, program.game.playerShip.getY() - 20, 120, 20);
			program.remove(frame);
			program.remove(message);
			program.remove(quit);
			program.remove(resume);
			program.add(resumeShip);
		}
		if(obj == program.game.playerShip) {
			program.gameTimer.restart();
			program.delPop();
			return;
		}
		
	}
}
