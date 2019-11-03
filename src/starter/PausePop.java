package starter;

import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class PausePop extends GraphicsPane {
	private MainApplication program;
	
	GButton frame;
	GLabel message;
	GButton quit;
	GButton resume;
	
	public PausePop(MainApplication app)
	{
		this.program = app;
		frame = new GButton("", program.getWidth()/4, program.getHeight()/4, program.getWidth()/2, program.getHeight()/2);
		message = new GLabel("The game has been paused", frame.getX()  + frame.getWidth()/65, frame.getY()  + frame.getWidth()/65);
		message.setFont("Arial-16");
		quit = new GButton("Quit Game", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		resume = new GButton("Resume Game", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
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
			program.delPop();
			// TODO will also need to resume the timer running in MainApplication as well
			return;
		}
		
	}
}
