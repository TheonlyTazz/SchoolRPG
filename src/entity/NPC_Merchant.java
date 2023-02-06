package entity;

import main.GamePanel;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 32;
        direction = "down";
        speed = 1;
        name = "Merchant";
        type = type_npc;
        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {

        up1 = setup("/npc/merchant_down_1");
        up2 = setup("/npc/merchant_down_2");
        down1 = setup("/npc/merchant_down_1");
        down2 = setup("/npc/merchant_down_2");
        left1 = setup("/npc/merchant_down_1");
        left2 = setup("/npc/merchant_down_2");
        right1 = setup("/npc/merchant_down_1");
        right2 = setup("/npc/merchant_down_2");
    }
    public void setDialogue() {
        dialogues[0] = "Ach Hallo "+gp.player.playerName+"\nWie gehts Ihnen?";
        dialogues[1] = "Ich bin der Zauberer von OZ";
        dialogues[2] = "Kann ich Sie behilflich sein?";
        dialogues[3] = "Hello there!";

    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

    public void setItems(){
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }

}
