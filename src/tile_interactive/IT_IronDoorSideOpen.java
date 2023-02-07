package tile_interactive;

import main.GamePanel;

public class IT_IronDoorSideOpen extends InteractiveTile{

    GamePanel gp;


    public IT_IronDoorSideOpen(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        down1 = setup("/tile_interactive/door_iron_open_side", gp.tileSize, gp.tileSize);
        destructible = false;
    }
    public void update() {

    }

}
