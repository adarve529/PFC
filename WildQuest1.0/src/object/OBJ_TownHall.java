package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_TownHall extends Entity{

public static final String objName = "Town Hall";
	
	public OBJ_TownHall(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/th1", (int) (gp.tileSize*3.8), (int) (gp.tileSize*3.6));
		collision = true;
		
		
		solidArea.x = 4;
		solidArea.y = 45;
		solidArea.width = 147;
		solidArea.height = 95;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
