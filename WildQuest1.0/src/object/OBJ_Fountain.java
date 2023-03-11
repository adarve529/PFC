package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Fountain extends Entity{

public static final String objName = "Fountain";
	
	public OBJ_Fountain(GamePanel gp) {
		super(gp);

		
		name = objName;
		type = type_obstacle;
		down1 = setup("/objects/fountain", (int) (gp.tileSize*2.5), (int) (gp.tileSize*2.5));
		collision = true;
		
		
		solidArea.x = 14;
		solidArea.y = 45;
		solidArea.width = 85;
		solidArea.height = 70;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
