package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 Pixels
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 64;


    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[] obj =  new Entity[20];
    public Entity[] npc = new Entity[20];
    public Entity[] mon = new Entity[20];
    public InteractiveTile[] iTile = new InteractiveTile[20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();


    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;





    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setiTile();
        aSetter.setNPC();
        aSetter.setMON();
        gameState = titleState;

    }
    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1_000_000_000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;


        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;

            }

            if(timer >= 1000000000) {
                //System.out.println("FPS:" + drawCount);
                timer = 0;
            }
        }
    }
    public void update(){
        if(gameState == playState){
            player.update();

            for (Entity entity : npc) {
                if (entity != null) entity.update();
            }
            for(int i = 0; i < mon.length; i++){
                if (mon[i] != null){
                    if(mon[i].alive && !mon[i].dying) {
                        mon[i].update();
                    }
                    if(!mon[i].alive) mon[i] = null;
                }
            }
            // iTILE UPDATE
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }
            // PARTICLE LIST UPDATE
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
        }
        if(gameState == pauseState){
            //TODO
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0;
        if(keyH.debug) drawStart = System.nanoTime();

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);

        }
        else{

            // TILE
            tileM.draw(g2);

            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }

            // ADD PLAYER
            entityList.add(player);

            // ADD NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // ADD OBJECTS
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // ADD MONSTER
            for (Entity entity: mon){
                if (entity != null){
                    entityList.add(entity);
                }
            }
            for (Entity value : particleList) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            entityList.sort((e1, e2) -> {
                return Integer.compare(e1.worldY, e2.worldY);
            });

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }


            // EMPTY Entity List
            entityList.clear();


            // UI
            ui.draw(g2);

            // DEBUG
            if(keyH.debug){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: "+passed);
            }
            g2.dispose();
        }

    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
