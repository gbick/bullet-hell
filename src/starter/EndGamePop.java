package starter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class EndGamePop extends GraphicsPane {
	private MainApplication program;
	private GameScreen game;
	private LeaderboardPane lead;
	private final static String LOSE_MUSIC = "Losing_Sound.mp3";
	private final static String WIN_MUSIC = "Victory_Fanfare.mp3";
	private final static String LEVEL_MUSIC = "Level_Music.mp3";
	private final static String BOSS_MUSIC = "Boss_Music.mp3";
	
	GImage frame;
	GLabel message;
	GImage returnToMenu;
	GImage exit;
	GLabel pointsLabel;
	GImage highScore;
	boolean canRecord;
	private AudioPlayer player;
	private ArrayList<GImage> buttons;
	
	public EndGamePop(MainApplication app, GameScreen game, LeaderboardPane lead) {
		this.program = app;
		this.game = game;
		this.lead = lead;
		player = AudioPlayer.getInstance();
		
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", program.getWidth()/5, program.getHeight()/3);
		message = new GLabel("", frame.getX() + 20, frame.getHeight() - 50);
		message.setFont("Arial-22");
		pointsLabel = new GLabel("Total Points: " + game.getPoints(), message.getX() + 65, message.getY() + 30);
		pointsLabel.setFont("Arial-22");
		highScore = new GImage("../media/sprites/screen_images/popup_option_record.png", frame.getX() + (frame.getWidth()/2) - 100, pointsLabel.getY() + 10);
		returnToMenu = new GImage("../media/sprites/screen_images/lead_button_main.png", frame.getX() + (frame.getWidth()/2) - 100, pointsLabel.getY() + highScore.getHeight() + 20);
		exit = new GImage("../media/sprites/screen_images/title_button_exit.png", frame.getX() + (frame.getWidth()/2) - 100, pointsLabel.getY() + highScore.getHeight() * 2 + 30);
		buttons = new ArrayList<GImage>(Arrays.asList(returnToMenu, exit, highScore));
	}
	@Override
	public void showContents() {
		if(lead.scores.get(program.getLevel()).size() > 0) {
			canRecord = game.getPoints() > lead.scores.get(program.getLevel()).get(lead.scores.get(program.getLevel()).size()-1).getValue() || lead.scores.get(program.getLevel()).size() < 5;
		}
		else {
			canRecord = true;
		}
		if(program.gameLost) {
			message.setLabel("You lose! Better luck next time");
			player.stopSound("sounds", LEVEL_MUSIC);
			player.stopSound("sounds", BOSS_MUSIC);
			player.playSound("sounds", LOSE_MUSIC);
		}
		else {
			message.setLabel("You win! Good job!");
			player.stopSound("sounds", LEVEL_MUSIC);
			player.stopSound("sounds", BOSS_MUSIC);
			player.playSound("sounds", WIN_MUSIC, true);
		}
		if(!canRecord) {
			highScore.setImage("../media/sprites/screen_images/popup_option_record_no.png");
		}
		program.add(frame);
		program.add(message);
		program.add(returnToMenu);
		program.add(exit);
		setPointsLabel();
		program.add(pointsLabel);
		program.add(highScore);
	}

	@Override
	public void hideContents() {
		program.remove(frame);
		program.remove(message);
		program.remove(returnToMenu);
		program.remove(exit);
		program.remove(pointsLabel);
		program.remove(highScore);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == returnToMenu)	{
			program.remove(frame);
			program.remove(message);
			program.remove(returnToMenu);
			program.remove(exit);
			program.addRtMPop(ReturnToEnum.END);
		}
		else if(obj == exit) {
			program.delPop();
			program.addExitPop(ReturnToEnum.END);
		}
		else if(obj == highScore && canRecord) {
			program.delPop();
			program.addRecordPop();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	public void setPointsLabel() {
		pointsLabel.setLabel("Total Points: " + game.getPoints());
	}

}
