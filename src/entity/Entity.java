package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage down1, down2, down3, down4, down5, down6;
    public BufferedImage left1, left2, left3, left4, left5, left6;
    public BufferedImage right1, right2, right3, right4, right5, right6;
    public BufferedImage read1, read2, read3, read4, read5, read6, read7, read8, read9, read10, read11, read12;

    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image1, image2, image3, image4, image5;
    public BufferedImage[] sprites = new BufferedImage[1300];
    public BufferedImage[] bodies = new BufferedImage[1300];
    public BufferedImage[] eyes = new BufferedImage[1300];
    public BufferedImage[] hairstyles = new BufferedImage[1300];
    public BufferedImage[] outfits = new BufferedImage[1300];
    //public BufferedImage[] accessories = new BufferedImage[50];
    public int bodyIndex, eyeIndex, hairIndex, hairColor, outfitIndex, outfitColor, accIndex;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);


    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public String[] dialogues = new String[20];

    // SPRITES
    public int spriteWidth = 48;
    public int spriteHeight = 48;

    // STATE
    public int worldX, worldY;
    public int previousX, previousY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;


    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;


    // CHARACTER ATTRIBUTES
    public String name;
    public int playerNum;
    public int speed;
    public int maxLife = 6;
    public int life = maxLife;
    public int level;
    public int strength;
    public int wisdom;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;


    public Entity currentWeapon;
    public Entity currentShield;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    // TYPE
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;

    public final int type_any = 10;
    public final int type_door = 11;




    public void getLayeredSprites(){}


    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void getSprites() {
        int i = 114;
        // 114 - 137 walking animation
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
    public void doRead(){
        for(int cycle = 0;cycle < 4; cycle++){
            switch(cycle){
                case 0 -> openbook();
                case 1,2,3 -> reading();
                case 4 -> closebook();
            }
        }
    }
    public void openbook(){
        int i = 342;
        read1 = sprites[i]; i++;
        read2 = sprites[i]; i++;
        read3 = sprites[i]; i++;
        read4 = sprites[i]; i++;
        read5 = sprites[i]; i++;
        read6 = sprites[i]; i++;
        read7 = sprites[i]; i++;
        read8 = sprites[i]; i++;
        read9 = sprites[i]; i++;
        read10 = sprites[i]; i++;
        read11 = sprites[i]; i++;
        read12 = sprites[i];

    }
    public void closebook(){
        int i = 353;
        read1 = sprites[i]; i--;
        read2 = sprites[i]; i--;
        read3 = sprites[i]; i--;
        read4 = sprites[i]; i--;
        read5 = sprites[i]; i--;
        read6 = sprites[i]; i--;
        read7 = sprites[i]; i--;
        read8 = sprites[i]; i--;
        read9 = sprites[i]; i--;
        read10 = sprites[i]; i--;
        read11 = sprites[i]; i--;
        read12 = sprites[i];

    }
    public void reading(){
        int i = 399;
        read1 = sprites[i]; i++;
        read2 = sprites[i]; i++;
        read3 = sprites[i]; i++;
        read4 = sprites[i]; i++;
        read5 = sprites[i]; i++;
        read6 = sprites[i]; i++;
        read7 = sprites[i]; i++;
        read8 = sprites[i]; i++;
        read9 = sprites[i]; i++;
        read10 = sprites[i]; i++;
        read11 = sprites[i]; i++;
        read12 = sprites[i];
    }

    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getMaxLife(){
        maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }
    public void speak(){
        if(dialogues[dialogueIndex] == null) dialogueIndex = 0;
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    public void setAction(){}
    public void damageReaction(){}

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc[gp.currentMap]);
        gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){
            if(!gp.player.invincible){
                gp.playSE(6);
                int damage = attack - gp.player.defense;
                if(damage < 0) damage = 0;

                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
        //Update seperate of Keyhander
        if (invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        // IF COLLISION IS FALSE, Entity CAN MOVE
        if(!collisionOn && this.type != type_npc) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if(spriteCounter > 16) {
            if(spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX ;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                case "down" -> {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                case "left" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                case "right" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }

            // Monster HP Bar
            if(type == 2 && hpBarOn){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                if(type == type_monster) changeAlpha(g2, 0.4f);
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            if(gp.keyH.debug) {

                g2.setColor(Color.white);
                g2.setFont(g2.getFont().deriveFont(24F));
                String text = worldX+"/"+worldY;
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(screenX + solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
                g2.drawString(text,screenX+gp.tileSize/4, screenY+gp.tileSize/2);
            }

            // RESET ALPHA
            changeAlpha(g2, 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 10;

        if (dyingCounter%i*2 == i) changeAlpha(g2, 0f);
        if (dyingCounter%i*2 == 0) changeAlpha(g2, 1f);
        if (dyingCounter > i*8) {
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void loadSprites(String sheetPath, BufferedImage[] sprites, int spriteWidth, int spriteHeight){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        int sheetWidth;
        int sheetHeight;
        int spritecounter = 0;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(sheetPath)));

        }catch(IOException e) {
            e.printStackTrace();
        }
        sheetWidth = image.getWidth(); sheetHeight = image.getHeight();
        for(int y = 0; y+spriteHeight <= sheetHeight; y += spriteHeight){
            for(int x = 0; x+spriteWidth <= sheetWidth; x += spriteWidth){
                spritecounter++;

                sprites[spritecounter] = image.getSubimage(x, y,spriteWidth,spriteHeight);
                sprites[spritecounter] = uTool.scaleImage(sprites[spritecounter], spriteWidth*gp.scale, spriteHeight*gp.scale);

            }
        }
    }

    public void selectSprite(){
        UtilityTool uTool = new UtilityTool();
        sprites = new BufferedImage[1300];
        int sheetWidth;
        int sheetHeight;
        int spritecounter = 0;
        BufferedImage image = null;
        BufferedImage imageBody = null;
        BufferedImage imageHair = null;
        BufferedImage imageEyes = null;
        BufferedImage imageOutfit = null;
        try {
            imageBody = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Character_Generator/Bodies/16x16/Body_0"+bodyIndex+".png")));
            imageHair = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Character_Generator/Eyes/16x16/Eyes_0"+eyeIndex+".png")));
            imageEyes = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Character_Generator/Outfits/16x16/Outfit_0"+outfitIndex+"_0"+outfitColor+".png")));
            imageOutfit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Character_Generator/Hairstyles/16x16/Hairstyle_0"+hairIndex+"_0"+hairColor+".png")));


        }catch(IOException e) {
            e.printStackTrace();
        }

        sheetWidth = imageBody.getWidth(); sheetHeight = imageBody.getHeight();
        BufferedImage c = new BufferedImage(sheetWidth, sheetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = c.getGraphics();
        g.drawImage(imageBody, 0, 0, null);
        g.drawImage(imageHair, 0, 0, null);
        g.drawImage(imageEyes, 0, 0, null);
        g.drawImage(imageOutfit, 0, 0, null);

        for(int y = 0; y+spriteHeight <= sheetHeight; y += spriteHeight){
            for(int x = 0; x+spriteWidth <= sheetWidth; x += spriteWidth){

                sprites[spritecounter] = c.getSubimage(x, y,16,32);
                sprites[spritecounter] = uTool.scaleImage(sprites[spritecounter], 16*gp.scale, 32*gp.scale);
                spritecounter++;

            }
        }

    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, spriteWidth, spriteHeight);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void debug(){

    }
}
