package starter;
import java.awt.event.MouseEvent;
import acm.graphics.GObject;

public class ExitPop extends GraphicsPane {
	
	private MainApplication program;
	
	GButton frame;
	GButton prompt;
	GButton yes;
	GButton no;
	private ReturnToEnum pane;
	
	public ExitPop(MainApplication app)
	{
		this.program = app;
		frame = new GButton("", program.getWidth()/4, program.getHeight()/4, program.getWidth()/2, program.getHeight()/2);
		prompt = new GButton("Are you sure you want to exit?", frame.getX()  + frame.getWidth()/65, frame.getY()  + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		
		yes = new GButton("Yes", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		no = new GButton("No", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(frame);
		program.add(prompt);
		program.add(yes);
		program.add(no);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(frame);
		program.remove(prompt);
		program.remove(yes);
		program.remove(no);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == yes)
		{
			System.exit(0);
		}
		else if(obj == no)
		{
			program.delPop();
			switch(pane) {
				case END: {
					program.addEndPop();
					break;
				}
				case PAUSE: {
					program.addPausePop();
					break;
				}
				case MENU: {
				}
			}
//			if(!program.gameTimer.isRunning()) {
//				program.gameTimer.restart();
//			}
			return;
		}
		
	}
	
	public void setPane(ReturnToEnum pane) {
		this.pane = pane;
	}
}