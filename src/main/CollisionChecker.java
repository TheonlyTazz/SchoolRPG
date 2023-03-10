package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
/*
    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBotRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                for(int i = 1; i <= gp.currentMapLayers; i++){
                    entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                    tileNum1 = gp.levelM.mapTileNum[gp.currentMap][i][entityLeftCol][entityTopRow];
                    tileNum2 = gp.levelM.mapTileNum[gp.currentMap][i][entityRightCol][entityTopRow];
                    if (tileNum1 == 0 || tileNum2 == 0) return;

                    if (gp.levelM.tileArray[tileNum1].collision || gp.levelM.tileArray[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }

            }
            case "down" -> {
                for(int i = 1; i <= gp.currentMapLayers; i++){
                    entityBotRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                    tileNum1 = gp.levelM. mapTileNum[gp.currentMap][i][entityLeftCol][entityBotRow];
                    tileNum2 = gp.levelM. mapTileNum[gp.currentMap][i][entityRightCol][entityBotRow];
                    if (tileNum1 == 0 || tileNum2 == 0) return;

                    if (gp.levelM.tileArray[tileNum1].collision || gp.levelM.tileArray[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }

            }
            case "left" -> {
                for(int i = 1; i <= gp.currentMapLayers; i++){
                    entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                    tileNum1 = gp.levelM. mapTileNum[gp.currentMap][i][entityLeftCol][entityTopRow];
                    tileNum2 = gp.levelM. mapTileNum[gp.currentMap][i][entityLeftCol][entityBotRow];
                    if (tileNum1 == 0 || tileNum2 == 0) return;

                    if (gp.levelM.tileArray[tileNum1].collision || gp.levelM.tileArray[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }

            }
            case "right" -> {
                for(int i = 1; i <= gp.currentMapLayers; i++){
                    entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                    tileNum1 = gp.levelM. mapTileNum[gp.currentMap][i][entityRightCol][entityTopRow];
                    tileNum2 = gp.levelM. mapTileNum[gp.currentMap][i][entityRightCol][entityBotRow];
                    if (tileNum1 == 0 || tileNum2 == 0) return;

                    if (gp.levelM.tileArray[tileNum1].collision || gp.levelM.tileArray[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
            }

        }
    }

 */
    public int checkObject(Entity entity, boolean player) {

        int index = 999;
        for (int i = 0; i < gp.obj[1].length; i++) {

            if(gp.obj[gp.currentMap][i] != null){

                // Get Entity Solid Pos
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get Objects Solid Position
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;


                direction(entity);

                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    if(gp.obj[gp.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player){
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;


            }
        }
        return index;
    }

    private void direction(Entity entity) {
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }
    }

    // NPC or MOB COLLISION
    public int checkEntity(Entity entity, Entity[] target){

        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] == entity) return index;
            if(target[i] != null){

                // Get Entity Solid Pos
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get Objects Solid Position
               target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
               target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;


                direction(entity);
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get Entity Solid Pos
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get Objects Solid Position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;


        direction(entity);
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
