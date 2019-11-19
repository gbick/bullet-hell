package starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * This class reads a level file and handles the logic behind spawning game objects.
 */

public class LevelReader {
	private int level;
	private File levelSequence;
	private Scanner scan;
	
	LevelReader(int levelNum) {
		level = levelNum;
		levelSequence = new File("../media/data/levels/level" + level + ".txt");
	}
	
	public String readLine(int line) {
		try {
			scan = new Scanner(levelSequence);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < line; i++) {
			if(scan.hasNextLine()) {
				scan.nextLine();
			}
			else {				
				return ("BOSS");
			}
		}		
		if(!(scan.hasNextLine())) {
			return ("BOSS");
		}
		String lineText = scan.nextLine();
		scan.close();
		return lineText;
	}
}
