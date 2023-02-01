package main;
import java.awt.*;
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
    public int slotCol = 0;
    public int slotRow = 0;



    public String [] menuString = new String[20];
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
        menuLength = 4;
        menuString[0] = "NEW GAME";
        menuString[1] = "LOAD GAME";
        menuString[2] = "OPTIONS";
        menuString[3] = "QUIT";
    }
    public void setCharScreen(){
        menuString = new String[20];
        menuLength = 6;
        menuString[0] = "Leroy";
        menuString[1] = "Florian";
        menuString[2] = "Daniel";
        menuString[3] = "Kevin";
        menuString[4] = "Malte";
        menuString[5] = "BACK TO MAIN MENU";
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
            drawInventory();

        }
    }

    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
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
    public void drawInventory(){
        // CREATE SUB WINDOW
        final int frameX = gp.screenWidth-gp.tileSize*8;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER INVENTORY
        for(int i = 0; i < gp.player.inventory.size(); i++){

            // EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if ((i+1) % 5 == 0){
                slotX = slotXstart;
                slotY += slotSize;
            }

        }

        // CURSOR
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

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.size()){
            drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 28;
            }
        }
    }
    public int getItemIndexOnSlot(){
        return slotCol + (slotRow*5);
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

            // CHARACTER IMAGE
            x = gp.screenWidth/2 -gp.tileSize;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            
            // Press Start
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
            g2.setColor(Color.white);

            y = gp.screenHeight - gp.tileSize*4;
            menuList(y);
        }
        else if(titleScreenState == 1){
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
        g2.drawString("X: "+ gp.player.worldX/gp.tileSize, x+ gp.tileSize*2, y);
        g2.drawString("Y: "+ gp.player.worldY/gp.tileSize, x+ gp.tileSize*3, y);

        g2.drawString(gp.player.life +" /"+ gp.player.maxLife, x+gp.tileSize*5, y);

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

}
