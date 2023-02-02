package main;

import monster.MON_GreenSlime;
import entity.*;
import object.*;
import tile_interactive.IT_IronDoor;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    public void createObject(int id,String OBJ,int x,int y) {
        switch(OBJ){
            case "Key" -> gp.obj[id] = new OBJ_Key(gp);
            case "Door" -> gp.obj[id] = new OBJ_Door(gp);
            case "Boots" -> gp.obj[id] = new OBJ_Boots(gp);
            case "Chest" -> gp.obj[id] = new OBJ_Chest(gp);
            case "Axe" -> gp.obj[id] = new OBJ_Axe(gp);
            case "Blue Shield" -> gp.obj[id] = new OBJ_Blue_Shield(gp);
        }
        gp.obj[id].worldX = gp.tileSize * x;
        gp.obj[id].worldY = gp.tileSize * y;
    }
    public void createiTile(int id,String iTile,int x,int y) {
        switch (iTile) {
            case "Iron Door" -> gp.iTile[id] = new IT_IronDoor(gp,x, y);
            case "Iron Door2" -> gp.iTile[id] = new IT_IronDoor(gp,x ,y);
        }
        gp.iTile[id].worldX = gp.tileSize * x;
        gp.iTile[id].worldY = gp.tileSize * y;
    }
    public void createNPC(int id, String NPC,int x,int y) {
        switch(NPC){
            case "OldMan" -> gp.npc[id] = new NPC_OldMan(gp);
            case "monster" -> gp.npc[id] = new NPC_OldMan(gp);
        }
        gp.npc[id].worldX = gp.tileSize * x;
        gp.npc[id].worldY = gp.tileSize * y;
    }
    public void createMON(int id, String MON,int x,int y) {
        switch(MON){
            case "Green Slime" -> gp.mon[id] = new MON_GreenSlime(gp);
            case "monster" -> gp.mon[id] = new NPC_OldMan(gp);
        }
        gp.mon[id].worldX = gp.tileSize * x;
        gp.mon[id].worldY = gp.tileSize * y;
    }

    public void setObject() {
        int i = 0;
        createObject(i, "Key", 28, 25); i++;
        createObject(i, "Key", 28, 26); i++;
        createObject(i, "Key", 28, 27); i++;
        createObject(i, "Key", 28, 28); i++;
        createObject(i, "Blue Shield", 30, 38); i++;
        createObject(i, "Axe", 31, 38);
    }
    public void setiTile(){
        int i = 0;
        createiTile(i, "Iron Door", 31, 22); i++;
        createiTile(i, "Iron Door", 24, 35); i++;
        createiTile(i, "Iron Door", 38, 35);

    }
    public void setNPC() {
        createNPC(0, "OldMan", 21, 21);
        createNPC(1, "OldMan", 22, 21);
        createNPC(2, "OldMan", 23, 21);
        createNPC(3, "OldMan", 24, 21);
        createNPC(4, "OldMan", 25, 21);

    }
    public void setMON(){
        createMON(0, "Green Slime", 23, 42);
        createMON(1, "Green Slime", 24, 42);
        createMON(2, "Green Slime", 25, 42);
        createMON(3, "Green Slime", 26, 42);
        createMON(4, "Green Slime", 27, 42);

    }


}
