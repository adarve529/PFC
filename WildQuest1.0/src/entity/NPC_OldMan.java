package entity;


import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		super(gp);


		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 30;
		
		dialogueSet = -1;

		getImage();
		setDialogue();
	}

	public void getImage() {

		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {

		dialogues[0][0] = "Morning, traveller";
		dialogues[0][1] = "Sucka";
		dialogues[0][2] = "Capitalism suffers from crisis every 3 years\n aaaaa";
		
		
		dialogues[1][0] = "Has despertado en este frondoso bosque lleno\n de peligros y adversidades.";
		dialogues[1][1] = "Si quieres escapar tendrás que enfrentarte a\n los numerosos peligros que hay en este lugar. Te\n he dejado un"
				+ " cofre escondido por aquí. Te servirá\n de ayuda de ahora en adelante.";
		dialogues[1][2] = "Si necesitas cualquier cosa, no dudes en ir a\n buscarme. Estaré en el ayuntamiento del poblado.\n Mucha suerte.";
		
		dialogues[2][0] = "¡Muy bien, ya has conseguido la espada,\n ahora a matar!!!";
		
		dialogues[3][0] = "¡Qué te ha pasado!!! ¿Estás bien?";
		
	}

	@Override
	public void setAction() {
		
		if(onPath == true) {
			
			// Para que vaya a un punto concreto
			int goalCol= 23;
			int goalRow= 42;
			
			// Para que siga al jugador
//			int goalCol= (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
//			int goalRow= (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol,goalRow);
		}else {
			actionLockCounter++;

			if(actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100)+1;// 0 al 99, sumamos 1 para que sea del 1 al 100

				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}	
		}
		

	}

	@Override
	public void speak() {
		// Do this character specific stuff
		
		facePlayer();
		startDialogue(this, dialogueSet);
		
		dialogueSet ++;
		
		if(dialogues[dialogueSet][0] == null) {
			
			//dialogueSet = 0;
			dialogueSet --;
		}
		if(opened = false) {
			dialogueSet = 2;
		}
		if(gp.player.life < gp.player.maxLife) {
			dialogueSet = 3;
		}
		
		//onPath = true;
	}
}
