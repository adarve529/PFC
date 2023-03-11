package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_RedSlime extends Entity{

	GamePanel gp;

	public MON_RedSlime(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		name = "Green Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 24;
		life = maxLife;
		attack = 3;
		defense = 1;
		exp = 5;
		projectile = new OBJ_Rock(gp);

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {
		String down_1 = "/monster/redslime_down_1";
		String down_2 = "/monster/redslime_down_2";

		up1 = setup(down_1, gp.tileSize, gp.tileSize);
		up2 = setup(down_2, gp.tileSize, gp.tileSize);
		down1 = setup(down_1, gp.tileSize, gp.tileSize);
		down2 = setup(down_2, gp.tileSize, gp.tileSize);
		left1 = setup(down_1, gp.tileSize, gp.tileSize);
		left2 = setup(down_2, gp.tileSize, gp.tileSize);
		right1 = setup(down_1, gp.tileSize, gp.tileSize);
		right2 = setup(down_2, gp.tileSize, gp.tileSize);
	}
	
	
	public void setAction() {
			
		if(onPath == true) {
			
			// Dejan de seguirte al alejarte 15 casillas
			checkStopChasingOrNot(gp.player, 15, 100);		
			
			// Para que vaya a un punto concreto
//			int goalCol= 12;
//			int goalRow= 9;
			
			// Para que siga al jugador
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
			
			//por si tira un proyectil
			checkShootOrNot(200, 30);
		}
		else {
			
		    //para empezar la persecucion
			checkStartChasingOrNot(gp.player, 5, 20);
			
			//direccion aleatoria
			getRandomDirection(120);	
		}		
}
	@Override
	public void damageReaction() {

		actionLockCounter = 0;
		//direction = gp.player.direction;
		onPath = true;

	}

	@Override
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100)+1;

		// SET THE MONSTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i <100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
	
}
