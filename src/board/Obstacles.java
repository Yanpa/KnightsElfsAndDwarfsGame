package board;

import java.awt.*;

public class Obstacles extends Tile {
    private final int OBSTACLE_SIZE = 70;
    int xCoordinates, yCoordinates;
    Color obstacleColor;

    public Obstacles(int x, int y, Color color){
        super(x, y, color);
    }

    public void renderObstacle(Graphics g){

    }
}
