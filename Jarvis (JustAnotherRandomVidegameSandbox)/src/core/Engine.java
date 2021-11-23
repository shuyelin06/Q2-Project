package core;



import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import gamestates.*;
import managers.FileManager;
import managers.KeyManager;
import managers.SoundManager;
import structures.Block;
/*
 * Perlin Noise - 
 * Simplex Noise - Both very common algorithms for random world generation 
 * 
 * Random Class in Java - Also allows randomization with seeding 
 */
public class Engine extends StateBasedGame 
{
	/*
	 * NOTE:
	 * If it is your first time playing this game, please read the README documentation for 
	 * a tutorial on how to play.
	 */
	
	/*
	 * Desktop Resolution: 1920 x 1080
	 * Laptop Resolution: 1366 x 768
	 */
	public final static int RESOLUTION_X = 1920; 	
	public final static int RESOLUTION_Y = 1080; 
	public final static int FRAMES_PER_SECOND = 60;
	
	public static final int StartingMenu_ID = 0;
    public static final int WorldSelect_ID = 1;
    public static final int Game_ID = 2;
    public static final int Pause_ID = 3;
    public static final int Debug_ID = 4;
    public static final int Settings_ID = 5;
    
    public static final int Loading_ID = 6;
    
    public static StartingMenu startingMenu;
    public static WorldSelect worldSelect;
    public static Loading loading;
    public static Game game;
    public static Pause pause;
    public static Debug debug;
    public static Settings settings;
    
	public Engine(String name) 
	{
		super(name);
		
		FileManager.LoadResFiles(); // Initializing res files
		
		startingMenu = new StartingMenu(StartingMenu_ID);
		worldSelect = new WorldSelect(WorldSelect_ID);
		loading = new Loading(Loading_ID);
		
		game = new Game(Game_ID);
		pause = new Pause(Pause_ID);
		debug = new Debug(Debug_ID);
		settings = new Settings(Settings_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		gc.setShowFPS(false);
		

		
		addState(startingMenu);
		addState(worldSelect);
		
		addState(loading);
		
		addState(game);
		addState(pause);
		addState(debug);
		addState(settings);
	}

	public static void main(String[] args) 
	{
		try {
			// Intializing Lists
			Block.Passable_Blocks.add(0); // Initializing Passable Blocks list
			KeyManager.keyList.add(Input.KEY_S); // Initializing key list
			KeyManager.keyList.add(Input.KEY_D);
			KeyManager.keyList.add(Input.KEY_A);
			
			AppGameContainer appgc = new AppGameContainer(new Engine("pogger"));
			
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);
						
		} catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}