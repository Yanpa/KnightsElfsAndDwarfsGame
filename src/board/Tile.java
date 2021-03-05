package board;

import java.awt.*;

public class Tile {
    private final int TILE_SIZE = 70;
    int xCoordinates, yCoordinates;
    Color colorOfTheTile;

    public Tile(int x, int y, Color color){
        this.xCoordinates = y * TILE_SIZE;
        this.yCoordinates = x * TILE_SIZE;
        this.colorOfTheTile = color;
    }

    public void renderTile(Graphics g){
        g.setColor(colorOfTheTile);
        g.fillRect(xCoordinates + 75, yCoordinates + 50, TILE_SIZE, TILE_SIZE);

        g.setColor(Color.black);
        g.drawRect(xCoordinates + 75, yCoordinates + 50, TILE_SIZE, TILE_SIZE);
    }
}
