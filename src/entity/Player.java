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
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;




    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;




        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();



    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 40;
        speed = 4;
        direction = "down";

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
    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }

    public void getPlayerAttackImage() {
        String type = "attack";
        switch(currentWeapon.type){
            case(type_sword) -> type = "attack";
            case(type_axe) -> type = "axe";
        }
        attackUp1 = setup("/player/boy_"+type+"_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/boy_"+type+"_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/boy_"+type+"_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/boy_"+type+"_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/boy_"+type+"_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/boy_"+type+"_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/boy_"+type+"_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/boy_"+type+"_right_2", gp.tileSize*2, gp.tileSize);

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
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MON COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon);
            contactMonster(monsterIndex);

            // CHECK iTILES COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
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
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.dialoguePressed = false;

            spriteCounter++;
            if(spriteCounter > 16) {
                if(spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
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
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon);
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
                inventory.add(gp.obj[i]);
                text = "Got a " + gp.obj[i].name + "!";
                gp.obj[i] = null;
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
                gp.npc[i].speak();
            }

        }
    }
    public void contactMonster(int i){

        if (i != 999){

            if(!invincible && !gp.mon[i].dying){
                gp.playSE(6);
                int damage = gp.mon[i].attack - defense;
                if(damage < 0) damage = 0;
                life -= damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int i){

        if (i != 999) {

            if(!gp.mon[i].invincible){
                gp.playSE(5);
                int damage = attack - gp.mon[i].defense;
                if(damage < 0) damage = 0;
                gp.mon[i].life -= damage;
                gp.ui.addMessage(damage + " Damage!");
                gp.mon[i].invincible = true;
                gp.mon[i].damageReaction();

                if(gp.mon[i].life <= 0){
                    gp.mon[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.mon[i].name+"!");
                    exp += gp.mon[i].exp;
                    checkLevelUp();
                }
            }


        }
    }
    public void changeInteractiveTile(int i){

        if(i != 999 && gp.keyH.dialoguePressed && gp.iTile[i].destructible && gp.iTile[i].isCorrectItem(this)){

            if (gp.iTile[i].attackCanceled)  attackCanceled = true;
            generateParticle(gp.iTile[i], gp.iTile[i]);
            gp.iTile[i] = gp.iTile[i].getDestroyedForm();
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
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
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

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
    }
}
