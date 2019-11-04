package starter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.program.GraphicsProgram;

public abstract class GraphicsApplication extends GraphicsProgram implements ActionListener {
	private GraphicsPane curScreen;
	private GraphicsPane curPop = null;
	
	public GraphicsApplication() {
		super();
		setupInteractions();
	}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	private void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	/* switchToScreen(newGraphicsPane)
	 * -------------------------------
	 * will simply switch from making one pane that was currently
	 * active, to making another one that is the active class.
	 */
	protected void switchToScreen(GraphicsPane newScreen) {
		if(curScreen != null) {
			curScreen.hideContents();
		}
		newScreen.showContents();
		curScreen = newScreen;
	}
	
	protected void addPop(GraphicsPane newPop) {
		if(curPop != null) {
			curPop.hideContents();
		}
		newPop.showContents();
		curPop = newPop;
	}
	
	protected void delPopUp() {
		if(curPop != null){
			curPop.hideContents();
			curPop = null;
		}
	}
	
	protected GraphicsPane getCurPop() {
		return(curPop);
	}
	/*
	 * These methods just override the basic
	 * mouse listeners to pass any information that
	 * was given to the application to a particular screen.
	 */
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseReleased(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseClicked(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseMoved(e);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyPressed(e);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyReleased(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyTyped(e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(curScreen != null) {
			curScreen.actionPerformed(e);
		}
	}
	
}
