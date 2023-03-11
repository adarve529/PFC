package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity{

	GamePanel gp;
	
	public static final String monName = "Skeleton Lord";

	public MON_SkeletonLord(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		boss = true;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 150;
		life = maxLife;
		attack = 10;
		defense = 5;
		exp = 50;
		knockBackPower = 5;
		sleep = true;
		
		int size = gp.tileSize*5;
		solidArea.x = 48;
		solidArea.y = 48;
		solidArea.width = size - 48*2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 170;
		attackArea.height = 170;
		motion1_duration = 25;
		motion2_duration = 30;

		getImage();
		getAttackImage();
		setDialogue();
	}

	public void getImage() {
		
		int i = 5;
			
		if(!inRage) {
			
			String down_1 = "/monster/skeletonlord_down_1";
			String down_2 = "/monster/skeletonlord_down_2";
			
			String up_1 = "/monster/skeletonlord_up_1";
			String up_2 = "/monster/skeletonlord_up_2";
			
			String left_1 = "/monster/skeletonlord_left_1";
			String left_2 = "/monster/skeletonlord_left_2";
			String right_1 = "/monster/skeletonlord_right_1";
			String right_2 = "/monster/skeletonlord_right_2";
			
			up1 = setup(up_1, gp.tileSize*i, gp.tileSize*i);
			up2 = setup(up_2, gp.tileSize*i, gp.tileSize*i);
			down1 = setup(down_1, gp.tileSize*i, gp.tileSize*i);
			down2 = setup(down_2, gp.tileSize*i, gp.tileSize*i);
			left1 = setup(left_1, gp.tileSize*i, gp.tileSize*i);
			left2 = setup(left_2, gp.tileSize*i, gp.tileSize*i);
			right1 = setup(right_1, gp.tileSize*i, gp.tileSize*i);
			right2 = setup(right_2, gp.tileSize*i, gp.tileSize*i);
		}
		if(inRage) {
			
			String down_1 = "/monster/skeletonlord_phase2_down_1";
			String down_2 = "/monster/skeletonlord_phase2_down_2";
			
			String up_1 = "/monster/skeletonlord_phase2_up_1";
			String up_2 = "/monster/skeletonlord_phase2_up_2";
			
			String left_1 = "/monster/skeletonlord_phase2_left_1";
			String left_2 = "/monster/skeletonlord_phase2_left_2";
			String right_1 = "/monster/skeletonlord_phase2_right_1";
			String right_2 = "/monster/skeletonlord_phase2_right_2";
			
			up1 = setup(up_1, gp.tileSize*i, gp.tileSize*i);
			up2 = setup(up_2, gp.tileSize*i, gp.tileSize*i);
			down1 = setup(down_1, gp.tileSize*i, gp.tileSize*i);
			down2 = setup(down_2, gp.tileSize*i, gp.tileSize*i);
			left1 = setup(left_1, gp.tileSize*i, gp.tileSize*i);
			left2 = setup(left_2, gp.tileSize*i, gp.tileSize*i);
			right1 = setup(right_1, gp.tileSize*i, gp.tileSize*i);
			right2 = setup(right_2, gp.tileSize*i, gp.tileSize*i);
		}

	}
	public void getAttackImage() {
		
		int i = 5;
		
		if(!inRage) {
			
			attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
			attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i * 2);
			attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i * 2);
	    	attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i * 2);
			attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.tileSize*i * 2, gp.tileSize*i);
			attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.tileSize*i * 2, gp.tileSize*i);
			attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.tileSize*i * 2, gp.tileSize*i);
			attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.tileSize*i * 2, gp.tileSize*i);
		}
		if(inRage) {
			attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
			attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*i * 2);
			attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*i * 2);
	    	attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*i * 2);
			attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gp.tileSize*i * 2, gp.tileSize*i);
			attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gp.tileSize*i * 2, gp.tileSize*i);
			attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gp.tileSize*i * 2, gp.tileSize*i);
			attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gp.tileSize*i * 2, gp.tileSize*i);
		}
		
		
	}
	public void setDialogue() {
		
		dialogues[0][0] = "Veo que has llegado hasta aqu�.";
		dialogues[0][1] = "Si quieres salir de aqu� tendr�s que matarme,\n y te aseguro que no te ser� f�cil.";
		dialogues[0][2] = "�Ven aqu�!!!!!";
	}
	public void setAction() {
		
		if(!inRage && life < maxLife/2) {
			inRage = true;
			getImage();
			getAttackImage();
			defaultSpeed++;
			speed = defaultSpeed;
			attack += 3;
		}
		
		if(getTileDistance(gp.player) < 10) {

			moveTowardPlayer(60);
		}
		else {
		
			//direccion aleatoria
			getRandomDirection(120);	
		}		
		
		//comprobar si ataca
		if(attacking == false) {
			checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
		}
	}
	@Override
	public void damageReaction() {

		actionLockCounter = 0;
	}

	@Override
	public void checkDrop() {

		gp.bossBattleOn = false;
		Progress.skeletonLordDefeated = true;
		
		//poner la musica original
		gp.stopMusic();
		gp.playMusic(19);
		
		//eliminar las puertas
		for (int i = 0; i < gp.obj[1].length; i++) {
			
			if(gp.obj[gp.currentMap][i] != null &&
					gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
				
				gp.playSE(22);
				gp.obj[gp.currentMap][i] = null;
			}
		}
		
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
