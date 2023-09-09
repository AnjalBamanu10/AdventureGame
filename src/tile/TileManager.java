package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < gp.maxScreenCol && col < numbers.length; col++) {
                    if (!numbers[col].isEmpty()) { // Check for empty strings
                        int num = 0;
                        try {
                            num = Integer.parseInt(numbers[col]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        mapTileNum[col][row] = num;
                    }
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int x = 0;
        int y = 0;

        for (int row = 0; row < gp.maxScreenRow; row++) {
            for (int col = 0; col < gp.maxScreenCol; col++) {
                int tileNum = mapTileNum[col][row];
                g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
                x += gp.tileSize;
            }
            x = 0; // Reset x to the left side for the next row
            y += gp.tileSize;
        }
    }
}

