package tile;


import com.google.gson.Gson;
import io.github.joafalves.ldtk.LdtkConverter;
import io.github.joafalves.ldtk.model.*;
import io.github.joafalves.ldtk.model.Level;
import main.GamePanel;
import main.LoadJSON.*;
import main.UtilityTool;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LevelManager {
    GamePanel gp;
    Gson gson = new Gson();
    UtilityTool uTool = new UtilityTool();

/*
    public int worldHeight, worldWidth, defaultGridSize;
    public String[] tilesheetString = new String[10];
    public int worldX, worldY;
    public LayerInstances[] layers; // Layers[level[gp.currentMap]]
    public int layersLength;
    String layerName;
    int tileX, tileY, tileId, sheetX, sheetY;
    public int[] intGrid;
    public int[][][][] mapTileNum;  // mapTileNum[level][map][tileX][tileY]
 */
    long gridSize;
    long[] intGridCsv;
    int colX, colY, tileId;
    long[] px, src;
    public int[][][][] mapTileNum = new int[10][20][101][101]; // mapTileNum[level][map][tileX[tileY]
    long layerSetId;

    BufferedImage tileSheet;
    Coordinate projectData;
    Definitions defsData;
    Level[] levelsData;
    LayerDefinition[] layersDef;
    LayerInstance[] layersInstance;
    TileInstance[] gridTiles, autoLayerTiles;
    TilesetDefinition tileSetDef;
    long[] tileSetUid = new long[10];
    String[] tileSetPath = new String[10];

    BufferedImage[] sprites = new BufferedImage[50000];
    public Tile[] tile = new Tile[50000];

    Map<String, Object> cachedData;
    String layerType;
    public LevelManager(GamePanel gp) {
        this.gp = gp;
    }


    public BufferedImage loadTileSheet(String tileSheet){
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tileSheet)));

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
/*
    public void loadTiles(LayerInstances Layer){
        //tilesheetString[gp.currentMap] = Layer.getTileSetPath();
        int colX, colY, flip;
        int intArray = 0;
        tilesheetString[gp.currentMap] = Layer.__tilesetRelPath;
        if (tilesheetString[gp.currentMap] != null){
            tileSheet = loadTileSheet(tilesheetString[gp.currentMap]);
            String type = Layer.getType();
            switch(type){
                case "IntGrid" -> {
                    for(LayerInstances.LayerTiles tile : Layer.getAutoLayerTiles()){
                        tileX = tile.getTileX();
                        tileY = tile.getTileY();
                        colX = tileX/16;
                        colY = tileY/16;
                        tileId = tile.getTileId();
                        sheetX = tile.getSheetX();
                        sheetY = tile.getSheetY();

                        intArray = colX + (colY*100);
                        if(sprites[tileId] == null){
                            sprites[tileId] = tileSheet.getSubimage(sheetX, sheetY, 16, 16);
                            sprites[tileId] = uTool.scaleImage(sprites[tileId], 16*gp.scale, 16*gp.scale);

                        }

                        mapTileNum[gp.currentMap][layersLength][tileX/16][tileY/16] = tileId ;
                        tileArray[tileId] = new Tile();
                        tileArray[tileId].image = sprites[tileId];


                        tileArray[tileId].layered = false;
                        tileArray[tileId].tileSize = 16;
                        tileArray[tileId].tileHeight = 16;
                        tileArray[tileId].tileWidth = 16;

                        switch(intGrid[intArray]){
                            case 1 -> tileArray[tileId].collision = true;
                        }

                    }
                }
                case "Tiles" ->{
                    for(LayerInstances.GridTile tile : Layer.getGridTiles()){
                        tileX = tile.getTileX();
                        tileY = tile.getTileY();
                        colX = tileX/16;
                        colY = tileY/16;
                        tileId = tile.getTileId();
                        sheetX = tile.getSheetX();
                        sheetY = tile.getSheetY();
                        flip = tile.getFlip();
                        mapTileNum[gp.currentMap][layersLength][tileX/16][tileY/16] = tileId ;

                        intArray = colX + (colY*100);
                        if(sprites[tileId] == null){
                            sprites[tileId] = tileSheet.getSubimage(sheetX, sheetY, 16, 16);
                            sprites[tileId] = uTool.scaleImage(sprites[tileId], 16*gp.scale, 16*gp.scale);

                        }
                        tileArray[tileId] = new Tile();
                        tileArray[tileId].image = sprites[tileId];
                        if(!Layer.__identifier.contains("Walkable")) tileArray[tileId].collision = true;
                        if(layerName.contains("Top")) tileArray[tileId].layered = true;

                    }

                }
            }
            layersLength--;

        }

    }
    public void loadIntGrid(LayerInstances Layer){
        intGrid = Layer.getIntGrid();
    }
*/

    public void getCollision(){
        String name = null;
        for(int i = 0; i < layersInstance.length; i++){
            name = layersInstance[i].getIdentifier();
            if(name.equals("Collision")) {
                intGridCsv = layersInstance[i].getIntGridCsv();
            }

        }
    }


    public void loadSheet(int layer){
        gridSize = layersDef[layer].getGridSize();
        String type = layersInstance[layer].getType();
        String layerSetPath;

        if (layersInstance[layer].getTilesetDefUid() != null){
            if(layerSetId != layersInstance[layer].getTilesetDefUid()) layerSetId = layersInstance[layer].getTilesetDefUid();
            for(int i = 0; i < tileSetUid.length; i++){
                if(tileSetUid[i] == layerSetId) {
                    layerSetPath = tileSetPath[i];
                    tileSheet = loadTileSheet(layerSetPath);
                }
            }
        }


        gridTiles = null;
        switch(type){
            case "Tiles" -> gridTiles = layersInstance[layer].getGridTiles();
            case "IntGrid" -> gridTiles = layersInstance[layer].getAutoLayerTiles();
            case "Entities", "AutoLayer" -> gridTiles = null;
        }

        if(gridTiles != null){
            long layerWidth, layerHeight;
            int colX = 0;
            int colY = 0;

            layerWidth = layersInstance[layer].getCWid();
            layerHeight = layersInstance[layer].getCHei();

            for(colY = 0; colY < layerHeight; colY++){
                System.out.println();

                for(colX = 0; colX < layerWidth; colX++){

                    for(int i = 0; i < gridTiles.length; i++){
                        px = gridTiles[i].getPx();
                        src = gridTiles[i].getSrc();
                        if(colX == (px[0]/16) && colY == (px[1]/16)){
                            tileId = (int) gridTiles[i].getT();
                            if(sprites[tileId] == null){

                                sprites[tileId] = tileSheet.getSubimage((int)src[0], (int)src[1], 16, 16);
                                sprites[tileId] = uTool.scaleImage(sprites[tileId], 16*gp.scale, 16*gp.scale);
                            }

                            if(tile[tileId] == null){
                                tile[tileId] = new Tile();
                                tile[tileId].image = sprites[tileId];
                                tile[tileId].tileSize = 16;
                                if(intGridCsv[colX+(colX*colY)] == 1)tile[tileId].collision = true;

                            }

                            System.out.println("X: "+px[0]+" | Y: "+px[1]);
                            mapTileNum[0][layer][colX][colY] = (int) gridTiles[i].getT();
                        }
                    }
                }
            }






        }
    }

    public void loadTileSets(){

        for(int i = 0; i < defsData.getTilesets().length; i++){
            tileSetDef = defsData.getTilesets()[i];
            tileSetUid[i] = tileSetDef.getUid();
            tileSetPath[i] = tileSetDef.getRelPath();
            System.out.println(tileSetUid[i] + " - " + tileSetPath[i]);

        }
    }

    public void loadLayers(){

        layersInstance = levelsData[0].getLayerInstances();
        getCollision();
        for(int layer = 0; layer < layersInstance.length; layer++){
            layersDef = defsData.getLayers();
            layerType = layersDef[layer].getType();

            loadSheet(layer);
            System.out.println("Loaded " + layersDef[layer].getIdentifier());

        }

    }

    public void loadLDtx(String filePath){
        try {
            projectData = LdtkConverter.fromJsonString(loadResourceFile(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        levelsData = projectData.getLevels();
        defsData = projectData.getDefs();
        loadTileSets();



    }

    public void draw(Graphics2D g2, boolean top) {

        int layer;
        for(layer = layersInstance.length; layer > 0; layer--){
            int worldCol = 0;
            int worldRow = 0;
            while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

                int tileNum = mapTileNum[gp.currentMap][layer][worldCol][worldRow];
                if(mapTileNum[gp.currentMap][layer][worldCol][worldRow] != 0){
                    int worldX = worldCol * tile[tileNum].tileSize*gp.scale;
                    int worldY = worldRow * tile[tileNum].tileSize*gp.scale;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX ;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if(worldX + tile[tileNum].tileWidth*gp.scale > gp.player.worldX - gp.player.screenX &&
                            worldX - tile[tileNum].tileWidth*gp.scale < gp.player.worldX + gp.player.screenX &&
                            worldY + tile[tileNum].tileHeight*gp.scale > gp.player.worldY - gp.player.screenY &&
                            worldY - tile[tileNum].tileHeight*gp.scale < gp.player.worldY + gp.player.screenY) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                        /*
                        if(tileNum != 0) {
                            if(!top && !tile[tileNum].layered){
                                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                            }
                            else if (top && tile[tileNum].layered){
                                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                            }
                        }

                         */
                        if(!top && gp.keyH.debug && tileNum != 0) {

                            String text = worldCol+"/"+worldRow;
                            g2.setStroke(new BasicStroke(3));
                            g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                            g2.drawString(text,screenX+gp.tileSize/4, screenY+gp.tileSize/2);
                        }
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


    private String loadResourceFile(String filename) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filename)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
