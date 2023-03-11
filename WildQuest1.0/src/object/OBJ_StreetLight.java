package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_StreetLight extends Entity{

public static final String objName = "Street Light";
	
	public OBJ_StreetLight(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/streetlight", (int) (gp.tileSize*1.6), (int) (gp.tileSize*1.6));
		collision = true;
		
		
		solidArea.x = 20;
		solidArea.y = 30;
		solidArea.width = 30;
		solidArea.height = 50;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
