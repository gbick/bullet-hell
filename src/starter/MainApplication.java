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
	private GameScreen game;
	
	//Popups
	private FileSelectPop load;
	private InstructPop controls;
	private ExitPop exitPop;
	private PausePop pausePop;
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		menu = new MenuPane(this);
		lead = new LeaderboardPane(this);
		sel = new LevelSelectPane(this);
		game = new GameScreen(this);
		load = new FileSelectPop(this);
		controls = new InstructPop(this);
		exitPop = new ExitPop(this);
		pausePop = new PausePop(this);
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
	public void addExitPop() {
		addPop(exitPop);
	}
	
	public void addPausePop() {
		addPop(pausePop);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

}
