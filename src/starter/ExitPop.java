package starter;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class ExitPop extends GraphicsPane {
	
	private MainApplication program;
	private final static double FRAME_X_Y = 162.5;
	private final static double LABELS_X_Y = 162.5;
	private final static double LABEL_2_Y = 272.83;
	private final static double LABEL_3_Y = 381.17;
	
	GImage frame;
	GImage prompt;
	GImage yes;
	GImage no;
	AudioPlayer player;
	private ReturnToEnum pane;
	
	public ExitPop(MainApplication app)
	{
		this.program = app;
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		prompt = new GImage("../media/sprites/screen_images/popup_option_exit_prompt.png", LABELS_X_Y, LABELS_X_Y);
		yes = new GImage("../media/sprites/screen_images/popup_option_exit_yes.png", LABELS_X_Y, LABEL_2_Y);
		no = new GImage("../media/sprites/screen_images/popup_option_exit_no.png", LABELS_X_Y, LABEL_3_Y);
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