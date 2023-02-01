package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Blue_Shield extends Entity {

    public OBJ_Blue_Shield(GamePanel gp) {
        super(gp);

        name = "Blue Shield";
        description = "["+name+"]\nJust a blue shield";
        type = type_shield;

        defenseValue = 2;

        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);

    }
}
