package entity.Students;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Student_1 extends Entity {
    public NPC_Student_1(GamePanel gp) {
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
        name = "Student_1";
        type = type_npc;
        loadSprites("/npc/Adam_run_16x16.png", sprites, 16, 32);


        getSprites();
        setDialogue();
    }
    public void getSprites() {
        int i = 1;
        right1 = sprites[i]; i++;
        right2 = sprites[i]; i++;
        right3 = sprites[i]; i++;
        right4 = sprites[i]; i++;
        right5 = sprites[i]; i++;
        right6 = sprites[i]; i++;
        up1 = sprites[i]; i++;
        up2 = sprites[i]; i++;
        up3 = sprites[i]; i++;
        up4 = sprites[i]; i++;
        up5 = sprites[i]; i++;
        up6 = sprites[i]; i++;
        left1 = sprites[i]; i++;
        left2 = sprites[i]; i++;
        left3 = sprites[i]; i++;
        left4 = sprites[i]; i++;
        left5 = sprites[i]; i++;
        left6 = sprites[i]; i++;
        down1 = sprites[i]; i++;
        down2 = sprites[i]; i++;
        down3 = sprites[i]; i++;
        down4 = sprites[i]; i++;
        down5 = sprites[i]; i++;
        down6 = sprites[i]; i++;
    }
    public void setDialogue() {
        dialogues[0] = "Ich bin Student 1";
        dialogues[1] = "Langsam wirds hier";
        dialogues[2] = "Aber wir sind noch lang nicht fertig";
        dialogues[3] = "Stay tuned for more!";

    }
    public void setAction() {
        actionLockCounter++;
        if(actionLockCounter <= 120) return;
        Random random = new Random();
        int i = random.nextInt(100)+1; // random number 1-100
        if (i <= 25) direction = "up";
        if (i > 25 && i <= 50) direction = "down";
        if (i > 50 && i <= 75) direction = "left";
        if (i > 75) direction = "right";
        actionLockCounter = 0;
    }
    public void speak(){super.speak();}
}

