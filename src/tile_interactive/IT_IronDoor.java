package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_IronDoor extends InteractiveTile{

    GamePanel gp;

    public IT_IronDoor(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        attackCanceled = true;
        type = type_door;

        down1 = setup("/tile_interactive/door_iron", gp.tileSize, gp.tileSize);
        destructible = true;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = entity.currentWeapon.type == type_axe;
        return isCorrectItem;
    }
    public void playSE(){}

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_IronDoorOpen(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(97, 97, 97);
        return color;
    }
    public int getParticleSize(){
        int size = 6;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getMaxLife(){
        maxLife = 10;
        return maxLife;
    }
}
