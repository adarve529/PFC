package main;


import java.awt.*;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	
	//numero de escena
	public final int NA = 0;
	public final int skeletonLord = 1;
	
	public CutsceneManager(GamePanel gp) {
		 this.gp = gp;
		 
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch(sceneNum) {
		case skeletonLord: scene_skeletonLord(); break;
		
		}
	}
	public void scene_skeletonLord() {
		
		if(scenePhase == 0) {
			
			gp.bossBattleOn = true;
			
			//cerrar la puerta
			for (int i = 0; i < gp.obj[1].length; i++) {
				
				if(gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
					gp.obj[gp.currentMap][i].temp = true;
					gp.playSE(22);
					break;
				}
			}
			
			//buscamos un hueco para el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {
				
				if(gp.npc[gp.currentMap][i] == null) {
					
					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
			
			gp.player.drawing = false;
			
			scenePhase++;
		}
		if(scenePhase == 1) {
			
			gp.player.worldY -=2;
			
			if(gp.player.worldY < gp.tileSize*16) {
				scenePhase++;
			}
		}
		if(scenePhase == 2) {
			
			//buscamos al boss en el array
			for (int i = 0; i < gp.monster[1].length; i++) {
				
				if(gp.monster[gp.currentMap][i] != null && 
						gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {
					
					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.monster[gp.currentMap][i];
					scenePhase++;
					break;
					
				}
			}
		}
		if(scenePhase == 3) {
			
			//dialogo del boss
			gp.ui.drawDialogueScreen();
		}
		if(scenePhase == 4) {
			
			//la camara vuelve al jugador
			
			//buscamos el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {
				
				if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
					
					//reseteamos a la posicion inicial
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					
					//eliminamos al jugador ficticio
					gp.npc[gp.currentMap][i] = null;
					break;
					
				}
			}
			
			//dibujamos al jugador
			gp.player.drawing = true;
			
			//reset
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;
			
			//cambiamos la muscia 
			gp.stopMusic();
			gp.playMusic(25);
		}
		
	}
}
