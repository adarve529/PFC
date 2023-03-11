package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;


public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font rTheory, maruMonica, purisaB;
	// Preparamos la imagen antes de que empiece el game loop, de otra manera se generaría 60 veces por segundo
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;// Texto que aparece al coger objetos
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;// Booleano para verificar si ha acabado el juego o no
	public String currentDialogue;
	public int commandNum;
	public int playerSlotCol;
	public int playerSlotRow;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0; // Para las opciones
	int counter = 0;
	public Entity npc;
	int charIndex = 0;
	String combineText = "";

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
//			InputStream is = getClass().getResourceAsStream("/font/bitui.ttf");
//			rTheory = Font.createFont(Font.TRUETYPE_FONT,  is);
			
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			
//			InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
//			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;

	}
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		//g2.setFont(rTheory);
		//g2.setFont(purisaB);
		g2.setFont(maruMonica);
		
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMonsterLife();
			drawMessage();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// GAMEOVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		// SLEEP STATE
		if(gp.gameState == gp.sleepState) {
			drawSleepScreen();
		}
	}
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		

		// DRAW MAX LIFE
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		// RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		// DRAW MAX MANA
		x = (gp.tileSize/2-5);
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		// DRAW MANA
		x = gp.tileSize/2-5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
	}
	public void drawMonsterLife() {
		
		for (int i = 0; i < gp.monster[1].length; i++) {
			
			Entity monster = gp.monster[gp.currentMap][i];
			
			if(monster != null && monster.inCamera()) {
				
				// Monster HP bar
				if(monster.hpBarOn && !monster.boss) {

					double oneScale = (double)gp.tileSize/monster.maxLife;
					double hpBarValue = oneScale*monster.life;

					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.tileSize+2, 12);

					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 10);

					monster.hpBarCounter++;

					if(monster.hpBarCounter > 600) {
						monster.hpBarCounter = 0;
						monster.hpBarOn = false;
					}
				} 
				else if(monster.boss) {
					
					double oneScale = (double)(gp.tileSize*8)/monster.maxLife;
					double hpBarValue = oneScale*monster.life;
					
					int x = gp.screenWidth/2 - gp.tileSize*4;
					int y = gp.tileSize*10;

					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(x-1, y-1, gp.tileSize*8 + 2, 22);

					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(x, y, (int)hpBarValue, 20);
					
					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
					g2.setColor(Color.white);
					g2.drawString(monster.name, x + 4, y - 10);
				}
			}
		}
	
	}
	public void drawMessage() {

		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

		for(int i = 0; i < message.size(); i++) {

			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);

				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1;// messageCounter ++
				messageCounter.set(i, counter);// set the counter to the array
				messageY += 50;

				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawTitleScreen() {
			// Color de fondo
			g2.setColor(Color.black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 98F));
			String text = "Wild Quest";
			

			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;

//			int x2 = getXforCenteredText(text2);
//			int y2 = gp.tileSize*4;
			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			//g2.drawString(text2, x2+10, y2+5);
			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			//g2.drawString(text2, x2+5, y2);
			// IMAGE
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			
			 
			g2.drawImage(gp.player.stand, x, y, gp.tileSize*2, gp.tileSize*2, null);
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			// NEW GAME
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*3.8;
			g2.drawString(text, x, y);
			if(commandNum == 0 ) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			// LOAD GAME
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1 ) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			// QUIT
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2 ) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
	public void drawPauseScreen() {
		
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
		String text = "PAUSED";
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		int y = gp.screenHeight/2;

		//g2.drawString(text, x, y);
		
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		
		// DIALOGUE WINDOW
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*6);
		int height = gp.tileSize*4;

		drawSubWindow(x, y, width, height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

		x += gp.tileSize;
		y += gp.tileSize;
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			
			//currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			
			if(charIndex < characters.length) {
				
				gp.playSE(18);
				String s = String.valueOf(characters[charIndex]);
				combineText = combineText + s;
				currentDialogue = combineText;
				
				charIndex++;
			}
			
			
			if(gp.keyH.enterPressed) {
				
				charIndex = 0;
				combineText = "";
				
				if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
					
					npc.dialogueIndex++;
					gp.keyH.enterPressed = false;
					
				}
			}
		}
		else { //si no hay texto en el array
			npc.dialogueIndex = 0;
			
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
			if(gp.gameState == gp.cutsceneState) {
				gp.csManager.scenePhase++;
			}
		}

		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	public void drawCharacterScreen() {
		// CREATE A FRAME
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strenght", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Shield", textX, textY);
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// reset textY
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.strength);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null );
		textY += gp.tileSize;

		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null );
	}

	public void drawInventory(Entity entity, boolean cursor) {
		int frameX;
		int frameY;
		int frameWidth;
		int frameHeight;
		int slotCol;
		int slotRow;
		
		if(entity == gp.player) {
			
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
			
		}
		else {
			
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		// DRAW PLAYERS ITEMS
		for(int i = 0; i < entity.inventory.size(); i++ ) {
			// EQUIP CURSOR
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield || 
					entity.inventory.get(i) == entity.currentLight) {

				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			
			// DISPLAY AMOUNT
			if(entity == gp.player && entity.inventory.get(i).amount > 1) {
				
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				
				String s = "" + entity.inventory.get(i).amount;
				amountX = getXforAlignRightText(s, slotX + 44);
				amountY = slotY + gp.tileSize;
				
				// SHADOW
				g2.setColor(new Color(60, 60, 60));
				g2.drawString(s, amountX, amountY);
				
				// NUMBER
				g2.setColor(Color.white);
				g2.drawString(s, amountX, amountY);
			}
			
			slotX += slotSize;

			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		// CURSOR
		if(cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			// DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			// DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			// DRAW DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28f));

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if(itemIndex < entity.inventory.size()) {

				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX,  textY);
					textY += 32;
				}
			}
		}
		
	}
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text,  x-4,  y-4);
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		// Back to the title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	public void drawOptionsScreen() {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		// SUB WINDOW
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {

		int textX;
		int textY;
		// TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				if(!gp.fullScreenOn) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		// MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		// SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		// CONTROL
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 2;
				commandNum = 0;
			}
		}
		// END GAME
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 3;
				commandNum = 0;
			}
		}
		// BACK
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		// FULL SCREEN CHECKBOX
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn) {
			g2.fillRect(textX, textY, 24, 24);
		}
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24* gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		// SE
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24* gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		try {
			gp.config.saveConfig();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void options_fullScreenNotification(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;

		currentDialogue = "The change will take \neffect after restarting \nthe game.";

		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		// BACK
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
			}
		}
	}
	public void options_control(int frameX, int frameY) {

		int textX;
		int textY;
		// TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY+=gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY+=gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY+=gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
		g2.drawString("Options", textX, textY);

		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;

		g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY+=gp.tileSize;
		g2.drawString("F", textX, textY); textY+=gp.tileSize;
		g2.drawString("C", textX, textY); textY+=gp.tileSize;
		g2.drawString("P", textX, textY); textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY);
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	public void options_endGameConfirmation(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;

		currentDialogue = "Quit the game and \nreturn to the title screen?";

		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.resetGame(true);
			}
		}
		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0, 0, 0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 50) { // transition is done
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;		
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			gp.changeArea();
			
		}
	}
	public void drawTradeScreen() {
		
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		
		npc.dialogueSet = 0;
		drawDialogueScreen();
		
		// DRAW WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height= (int)(gp.tileSize * 3.5);
		drawSubWindow(x , y, width, height);
		
		// DRAW TEXTS
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}
		y += gp.tileSize;
		
	}
	public void trade_buy() {
		
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		
		// DRAW NPC INVENTORY
		drawInventory(npc, true);
		
		// DRAW HINT WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins: " + gp.player.coin, x + 24, y + 60);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			 g2.drawImage(coin, x+10, y+8, 32, 32, null);
			 
			 int price = npc.inventory.get(itemIndex).price;
			 String text = "" + price;
			 x = getXforAlignRightText(text, gp.tileSize*8-20);
			 g2.drawString(text, x, y+32);
			 
			 // BUY AN ITEM
			 if(gp.keyH.enterPressed == true) {
				 if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					 subState = 0;
					 npc.startDialogue(npc, 2);
				 }
				 else {
					 if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						 gp.player.coin -= npc.inventory.get(itemIndex).price; 
					 }
					 else {
						 subState = 0;
						 npc.startDialogue(npc, 3);
					 }
				 }

			 }
		}	
	}
	
	public void trade_sell( ) {
		
		
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);
		
		int x;
		int y;
		int width;
		int height;
		
		// DRAW HINT WINDOW
		x = gp.tileSize*2;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins: " + gp.player.coin, x + 24, y + 60);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			
			x = (int)(gp.tileSize*15.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			 g2.drawImage(coin, x+10, y+8, 32, 32, null);
			 
			 int price = gp.player.inventory.get(itemIndex).price/2;
			 String text = "" + price;
			 x = getXforAlignRightText(text, gp.tileSize*18-20);
			 g2.drawString(text, x, y+32);
			 
			 // SELL AN ITEM
			 if(gp.keyH.enterPressed == true) {
				
				 if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || 
						 gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
					 commandNum = 0;
					 subState = 0;
					 npc.startDialogue(npc, 4);
				 }
				 else {
					 if(gp.player.inventory.get(itemIndex).amount > 1 ) {
						 gp.player.inventory.get(itemIndex).amount--;
					 }
					 else {
						 gp.player.inventory.remove(itemIndex);
					 }
					 
					 gp.player.coin += price;
				 }
			 }
		}	
	}
	public void drawSleepScreen() {
		counter++;
		
		if(counter < 120) {
			gp.eManager.lighting.filterAlpha += 0.01f;
			if(gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		if(counter >= 120) {
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if(gp.eManager.lighting.filterAlpha <= 0f) {
				gp.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.eManager.lighting.dayCounter = 0;
				gp.gameState = gp.playState;
				gp.player.getImage();
			}
		}
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		return slotCol + (slotRow*5);
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - length/2;
	}
	public int getXforAlignRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return tailX - length;
	}
}
