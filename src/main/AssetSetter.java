package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

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
            case "Monster" -> gp.npc[id] = new NPC_OldMan(gp);
        }
        gp.npc[id].worldX = gp.tileSize * x;
        gp.npc[id].worldY = gp.tileSize * y;
    }

    public void setNPC() {
        createNPC(0, "OldMan", 21, 21);


    }
    public void setObject() {

    }
}
