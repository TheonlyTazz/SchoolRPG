package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tree extends Entity {
    public OBJ_Tree(GamePanel gp) {
        super(gp);
        name = "Tree";
        down1 = setup("/tiles/ME_Singles_City_Props_16x16_Tree_15");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
