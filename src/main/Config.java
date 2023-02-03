package main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // FULL SCREEN
            if(gp.fullScreenOn) bw.write("FullScreenOn");
            if(!gp.fullScreenOn) bw.write("FullScreenOff");
            bw.newLine();

            // MUSIC
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();


        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            if(s.equals("FullScreenOn")) gp.fullScreenOn = true;
            if(s.equals("FullscreenOff")) gp.fullScreenOn = false;

            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
