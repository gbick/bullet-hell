package starter;
public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 900;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private MenuPane menu;

	private LeaderboardPane lead;

	private LevelSelectPane sel;
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		menu = new MenuPane(this);
		lead = new LeaderboardPane(this);
		sel = new LevelSelectPane(this);
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

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
}
