package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, dialoguePressed, attackPressed, charPressed;

    //DEBUG
    boolean debug;

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
                            case 0 -> gp.ui.titleScreenState = 1;
                            case 1 -> System.out.println(0);
                            case 2 -> System.out.println(1);
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
                            gp.gameState = gp.playState;
                        }

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
            case KeyEvent.VK_ESCAPE -> gp.gameState = gp.titleState;
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
            switch(code){
                case KeyEvent.VK_W -> {
                    if(gp.ui.slotRow == 0) gp.ui.slotRow = 3;
                    else gp.ui.slotRow--;
                }
                case KeyEvent.VK_S -> {
                    if(gp.ui.slotRow == 3) gp.ui.slotRow = 0;
                    else gp.ui.slotRow++;
                }
                case KeyEvent.VK_A -> {
                    if(gp.ui.slotCol == 0) gp.ui.slotCol = 4;
                    else gp.ui.slotCol--;
                }
                case KeyEvent.VK_D -> {
                    if(gp.ui.slotCol == 4) gp.ui.slotCol = 0;
                    else gp.ui.slotCol++;
                }
                case KeyEvent.VK_E -> gp.player.selectItem();
            }

            if(code == KeyEvent.VK_C && !charPressed) {
                    gp.gameState = gp.playState;
            }
            else if (code == KeyEvent.VK_C) charPressed = false;
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
