package entity;

import main.GamePanel;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);
        // SPRITES
        spriteWidth = 48;
        spriteHeight = 96;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 64;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        direction = "down";
        speed = 1;
        name = "Merchant";
        type = type_npc;
        loadSprites("/npc/idle_16x16_6.png", sprites, 16, 32);
        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        int i = 1;
        right1 = sprites[i];
        right2 = sprites[i]; i++;

        up1 = sprites[i];
        up2 = sprites[i]; i++;

        left1 = sprites[i];
        left2 = sprites[i]; i++;

        down1 = sprites[i];
        down2 = sprites[i];

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
        gp.ui.commandNum = 0;
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
