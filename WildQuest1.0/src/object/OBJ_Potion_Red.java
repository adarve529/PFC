package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	public static final String objName = "Red Potion";
	
	GamePanel gp;
	int value = 5;

	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_consumable;
		value = 5;
		name = objName;
		down1 = setup("/objects/potion_red",gp.tileSize, gp.tileSize);
		description = "[RED POTION]\nHeals your life by " + value + ".";
		price = 15;
		stackable = true;
		
		setDialogue();
		}
	public void setDialogue() {
		
		dialogues[0][0] =  "You drink the " + name + "!\n"
				+ "Your life has been recovered by " + value + ".";
		
	}
	public boolean use(Entity entity) {

		startDialogue(this, 0);
		entity.life += value;
		gp.playSE(2);
		
		return true;
	}

}
