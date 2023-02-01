package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        name = "Axe";
        description = "["+name+"]\nBit rusty, but can\nbe used to cut trees";
        type = type_axe;

        attackValue = 5;

        attackArea.width = 30;
        attackArea.height = 30;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);

    }
}
