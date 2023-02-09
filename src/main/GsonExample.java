package main;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GsonExample {
    List<Integer> layer = new ArrayList();


    public static void main(String path,String[] args) {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(path);
            Data data = gson.fromJson(reader, Data.class);
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Data {
    int tileheight;
    int tilewidth;
    ArrayList layer;

    public ArrayList getLayers() {
        return layer;
    }

    public int getTileHeight() {
        return tileheight;
    }

    public int getTileWidth() {
        return tilewidth;
    }
}
