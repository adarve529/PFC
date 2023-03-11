package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_LargeCottage extends Entity{

public static final String objName = "Large Cottage";
	
	public OBJ_LargeCottage(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/largecottage", (int) (gp.tileSize*4.5), (int) (gp.tileSize*4.5));
		collision = true;
		
		
		solidArea.x = 4;
		solidArea.y = 65;
		solidArea.width = 180;
		solidArea.height = 120;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
