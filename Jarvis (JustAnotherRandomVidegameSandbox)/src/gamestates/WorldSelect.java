package gamestates;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import background.Background;
import core.Engine;
import core.Values;
import structures.Particle;
import support.FileLoader;

public class WorldSelect extends BasicGameState 
{
	//Gamestate ID
	int id;
	
	private boolean createNewWorld = true;
	
	//World selected ID
	private int worldID, worldIDMax, worldIDMin;
	
	//ready to start boolean
	private boolean readyStart;
	private boolean readySettings;
	
	//firework code
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static int arraySize = 50;
	public static int fireworkType = 0;
	
	//background
	private Background bg;
	//image variables
	private Image mainButton;
	private Image w1Button;
	private Image w2Button;
	private Image worldImage;
	private Image s1Button;
	private Image newWorldButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	private int w1ButtonX, w1ButtonY, w1ButtonW, w1ButtonH;
	private int w2ButtonX, w2ButtonY, w2ButtonW, w2ButtonH;
	private int worldImageX, worldImageY, worldImageW, worldImageH;
	private int s1ButtonX, s1ButtonY, s1ButtonW, s1ButtonH;
	private int newWorldButtonX, newWorldButtonY, newWorldButtonW, newWorldButtonH;
	
	public WorldSelect(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//worldID settings
		worldID = 1;
		worldIDMax = 3;
		worldIDMin = 1;
		
		//background
		bg = new Background();
		
		//image settings
		
		// 100 width: (int) (0.05208333333*gc.getWidth());
		// 100 height: (int) (0.09259259259*gc.getHeight());
		setImage("res/placeholder.png");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		mainButtonW = 3* (int) (0.05208333333*gc.getWidth());
		mainButtonH = (int) (0.09259259259*gc.getHeight());
		w1ButtonX = 2*gc.getWidth()/3;
		w1ButtonY = 2*gc.getHeight()/3;
		w1ButtonW = (int) (0.05208333333*gc.getWidth());
		w1ButtonH = (int) (0.09259259259*gc.getHeight());
		w2ButtonX = gc.getWidth()/3;
		w2ButtonY = 2*gc.getHeight()/3;
		w2ButtonW = (int) (0.05208333333*gc.getWidth());
		w2ButtonH = (int) (0.09259259259*gc.getHeight());
		worldImageX = gc.getWidth()/2;
		worldImageY = 2*gc.getHeight()/3;
		worldImageW = (int) (0.05208333333*gc.getWidth());
		worldImageH = (int) (0.09259259259*gc.getHeight());
		s1ButtonX = gc.getWidth()/5;
		s1ButtonY = gc.getHeight()/5;
		s1ButtonW = (int) (0.05208333333*gc.getWidth());
		s1ButtonH = (int) (0.09259259259*gc.getHeight());
		newWorldButtonX = 4*gc.getWidth()/5;
		newWorldButtonY = gc.getHeight()/5;
		newWorldButtonW = (int) (0.05208333333*gc.getWidth());
		newWorldButtonH = (int) (0.09259259259*gc.getHeight());
		
		readyStart = false;
		readySettings = false;
		
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Render the list of worlds
		String[] worldList = FileLoader.getWorldList();
		g.setBackground(new Color(100, 100, 100));
		
		bg.render(g, 0, 0);
		
		//draws all buttons and world number image
		drawImages(g);
		
		//draws fireworks
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// Start the game
		if (readyStart) {
			Integer i = worldID;
			Engine.game.getWorld().changeName(i.toString());
			
			// Enter Game gamestate
			Values.LastState = Engine.WorldSelect_ID;
			readyStart = !readyStart;
						
			Engine.game.respawn();
			
			// Send to Loading Screen on World Creation
			if(createNewWorld) sbg.enterState(Engine.Loading_ID); 
			// Else, directly enter the game
			else sbg.enterState(Engine.Game_ID);
		}
		
		if (readySettings) {
			Values.LastState = Engine.WorldSelect_ID;
			readySettings = !readySettings;
			sbg.enterState(Engine.Settings_ID);
			//settings gamestate
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(gc);
		}
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	public void keyPressed(int key, char c)
	{
		
		if (key == Input.KEY_1) {
			worldID = 1;
		} else if (key == Input.KEY_2) {
			worldID = 2;
		} else if (key == Input.KEY_3) {
			worldID = 3;
		}
		
		if (key == Input.KEY_Q) {
			readyStart = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
		
		//check main button
		if ((x > mainButtonX - (mainButtonW / 2))
				&& (x < mainButtonX + (mainButtonW / 2))
				&& (y > mainButtonY - (mainButtonH / 2))
				&& (y < mainButtonY + (mainButtonH / 2))
				) {
			readyStart = true;
			return;
		}
		
		//settings
		if ((x > s1ButtonX - (s1ButtonW / 2))
				&& (x < s1ButtonX + (s1ButtonW / 2))
				&& (y > s1ButtonY - (s1ButtonH / 2))
				&& (y < s1ButtonY + (s1ButtonH / 2))
				) {
			readySettings = true;
			return;
		}
		
		//toggle new world
		if ((x > newWorldButtonX - (newWorldButtonW / 2))
				&& (x < newWorldButtonX + (newWorldButtonW / 2))
				&& (y > newWorldButtonY - (newWorldButtonH / 2))
				&& (y < newWorldButtonY + (newWorldButtonH / 2))
				) {
			createNewWorld = !createNewWorld;
			return;
		}
		
		//change world ID when clicking on buttons
		if ((x > w1ButtonX - (w1ButtonW / 2))
				&& (x < w1ButtonX + (w1ButtonW / 2))
				&& (y > w1ButtonY - (w1ButtonH / 2))
				&& (y < w1ButtonY + (w1ButtonH / 2))
				) {
			worldID++;
			//max world ID
			if (worldID > worldIDMax) {
				worldID = worldIDMin;
			}
		}
		if ((x > w2ButtonX - (w2ButtonW / 2))
				&& (x < w2ButtonX + (w2ButtonW / 2))
				&& (y > w2ButtonY - (w2ButtonH / 2))
				&& (y < w2ButtonY + (w2ButtonH / 2))
				) {
			worldID--;
			//min world ID
			if (worldID < worldIDMin) {
				worldID = worldIDMax;
			}
		}
		
		//check for type of firework
		if(button == 0) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 0;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		if(button == 1) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 1;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		if(button == 2) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 2;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		
		
		
	}
	
	public void drawImages(Graphics g) {
		//image drawing
		
		setImage("res/menu/startButton.png");
		mainButton.setFilter(Image.FILTER_NEAREST);
		mainButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
		setImage("res/menu/arrowRight.png");
		w1Button.draw(w1ButtonX - (w1ButtonW / 2), w1ButtonY - (w1ButtonH / 2), w1ButtonW, w1ButtonH);
		setImage("res/menu/arrowLeft.png");
		w2Button.draw(w2ButtonX - (w2ButtonW / 2), w2ButtonY - (w2ButtonH / 2), w2ButtonW, w2ButtonH);
		
		setImage("res/menu/arrowLeft.png");
		s1Button.draw(s1ButtonX - (s1ButtonW / 2), s1ButtonY - (s1ButtonH / 2), s1ButtonW, s1ButtonH);
		setImage("res/menu/arrowRight.png");
		newWorldButton.draw(newWorldButtonX - (newWorldButtonW / 2), newWorldButtonY - (newWorldButtonH / 2), newWorldButtonW, newWorldButtonH);
		
		//draws based on world number
		if (worldID == 1) {
			setImage("res/menu/1.png");
		} else if (worldID == 2) {
			setImage("res/menu/2.png");
		} else if (worldID == 3) {
			setImage("res/menu/3.png");
		} else if (worldID == 4) {
			setImage("res/menu/4.png");
		} else if (worldID == 5) {
			setImage("res/menu/5.png");
		}
		worldImage.draw(worldImageX - (worldImageW / 2), worldImageY - (worldImageH / 2), worldImageW, worldImageH);
			
	}
	
	public void setImage(String filepath)
	{
		try
		{
			mainButton = new Image(filepath);
			w1Button = new Image(filepath);
			w2Button = new Image(filepath);
			worldImage = new Image(filepath);
			s1Button = new Image(filepath);
			newWorldButton = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
