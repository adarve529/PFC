package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Stall extends Entity{

public static final String objName = "Stall";
	
	public OBJ_Stall(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/stall", (int) (gp.tileSize*2), (int) (gp.tileSize*2));
		collision = true;
		
		
		solidArea.x = 4;
		solidArea.y = 40;
		solidArea.width = 96;
		solidArea.height = 35;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
