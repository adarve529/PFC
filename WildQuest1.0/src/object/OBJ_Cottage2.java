package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Cottage2 extends Entity{

public static final String objName = "Cottage2";
	
	public OBJ_Cottage2(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/cottage2", (int) (gp.tileSize*2.7), (int) (gp.tileSize*2.6));
		collision = true;
		
		
		solidArea.x = 4;
		solidArea.y = 25;
		solidArea.width = 100;
		solidArea.height = 80;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
