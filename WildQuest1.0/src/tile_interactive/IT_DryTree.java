package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {

	GamePanel gp;

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);

		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 1;
	}

	@Override
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;

		if (entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}

	@Override
	public void playSE() {
		gp.playSE(7);
	}

	@Override
	public InteractiveTile getDestroyedForm() {
		return new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
	}

	@Override
	public Color getParticleColor() {
		return new Color(65, 50, 30);
	}

	@Override
	public int getParticleSize() {
		return 6;
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
