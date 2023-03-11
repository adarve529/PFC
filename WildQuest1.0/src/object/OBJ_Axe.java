package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public static final String objName = "Woodcutters's Axe";
	
	public OBJ_Axe(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = objName;
		down1 = setup("/objects/diamond_axe", gp.tileSize, gp.tileSize);
		attackValue = 12;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nA huge axe";
		price = 75;
		knockBackPower = 3;
		motion1_duration = 15;
		motion2_duration = 32;
	}
}
