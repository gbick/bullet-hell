package starter;

import java.io.File;

import javax.swing.Timer;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 650;
	public static final int WINDOW_HEIGHT = 650;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	//Screens
	private MenuPane menu;
	private LeaderboardPane lead;
	private LevelSelectPane sel;
	public GameScreen game; // TODO maybe encapsulate this and make getters to refactor?
	private RecordPop recordPop;
	
	//Popups
	private FileSelectPop load;
	private InstructPop controls;
	private ExitPop exitPop;
	private PausePop pausePop;
	private ReturnToMenuPop RtMPop;
	private EndGamePop endPop;
	private int count;
	
	private File currentSave;
	private int currentLevel;
	public Timer gameTimer;
	public boolean gameLost;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		LevelReader read = new LevelReader(1);
		int i = 0;
		String line;
		do {
			line = read.readLine(i);
			if(line == "BOSS") {
				break;
			}
			System.out.println(line);
			i++;
		} while(true);
		
		gameTimer = new Timer(10, this);
		menu = new MenuPane(this);
		lead = new LeaderboardPane(this);
		sel = new LevelSelectPane(this);
		game = new GameScreen(this);
		load = new FileSelectPop(this);
		controls = new InstructPop(this);
		exitPop = new ExitPop(this);
		pausePop = new PausePop(this);
		RtMPop = new ReturnToMenuPop(this);
		gameLost = false;
		recordPop = new RecordPop(this, game, lead);
		endPop = new EndGamePop(this, game);
		switchToMenu();
	}
	
	public void setSave(File save) {
		currentSave = save;
	}
	
	public File getSave() {
		return currentSave;
	}
	
	public void setLevel(int level) {
		currentLevel = level;
	}
	
	public int getLevel() {
		return currentLevel;
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}

	public void switchToLeaderboard() {
		switchToScreen(lead);
	}
	public void switchToSel() {
		switchToScreen(sel);
	}
	public void switchToGame() {
		switchToScreen(game);
	}
	
	public void delPop() {
		delPopUp();
	}
	
	public void addPopFileSelect() {
		addPop(load);
	}
	
	public void addInstructPop() {
		addPop(controls);
	}
	public void addExitPop(String pop) { // TODO make enum
		addPop(exitPop);
		exitPop.setPane(pop);
	}
	
	public void addPausePop() {
		addPop(pausePop);
	}
	public void addRtMPop(String pop) { // TODO make enum
		addPop(RtMPop);
		RtMPop.setPane(pop);
	}
	
	public void addRecordPop() {
		addPop(recordPop);
	}
	
	public void addEndPop() {
		addPop(endPop);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

}
