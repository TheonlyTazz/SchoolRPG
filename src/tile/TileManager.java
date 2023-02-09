package tile;

import com.google.gson.Gson;
import main.GamePanel;
import main.MapData;
import main.UtilityTool;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.IOException;




public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][][] mapTileNum;
    BufferedImage[] sprites = new BufferedImage[50000];
    public int spriteWidth = 16;
    public int spriteHeight = 32;
    public String json = "tiles/map_overworld.json";
    Gson gson = new Gson();
    public int layers;



    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[50000];
        mapTileNum = new int [gp.maxMap][gp.maxLayer][gp.maxWorldCol][gp.maxWorldRow];
        //loadSprites("/tiles/Room_Builder_subfiles/Room_Builder_3d_walls_16x16.png", sprites, spriteWidth, spriteHeight);

        getTileImage();
        loadSprites("/spritesheets/Modern_Exteriors_Complete_Tileset.png", sprites, 16, 16);
        for(int i = 0; i < sprites.length; i++){
            newTilefromSprite(i, sprites[i], 16, false);
        }
        loadMapSheet("/maps/Overworld.json", 0);


    }
    //0-7
    //24-31
    //48-55
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
    public void newTilefromSprite(int id, BufferedImage sprite, int tileSize, boolean collision) {
        tile[id] = new Tile();
        tile[id].image = sprite;
        tile[id].collision = collision;
        tile[id].tileSize = tileSize;

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
        //newTilefromSprite(40, sprites[28], 32, true);
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
        newTilefromSprite(51, sprites[5], 32, true);
        newTilefromSprite(52, sprites[2], 32, true);
        newTilefromSprite(53, sprites[26], 32, true);
        newTilefromSprite(99, sprites[999], 16, true);
    }
    //0-7
    //24-31
    //48-55
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
                sprites[spritecounter] = image.getSubimage(x, y,spriteWidth,spriteHeight);
                sprites[spritecounter] = uTool.scaleImage(sprites[spritecounter], spriteWidth*gp.scale, spriteHeight*gp.scale);
                spritecounter++;

            }
        }
    }


    public void loadMapSheet(String filePath, int map){
        boolean collision = false;
        String tilesheetString;
        int col = 0;
        int row = 0;

        FileReader reader = null;

        InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        MapData mapData = gson.fromJson(br, MapData.class);

        System.out.println("Height: "+ mapData.getHeight());
        System.out.println("Width: "+ mapData.getWidth());
        System.out.println("NextlayerId: "+ mapData.getNextlayerid());
        System.out.println("RenderOrder: "+ mapData.getRenderorder());
        layers = mapData.getLayers().length;
        for(int i = 0; i < layers; i++){

            MapData.Layer Layer = mapData.getLayers()[i];
            int layerID = Layer.getId();
            System.out.println(layerID);
            MapData.Property[] properties = Layer.getProperties();
            for (MapData.Property property : properties) {
                collision = property.isValue();
            }
            // TILE SHEET
            MapData.Tileset[] tilesets = mapData.getTilesets();
            String str = tilesets[0].getSource();
            int dotIndex = str.lastIndexOf(".");
            String sheetPath = str.substring(0, dotIndex)+".png";
            System.out.println("Layer Name:" + Layer.getName());
            System.out.println("Sheet Path: " +sheetPath);
            System.out.println("Collision: "+ collision);
            System.out.println();


            int width = mapData.getWidth();
            int height = mapData.getHeight();
            long[] data = Layer.getData();
            col = 0;
            row = 0;
            for (long value : data) {
                if(value == 0) mapTileNum[map][i][col][row] = 0;
                else mapTileNum[map][i][col][row] = (int) value -1;
                col++;
                if (col == width){
                    col = 0;
                    row++;
                    if (row >= height)  row = 100;
                }

            }
        }
    }

    public void draw(Graphics2D g2) {


        int layer = 0;
        for(layer = 0; layer < layers; layer++){
            int worldCol = 0;
            int worldRow = 0;
            while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

                int tileNum = mapTileNum[gp.currentMap][layer][worldCol][worldRow];
                int worldX = worldCol * tile[tileNum].tileWidth*gp.scale;
                int worldY = worldRow * tile[tileNum].tileHeight*gp.scale;

                int screenX = worldX - gp.player.worldX + gp.player.screenX ;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if(worldX + tile[tileNum].tileWidth*gp.scale > gp.player.worldX - gp.player.screenX &&
                        worldX - tile[tileNum].tileWidth*gp.scale < gp.player.worldX + gp.player.screenX &&
                        worldY + tile[tileNum].tileHeight*gp.scale > gp.player.worldY - gp.player.screenY &&
                        worldY - tile[tileNum].tileHeight*gp.scale < gp.player.worldY + gp.player.screenY) {
                    if(tileNum != 0) g2.drawImage(tile[tileNum].image, screenX, screenY, null);
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
}
