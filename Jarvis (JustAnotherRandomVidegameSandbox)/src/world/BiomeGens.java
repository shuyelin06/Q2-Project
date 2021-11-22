package world;

import core.Values;
import structures.Block;

public class BiomeGens //just holds all the biome generation, for organization purposes
{
	public static Block[][] desertGen(Block[][] inputBlocks, int edge)
	{
		Block[][] blocks = inputBlocks;
		
		if(edge == 0)
		{
			for(int i = 0; i < Values.Chunk_Size_X; i++)
			{
				for(int j = 0; j < Values.Chunk_Size_Y; j++)
				{
					switch(blocks[i][j].getID())
					{
						case(1): blocks[i][j].setID(7); break; //change to 7 and 8 once i manage to get sand and sandstone textures
						case(2): blocks[i][j].setID(7); break; 
						case(3): blocks[i][j].setID(8);
					}
				}
			}	
		}
		else if(edge == 1)
		{
			for(int j = 0; j < Values.Chunk_Size_Y; j++)
			{
				for(int i = 0; i < Values.Chunk_Size_X * Math.random(); i++)
				{
					switch(blocks[i][j].getID())
					{
						case(1): blocks[i][j].setID(7); break;
						case(2): blocks[i][j].setID(7); break;
						case(3): blocks[i][j].setID(8);
					}
				}
			}
				
		}
		else if(edge == -1)
		{
			for(int j = 0; j < Values.Chunk_Size_Y; j++)
			{
				for(int i = Values.Chunk_Size_X - 1; i > (int)(Values.Chunk_Size_X * Math.random()) - 1; i--)
				{
					switch(blocks[i][j].getID())
					{
						case(1): blocks[i][j].setID(7); break;
						case(2): blocks[i][j].setID(7); break;
						case(3): blocks[i][j].setID(8);
					}
				}
			}
		}
		
		return(blocks);
	}
	
	private Block[][] structures(Block[][] blocks) //oh boy
	{
		for(int i = 0; i < Values.Chunk_Size_X; i++)
		{
			for(int j = 0; j < Values.Chunk_Size_Y; i++)
			{
				if(blocks[i][j].getID() == 2 && WorldGen.adjacentTo(i, j, 'n', 0, blocks) 
						&& WorldGen.adjacentTo(i, j + 1, 'e', 0, blocks) && WorldGen.adjacentTo(i, j + 1, 'w', 0, blocks)) // tree generation bruhhhh
				{
					if(i % (4 + (int)Math.random() * 3) == 0)
					{
						blocks[i][j + 1].setID(3);
					}
					
				}	
			}		
		}
		return(blocks);
	}
	
	
}