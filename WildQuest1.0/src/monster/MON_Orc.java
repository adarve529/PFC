package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Orc extends Entity{

	GamePanel gp;

	public MON_Orc(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 100;
		life = maxLife;
		attack = 8;
		defense = 4;
		exp = 30;
		knockBackPower = 5;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1_duration = 35;
		motion2_duration = 80;

		getImage();
		getAttackImage();
	}

	public void getImage() {
		String down_1 = "/monster/orc_down_1";
		String down_2 = "/monster/orc_down_2";
		
		String up_1 = "/monster/orc_up_1";
		String up_2 = "/monster/orc_up_2";
		
		String left_1 = "/monster/orc_left_1";
		String left_2 = "/monster/orc_left_2";
		
		String right_1 = "/monster/orc_right_1";
		String right_2 = "/monster/orc_right_2";

		up1 = setup(up_1, gp.tileSize, gp.tileSize);
		up2 = setup(up_2, gp.tileSize, gp.tileSize);
		down1 = setup(down_1, gp.tileSize, gp.tileSize);
		down2 = setup(down_2, gp.tileSize, gp.tileSize);
		left1 = setup(left_1, gp.tileSize, gp.tileSize);
		left2 = setup(left_2, gp.tileSize, gp.tileSize);
		right1 = setup(right_1, gp.tileSize, gp.tileSize);
		right2 = setup(right_2, gp.tileSize, gp.tileSize);
	}
	public void getAttackImage() {
		
		attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);

		attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);

		attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);

		attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
	}
	
	public void setAction() {
			
		if(onPath == true) {
			
			// Dejan de seguirte al alejarte 20 casillas
			checkStopChasingOrNot(gp.player, 15, 100);		
			
			// Para que vaya a un punto concreto
//			int goalCol= 12;
//			int goalRow= 9;
			
			// Para que siga al jugador
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
		}
		else {
			
		    //para empezar la persecucion
			checkStartChasingOrNot(gp.player, 5, 100);
			
			//direccion aleatoria
			getRandomDirection(120);	
		}		
		
		//comprobar si ataca
		if(attacking == false) {
			checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
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
