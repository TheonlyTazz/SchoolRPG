package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, dialoguePressed, attackPressed, charPressed, enterPressed;

    //DEBUG
    public boolean debug;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            playState(code);
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            characterState(code);
        }
        // OPTION STATE
        if(gp.gameState == gp.optionState){
            optionState(code);
        }

        // DEATH STATE
        if(gp.gameState == gp.gameOverState) deathState(code);

        if(gp.gameState == gp.tradeState) tradeState(code);
    }

    public void titleState(int code){
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gp.ui.commandNum == 0) gp.ui.commandNum = gp.ui.menuLength -1;
                else gp.ui.commandNum--;
            }
            case KeyEvent.VK_S -> {
                if (gp.ui.commandNum == gp.ui.menuLength -1) gp.ui.commandNum = 0;
                else gp.ui.commandNum++;
            }
            case KeyEvent.VK_SPACE -> {
                switch(gp.ui.titleScreenState) {
                    //MAIN MENU
                    case 0 -> {
                        switch (gp.ui.commandNum) {
                            case 0 -> {
                                gp.ui.titleScreenState = 1;
                                gp.player.setDefaultValues();
                            }
                            case 1 -> System.out.println(0);
                            case 2 -> gp.ui.titleScreenState = 2;
                            case 3 -> System.exit(0);
                            default -> System.out.println(3);
                        }
                    }
                    // CHAR SELECTION
                    case 1 ->{
                        if (gp.ui.commandNum == gp.ui.menuLength-1) {
                            gp.ui.titleScreenState = 0;
                            gp.ui.commandNum = 0;
                        }
                        else {
                            System.out.println(gp.ui.commandNum+" - "+gp.ui.menuString[gp.ui.commandNum]);
                            gp.player.playerName = gp.ui.menuString[gp.ui.commandNum];
                            gp.setupGame();
                            gp.gameState = gp.playState;
                        }

                    }
                    case 2 ->{
                        gp.ui.drawOptionsScreen();
                    }
                }

            }
        }
    }
    public void playState(int code){
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_O -> debug = !debug;
            case KeyEvent.VK_P -> gp.gameState = gp.pauseState;
            case KeyEvent.VK_E -> dialoguePressed = true;
            case KeyEvent.VK_ESCAPE -> {
                gp.gameState = gp.optionState;
                charPressed = true;
            }
            case KeyEvent.VK_CONTROL -> attackPressed = true;
            case KeyEvent.VK_C -> {
                gp.gameState = gp.characterState;
                charPressed = true;
            }
        }
    }
    public void pauseState(int code){
        switch (code) {
            case KeyEvent.VK_O -> debug = !debug;
            case KeyEvent.VK_P -> gp.gameState = gp.playState;
    }}
    public void dialogueState(int code){
        switch (code) {
            case KeyEvent.VK_O -> debug = !debug;
            case KeyEvent.VK_E -> gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if(gp.gameState == gp.characterState){
            enterPressed = false;
            if (code == KeyEvent.VK_E) gp.player.selectItem();
            if(code == KeyEvent.VK_C && !charPressed) {
                    gp.gameState = gp.playState;
            }
            else if (code == KeyEvent.VK_C) charPressed = false;
            playerInventory(code);
        }
    }
    public void optionState(int code){
        switch(gp.ui.subState){
            case 0 -> {
                switch(code){
                    case KeyEvent.VK_ENTER -> enterPressed = true;
                    case KeyEvent.VK_W -> {
                        if (gp.ui.commandNum == 0) gp.ui.commandNum = gp.ui.menuLength;
                        else gp.ui.commandNum--;
                        if (Objects.equals(gp.ui.menuString[gp.ui.commandNum], "")) gp.ui.commandNum--;
                    }
                    case KeyEvent.VK_S -> {
                        if (gp.ui.commandNum == gp.ui.menuLength) gp.ui.commandNum = 0;
                        else gp.ui.commandNum++;
                        if (Objects.equals(gp.ui.menuString[gp.ui.commandNum], "")) gp.ui.commandNum++;

                    }
                    case KeyEvent.VK_SPACE -> {
                        switch (gp.ui.menuString[gp.ui.commandNum]){
                            case "Full Screen" -> gp.fullScreenOn = !gp.fullScreenOn;
                            case "Control View" -> gp.ui.subState = 1;
                            case "Quit Game" -> {
                                gp.ui.subState = 2;
                                /*
                                gp.gameState = gp.titleState;
                                gp.ui.commandNum = 0;
                                gp.ui.titleScreenState = 0;

                                 */
                            }
                            case "Back" -> {
                                gp.ui.commandNum = 0;
                                gp.gameState = gp.playState;
                            }
                        }
                    }
                    case KeyEvent.VK_D -> {
                        switch (gp.ui.menuString[gp.ui.commandNum]){
                            case "Sound Effects" -> {
                                if(gp.se.volumeScale < 5) {
                                    gp.se.volumeScale++;
                                    System.out.println(gp.se.volumeScale);

                                }
                            }
                            case "Music" ->{
                                if(gp.music.volumeScale < 5) {
                                    gp.music.volumeScale++;
                                    System.out.println(gp.music.volumeScale);
                                }
                            }
                        }
                    }
                    case KeyEvent.VK_A -> {
                        switch (gp.ui.menuString[gp.ui.commandNum]){
                            case "Sound Effects" -> {
                                if(gp.se.volumeScale > 0) {
                                    gp.se.volumeScale--;

                                }
                            }
                            case "Music" ->{
                                if(gp.music.volumeScale > 0) {
                                    gp.music.volumeScale--;
                                    System.out.println(gp.music.volumeScale);
                                }
                            }
                        }
                    }
                }

            }
            case 1 -> {
                switch(code){
                    case KeyEvent.VK_E, KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE, KeyEvent.VK_ENTER -> gp.ui.subState = 0;
                }

            }
            case 2 -> {
                System.out.println(gp.ui.commandNum +" "+ code);
                switch(code){
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> enterPressed = true;
                    case KeyEvent.VK_ESCAPE -> gp.ui.subState = 0;
                }

            }
        }


        if(code == KeyEvent.VK_ESCAPE && !charPressed) {
            gp.gameState = gp.playState;
        }
        else if (code == KeyEvent.VK_ESCAPE) charPressed = false;
    }
    public void deathState(int code){
        switch(code){
            case KeyEvent.VK_E,KeyEvent.VK_SPACE,KeyEvent.VK_ENTER,KeyEvent.VK_ESCAPE -> {
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.player.setDefaultValues();
            }
        }
    }
    public void tradeState(int code) {
        enterPressed = false;
        switch (code) {
            case KeyEvent.VK_ENTER, KeyEvent.VK_E -> enterPressed = true;
            case KeyEvent.VK_W -> {
                if (gp.ui.commandNum == 0) gp.ui.commandNum = gp.ui.menuLength;
                else gp.ui.commandNum--;
                if (Objects.equals(gp.ui.menuString[gp.ui.commandNum], "")) gp.ui.commandNum--;
            }
            case KeyEvent.VK_S -> {
                if (gp.ui.commandNum == gp.ui.menuLength) gp.ui.commandNum = 0;
                else gp.ui.commandNum++;
                if (Objects.equals(gp.ui.menuString[gp.ui.commandNum], "")) gp.ui.commandNum++;

            }
            case KeyEvent.VK_ESCAPE -> {
                switch(gp.ui.subState){
                    case 1, 2 -> {
                        gp.ui.subState = 0;
                        gp.ui.commandNum = 0;
                    }
                }
            }
        }
        if (enterPressed) {
            switch (gp.ui.commandNum) {
                case 0 -> gp.ui.subState = 1;
                case 1 -> gp.ui.subState = 2;
                case 2 -> {
                    gp.gameState = gp.playState;
                    gp.ui.commandNum = 0;
                }
            }
        }
        switch(gp.ui.subState){
            case 1 -> npcInventory(code);
            case 2 -> playerInventory(code);
        }


    }

    public void playerInventory (int code){
            switch (code) {
                case KeyEvent.VK_W -> {
                    if (gp.ui.playerSlotRow == 0) gp.ui.playerSlotRow = 3;
                    else gp.ui.playerSlotRow--;
                }
                case KeyEvent.VK_S -> {
                    if (gp.ui.playerSlotRow == 3) gp.ui.playerSlotRow = 0;
                    else gp.ui.playerSlotRow++;
                }
                case KeyEvent.VK_A -> {
                    if (gp.ui.playerSlotCol == 0) gp.ui.playerSlotCol = 4;
                    else gp.ui.playerSlotCol--;
                }
                case KeyEvent.VK_D -> {
                    if (gp.ui.playerSlotCol == 4) gp.ui.playerSlotCol = 0;
                    else gp.ui.playerSlotCol++;
                }
            }
        }
    public void npcInventory (int code){
            switch (code) {
                case KeyEvent.VK_W -> {
                    if (gp.ui.npcSlotRow == 0) gp.ui.npcSlotRow = 3;
                    else gp.ui.npcSlotRow--;
                }
                case KeyEvent.VK_S -> {
                    if (gp.ui.npcSlotRow == 3) gp.ui.npcSlotRow = 0;
                    else gp.ui.npcSlotRow++;
                }
                case KeyEvent.VK_A -> {
                    if (gp.ui.npcSlotCol == 0) gp.ui.npcSlotCol = 4;
                    else gp.ui.npcSlotCol--;
                }
                case KeyEvent.VK_D -> {
                    if (gp.ui.npcSlotCol == 4) gp.ui.npcSlotCol = 0;
                    else gp.ui.npcSlotCol++;
                }
            }
        }


    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}
