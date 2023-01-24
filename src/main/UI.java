package main;
import java.awt.*;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font courier_40, courier_80B;
    //public boolean messageOn = true;
    //public String message = "";

    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first Titlescreen, 1: second Titlescreen
    public String [] menuString = new String[20];

    public int menuLength = 0;

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
        menuString[1] = "Spieler 2";
        menuString[2] = "Spieler 3";
        menuString[3] = "Spieler 4";
        menuString[4] = "Spieler 5";
        menuString[5] = "BACK TO MAIN MENU";
    }


    public UI(GamePanel gp){
        this.gp = gp;
        courier_40 = new Font("Courier", Font.PLAIN, 40);
        courier_80B = new Font("Courier", Font.BOLD, 80);

    }
    /*
    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

     */
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
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255, 220);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);


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

}
