package starter;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ReturnToMenuPop extends GraphicsPane {
	
	private MainApplication program;
	private final static String LEVEL_MUSIC = "Level_Music.mp3";
	private final static String BOSS_MUSIC = "Boss_Music.mp3";
	private final static double FRAME_X_Y = 162.5;
	private final static double LABELS_X_Y = 162.5;
	private final static double LABEL_2_Y = 272.83;
	private final static double LABEL_3_Y = 381.17;
	private AudioPlayer player;
	
	GImage frame;
	GImage prompt;
	GImage yes;
	GImage no;
	private ReturnToEnum pane;
	
	public ReturnToMenuPop(MainApplication app)
	{
		this.program = app;
		player = AudioPlayer.getInstance();
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		prompt = new GImage("../media/sprites/screen_images/popup_option_menu_prompt.png", LABELS_X_Y, LABELS_X_Y);
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
			program.delPop();
			program.switchToMenu();
			return;
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
				default:
			}
		}
		
	}
	
	public void setPane(ReturnToEnum pane) {
		this.pane = pane;
	}
}