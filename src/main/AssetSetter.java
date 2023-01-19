package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void createObject(String OBJ,int id,int x,int y) {
        switch(OBJ){
            case "Key" -> gp.obj[id] = new OBJ_Key(gp);
            case "Door" -> gp.obj[id] = new OBJ_Door(gp);
            case "Boots" -> gp.obj[id] = new OBJ_Boots(gp);
            case "Chest" -> gp.obj[id] = new OBJ_Chest(gp);
        }
        gp.obj[id].worldX = gp.tileSize * x;
        gp.obj[id].worldY = gp.tileSize * y;
    }
    public void setObject() {
        createObject("Key", 0, 23, 7);
        createObject("Key", 1, 23, 40);
        createObject("Key", 2, 38, 8);
        createObject("Key", 3, 10, 11);
        createObject("Key", 4, 8, 28);
        createObject("Key", 5, 12, 22);
        createObject("Key", 6, 10, 7);
    }
}
