package starter;
public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 650;
	public static final int WINDOW_HEIGHT = 650;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	//Screens
	private MenuPane menu;
	private LeaderboardPane lead;
	private LevelSelectPane sel;
	
	//Popups
	private FileSelectPop load;
	private ControlsPop controls;
	private ExitPop exitPop;
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		menu = new MenuPane(this);
		lead = new LeaderboardPane(this);
		sel = new LevelSelectPane(this);
		load = new FileSelectPop(this);
		controls = new ControlsPop(this);
		exitPop = new ExitPop(this);
		switchToMenu();
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
	
	public void delPop() {
		delPopUp();
	}
	
	public void addPopFileSelect() {
		addPop(load);
	}
	
	
	public void addControlsPop() {
		addPop(controls);
	}
	public void addExitPop() {
		addPop(exitPop);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

}
