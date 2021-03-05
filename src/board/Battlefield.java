package board;

import javax.swing.*;
import java.awt.*;

public class Battlefield extends JPanel {

    private final int BATTLEFIELD_HEIGHT = 9;
    private final int BATTLEFIELD_WIDTH = 7;
    Tile[][] battlefieldBoard = new Tile[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];

    public Battlefield(){
        //this.setPreferredSize(new Dimension(BATTLEFIELD_WIDTH * 40, BATTLEFIELD_HEIGHT * 40));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1100, 700));

        createTheBattlefield();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){
        for(int i = 0; i < BATTLEFIELD_WIDTH; i++) {
            for (int j = 0; j < BATTLEFIELD_HEIGHT; j++) {
                battlefieldBoard[i][j].renderTile(g);
            }
        }
    }

    private void createTheBattlefield(){
        for(int i = 0; i < BATTLEFIELD_WIDTH; i++){
            for(int j = 0; j < BATTLEFIELD_HEIGHT; j++){
                int changeOfColors = 1;
                if(i >= 2 && i <= 4){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(213, 211, 211));
                    changeOfColors = 2;
                }
                else if(changeOfColors % 2 != 0){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(130, 128, 128));
                    changeOfColors++;
                }
                else if(changeOfColors % 2 == 0){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(54, 51, 51));
                    changeOfColors++;
                }
            }
        }
    }
}
