package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class LosePop extends GraphicsPane {
	private MainApplication program;
	
	GButton frame;
	GLabel message;
	GButton returnToMenu;
	GButton exit;
	
	public LosePop(MainApplication app) {
		this.program = app;
		frame = new GButton("", program.getWidth()/5, program.getHeight()/3, program.getWidth()/2, program.getHeight()*((double)4/13));
		frame.setFillColor(Color.MAGENTA);
		message = new GLabel("You lose! Better luck next time", frame.getX(), frame.getHeight()+60);
		message.setFont("Arial-24");
		returnToMenu = new GButton("Return to Main Menu", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		returnToMenu.setFillColor(Color.MAGENTA);
		returnToMenu.setEdgeColor(Color.MAGENTA);
		exit = new GButton("Exit Game", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		exit.setFillColor(Color.MAGENTA);
		exit.setEdgeColor(Color.MAGENTA);
	}
	@Override
	public void showContents() {
		program.add(frame);
		program.add(message);
		program.add(returnToMenu);
		program.add(exit);
		program.gameLost = true;

	}

	@Override
	public void hideContents() {
		program.remove(frame);
		program.remove(message);
		program.remove(returnToMenu);
		program.remove(exit);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == returnToMenu)	
		{
			program.remove(frame);
			program.remove(message);
			program.remove(returnToMenu);
			program.remove(exit);
			program.addRtMPop();
		}
		else if(obj == exit)
		{
			program.delPop();
			program.addExitPop();
		}
	}

}
