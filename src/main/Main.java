package main;

import javax.swing.*;

public class Main {
    public static void  main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Schulprojekt");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);


        gamePanel.config.loadConfig();
        window.pack();

        window.setVisible(true);
        window.setLocationRelativeTo(null);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
