package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

	GamePanel gp;

	// Booleanos para determinar si estamos pulsando la tecla o no
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;

	// DEBUG
	boolean checkDrawTime;
	public boolean checkWeapon;
	public boolean godModeOn;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
   // TODO document why this method is empty
 }

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); // getKeyCode() devuelve el KeyCode entero asociado con la tecla pulsada en el eveento

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}

		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
			dialogueState(code);
		}

		// CHARACTER STATE
		else if(gp.gameState == gp.characterState) {
			characterState(code);
		}

		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}

		// GAMEOVER STATE
		else if (gp.gameState == gp.gameOverState) {

			gameOverState(code);
		}
		
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {

			tradeState(code);
		}
		// MAP STATE
		else if (gp.gameState == gp.mapState) {

			mapState(code);
		}

	}

	public void titleState(int code) {

			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;

				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
					gp.playSE(8);
				} else {
					gp.playSE(3);
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;

				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
					gp.playSE(8);
				} else {
					gp.playSE(3);
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					gp.saveLoad.load();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}

	public void optionsState(int code) {

		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 5; break;
		case 3: maxCommandNum = 1;
		}

		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum --;
			gp.playSE(8);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
				gp.playSE(3);
			}
		}

		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum ++;
			gp.playSE(8);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
				gp.playSE(3);
			}
		}

		if(code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(8);
				}

				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(8);
				}

			}
		}

		if(code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(3);
				}

				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(3);
				}
			}
		}

	}

	public void playState(int code) {
		// Si pulsamos una tecla, como la W, upPressed pasa a true.
					if (code == KeyEvent.VK_W) {
						upPressed = true;
					}
					if (code == KeyEvent.VK_S) {
						downPressed = true;
					}
					if (code == KeyEvent.VK_A) {
						leftPressed = true;
					}
					if (code == KeyEvent.VK_D) {
						rightPressed = true;
					}
					// PAUSAR
					if (code == KeyEvent.VK_P) {
						gp.gameState = gp.pauseState;
					}

					if (code == KeyEvent.VK_C) {
						gp.gameState = gp.characterState;
					}

					if (code == KeyEvent.VK_ENTER) {
						enterPressed = true;
					}

					if (code == KeyEvent.VK_L) {
						shotKeyPressed = true;
					}

					if (code == KeyEvent.VK_ESCAPE) {
						gp.gameState = gp.optionsState;
					}
					// MAPA
					if (code == KeyEvent.VK_M) {
						gp.gameState = gp.mapState;
					}
					// MINIMAPA
					if (code == KeyEvent.VK_X) {
						if(gp.map.miniMapOn == false) {
							gp.map.miniMapOn = true;
						}else {
							gp.map.miniMapOn = false;
						}
					}
					
					//GUARD
					if (code == KeyEvent.VK_SPACE) {
						spacePressed = true;
					}


					// DEBUG
					if (code == KeyEvent.VK_T) {
						if (!checkDrawTime) {
							checkDrawTime = true;
						} else if (checkDrawTime) {
							checkDrawTime = false;
						}
					}
					if (code == KeyEvent.VK_Y) {
						if (!checkWeapon) {
							checkWeapon = true;
						} else if (checkWeapon) {
							checkWeapon = false;
						}
					}

					if (code == KeyEvent.VK_R) {
						switch(gp.currentMap) {
						case 0: gp.tileM.loadMap("/maps/worldV3.txt", 0); break;
						case 1: gp.tileM.loadMap("/maps/interior01.txt", 1); break;
						}

					}
					if (code == KeyEvent.VK_G) {
						
						if (!godModeOn) {
							godModeOn = true;
						} else if (godModeOn) {
							godModeOn = false;
						}

					}

	}

	public void pauseState(int code) {
		if (code == KeyEvent.VK_P) {

			gp.gameState = gp.playState;
		}
	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {

			enterPressed = true;
		}
	}

	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		
		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		
		playerInventory(code);
		
	}

	public void gameOverState(int code) {

		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(8);
		}

		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(0);
			}
			else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.resetGame(true);

			}
		}

	}
	
	public void tradeState(int code) {
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		if(gp.ui.subState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(8);
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(3);
			}
		}
		
		if(gp.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		
		if(gp.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}
	public void mapState(int code) {
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	
	public void playerInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(11);
			}

		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(11);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
				gp.playSE(11);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
				gp.playSE(11);
			}
		}
	}
	
public void npcInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(11);
			}

		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(11);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
				gp.playSE(11);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
				gp.playSE(11);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		// Al dejar de pulsar la tecla, como la W, upPressed pasa a false.
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_L) {
			shotKeyPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

}
