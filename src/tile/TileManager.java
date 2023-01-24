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
    public static final String fileName = ( "/maps/worldv3.txt");
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap(fileName);
    }
    public void newTile(int id, String tileImage, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[id] = new Tile();
            tile[id].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + tileImage + ".png")));
            tile[id].image = uTool.scaleImage(tile[id].image, gp.tileSize, gp.tileSize);
            tile[id].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        //newTile(id, tileImage, collision)

        //Placeholder
        newTile(0, "grass00", false);
        newTile(1, "grass00", false);
        newTile(2, "grass00", false);
        newTile(3, "grass00", false);
        newTile(4, "grass00", false);
        newTile(5, "grass00", false);
        newTile(6, "grass00", false);
        newTile(7, "grass00", false);
        newTile(8, "grass00", false);
        newTile(9, "grass00", false);
        //Placeholder

        newTile(10, "grass00", false);
        newTile(11, "grass01", false);
        newTile(12, "water00", true);
        newTile(13, "water01", true);
        newTile(14, "water02", true);
        newTile(15, "water03", true);
        newTile(16, "water04", true);
        newTile(17, "water05", true);
        newTile(18, "water06", true);
        newTile(19, "water07", true);
        newTile(20, "water08", true);
        newTile(21, "water09", true);
        newTile(22, "water10", true);
        newTile(23, "water11", true);
        newTile(24, "water12", true);
        newTile(25, "water13", true);
        newTile(26, "road00", false);
        newTile(27, "road01", false);
        newTile(28, "road02", false);
        newTile(29, "road03", false);
        newTile(30, "road04", false);
        newTile(31, "road05", false);
        newTile(32, "road06", false);
        newTile(33, "road07", false);
        newTile(34, "road08", false);
        newTile(35, "road09", false);
        newTile(36, "road10", false);
        newTile(37, "road11", false);
        newTile(38, "road12", false);
        newTile(39, "earth", false);
        newTile(40, "wall", true);
        newTile(41, "tree", true);
        newTile(42, "window", true);
        newTile(43, "blockroad00", true);
        newTile(44, "blockroad01", true);
        newTile(45, "blockroad02", true);

    }
    public void loadMap(String filePath) {
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
                    mapTileNum[col][row] = num;
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

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX ;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }
}
