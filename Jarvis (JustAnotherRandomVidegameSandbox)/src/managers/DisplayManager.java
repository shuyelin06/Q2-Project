package managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity;
import entities.Entity.EntType;
import entities.living.Player;
import gamestates.Game;
import items.Inventory;
import items.Item;
import world.Chunk;
import world.World;

// Will handle all of the game's graphics / display
public class DisplayManager {
	private Graphics graphics;
	
	final private static double Span_Divide = 1.5;
	// Everything will be displayed relative to this center (which is the player)
	Coordinate center;
	
	// Tileset
	private HashMap<Integer, Integer> tileHash;
	private SpriteSheet tileset;
	
	private Game game;
	
	public DisplayManager(Game g, Graphics graphics) throws SlickException {
		this.center = g.getPlayer().getPosition();
		
		this.graphics = graphics;
		this.game = g;
		
		
		// Tile set and hashings
		tileset = new SpriteSheet("res/tileset.png", 30, 30);
		//block image hashes: key = block id, 
		tileHash = new HashMap<Integer, Integer>();
		tileHash.put(1, 1); //block id 1 = dirt
		tileHash.put(2, 0); //block id 2 = grass
		tileHash.put(3, 2); //block id 3 = stone
		tileHash.put(4, 3); //coal
		tileHash.put(5, 4);	//gold
		tileHash.put(6, 5); //mine diamonds
		tileHash.put(7, 6); //sand
		tileHash.put(8, 7); //sandstone
	};
	
	// Returns the pixel coordinates on screen for some block coordinate
	public float[] positionOnScreen(float x, float y) {
		float[] output = center.displacement(x, y);
		
		output[0] = output[0] * Values.Pixels_Per_Block + Values.CenterX;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Values.Pixels_Per_Block + Values.CenterY);
		
