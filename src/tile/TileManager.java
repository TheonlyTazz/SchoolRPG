package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[99];
        mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldv3.txt", 0);
        loadMap("/maps/hallway_01.txt", 1);
        loadMap("/maps/hallway_01_upstairs.txt", 2);
        loadMap("/maps/klassenzimmer.txt", 3);
        loadMap("/maps/class_up.txt", 8);
        loadMap("/maps/class_down.txt", 9);

    }
    public void newTile(int id, String tileImage, int tileSize, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[id] = new Tile();
            tile[id].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + tileImage + ".png")));
            tile[id].image = uTool.scaleImage(tile[id].image, tileSize*gp.scale, tileSize*gp.scale);
            tile[id].collision = collision;
            tile[id].tileSize = tileSize;

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        //newTile(id, tileImage, collision)

        //Placeholder
        newTile(0, "grass00", 16, false);
        newTile(1, "grass00", 16, false);
        newTile(2, "grass00", 16, false);
        newTile(3, "grass00", 16, false);
        newTile(4, "grass00", 16, false);
        newTile(5, "grass00", 16, false);
        newTile(6, "grass00", 16, false);
        newTile(7, "grass00", 16, false);
        newTile(8, "grass00", 16, false);
        newTile(9, "grass00", 16, false);
        //Placeholder

        newTile(10, "grass00",16, false);
        newTile(11, "grass01",16, false);
        newTile(12, "water00",16, true);
        newTile(13, "water01",16, true);
        newTile(14, "water02",16, true);
        newTile(15, "water03",16, true);
        newTile(16, "water04",16, true);
        newTile(17, "water05",16, true);
        newTile(18, "water06",16, true);
        newTile(19, "water07",16, true);
        newTile(20, "water08",16, true);
        newTile(21, "water09",16, true);
        newTile(22, "water10",16, true);
        newTile(23, "water11",16, true);
        newTile(24, "water12",16, true);
        newTile(25, "water13",16, true);
        newTile(26, "road00", 16, false);
        newTile(27, "road01", 16, false);
        newTile(28, "road02", 16, false);
        newTile(29, "road03", 16, false);
        newTile(30, "road04", 16, false);
        newTile(31, "road05", 16, false);
        newTile(32, "road06", 16, false);
        newTile(33, "road07", 16, false);
        newTile(34, "road08", 16, false);
        newTile(35, "road09", 16, false);
        newTile(36, "road10", 16, false);
        newTile(37, "road11", 16, false);
        newTile(38, "road12", 16, false);
        newTile(39, "earth",16, false);
        newTile(40, "wall",16, true);
        newTile(41, "tree",16, true);
        newTile(42, "window",16, true);
        newTile(43, "blockroad00",16, true);
        newTile(44, "blockroad01",16, true);
        newTile(45, "blockroad02",16, true);
        newTile(46, "stair_up",16, false);
        newTile(47, "stair_down",16, false);
        newTile(48, "stair_left",16, false);
        newTile(49, "stair_right",16, false);
        newTile(50, "table01",16, true);


    }
    public void loadMap(String filePath, int map) {
        try {
            //InputStream is = new FileInputStream("../../maps/map01.txt");
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();
                while (col < gp.maxWorldCol) {

                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        }catch(Exception e) {
            System.out.println("Cant load Map!");

        }

    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX ;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                if(gp.keyH.debug) {
                    String text = worldCol+"/"+worldRow;
                    g2.setStroke(new BasicStroke(3));
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                    g2.drawString(text,screenX+gp.tileSize/4, screenY+gp.tileSize/2);
                }
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }
}
