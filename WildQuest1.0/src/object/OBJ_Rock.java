package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{

	public static final String objName = "Rock";
    
	GamePanel gp;

	public OBJ_Rock(GamePanel gp) {
		super(gp);

		this.gp = gp;

		name= objName;
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 5;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);

		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean haveResource(Entity user) {

		boolean haveResource = false;

		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}

	@Override
	public void substractResource(Entity user) {
		user.ammo -= useCost;
	}

	@Override
	public Color getParticleColor() {
		return new Color(40, 50, 0);
	}

	@Override
	public int getParticleSize() {
		return 10;
	}

	@Override
	public int getParticleSpeed() {

		return 1;
	}

	@Override
	public int getParticleMaxLife() {
		return 20;
	}
}
