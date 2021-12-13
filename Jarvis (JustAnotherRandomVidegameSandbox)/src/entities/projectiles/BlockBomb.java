package entities.projectiles;

import core.Coordinate;
import entities.living.Living;

public class BlockBomb extends Projectile {
	private static float baseSpeed = 50f;
	private static int baseRadius = 4;
	
	private int radius;
	
	public BlockBomb(Living origin, Coordinate target, float scaling, int id) {
		super(origin, target);
		
		this.sprite = game.displayManager.getBlockSprite(id);
		
		this.radius = (int) (baseRadius * scaling);
		
		double theta = Math.atan2(target.getY() - origin.getPosition().getY(), target.getX() - origin.getPosition().getX());
		this.xSpeed = (float) Math.cos(theta) * baseSpeed;
		this.ySpeed = (float) Math.sin(theta) * baseSpeed;
	}
	
	@Override
	protected void entityCollisions() {}
	
	protected void onBlockCollision() {
		// Blow up blocks
		int centerX = (int) position.getX();
		int centerY = (int) position.getY();
		
		for(int i = -radius; i < radius; i++)
		{
			for(int j = -radius; j < radius; j++)
			{
				game.getWorld().destroyBlock(centerX + i, centerY + j);
			}
		}
		
		this.remove = true;
	}
}