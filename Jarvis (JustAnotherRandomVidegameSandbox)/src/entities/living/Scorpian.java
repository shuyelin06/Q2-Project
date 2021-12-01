package entities.living;

import managers.ImageManager;
import support.Utility;

//supposed to be spelled with an "a"
public class Scorpian extends Enemy{

	int jumpTimer;
	int jumpCD;
	
	public Scorpian (float x, float y) {
		super(x,y);
		sizeX = 3;
		sizeY = 3;
		
		contactDmg = 25;
		aggroRange = 15;
		
		jumpTimer = 0;
		jumpCD = 180; //should be 3 seconds
		
		try {
			this.sprite = ImageManager.getImage("placeholder");
		} catch(Exception e) {}
		
		
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			moveToPlayerX(p, 10f);
			supaJump(p, 25f);
			
		}
	}
	public void supaJump(Player p, float speed) {
		jumpTimer++;
		
		if(jumpTimer > jumpCD) {
			if(Utility.random(0,100) < 2) {
				jump(speed);
				jumpTimer = 0;
			}
		}
		
		
		
	}
	
}
