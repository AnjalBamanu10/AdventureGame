package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16px tile or character size
    final int scale= 3;

   public  int tileSize = originalTileSize * scale; //48px tile
    public  int maxScreenCol = 15;
    public  int maxScreenRow = 12;
    public  int screenWidth = tileSize * maxScreenCol; //768 pixels
    public  int screenHeight = tileSize * maxScreenRow; //576 pixels

//    WORLD SETTING
    public final int  maxWorldCol = 50;
    public final int  maxWorldRow = 50;
    public final int  worldwWidth = tileSize *  maxWorldCol;
    public final int  worldHeight = tileSize * maxScreenRow;



    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this,keyH);




    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void zoomInOut(int i){
        int oldWorldWidth = tileSize * maxWorldCol;
        tileSize += i;

        int newWorldWidth = tileSize * maxWorldCol;
        player.speed = (double) newWorldWidth/600;

        double multiplier = (double)newWorldWidth/oldWorldWidth;

        System.out.println("tileSize:" + tileSize);
        System.out.println("worldWidth:" + newWorldWidth);
        System.out.println("player WorldX:" + player.worldX);

        double newPlayerWorldX = player.worldX * multiplier;
        double newPlayerWorldY = player.worldY * multiplier;

        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }
    }
    public void update(){

        player.update();
    }
    public void   paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
