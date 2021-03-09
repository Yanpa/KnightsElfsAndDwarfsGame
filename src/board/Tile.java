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

    public void showWhereToPutFigures(Graphics g, int countOfMoves){
        if(countOfMoves % 2 != 0){
            g.setColor(Color.black);
            g.fillRect(xCoordinates + 75, yCoordinates + 50, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.red);
            g.drawString("X", xCoordinates + 100, yCoordinates + 75);
        }

        else{
            g.setColor(Color.red);
            g.fillRect(xCoordinates + 75, yCoordinates + 50, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.black);
            g.drawString("X", xCoordinates + 100, yCoordinates + 75);
        }
    }
}