		return output;
	}
	// Return block coordinates of some position on screen
	public float[] positionInGame(float x, float y) {
		float[] output = new float[2];
		
		// Find the distance from the pixel center
		output[0] = center.getX() + (x - Values.CenterX) / Values.Pixels_Per_Block ;
		output[1] = center.getY() + 1 + (Values.CenterY - y) / Values.Pixels_Per_Block ;
		
		return output;
	}
		
	public SpriteSheet getSpriteSheet() {
		return tileset;
	}
	public HashMap<Integer, Integer> getSpriteHash(){
		return tileHash;
	}
	
	public void pinpoint(float x, float y) {
		graphics.setColor(Color.black);
		
		float[] renderPos = positionOnScreen(x, y);
		graphics.fill(new Circle(renderPos[0], renderPos[1], 5f));
	}
	public void highlightBlock(int x1, int y1) {
		graphics.setColor(Color.white);
		
		float[] renderPos = positionOnScreen(x1, y1);
		graphics.draw(new Rectangle(renderPos[0], renderPos[1], Values.Pixels_Per_Block , Values.Pixels_Per_Block ));
		
	}
	public void renderBackground(Graphics g) {
		float[] backgroundPosition = positionOnScreen(0, Values.Surface);
		
		game.getBackground().render(g, backgroundPosition[0], backgroundPosition[1]);
	}
	
	public void renderTutorial(Graphics g) {
		if (game.getTutorial().canRender()) {
			game.getTutorial().render(g);
		}
	}
	
	public void renderBlocks(Graphics g) {
		World world = game.getWorld();

		// X Span
		final int xSpan = (int) (Math.ceil(Engine.RESOLUTION_X / Values.Pixels_Per_Block) / Span_Divide);
		final int ySpan = (int) (Math.ceil(Engine.RESOLUTION_Y / Values.Pixels_Per_Block ) / Span_Divide);
		
		// Iterate through every block that will be displayed on screen
		for(int i = -xSpan; i < xSpan; i++) {
			for(int j = -ySpan; j < ySpan; j++) {
				int blockX = (int) center.getX() + i;
				int blockY = (int) center.getY() + j;
				
				int relChunkX = blockX % Values.Chunk_Size_X;
				
				Chunk c = world.getChunk(blockX / Values.Chunk_Size_X);
				if(c == null) continue;
				 
				// Fail safes
				if(relChunkX < 0) continue;
				if(blockY < 0 || blockY > Values.Chunk_Size_Y - 1) continue;
					
				// Get the block ID
				int id = c.getBlocks()[relChunkX][blockY].getID();
				float[] position = positionOnScreen(blockX, blockY);
				
				switch(id) {
					case 0: // Air, don't do anything
						break;
					case 2: // Grass
						int variant = world.getGrassVariant(c.getBlocks(), blockX % Values.Chunk_Size_X, blockY, c.getX());
						if(variant == 7) {
							g.drawImage(tileset.getSubImage(0, 1), position[0], position[1]);
						}else {
							g.drawImage(tileset.getSubImage(variant, 0), position[0], position[1]);
						}
						break;
					case 3: // Stone
						g.drawImage(tileset.getSubImage(c.getBlocks()[relChunkX][blockY].getVariant(), tileHash.get(id)), position[0], position[1]);
						break;
					default: // Every other block
						g.drawImage(tileset.getSubImage(0, tileHash.get(id)), position[0], position[1]);
						break;
				}
			}
		} 
	}
	public void renderEntities(Graphics g) {
		for(ArrayList<Entity> list: game.getAllEntities().values()) {
			for(Entity e: list) {
				renderEntity(g, e);
			}	
    	}
		// Outline EBlocks
		for(Entity eBlock: game.getEntities(EntType.Items)) {
			g.setColor(Color.black);
			
			float[] renderPos = positionOnScreen(eBlock.getPosition().getX(), eBlock.getPosition().getY());
			g.drawRect(renderPos[0], renderPos[1], eBlock.getSizeX() * Values.Pixels_Per_Block, eBlock.getSizeY() * Values.Pixels_Per_Block );
		}
		
	}
	public void renderPlayer(Graphics g) {
		Player p = game.getPlayer();
		
		renderEntity(g, p);
		drawPlayerHealth(g, p);
		drawPlayerInventory(g, p);
	}
	
	
	private void renderEntity(Graphics g, Entity e) {
		float[] renderPosition = positionOnScreen(e.getPosition().getX(), e.getPosition().getY());
		Image sprite = e.getSprite();
		
		if(e.getDirection()) { // Moving right
			sprite.draw(renderPosition[0], renderPosition[1], e.getSizeX() * Values.Pixels_Per_Block, e.getSizeY() * Values.Pixels_Per_Block ); 
		} else { // Moving left
			sprite.draw(renderPosition[0] + e.getSizeX() * Values.Pixels_Per_Block , renderPosition[1], -e.getSizeX() * Values.Pixels_Per_Block , e.getSizeY() * Values.Pixels_Per_Block); 
		}
	}
	private void drawPlayerHealth(Graphics g, Player p) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) (30f / 1080f) * Engine.game.getGC().getHeight();
		
		// Player max health bar
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
		
		// Player health bar
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH * Engine.game.getPlayer().getPercentHealth(), BAR_HEIGHT);
		
		// Health bar white outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
	}
	private void drawPlayerInventory(Graphics g, Player p) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) ((60f / 1080f) * Engine.game.getGC().getHeight());
		
		// Inventory grey coloration 
		g.setColor(new Color(150, 150, 150, 150));
		g.fillRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703 * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Inventory outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703f * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Draw every item in the player's inventory
		final float boxSize = BAR_WIDTH / (float) Inventory.Inventory_Size;
		Item[] list = p.getInventory().getItems();
		final float center = (boxSize - (float) Values.Pixels_Per_Block) / 2f;
		
		for(int i = 0; i < list.length; i++) {
			if(list[i] == null) continue;
			
			Integer id = list[i].getID();
			if(id == 0) continue;
			Integer count = list[i].getCount();
			
			float barDisp = i * boxSize;
			
			
			Engine.game.displaymanager.getSpriteSheet().getSubImage(0, Engine.game.displaymanager.getSpriteHash().get(id)).draw(
					barDisp + 0.050208333333f * Engine.RESOLUTION_X + center, 
					0.03703703703f * Engine.game.getGC().getHeight() + center // 5 pixel displacement downwards - block centering will later be automatically done.
					);
			g.drawString(count.toString(), barDisp + 0.050208333333f * Engine.RESOLUTION_X,  0.03703703703f * Engine.game.getGC().getHeight()); // Text	
		}
		
		// Draw a box around the selected item
		g.setColor(Color.black);
		g.drawRect(p.inventorySelected() * boxSize + 0.050208333333f * Engine.RESOLUTION_X, 0.03703703703f * Engine.game.getGC().getHeight(), boxSize, BAR_HEIGHT);
	}
	
}