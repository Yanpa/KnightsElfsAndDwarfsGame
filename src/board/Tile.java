package board;


import java.awt.*;

public class Tile {
    private final int TILE_SIZE = 70;
    int xCoordinates, yCoordinates;
    Color colorOfTheTile;

    public Tile() {

    }

    public Tile(int x, int y, Color color){
        this.xCoordinates = y * TILE_SIZE;
        this.yCoordinates = x * TILE_SIZE;

        this.colorOfTheTile = color;
    }

    /**
     * Рисува всяка плочка по зададени координати и цвят
     * @param g
     */
    public void renderTile(Graphics g){
        g.setColor(colorOfTheTile);
        g.fillRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);

        g.setColor(Color.black);
        g.drawRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Прикрива полето в началото на играта
     * @param g
     */
    public void showWhereToPutFiguresASide(Graphics g){
        g.setColor(Color.black);
        g.fillRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.red);
        g.drawString("X", xCoordinates + 100, yCoordinates + 110);
        g.setColor(Color.WHITE);
        g.drawRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Прикрива полето в началото на играта
     * @param g
     */
    public void showWhereToPutFiguresBSide(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.black);
        g.drawString("X", xCoordinates + 100, yCoordinates + 110);
        g.setColor(Color.WHITE);
        g.drawRect(xCoordinates + 70, yCoordinates + 70, TILE_SIZE, TILE_SIZE);
    }
}
