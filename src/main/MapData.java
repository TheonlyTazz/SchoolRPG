package main;

import com.google.gson.annotations.SerializedName;

public class MapData {
    private int compressionlevel;
    private int height;
    private boolean infinite;
    private Layer[] layers;
    private int nextlayerid;
    private int nextobjectid;
    private String orientation;
    private String renderorder;
    private String tiledversion;
    private int tileheight;
    private Tileset[] tilesets;
    private int tilewidth;
    private String type;
    private String version;
    private int width;

    public class Layer {
        private long[] data;
        private int height;
        private int id;
        private String name;
        private int opacity;
        private String type;
        private boolean visible;
        private int width;
        private int x;
        private int y;
        private Property[] properties;

        public String getName(){return name;}
        public long[] getData(){return data;}
        public int getId(){return id;}
        public Property[] getProperties() {
            return properties;
        }
    }
    public class Property {
        public String name;
        private String type;
        private boolean value;
        private String valueString;

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean isValue() {
            return value;
        }
        public String isValueString() {
            return String.valueOf(value);
        }
    }

    public class Tileset {
        @SerializedName("firstgid")
        private int firstGid;
        private String source;

        public String getSource(){
            return source;
        }
    }

    public int getCompressionlevel() {
        return compressionlevel;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public int getNextlayerid() {
        return nextlayerid;
    }

    public int getNextobjectid() {
        return nextobjectid;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getRenderorder() {
        return renderorder;
    }

    public String getTiledversion() {
        return tiledversion;
    }

    public int getTileheight() {
        return tileheight;
    }

    public Tileset[] getTilesets() {
        return tilesets;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public int getWidth() {
        return width;
    }
}
