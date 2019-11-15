package starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
		levelSequence = new File("../media/data/levels/level" + levelNum + ".txt");
		do {
			try(Scanner scanner = new Scanner(levelSequence)){
				break;
			}
			catch(FileNotFoundException e) {
				try {
					levelSequence.createNewFile();
					System.out.println("File 'level" + levelNum + ".txt created.");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}while(true);
	}
	
	
	
}
