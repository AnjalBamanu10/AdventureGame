package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        CRETES SCREEN
        JFrame  window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Bucky's Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);




    }
}
