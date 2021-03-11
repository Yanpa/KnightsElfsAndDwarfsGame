package board;

import javax.swing.*;
import java.awt.*;

public class PlayerChoice {
    private int xCoordinates, yCoordinates;
    private char player;

    JButton jButton;

    public PlayerChoice(int x, int y, char player){
        this.xCoordinates = x;
        this.yCoordinates = y;

        this.player = player;
    }

    public void renderPlayerChoice(Graphics g){
        g.setColor(Color.black);
        g.drawRect(xCoordinates, yCoordinates, 250, 300);

        g.setFont(new Font("Ariel", Font.BOLD, 20));
        g.drawString("Player " + player + " turn", xCoordinates + 60, yCoordinates + 30);
    }

    public void removeRenderedPlayerChoice(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(xCoordinates, yCoordinates, 250, 300);

        g.setFont(new Font("Ariel", Font.BOLD, 20));
        g.drawString("Player " + player + " turn", xCoordinates + 60, yCoordinates + 30);
    }

    public void addButtonsForChoices(){

    }
}
