package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;
    public boolean attackCanceled = false;



    public InteractiveTile(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }
    public boolean isCorrectItem(Entity entity){

        return false;
    }
    public void update(){


    }
    public void playSE(){}
    public InteractiveTile getDestroyedForm(){
        return null;
    }
}
