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
    BufferedImage[][] sprites = new BufferedImage[10][50000];
    Gson gson = new Gson();
    public int layers;
    boolean[] collision = new boolean[50000];
    boolean[] layered = new boolean[50000];
    String[] file = new String[50000];



    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[50000];
        mapTileNum = new int [gp.maxMap][gp.maxLayer][gp.maxWorldCol][gp.maxWorldRow];

        loadSprites("/spritesheets/Modern_Exteriors_Complete_Tileset.png", sprites[0], 16, 16);
        //loadSprites("/spritesheets/Room_Builder_16x16.png", sprites[1], 16, 16);
        //loadSprites("/spritesheets/Interiors_16x16.png", sprites[2], 16, 16);

        loadMapSheet("/maps/Overworld.json", 0);
        //loadMapSheet("/maps/Hallway 1.json", 1);

    }

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

        String tilesheetString;
        int col = 0;
        int row = 0;
        int id;

        FileReader reader = null;

        InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        MapData mapData = gson.fromJson(br, MapData.class);

        layers = mapData.getLayers().length;
        gp.currentMapLayers = layers;
        for(int i = 0; i < layers; i++){
            MapData.Layer Layer = mapData.getLayers()[i];
            int layerID = Layer.getId();
            System.out.println(layerID);
            MapData.Property[] properties = Layer.getProperties();
            for (MapData.Property property : properties) {
                if(Objects.equals(property.name, "collision")) {
                    collision[i] = property.isValue();
                }
                if(Objects.equals(property.name, "layered")) {
                    layered[i] = property.isValue();
                }


            }
            // TILE SHEET
            MapData.Tileset[] tilesets = mapData.getTilesets();
            String str = tilesets[0].getSource();
            int dotIndex = str.lastIndexOf(".");
            String sheetPath = str.substring(0, dotIndex)+".png";


            int width = mapData.getWidth();
            int height = mapData.getHeight();
            long[] data = Layer.getData();
            col = 0;
            row = 0;
            for (long value : data) {
                id = (int) value;
                if(id != 0) id--;
                tile[id] = new Tile();

                if(id == 0) {
                    mapTileNum[map][i][col][row] = 0;
                    tile[id] = new Tile();
                    tile[id].image = sprites[map][0];
                    tile[id].collision = false;
                    tile[id].layered = false;
                    tile[id].tileSize = 16;
                }
                else if(id != 0){

                    mapTileNum[map][i][col][row] = id ;
                    tile[id] = new Tile();
                    tile[id].image = sprites[0][id];
                    tile[id].collision = collision[i];
                    tile[id].layered = layered[i];
                    tile[id].tileSize = 16;
                }
                if(tile[id].collision) {
                    System.out.println("COLLISION on tile: " + id);
                }


                col++;
                if (col == width){
                    col = 0;
                    row++;
                    if (row >= height)  row = 100;
                }

            }
        }
    }

    public void draw(Graphics2D g2, boolean top) {

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
                    if(tileNum != 0) {
                        if(!top && !tile[tileNum].layered){
                            g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                        }
                        else if (top && tile[tileNum].layered){
                            g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                        }
                    }
                    if(!top && gp.keyH.debug && layer == 0 && tileNum != 0) {

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
