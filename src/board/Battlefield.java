package board;

import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Battlefield extends JPanel implements MouseListener {

    private final int BATTLEFIELD_HEIGHT = 9;
    private final int BATTLEFIELD_WIDTH = 7;
    Tile[][] battlefieldBoard = new Tile[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];
    Piece[][] piecesBoard = new Piece[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];

    PlayerChoice playerChoiceA = new PlayerChoice(800, 50, 'A');
    PlayerChoice playerChoiceB = new PlayerChoice(800, 50, 'B');

    int currentXClicked, currentYClicked;
    boolean gameIsRunning = false;


    ArrayList<Piece> playerAChampions = new ArrayList<>(6);
    ArrayList<Piece> playerBChampions = new ArrayList<>(6);

    Knight firstKnightASide = new Knight('K', 1);
    Knight secondKnightASide = new Knight('K', 1);
    Elf firstElfASide = new Elf('E', 1);
    Elf secondElfASide = new Elf('E', 1);
    Dwarf firstDwarfASide = new Dwarf('D', 1);
    Dwarf secondDwarfASide = new Dwarf('D', 1);

    Knight firstKnightBSide = new Knight('k', 2);
    Knight secondKnightBSide = new Knight('k', 2);
    Elf firstElfBSide = new Elf('e', 2);
    Elf secondElfBSide = new Elf('e', 2);
    Dwarf firstDwarfBSide = new Dwarf('d', 2);
    Dwarf secondDwarfBSide = new Dwarf('d', 2);

    public Battlefield(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1100, 700));
        this.setLayout(null);
        this.addMouseListener(this);

        createTheBattlefield();
        createPieces();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentXClicked = (e.getX() - 70) / 70;
        currentYClicked = (e.getY() - 70) / 70;

        System.out.println("X: " + currentXClicked);
        System.out.println("Y: " + currentYClicked);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        for (int i = 0; i < BATTLEFIELD_WIDTH; i++) {
            for (int j = 0; j < BATTLEFIELD_HEIGHT; j++) {
                battlefieldBoard[i][j].renderTile(g);
            }
        }

        firstKnightASide.setXCoordinates(4);
        firstKnightASide.setYCoordinates(5);
        firstKnightASide.renderPiece(g);

        firstDwarfBSide.setXCoordinates(2);
        firstDwarfBSide.setXCoordinates(4);
        firstDwarfBSide.renderPiece(g);

        playerChoiceA.renderPlayerChoice(g);
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

    private void createPieces(){
        playerAChampions.add(firstKnightASide);
        playerAChampions.add(secondKnightASide);
        playerAChampions.add(firstDwarfASide);
        playerAChampions.add(secondDwarfASide);
        playerAChampions.add(firstElfASide);
        playerAChampions.add(secondElfASide);

        playerBChampions.add(firstKnightBSide);
        playerBChampions.add(secondKnightBSide);
        playerBChampions.add(firstDwarfBSide);
        playerBChampions.add(secondDwarfBSide);
        playerBChampions.add(firstElfBSide);
        playerBChampions.add(secondElfBSide);

    }


}
