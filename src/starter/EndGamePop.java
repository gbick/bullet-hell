package starter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class EndGamePop extends GraphicsPane {
	private MainApplication program;
	private GameScreen game;
	private LeaderboardPane lead;
	
	GButton frame;
	GLabel message;
	GButton returnToMenu;
	GButton exit;
	GLabel pointsLabel;
	GButton highScore;
	boolean canRecord;
	private ArrayList<GButton> buttons;
	
	public EndGamePop(MainApplication app, GameScreen game, LeaderboardPane lead) {
		this.program = app;
		this.game = game;
		this.lead = lead;
		frame = new GButton("", program.getWidth()/5, program.getHeight()/3, program.getWidth()/2, program.getHeight()*((double)6/13));
		frame.setFillColor(Color.MAGENTA);
		message = new GLabel("", frame.getX() + 20, frame.getHeight() - 50);
		message.setFont("Arial-22");
		returnToMenu = new GButton("Return to Main Menu", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		exit = new GButton("Exit Game", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		pointsLabel = new GLabel("Total Points: " + game.getPoints(), message.getX() + 65, message.getY() + 30);
		pointsLabel.setFont("Arial-22");
		highScore = new GButton("Record your high score", returnToMenu.getX(), returnToMenu.getY() - 40, returnToMenu.getWidth(), returnToMenu.getHeight() - 40);
		highScore.setEdgeColor(Color.MAGENTA);
		buttons = new ArrayList<GButton>(Arrays.asList(returnToMenu, exit, highScore));
		for(GButton button : buttons) {
			button.setFillColor(Color.MAGENTA);
			button.setEdgeColor(Color.MAGENTA);
		}
	}
	@Override
	public void showContents() {
		canRecord = game.getPoints() > lead.scores.get(program.getLevel()).get(lead.scores.get(program.getLevel()).size()-1).getValue() || lead.scores.get(program.getLevel()).size() < 5;
		if(program.gameLost) {
			message.setLabel("You lose! Better luck next time");
		}
		else {
			message.setLabel("You win! Good job!");
		}
		if(!canRecord) {
			highScore.setFillColor(Color.LIGHT_GRAY);
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
