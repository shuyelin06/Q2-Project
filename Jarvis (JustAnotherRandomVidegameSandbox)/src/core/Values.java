package core;

import java.util.HashMap;
import org.newdawn.slick.Color;

public class Values // this will be quite useful for organizing stuff later on
{	
	// Defines our block system
	final public static int Pixels_Per_Block = 30;
	
	// Rendering Variables
	final public static HashMap<Integer, Color> BlockHash = new HashMap<Integer, Color>(); // Stores our Block - Color Combinations
	final public static int Render_Distance = 2; // Render distance
	
	final public static float CenterX = Engine.RESOLUTION_X / 2; // Player Center X
	final public static float CenterY = Engine.RESOLUTION_Y / 2; // Player Center Y
	
	// Background variables
	final public static int nightLength = 3200;
	final public static int dayLength = 3200;
	final public static int transitionLength = 1400;
	
	// Player Variables
	final public static float Player_Reach = 10f;
	
	// Word Generation Variables
	final public static int Surface = 1024; // Surface Mapping
	
	// Spawning Variables
	final public static float SpawnX = Values.World_X_Size * Values.Chunk_Size_X / 2; // Player X Spawn
	final public static float SpawnY = Surface + 32f; // Player Y Spawn
	
	final public static float Spawn_Rate = 3f; // Entity Spawn Rate
	
	// World Size Variables
	final public static int World_X_Size = 64; // World Size (in chunks)
	final public static int Chunk_Size_X = 16; // Chunk X Size (in blocks)
	final public static int Chunk_Size_Y = 2048; // Chunk Y Size (in blocks)
	
	// Biome Variables
	public static int desertStart = (int) (Values.World_X_Size * 0.3);
	public static int desertEnd = desertStart + (int) (Values.World_X_Size * 0.15);
	
	// Physics Variables
	final public static float Drag_Coefficient = 0.45f;
	final public static float Friction_Coefficient = 0.5f;
	final public static float Acceleration_of_Gravity = 0.75f;
	
	// GameState Storage
	public static int LastState = 0; // Previous GameState
	
	// File Save Paths
	final public static String Sound_Path = "res/Sound"; // Directory where sound is stored
	final public static String Save_Folder = "saves/"; // Directory where all world information will be saved
	final public static String Hash_File_Path = "src/settings/BlockHashing.txt"; // File where all block hashing will be located
}
