package main;


import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		// Checkeamos si coinciden algunos de los laterales del area de colision con las
		// coordenadas de la tile
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		// Basándonos en estas coordenadas, encontraremos su col y row
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		// Creamos 2 mas
		int tileNum1, tileNum2;
		
		//usamos una direccion temporal
		String direction = entity.direction;
		if(entity.knockBack == true) {	
			direction = entity.knockBackDirection;
		}

		switch (direction) {
		case "up":
			// Restamos la velocidad para saber hacia donde va, predecir, hacia que tile se
			// aproxima
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];

			// Si alguno de los 2 o ambos son verdaderos, el jugador está colisionando, y no
			// puede ir en esa dirección
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			// Si alguno de los 2 o ambos son verdaderos, el jugador está colisionando, y no
			// puede ir en esa dirección
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

			// Si alguno de los 2 o ambos son verdaderos, el jugador está colisionando, y no
			// puede ir en esa dirección
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			// Si alguno de los 2 o ambos son verdaderos, el jugador está colisionando, y no
			// puede ir en esa dirección
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		}

	}

	public int checkObject(Entity entity, boolean player) {
		
		// Checkeamos si el Player está colisionando con algún objeto, de ser así
		// devolvemos el índice del objeto
		int index = 999;
		
		//usamos una direccion temporal
		String direction = entity.direction;
		if(entity.knockBack == true) {	
			direction = entity.knockBackDirection;
			}

		int objLength = gp.obj[1].length;
		// Escaneamos el array de objetos
		for (int i = 0; i < objLength; i++) {
			// Checkeamos si es null o no
			if (gp.obj[gp.currentMap][i] != null) {
				// Get entity`s solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object`s solid area position
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;// Debido a que por defecto el
																					// solidArea es 0, la suma no añade
																					// nada.
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;// Pero permite ser modificada si se
																					// diese el caso con valores
																					// específicos

				switch (direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed;break;
				case "left": entity.solidArea.x -= entity.speed;break;
				case "right":entity.solidArea.x += entity.speed; break;
				}

				if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {

					if (gp.obj[gp.currentMap][i].collision) {
						entity.collisionOn = true;
					}
					// Check if its player or not
					if (player) {
						index = i;
					}
				}

				// Reseteamos los valores del solid area después del switch para que no stackeen
				// infinitamente
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	// NPC OR MONSTER
	public int checkEntity(Entity entity, Entity[][] target) {
		// Checkeamos si el NPC O MONSTRUO está colisionando con algún objeto, de ser
		// así devolvemos el índice del objeto
		int index = 999;
		
		//usamos una direccion temporal
		String direction = entity.direction;
		if(entity.knockBack == true) {	
		   direction = entity.knockBackDirection;
			}

		// Escaneamos el array de objetos
		for (int i = 0; i < target[1].length; i++) {

			// Checkeamos si es null o no
			if (target[gp.currentMap][i] != null) {
				// Get entity`s solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object`s solid area position
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;// Debido a que por defecto el
																					// solidArea es 0, la suma no añade
																					// nada.
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;// Pero permite ser modificada si se
																					// diese

				switch (direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
					}

				if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea) &&
						(target[gp.currentMap][i] != entity)) {
					// Con esto evitamos que las entidades se consideren a sí mismas como collision targets
						entity.collisionOn = true;
						index = i;
				}
				// Reseteamos los valores del solid area después del switch para que no stackeen
				// infinitamente
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;


		// Get entity`s solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		// Get the object`s solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;// Debido a que por defecto el
																			// solidArea es 0, la suma no añade
																			// nada.
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;// Pero permite ser modificada si se diese
																			// el caso con valores específicos

		switch (entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left":entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;

		}
		// Reseteamos los valores del solid area después del switch para que no stackeen
		// infinitamente
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;

		return contactPlayer;
	}

}
