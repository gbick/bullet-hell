package starter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class LosePop extends GraphicsPane {
	private MainApplication program;
	private GameScreen game;
	
	GButton frame;
	GLabel message;
	GButton returnToMenu;
	GButton exit;
	GLabel pointsLabel;
	GButton highScore;
	
	public LosePop(MainApplication app, GameScreen game) {
		this.program = app;
		this.game = game;
		frame = new GButton("", program.getWidth()/5, program.getHeight()/3, program.getWidth()/2, program.getHeight()*((double)6/13));
		frame.setFillColor(Color.MAGENTA);
		message = new GLabel("You lose! Better luck next time", frame.getX() + 20, frame.getHeight() - 50);
		message.setFont("Arial-22");
		returnToMenu = new GButton("Return to Main Menu", frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		returnToMenu.setFillColor(Color.MAGENTA);
		returnToMenu.setEdgeColor(Color.MAGENTA);
		exit = new GButton("Exit Game", frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
				frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
		exit.setFillColor(Color.MAGENTA);
		exit.setEdgeColor(Color.MAGENTA);
		pointsLabel = new GLabel("Total Points: " + game.getPoints(), message.getX() + 65, message.getY() + 30);
		pointsLabel.setFont("Arial-22");
		highScore = new GButton("Record your high score", returnToMenu.getX(), returnToMenu.getY() - 40, returnToMenu.getWidth(), returnToMenu.getHeight() - 40);
		highScore.setFillColor(Color.MAGENTA);
		highScore.setEdgeColor(Color.MAGENTA);
	}
	@Override
	public void showContents() {
		program.add(frame);
		program.add(message);
		program.add(returnToMenu);
		program.add(exit);
		setPointsLabel();
		program.add(pointsLabel);
		program.add(highScore);
		program.gameLost = true;

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
			program.addRtMPop();
		}
		else if(obj == exit) {
			program.delPop();
			program.addExitPop();
		}
		else if(obj == highScore) {
			program.delPop();
			program.addRecordPop();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.exit(0);
	}
	
	public void setPointsLabel() {
		pointsLabel.setLabel("Total Points: " + game.getPoints());
	}

}
