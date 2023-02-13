package entity;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public String playerName = "Player";
    public boolean attackCanceled = false;





    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 64;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        playerNum = 1;
        // SPRITES
        spriteWidth = 16;
        spriteHeight = 32;


        setDefaultValues();



    }
    public void setDefaultPos(){
        switch(gp.currentMap){
            case 0 ->{
                worldX = gp.tileSize * 31;
                worldY = gp.tileSize * 44;
            }
            case 1 -> {
                worldX = gp.tileSize * 20;
                worldY = gp.tileSize * 28-48;
            }
        }
    }
    public void setDefaultValues() {

        setDefaultPos();
        speed = 4;
        direction = "down";
        invincible = false;
        invincibleCounter = 0;
        gp.ui.commandNum = 0;
        gp.keyH.enterPressed = false;

        // PLAYER STATS
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        wisdom = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();

        getLayeredSprites();
        getPlayerImage();
        setItems();
    }
    public void setItems(){
        inventory = new ArrayList<>();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }
    public int getNextLevelExp(){return nextLevelExp = (int) (nextLevelExp + (nextLevelExp*1.1));}
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        attack = strength * currentWeapon.attackValue;
        return attack;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    public void getLayeredSprites(){
        bodyIndex = 1;
        eyeIndex = 1;
        outfitIndex = 1;
        outfitColor = 1;
        hairIndex = 1;
        hairColor = 1;
        loadSprites("/player/Character_Generator/Bodies/16x16/Body_0"+bodyIndex+".png", bodies, 16, 32);
        loadSprites("/player/Character_Generator/Eyes/16x16/Eyes_0"+eyeIndex+".png", eyes, 16, 32);
        loadSprites("/player/Character_Generator/Outfits/16x16/Outfit_0"+outfitIndex+"_0"+outfitColor+".png", outfits, 16, 32);
        loadSprites("/player/Character_Generator/Hairstyles/16x16/Hairstyle_0"+hairIndex+"_0"+hairColor+".png", hairstyles, 16, 32);
        //loadSprites("/player/Character_Generator/Accessories/16x16/", accessories, 16, 32);

    }
    public void getPlayerImage() {
        int i = 114;
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


    public void update() {

        if(attacking){
            attacking();

        }else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.dialoguePressed){
            if(keyH.upPressed) direction = "up";
            else if(keyH.downPressed) direction = "down";
            else if(keyH.leftPressed) direction = "left";
            else if(keyH.rightPressed) direction = "right";

            // CHECK TILE COLLISION
            collisionOn = false;
            //gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc[gp.currentMap]);
            interactNPC(npcIndex);

            // CHECK MON COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
            contactMonster(monsterIndex);

            // CHECK iTILES COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile[gp.currentMap]);
            changeInteractiveTile(iTileIndex);


            // CHECK EVENT
            gp.eHandler.checkEvent();



            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.dialoguePressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            if(gp.keyH.dialoguePressed && !attackCanceled){
                attacking = false;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.dialoguePressed = false;

            spriteCounter++;
            if(spriteCounter > 16) {
                if(spriteNum == 1) spriteNum++;
                else if (spriteNum == 2) spriteNum++;
                else if (spriteNum == 3) spriteNum++;
                else if (spriteNum == 4) spriteNum++;
                else if (spriteNum == 5) spriteNum = 1;

                spriteCounter = 0;
            }
        }

        //Update seperate of Keyhander
        if (invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(life <= 0) gp.gameState = gp.gameOverState;
    }
    public void attacking(){

        spriteCounter++;
        if(spriteCounter <= 5) spriteNum = 1;
        if(spriteCounter > 5  && spriteCounter <= 25) {
            spriteNum = 2;

            // SAVE
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust Player WorldX/Y for attackArea
            switch(direction){
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check monster collision with updated X, Y and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
            damageMonster(monsterIndex);


            // Restore original Data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i) {
        if(i != 999) {
            String text;

            if(inventory.size() != maxInventorySize){
                inventory.add(gp.obj[gp.currentMap][i]);
                text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                gp.obj[gp.currentMap][i] = null;
            }
            else {
                text = "Inventory Full";
            }
            gp.ui.addMessage(text);

        }
    }
    public void interactNPC(int i) {
        if(gp.keyH.dialoguePressed){

            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }

        }
    }
    public void contactMonster(int i){

        if (i != 999){

            if(!invincible && !gp.mon[gp.currentMap][i].dying){
                gp.playSE(6);
                int damage = gp.mon[gp.currentMap][i].attack - defense;
                if(damage < 0) damage = 0;
                life -= damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int i){

        if (i != 999) {

            if(!gp.mon[gp.currentMap][i].invincible){
                gp.playSE(5);
                int damage = attack - gp.mon[gp.currentMap][i].defense;
                if(damage < 0) damage = 0;
                gp.mon[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " Damage!");
                gp.mon[gp.currentMap][i].invincible = true;
                gp.mon[gp.currentMap][i].damageReaction();

                if(gp.mon[gp.currentMap][i].life <= 0){
                    gp.mon[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.mon[gp.currentMap][i].name+"!");
                    exp += gp.mon[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }


        }
    }
    public void changeInteractiveTile(int i){

        if(i != 999 && gp.keyH.dialoguePressed && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this)){

            if (gp.iTile[gp.currentMap][i].attackCanceled)  attackCanceled = true;
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
        }
    }
    public void checkLevelUp(){
        if (exp >= nextLevelExp){
            level++;
            exp -= nextLevelExp;
            nextLevelExp = getNextLevelExp();
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable) {
                // TODO
            }

        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    if (spriteNum == 3) image = up3;
                    if (spriteNum == 4) image = up4;
                    if (spriteNum == 5) image = up5;
                    if (spriteNum == 6) image = up6;


                }
                if(attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = attackUp1;
                    if (spriteNum == 2) image = attackUp2;
                }
            }
            case "down" -> {
                if(!attacking){
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    if (spriteNum == 3) image = down3;
                    if (spriteNum == 4) image = down4;
                    if (spriteNum == 5) image = down5;
                    if (spriteNum == 6) image = down6;

                }
                if(attacking){
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                }
            }
            case "left" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                    if (spriteNum == 4) image = left4;
                    if (spriteNum == 5) image = left5;
                    if (spriteNum == 6) image = left6;

                }
                if(attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) image = attackLeft1;
                    if (spriteNum == 2) image = attackLeft2;
                }
            }
            case "right" -> {
                if(!attacking){
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                    if (spriteNum == 4) image = right4;
                    if (spriteNum == 5) image = right5;
                    if (spriteNum == 6) image = right6;

                }
                if(attacking){
                    if (spriteNum == 1) image = attackRight1;
                    if (spriteNum == 2) image = attackRight2;
                }

            }
        }
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        if(gp.keyH.debug) {
            String text = solidArea.x+"/"+solidArea.y;
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(screenX + solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
            g2.drawString(text,screenX+gp.tileSize/4, screenY+gp.tileSize/2);
        }

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
    }
}
