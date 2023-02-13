package main;
import com.google.gson.annotations.SerializedName;
import io.github.joafalves.ldtk.model.*;
import java.util.Arrays;


public class LoadJSON {
    private int worldGridWidth;
    private int worldGridHeight;
    private int defaultGridSize;
    private Level[] levels;

    public Level[] getLevels() {
        return levels;
    }



    @Override
    public String toString() {
        return "Data{" +
                "levels=" + Arrays.toString(levels) +
                '}';
    }

    public int getWorldGridWidth(){return worldGridWidth;}
    public int getWorldGridHeight(){return worldGridHeight;}
    public int getDefaultGridSize(){return  defaultGridSize;}
    public class Level{
        private String identifier;
        private String iid;
        private int worldX;
        private int worldY;
        private LayerInstances[] layerInstances;
        private String doc;


        public String getIdentifier() {return identifier;}

        public String getIid() {return iid; }
        public int getWorldX(){return worldX;}
        public int getWorldY(){return worldY;}
        public boolean getCollision(){
            if(doc!= null){
                if(doc.contains("collision")) {
                    return true;
                }
            }
            return false;
        }
        public LayerInstances[] getLayers(){return layerInstances;}

    }
    public class LayerInstances{
        private String  __type;
        public String __identifier;
        private int levelId;
        public String __tilesetRelPath;
        private LayerTiles[] autoLayerTiles;
        private GridTile[] gridTiles;
        private int[] intGridCsv;

        public String getName(){return __identifier;}
        public String getType(){return __type;}


        public int getLevelId(){return levelId;}
        public String getTileSetPath(){return __tilesetRelPath;}
        public GridTile[] getGridTiles(){
            return gridTiles;}
        public LayerTiles[] getAutoLayerTiles(){return autoLayerTiles;}
        public int[] getIntGrid(){return intGridCsv;}
        public class LayerTiles{

            private int[] px;
            private int[] src;
            private int t;
            private int f;

            public int getTileX(){return px[0];}
            public int getTileY(){return px[1];}
            public int getSheetX(){return src[0];}
            public int getSheetY(){return src[1];}
            public int getFlip(){return f;}
            public int[] getSrc(){return src;}

            public int getTileId(){return t;}

        }
        public class GridTile{

            private int[] px;
            private int[] src;
            private int t;
            private int f;
            public int getTileX(){return px[0];}
            public int getTileY(){return px[1];}
            public int getSheetX(){return src[0];}
            public int getSheetY(){return src[1];}
            public int getFlip(){return f;}
            public int[] getSrc(){return src;}
            public int getTileId(){return t;}

        }

    }
}

