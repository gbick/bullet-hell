package starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage title;
	private GImage background;
	private GImage start;
	private GImage lead;
	private GImage controls;
	private GImage exit;
	private final static int BUTTON_X = 325;
	private final static double BUTTON_Y = 81.25;
	private final static String MENU_MUSIC = "Menu_Music.mp3";
	private final static String CLICK_NOISE = "Menu_Button_Click.mp3";
	private AudioPlayer player;
	

	public MenuPane(MainApplication app) {
		super();
		program = app;
		player = AudioPlayer.getInstance();
		
		background = new GImage("../media/sprites/screen_images/title_back.png", 0, 0);
		
		title = new GImage("../media/sprites/screen_images/title.png",program.getWidth()/5, 0);
		
		start = new GImage("../media/sprites/screen_images/title_button_start.png", BUTTON_X - 100 , BUTTON_Y + 100);
		
		lead = new GImage("../media/sprites/screen_images/title_button_leaderboards.png", BUTTON_X - 100, BUTTON_Y + 200);
		
		controls = new GImage("../media/sprites/screen_images/title_button_controls.png",BUTTON_X - 100, BUTTON_Y + 300);
		
		exit = new GImage("../media/sprites/screen_images/title_button_exit.png", BUTTON_X - 100, BUTTON_Y + 400);	
	}

	@Override
	public void showContents() {
		player.playSound("sounds", MENU_MUSIC);
		program.add(background);
		program.add(title);
		program.add(start);
		program.add(lead);
		program.add(controls);
		program.add(exit);
	}

	@Override
	public void hideContents() {
		program.remove(start);
		program.remove(lead);
		program.remove(controls);
		program.remove(exit);
		program.remove(title);
		program.remove(background);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//ICheck for popups
		if(program.getCurPop() != null) {
			program.getCurPop().mousePressed(e);
			return;
		}
		
		//No popups
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == start)
		{
			program.addPopFileSelect();
			player.playSound("sounds", CLICK_NOISE);
		}
		else if(obj==lead)
		{
			program.switchToLeaderboard();
			player.playSound("sounds", CLICK_NOISE);
		}
		else if(obj==controls)
		{
			program.addInstructPop();
			player.playSound("sounds", CLICK_NOISE);
		}
		
		else if(obj==exit)
		{
			program.addExitPop("menu");
			player.playSound("sounds", CLICK_NOISE);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//Check for popups
		if(program.getCurPop() != null) {
			program.getCurPop().keyPressed(e);
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
}
