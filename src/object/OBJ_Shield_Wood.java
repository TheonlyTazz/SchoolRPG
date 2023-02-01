package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        description = "["+name+"]\nJust a wooden Shield";
        type = type_shield;

        defenseValue = 1;

        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);

    }
}
