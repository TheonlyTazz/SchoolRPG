package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    //DEBUG
    boolean debug;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }

    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // PLAY STATE
        if(gp.gameState == gp.playState){
            switch (code) {
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                case KeyEvent.VK_O -> debug = !debug;
                case KeyEvent.VK_P -> gp.gameState = gp.pauseState;
            }
        }

        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            switch (code) {
                case KeyEvent.VK_O -> debug = !debug;
                case KeyEvent.VK_P -> gp.gameState = gp.playState;
            }
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            switch (code) {
                case KeyEvent.VK_O -> debug = !debug;
                case KeyEvent.VK_E -> gp.gameState = gp.playState;
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
