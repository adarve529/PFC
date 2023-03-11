package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Cottage extends Entity{

public static final String objName = "Cottage";
	
	public OBJ_Cottage(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/cottage", (int) (gp.tileSize*2.7), (int) (gp.tileSize*2.6));
		collision = true;
		
		
		solidArea.x = 4;
		solidArea.y = 25;
		solidArea.width = 100;
		solidArea.height = 80;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
