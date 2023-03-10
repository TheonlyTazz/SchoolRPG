package main;
import entity.Entity;

import java.awt.*;
import java.io.IOException;

import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font courier_40, courier_80B;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first Titlescreen, 1: second Titlescreen
    int subState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    public Entity npc;
    public String [] menuString = new String[20];
    public String[] menuValues = new String[20];
    public int menuLength = 0;


    public void setCharValue(){
        menuString = new String[20];
        menuLength = 11;
        menuString[0] = String.valueOf(gp.player.level);
        menuString[1] = String.valueOf(gp.player.exp);
        menuString[2] = String.valueOf(gp.player.nextLevelExp);
        menuString[3] = "";
        menuString[4] = String.valueOf(gp.player.strength);
        menuString[5] = String.valueOf(gp.player.dexterity);
        menuString[6] = String.valueOf(gp.player.attack);
        menuString[7] = String.valueOf(gp.player.defense);
        menuString[8] = gp.player.currentWeapon.name;
        menuString[9] = gp.player.currentShield.name;
        menuString[10] = String.valueOf(gp.player.coin);
    }
    public void setCharString(){
        menuString = new String[20];
        menuLength = 11;
        menuString[0] = "Level";
        menuString[1] = "Exp";
        menuString[2] = "Next Level";
        menuString[3] = "";
        menuString[4] = "Strength";
        menuString[5] = "Dexterity";
        menuString[6] = "Attack";
        menuString[7] = "Defense";
        menuString[8] = "Weapon";
        menuString[9] = "Shield";
        menuString[10] = "Coins";
    }
    public void setTitleScreen(){
        menuString = new String[20];
        int i = 0;
        menuString[i] = "NEW GAME"; i++;
        menuString[i] = "LOAD GAME"; i++;
        menuString[i] = "QUIT";
        menuLength = i;

    }
    public void setCharScreen(){
        menuString = new String[20];
        int i = 0;
        menuString[i] = "Tazz"; i++;
        menuString[i] = "Player 2"; i++;
        menuString[i] = "Player 3"; i++;
        menuString[i] = "Player 4"; i++;
        menuString[i] = "Player 5"; i++;
        menuString[i] = "BACK TO MAIN MENU";
        menuLength = i;
    }
    public void setCharCustom(){
        menuString = new String[20];
        menuValues = new String[20];
        int i = 0;
        menuString[i] = "Bodies";
        menuValues[i] = ""+gp.player.bodyIndex; i++;
        menuString[i] = "Eyes";
        menuValues[i] = ""+gp.player.eyeIndex; i++;
        menuString[i] = "Hair";
        menuValues[i] = ""+gp.player.hairIndex; i++;
        menuString[i] = "Hair Variant";
        menuValues[i] = ""+gp.player.hairColor; i++;
        menuString[i] = "Outfit";
        menuValues[i] = ""+gp.player.outfitIndex; i++;
        menuString[i] = "Outfit Variant";
        menuValues[i] = ""+gp.player.outfitColor; i++;
        menuString[i] = "Start Game"; i++;
        menuString[i] = "Back";

        menuLength = i;
    }

    public void setOptions_top(){
        menuString = new String[20];
        int i = 0;
        menuString[i] = "Full Screen"; i++;
        menuString[i] = "Music"; i++;
        menuString[i] = "Sound Effects"; i++;
        menuString[i] = "Control View"; i++;
        menuString[i] = "Quit Game"; i++;
        menuString[i] = ""; i++;
        menuString[i] = "Back";
        menuLength = i;

    }
    public void setControl_View1(){
        menuString = new String[20];
        int i = 0;
        menuString[i] = "Up"; i++;
        menuString[i] = "Down"; i++;
        menuString[i] = "Left"; i++;
        menuString[i] = "Right"; i++;
        menuString[i] = "Interact/Use"; i++;
        menuString[i] = "Options"; i++;
        menuLength = i;

    }
    public void setControl_View2(){
        menuString = new String[20];
        int i = 0;
        menuString[i] = "W"; i++;
        menuString[i] = "S"; i++;
        menuString[i] = "A"; i++;
        menuString[i] = "D"; i++;
        menuString[i] = "E"; i++;
        menuString[i] = "ESC"; i++;
        menuLength = i;

    }

    public UI(GamePanel gp){
        this.gp = gp;
        courier_40 = new Font("Courier", Font.PLAIN, 40);
        courier_80B = new Font("Courier", Font.BOLD, 80);

    }

    public void addMessage(String text){

        message.add(text);
        messageCounter.add(0);

    }


    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(courier_40);
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            //playstuff
            drawMessage();
            drawUiBar();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            //pausestuff
            drawPauseScreen();
            drawUiBar();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            drawUiBar();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawUiBar();
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        // OPTION STATE
        if(gp.gameState == gp.optionState){
            drawUiBar();
            drawOptionsScreen();
        }
        // DEATH STATE
        if(gp.gameState == gp.gameOverState){
            drawDeathScreen();
        }
        if (gp.gameState == gp.tradeState){
            drawTradeScreen();
        }

    }

    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*4;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*8);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawCharacterScreen(){
        // CREATE SUB WINDOW
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*8;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + gp.tileSize/2;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;


        // NAMES
        setCharString();
        String Text;
        for (String s : menuString) {
            if (s != null) {
                Text = s;
                g2.drawString(Text, textX, textY);
                textY += lineHeight;
            }
        }

        // Value
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;

        setCharValue();
        for (String s : menuString) {
            if (s != null) {
                Text = s;
                textX = getXforAlignToRightText(Text, tailX);
                g2.drawString(Text, textX, textY);
                textY += lineHeight;
            }
        }





    }
    public void drawInventory(Entity entity, boolean cursor){

        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if(entity == gp.player){
                frameX = gp.screenWidth-gp.tileSize*8;
                frameY = gp.tileSize;
                frameWidth = gp.tileSize*6;
                frameHeight = gp.tileSize*5;
                slotCol = playerSlotCol;
                slotRow = playerSlotRow;
        }else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;

        }
        // CREATE SUB WINDOW
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER INVENTORY
        for(int i = 0; i < entity.inventory.size(); i++){

            // EQUIP CURSOR
            if(entity.inventory.get(i) == gp.player.currentWeapon || entity.inventory.get(i) == entity.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if ((i+1) % 5 == 0){
                slotX = slotXstart;
                slotY += slotSize;
            }

        }

        // CURSOR
        if(cursor){
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorheigth= gp.tileSize;
            // DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorheigth, 10, 10);

            // DESCRIPTION FRAME
            int dFrameY = frameY + frameHeight;
            int dFrameHeight = gp.tileSize*3;

            // DRAW DESCRIPTION
            int textX = frameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(22F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()){
                drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY);
                    textY += 28;
                }
            }
        }
    }

    public void drawCharacter(){
        // CHARACTER IMAGE
        int x = gp.screenWidth/2 -gp.tileSize;
        int y = gp.tileSize*5;
        int scale = 6;
        g2.drawImage(gp.player.bodies[4], x, y, 16*scale, 32*scale, null);
        g2.drawImage(gp.player.eyes[4], x, y, 16*scale, 32*scale, null);
        g2.drawImage(gp.player.outfits[4], x, y, 16*scale, 32*scale, null);
        g2.drawImage(gp.player.hairstyles[4], x, y, 16*scale, 32*scale, null);

    }


    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255, 220);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);


    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for (int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/4;

        g2.drawString(text, x, y);
    }
    public void drawTitleScreen(){

        if (titleScreenState == 0){
            setTitleScreen();
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);


            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "School - RPG";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            // Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);
            //Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            /*
            // CHARACTER IMAGE
            x = gp.screenWidth/2 -gp.tileSize;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

             */

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            
            // Press Start
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
            g2.setColor(Color.white);

            y = gp.screenHeight - gp.tileSize*4;
            menuList(y);
        }
        else if(titleScreenState == 1){
            selectCharName();
        }
        else if(titleScreenState == 2){selectCharacterCustomization();}


    }
    public void selectCharName(){
        setCharScreen();

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(40F));

        String text = "Select your Character!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        g2.drawString(text, x, y);

        y = gp.screenHeight - gp.tileSize*6;
        menuList(y);

    }
    public void selectCharacterCustomization(){
        setCharCustom();

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        String text = "Customize Character!";
        int x = gp.screenWidth-gp.tileSize*5;
        int y = gp.tileSize*3;
        g2.drawString(text, x, y);

        y += gp.tileSize;
        optionList(x, y, menuString, true);

        // reset
        x = gp.screenWidth-gp.tileSize*2;
        y = gp.tileSize*4;
        optionList(x, y, menuValues, false);
        drawCharacter();



    }
    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        // CREATE SUB WINDOW
        final int frameX = gp.screenWidth-gp.tileSize*10;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*8;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0 -> options_top(frameX, frameY);
            case 1 -> control_view(frameX, frameY);
            case 2 -> options_endGameConfirmation(frameX, frameY);
        }



    }
    public void drawDeathScreen(){

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TEXT
        String text = "Game Over!";
        int x;
        int y;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));

        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // RETRY

        // TITLE SCREEN

    }
    public void drawTradeScreen(){
        switch(subState){
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select(){
        drawDialogueScreen();
        // DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = (int)(gp.tileSize * 4.5);
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        //Draw Texts
        x += gp.tileSize;
        menuString = new String[20];
        int i = 0;
        menuString[i] = "Buy"; i++;
        menuString[i] = "Sell"; i++;
        menuString[i] = "Leave";
        menuLength = i;
        optionList(x,y, menuString,true);


    }
    public void trade_buy(){
        drawInventory(gp.player, false);
        drawInventory(npc, true);

    }
    public void trade_sell(){
        drawInventory(npc, false);
        drawInventory(gp.player, true);

    }

    public void options_top(int frameX, int frameY){
        setOptions_top();
        int textX;
        int textY;

        // TITLE
        String text = "- Optionen -";
        textX = getXforCenteredText(text)+gp.tileSize*6;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;

        optionList(textX, textY, menuString, true);

        // FULL SCREEN CHECK BOX
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize + 26;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn) g2.fillRect(textX, textY, 24, 24);

        //MUSIC
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SOUND EFFECT VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        try {
            gp.config.saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void control_view(int frameX, int frameY){
        setControl_View1();
        int textX;
        int textY;

        // TITLE
        String text = "- Tastenbelegung -";
        textX = getXforCenteredText(text)+gp.tileSize*6;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        optionList(textX, textY, menuString, false);
        setControl_View2();

        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize;
        optionList(textX, textY, menuString,false);

    }
    public void options_endGameConfirmation(int frameX, int frameY){
        commandNum = 0;
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;
        currentDialogue = "Quit Game and\nReturn to Title Screen?";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // YES
        String text = "Yes";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
            }
        }
        // NO
        text = "No";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;


            }
        }

    }



    public void drawUiBar(){
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillRect(0, gp.screenHeight-gp.tileSize+5, gp.screenWidth, gp.tileSize);

        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, gp.screenHeight-gp.tileSize+10, gp.screenWidth, gp.tileSize);

        //FILL IN UI BAR
        int x = gp.tileSize/2-5;
        int y = gp.screenHeight-gp.tileSize/3;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(new Color(255, 255, 255));


        g2.drawString(gp.player.playerName, x, y);
        g2.drawString("X: "+ (gp.player.worldX/gp.tileSize+1), x+ gp.tileSize*2, y);
        g2.drawString("Y: "+ (gp.player.worldY/gp.tileSize+1), x+ gp.tileSize*3, y);

        g2.drawString(gp.player.life +" /"+ gp.player.maxLife, x+gp.tileSize*5, y);

        g2.drawString(gp.time.timeString, gp.screenWidth-gp.tileSize*3, y);

    }
    private void optionList(int x, int y, String[] menuString, boolean selectable){
        String text;
        for(int i = 0; i <= menuString.length; i++){
            if(menuString[i] == null) return;
            text = menuString[i];
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(selectable && commandNum == i) {
                    g2.drawString(">", x - gp.tileSize / 2, y);
            }

        }

    }
    private void menuList(int y) {
        String text;
        int x;
        for(int i = 0; i <= menuString.length; i++){
            if(menuString[i] == null) return;
            text = menuString[i];
            x = getXforCenteredText(text);
            y += gp.tileSize/1.5;
            g2.drawString(text, x, y);
            if(commandNum == i){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        return slotCol + (slotRow *5);
    }


}
