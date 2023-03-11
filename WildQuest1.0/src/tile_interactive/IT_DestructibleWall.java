package tile_interactive;

import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class IT_DestructibleWall extends InteractiveTile{

	GamePanel gp;

	public IT_DestructibleWall(GamePanel gp, int col, int row) {
		super(gp, col, row);

		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 1;
		
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;

		if (entity.currentWeapon.type == type_pickaxe) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}
	public void playSE() {
		gp.playSE(21);
	}
	public InteractiveTile getDestroyedForm() {
		return null;
	}
	public Color getParticleColor() {
		return new Color(65, 65, 65);
	}
	public int getParticleSize() {
		return 6;
	}
	public int getParticleSpeed() {

		return 1;
	}
	public int getParticleMaxLife() {
		return 20;
	}
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100)+1;

		// SET THE MONSTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i <100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
	
}
