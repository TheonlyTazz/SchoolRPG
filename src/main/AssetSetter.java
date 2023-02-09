package main;

import monster.MON_GreenSlime;
import entity.*;
import object.*;
import tile_interactive.*;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    public void createObject(int id, int mapNum, String OBJ,int x,int y) {

        switch(OBJ){
            case "Key" -> gp.obj[mapNum][id] = new OBJ_Key(gp);
            case "Door" -> gp.obj[mapNum][id] = new OBJ_Door(gp);
            case "Boots" -> gp.obj[mapNum][id] = new OBJ_Boots(gp);
            case "Chest" -> gp.obj[mapNum][id] = new OBJ_Chest(gp);
            case "Axe" -> gp.obj[mapNum][id] = new OBJ_Axe(gp);
            case "Blue Shield" -> gp.obj[mapNum][id] = new OBJ_Blue_Shield(gp);
        }
        gp.obj[mapNum][id].worldX = gp.tileSize * x;
        gp.obj[mapNum][id].worldY = gp.tileSize * y;
    }
    public void createiTile(int id, int mapNum, String iTile,int x,int y) {
        switch (iTile) {
            case "Iron Door" -> gp.iTile[mapNum][id] = new IT_IronDoor(gp,x, y);
            case "Iron Door Side" -> gp.iTile[mapNum][id] = new IT_IronDoorSide(gp,x ,y);
        }
        gp.iTile[mapNum][id].worldX = gp.tileSize * x;
        gp.iTile[mapNum][id].worldY = gp.tileSize * y;
    }
    public void createNPC(int id, int mapNum, String NPC,int x,int y) {
        switch(NPC){
            case "OldMan" -> gp.npc[mapNum][id] = new NPC_OldMan(gp);
            case "Merchant" -> gp.npc[mapNum][id] = new NPC_Merchant(gp);
        }
        gp.npc[mapNum][id].worldX = gp.tileSize * x;
        gp.npc[mapNum][id].worldY = gp.tileSize * y;
    }
    public void createMON(int id, int mapNum, String MON,int x,int y) {
        switch(MON){
            case "Green Slime" -> gp.mon[mapNum][id] = new MON_GreenSlime(gp);
            case "monster" -> gp.mon[mapNum][id] = new NPC_OldMan(gp);
        }
        gp.mon[mapNum][id].worldX = gp.tileSize * x;
        gp.mon[mapNum][id].worldY = gp.tileSize * y;
    }

    public void setObject() {
        int i = 0;
        int mapNum;
        mapNum = 0;
        /*
        createObject(i, mapNum, "Key", 28, 25); i++;
        createObject(i, mapNum, "Key", 28, 26); i++;
        createObject(i, mapNum, "Key", 28, 27); i++;
        createObject(i, mapNum, "Key", 28, 28); i++;
        createObject(i, mapNum, "Blue Shield", 30, 38); i++;
        createObject(i, mapNum, "Axe", 31, 38); i++;

         */

        mapNum = 1;
        createObject(i, mapNum, "Key", 28, 28);


    }
    public void setiTile(){
        int i = 0;
        int mapNum;
        mapNum = 0;/*
        createiTile(i, mapNum, "Iron Door", 31, 22); i++;
        createiTile(i, mapNum, "Iron Door", 24, 35); i++;
        createiTile(i, mapNum, "Iron Door", 38, 35); i++;
        */
        mapNum = 1;
        createiTile(i, mapNum, "Iron Door Side", 19, 27);i++;
        createiTile(i, mapNum, "Iron Door", 26, 24);i++;
        createiTile(i, mapNum, "Iron Door", 35, 24);i++;
        createiTile(i, mapNum, "Iron Door", 33, 30);i++;

        mapNum = 2;
        createiTile(i, mapNum, "Iron Door", 19, 34);i++;
        createiTile(i, mapNum, "Iron Door", 32, 34);i++;
        createiTile(i, mapNum, "Iron Door", 39, 34);




    }
    public void setNPC() {
        int i = 0;
        int mapNum;

        mapNum = 0;
        /*
        createNPC(i, mapNum, "OldMan", 21, 21);i++;
        createNPC(i, mapNum, "OldMan", 22, 21);i++;
        createNPC(i, mapNum, "OldMan", 23, 21);i++;
        createNPC(i, mapNum, "OldMan", 24, 21);i++;
        createNPC(i, mapNum, "OldMan", 25, 21);i++;

         */

        mapNum = 1;
        createNPC(i, mapNum, "OldMan", 26, 29); i++;
        createNPC(i, mapNum, "Merchant", 35, 27); i++;

    }
    public void setMON(){
        int i = 0;
        int mapNum;

        mapNum = 0;/*
        createMON(i, mapNum, "Green Slime", 23, 42);i++;
        createMON(i, mapNum, "Green Slime", 24, 42);i++;
        createMON(i, mapNum, "Green Slime", 25, 42);i++;
        createMON(i, mapNum, "Green Slime", 26, 42);i++;
        createMON(i, mapNum, "Green Slime", 27, 42);
        */
    }


}
