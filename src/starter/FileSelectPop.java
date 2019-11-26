package starter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class FileSelectPop extends GraphicsPane {
	private MainApplication program;
	private final static double FRAME_X_Y = 162.5;
	private final static double DELETE_YES_NO_X_Y = 162.5;
	private final static double YES_Y = 272.83;
	private final static double NO_Y = 381.17;

	GButton file1;
	GButton file2;
	GButton file3;
	GButton delFile1;
	GButton delFile2;
	GButton delFile3;
	GButton newFile;
	GObject sel;
	GImage frame;
	GImage frame2;
	GImage yes;
	GImage no;
	GImage delete;
	GLabel instructions;
	String display;
	
	int charNum = 0;
	int num;
	boolean confirmed = true;
	boolean delConfirmed = true;
	
	ArrayList<Character> id = new ArrayList<Character>();
	ArrayList<File> saves = new ArrayList<File>();
	File save;
	Scanner scan;
	

	public FileSelectPop(MainApplication app) {
		this.program = app;
		frame = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		frame2 = new GImage("../media/sprites/screen_images/popup_frame.png", FRAME_X_Y, FRAME_X_Y);
		instructions = new GLabel("Please enter a 3-character ID: ", 0, 0);
		delete = new GImage("../media/sprites/screen_images/popup_option_delete_prompt.png", DELETE_YES_NO_X_Y, DELETE_YES_NO_X_Y);
		yes = new GImage("../media/sprites/screen_images/popup_option_exit_yes.png", DELETE_YES_NO_X_Y, YES_Y);
		no = new GImage("../media/sprites/screen_images/popup_option_exit_no.png",  DELETE_YES_NO_X_Y, NO_Y);
		
		id.add('_');
		id.add('_');
		id.add('_');
	}
	
	public void createSave(KeyEvent e) {
		if(charNum != 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			id.set(charNum - 1, '_');
			charNum--;
		}
		else if(charNum < 3 && !e.isActionKey() && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED &&
				e.getKeyCode() != KeyEvent.VK_DELETE && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
			id.set(charNum, e.getKeyChar());
			charNum++;
		}
		else {
			if(charNum == 3 && e.getKeyCode() == KeyEvent.VK_ENTER) {
				program.delPop();
				charNum = 0;
				confirmed = true;
				
				try {
					saves.get(num - 1).createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        try {
		            FileWriter writer = new FileWriter(saves.get(num-1), true);
		            writer.write("ID: " + id.get(0) + id.get(1) + id.get(2));
		            writer.write("\r\n");
		            writer.write("Unlocked Levels: 1");
		            writer.close();
		        } catch (IOException e2) {
		            e2.printStackTrace();
		        }
		        id.set(0, '_');
		        id.set(1,  '_');
		        id.set(2, '_');
		        program.setSave(saves.get(num-1));
		        program.switchToSel();
				return;
			}
		}
		newFile.setLabelText(id.get(0) + " " + id.get(1) + " " + id.get(2));
		program.remove(instructions);
		program.remove(newFile);
		program.add(newFile);
		program.add(instructions);
	}
	
	public void deleteSave(GObject delButton) {
		File del;
		if(delButton == delFile1) {
			del = saves.get(0);
		}
		else if(delButton == delFile2) {
			del = saves.get(1);
		}
		else if(delButton == delFile3) {
			del = saves.get(2);
		}
		else {
			return;
		}
		del.delete();
	}
	
	public void checkSaves() {
		//Read saves
		for(int i = 1; i <= 3; i++) {
			save = new File("../media/data/saves/save" + (i) + ".txt");
			saves.add(save);
			display = "";
			try {
				scan = new Scanner(save);
			} catch (FileNotFoundException e) {
				display = "Empty";
			}
			if(display != "Empty") {
				scan.skip("ID: ");
				if(scan.hasNext("Unlocked")) {
					display = "   ";
				}
				else {				
					display = scan.next();
				}
			}
			switch (i) {
			case 1:
				file1 = new GButton(display, frame.getX()  + frame.getWidth()/65, frame.getY()  + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
				delFile1 = new GButton("X", file1.getX() + (file1.getWidth() - (file1.getWidth()/15)), 
						file1.getY() + (file1.getHeight() - (file1.getWidth()/15)), file1.getWidth()/15, file1.getWidth()/15, Color.RED);
				if(display == "Empty") {
					delFile1 = new GButton("", 0, 0, 0, 0);
				}
				break;
			case 2:
				file2 = new GButton(display, frame.getX() + frame.getWidth()/65, frame.getY() + frame.getHeight()/3 + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
				delFile2 = new GButton("X", file2.getX() + (file2.getWidth() - (file2.getWidth()/15)), 
						file2.getY() + (file2.getHeight() - (file2.getWidth()/15)), file2.getWidth()/15, file2.getWidth()/15, Color.RED);
				if(display == "Empty") {
					delFile2 = new GButton("", 0, 0, 0, 0);
				}
				break;
			case 3:
				file3 = new GButton(display, frame.getX() + frame.getWidth()/65, frame.getY() + ((frame.getHeight()/3) * 2) + frame.getWidth()/65,
						frame.getWidth() - ((frame.getWidth()/65) * 2), frame.getHeight()/3 - ((frame.getWidth()/65) * 2));
				delFile3 = new GButton("X", file3.getX() + (file3.getWidth() - (file3.getWidth()/15)), 
						file3.getY() + (file3.getHeight() - (file3.getWidth()/15)), file3.getWidth()/15, file3.getWidth()/15, Color.RED);
				if(display == "Empty") {
					delFile3 = new GButton("", 0, 0, 0, 0);
				}
				break;
			default:
				break;
			}
			if(display != "Empty") {				
				scan.close();
			}
		}
	}

	@Override
	public void showContents() {
		checkSaves();
		program.add(frame);
		program.add(file1);
		program.add(file2);
		program.add(file3);
		program.add(delFile1);
		program.add(delFile2);
		program.add(delFile3);
		//=====
	}

	@Override
	public void hideContents() {
		program.remove(frame);
		program.remove(file1);
		program.remove(file2);
		program.remove(file3);
		program.remove(delFile1);
		program.remove(delFile2);
		program.remove(delFile3);
		program.remove(instructions);
		if(!confirmed) {		
			program.remove(newFile);
			confirmed = true;
		}
		//=====
	}

	//TODO Add all mouse/key events below here \/ \/ \/ 
	//Don't forget your @Override!
	@Override
	public void mousePressed(MouseEvent e) {
		if(!delConfirmed) {
			if(program.getElementAt(e.getX(), e.getY()) == yes) {
				deleteSave(sel);
			}
			else if(program.getElementAt(e.getX(), e.getY()) == no) {
				
			}
			else {
				return;
			}
			program.delPop();
			program.remove(frame2);
			program.remove(delete);
			program.remove(yes);
			program.remove(no);
			delConfirmed = true;
			program.addPopFileSelect();
			return;
		}
		//Click outside of window
		if(e.getX() < frame.getX() || e.getX() > frame.getX() + frame.getWidth() || 
				e.getY() < frame.getY() || e.getY() > frame.getY() + frame.getHeight()) {
			program.delPop();
			return;
		}
		//Do nothing if already waiting for new file ID
		if(!confirmed) {
			return;
		}
		//set sel to current selected object
		sel = program.getElementAt(e.getX(), e.getY());
		num = 0;
		if(sel == file1) {
			num = 1;
		}
		else if(sel == file2) {
			num = 2;
		}
		else if(sel == file3) {
			num = 3;
		}
		//File was clicked on
		if(sel == file1 || sel == file2 || sel == file3) {
			if(((GButton) sel).getLabelText() == "Empty") {
				//Create new file
				
				//Prompt for ID
				newFile  = (GButton) sel;
				program.remove(newFile);
				((GButton) sel).setLabelText("_ _ _");
				instructions.setLocation(newFile.getX() + 10, newFile.getY() + 10);
				program.add(newFile);
				program.add(instructions);
				
				//Wait for ID
				confirmed = false;
			}
			else {
				//Load existing file
				program.setSave(saves.get(num - 1));
				program.delPop();
				program.switchToSel();
			}
		}
		if(sel == delFile1 || sel == delFile2 || sel == delFile3) {
			//Delete file
			program.add(frame2);
			program.add(delete);
			program.add(yes);
			program.add(no);
			delConfirmed = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!delConfirmed) {
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.remove(instructions);
			program.delPop();
			return;
		}
		
		//Listening for ID input
		if(!confirmed) {
			createSave(e);
		}
	}
}
