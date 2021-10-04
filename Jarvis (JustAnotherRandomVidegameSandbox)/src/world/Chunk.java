package world;

import java.util.Arrays;

import structures.Block;

public class Chunk{
	// Size of chunk (in blocks)
	final public static int Chunk_Size_X = 25;
	final public static int Chunk_Size_Y = 100;
	
	// All blocks in the chunk
	private Block[][] blocks;
	
	// Location of the chunk's bottom-left corner
	private int chunkX;
	
	// Default constructor which generates values for every block
	public Chunk(int x) {
		this.chunkX = x;
		
		this.blocks = new Block[Chunk_Size_X][Chunk_Size_Y];
		
		for(int j = 0; j < Chunk_Size_Y; j++) {
			for(int i = 0; i < Chunk_Size_X; i++) {
				blocks[i][j] = new Block(j);
			}
		}
	}
	public Chunk(int x, Block[][] blocks) {
		this.chunkX = x;
		this.blocks = blocks;
	}
	
	// Returns the chunk x
	public int getX(){
		return chunkX;
	}
	public Block[][] getBlocks() {
		return blocks;
	}
	
	// Update a block in the chunk
	public void updateBlock(int x, int y, Block newBlock) {
		blocks[x][y] = newBlock;
		
		System.out.println("Block updated");
	}
	
}