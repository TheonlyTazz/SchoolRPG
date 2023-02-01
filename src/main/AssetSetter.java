package main;

import monster.MON_GreenSlime;
import entity.*;
import object.*;


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
        }
        gp.obj[id].worldX = gp.tileSize * x;
        gp.obj[id].worldY = gp.tileSize * y;
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

    public void setNPC() {
        createNPC(0, "OldMan", 21, 21);
        createNPC(1, "OldMan", 22, 21);
        createNPC(2, "OldMan", 23, 21);
        createNPC(3, "OldMan", 24, 21);
        createNPC(4, "OldMan", 25, 21);

    }
    public void setMON(){
        createMON(0, "Green Slime", 23, 42);
    }
    public void setObject() {
        /*
        createObject(0, "Door", 24, 19);
        createObject(1, "Door", 17, 32);
        createObject(2, "Door", 30, 32);
        createObject(3, "Key", 24, 25);
         */
    }
}
