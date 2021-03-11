package board;

import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Battlefield extends JPanel implements MouseListener, ActionListener {

    Random random = new Random();

    private final int BATTLEFIELD_HEIGHT_TILE = 9;
    private final int BATTLEFIELD_WIDTH_TILE = 7;

    private final int BATTLEFIELD_WIDTH = 9;
    private final int BATTLEFIELD_HEIGHT = 7;

    Tile[][] battlefieldBoard = new Tile[BATTLEFIELD_WIDTH_TILE][BATTLEFIELD_HEIGHT_TILE];
    Piece[][] piecesBoard = new Piece[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];

    PlayerChoice playerChoiceA = new PlayerChoice(800, 50, 'A');
    PlayerChoice playerChoiceB = new PlayerChoice(800, 50, 'B');

    JButton knight, elf, dwarf, attack, heal, move;

    int currentXClicked, currentYClicked, numberOfObstacles, numberOfASideKnights, numberOfBSideKnights, numberOfASideElfs,
            numberOfBSideElfs, numberOfASideDwarfs, numberOfBSideDwarfs, numberOfMoves = 1, numberOfTimesTheBoardHasToBeCovered = 12;

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

        creatingObstacles();
        createTheBattlefield();
        createPieces();
        addButtonsChoicesForFigures();
        addButtonsChoicesForActions();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == knight){
            placingTheKnightsOnTheBoard();
            //placingThePiecesAndLogic(firstKnightASide, secondKnightASide, firstKnightBSide, secondKnightBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions, playerBChampions, numberOfASideKnights, numberOfBSideKnights);
        }
        if(e.getSource() == elf)
            placingTheElfsOnTheBoard();
        if(e.getSource() == dwarf)
            placingTheDwarfsOnTheBoard();

        if(e.getSource() == attack){

        }

        if(startOfTheBattle()){
            removingTheButtonsForFigures();
            addButtonsChoicesForActions();
            createPieces();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        drawingTheTileBoard(g);
        drawingThePieces(g);
        drawingThePlayerChoice(g);
        coveringTheBoard(g);
    }

    private void drawingTheTileBoard(Graphics g){
        for (int i = 0; i < BATTLEFIELD_WIDTH_TILE; i++) {
            for (int j = 0; j < BATTLEFIELD_HEIGHT_TILE; j++) {
                battlefieldBoard[i][j].renderTile(g);
            }
        }
    }

    private void drawingThePieces(Graphics g){
        for (int i = 0; i < BATTLEFIELD_WIDTH; i++){
            for(int j = 0; j < BATTLEFIELD_HEIGHT; j++){
                if(piecesBoard[i][j] != null){
                    piecesBoard[i][j].renderPiece(g);
                }
            }
        }
    }

    private void drawingThePlayerChoice(Graphics g){
        if(numberOfMoves % 2 == 0){
            playerChoiceA.removeRenderedPlayerChoice(g);
            playerChoiceB.renderPlayerChoice(g);
        }
        else{
            playerChoiceB.removeRenderedPlayerChoice(g);
            playerChoiceA.renderPlayerChoice(g);
        }
    }

    private void createTheBattlefield(){
        int changeOfColors = 1;

        for(int i = 0; i < BATTLEFIELD_WIDTH_TILE; i++){
            for(int j = 0; j < BATTLEFIELD_HEIGHT_TILE; j++){
                if((i >= 2 && i <= 4) && battlefieldBoard[i][j] == null){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(213, 211, 211));
                    changeOfColors = 2;
                }
                else if(changeOfColors % 2 != 0 && battlefieldBoard[i][j] == null){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(130, 128, 128));
                }
                else if(changeOfColors % 2 == 0 && battlefieldBoard[i][j] == null){
                    battlefieldBoard[i][j] = new Tile(i, j, new Color(54, 51, 51));
                }

                changeOfColors++;
            }
        }
    }

    private void coveringTheBoard(Graphics g){
        if(numberOfTimesTheBoardHasToBeCovered % 2 == 0 && numberOfTimesTheBoardHasToBeCovered > 0){
            for(int i = 0; i < BATTLEFIELD_WIDTH_TILE - 2; i++) {
                for (int j = 0; j < BATTLEFIELD_HEIGHT_TILE; j++) {
                    battlefieldBoard[i][j].showWhereToPutFiguresASide(g);
                }
            }
        }

        if(numberOfTimesTheBoardHasToBeCovered % 2 != 0 && numberOfTimesTheBoardHasToBeCovered > 0){
            for(int i = 2; i < BATTLEFIELD_WIDTH_TILE; i++) {
                for (int j = 0; j < BATTLEFIELD_HEIGHT_TILE; j++) {
                    battlefieldBoard[i][j].showWhereToPutFiguresBSide(g);
                }
            }
        }

        numberOfTimesTheBoardHasToBeCovered--;
    }

    private void creatingObstacles(){
        numberOfObstacles = random.nextInt(5) + 1;

        int randXCoordinate;
        int randYCoordinate;

        for(int i = 0; i < numberOfObstacles;){
            randXCoordinate = random.nextInt(3) +2;
            randYCoordinate = random.nextInt(8);
            if(battlefieldBoard[randXCoordinate][randYCoordinate] == null){
                battlefieldBoard[randXCoordinate][randYCoordinate] = new Obstacles(randXCoordinate, randYCoordinate, new Color(160, 0, 20));
                i++;
            }
        }
    }

    private void createPieces(){
        numberOfASideKnights = 2;
        numberOfBSideKnights = 2;
        numberOfASideDwarfs = 2;
        numberOfBSideDwarfs = 2;
        numberOfASideElfs = 2;
        numberOfBSideElfs = 2;

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

    private void placingPiecesOnBoard(Piece thePiece, Piece[][] piecesBoard, boolean isOnTheBoard, int x, int y, ArrayList champions){
        if(isOnTheBoard && piecesBoard[x][y] == null){
            thePiece.setXCoordinates(x);
            thePiece.setYCoordinates(y);
            piecesBoard[thePiece.getXCoordinates()][thePiece.getYCoordinates()] = thePiece;
            champions.remove(thePiece);

            repaint();
        }
    }

    private void placingTheKnightsOnTheBoard(){
        if(numberOfMoves % 2 != 0 && numberOfASideKnights > 0){
            if(numberOfASideKnights == 2) {
                placingPiecesOnBoard(firstKnightASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            if(numberOfASideKnights == 1) {
                placingPiecesOnBoard(secondKnightASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            numberOfASideKnights--;
        }

        if(numberOfMoves % 2 == 0 && numberOfBSideKnights > 0){
            if(numberOfBSideKnights == 2) {
                placingPiecesOnBoard(firstKnightBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            if(numberOfBSideKnights == 1) {
                placingPiecesOnBoard(secondKnightBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            numberOfBSideKnights--;
        }

        if(numberOfASideKnights > 0 || numberOfBSideKnights > 0)
            numberOfMoves++;
    }

    private void placingTheElfsOnTheBoard(){
        if(numberOfMoves % 2 != 0 && numberOfASideElfs > 0){
            if(numberOfASideElfs == 2) {
                placingPiecesOnBoard(firstElfASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            if(numberOfASideElfs == 1) {
                placingPiecesOnBoard(secondElfASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            numberOfASideElfs--;
        }

        if(numberOfMoves % 2 == 0 && numberOfBSideElfs > 0){
            if(numberOfBSideElfs == 2) {
                placingPiecesOnBoard(firstElfBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            if(numberOfBSideElfs == 1) {
                placingPiecesOnBoard(secondElfBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            numberOfBSideElfs--;
        }

        numberOfMoves++;
    }

    private void placingTheDwarfsOnTheBoard(){
        if(numberOfMoves % 2 != 0 && numberOfASideDwarfs > 0){
            if(numberOfASideDwarfs == 2) {
                placingPiecesOnBoard(firstDwarfASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            if(numberOfASideDwarfs == 1) {
                placingPiecesOnBoard(secondDwarfASide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheASideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerAChampions);
            }
            numberOfASideDwarfs--;
        }

        if(numberOfMoves % 2 == 0 && numberOfBSideDwarfs > 0){
            if(numberOfBSideDwarfs == 2) {
                placingPiecesOnBoard(firstDwarfBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            if(numberOfBSideDwarfs == 1) {
                placingPiecesOnBoard(secondDwarfBSide, piecesBoard, theClickedPositionIsInTheBoard(currentXClicked, currentYClicked) && isOnTheBSideOfTheBoard(currentXClicked, currentYClicked), currentXClicked, currentYClicked, playerBChampions);
            }
            numberOfBSideDwarfs--;
        }

        numberOfMoves++;
    }


    private void placingThePiecesAndLogic(Piece theFirstPieceASide, Piece theSecondPieceASide, Piece theFirstPieceBSide, Piece theSecondPieceBSide, Piece[][] piecesBoard, boolean isOnTheBoard, int x, int y, ArrayList championsA, ArrayList championsB, int numberOfPiecesASide, int numberOfPiecesBSide){
        if(numberOfMoves % 2 != 0 && numberOfPiecesASide > 0){
            if(numberOfPiecesASide == 2)
                placingPiecesOnBoard(theFirstPieceASide, piecesBoard, isOnTheBoard, x, y, championsA);
            if(numberOfPiecesASide == 1)
                placingPiecesOnBoard(theSecondPieceASide, piecesBoard, isOnTheBoard, x, y, championsA);
            --numberOfPiecesASide;
        }

        if(numberOfMoves % 2 == 0 && numberOfPiecesBSide > 0){
            if(numberOfPiecesBSide == 2)
                placingPiecesOnBoard(theFirstPieceBSide, piecesBoard, isOnTheBoard, x, y, championsB);
            if(numberOfPiecesBSide == 1)
                placingPiecesOnBoard(theSecondPieceBSide, piecesBoard, isOnTheBoard, x, y, championsB);
            --numberOfPiecesBSide;
        }

        numberOfMoves++;
    }

    private void addButtonsChoicesForFigures(){
        knight = new JButton("Knight");
        elf = new JButton("Elf");
        dwarf = new JButton("Dwarf");

        this.add(knight);
        this.add(elf);
        this.add(dwarf);

        knight.setBounds(860, 120, 140, 40);
        elf.setBounds(860, 180, 140, 40);
        dwarf.setBounds(860, 240, 140, 40);

        knight.addActionListener(this);
        elf.addActionListener(this);
        dwarf.addActionListener(this);
    }

    private void removingTheButtonsForFigures(){
        this.remove(knight);
        this.remove(elf);
        this.remove(dwarf);
    }

    private void addButtonsChoicesForActions(){
        attack = new JButton("Attack");
        heal = new JButton("Heal");
        move = new JButton("Move");

        this.add(attack);
        this.add(heal);
        this.add(move);

        attack.setBounds(860, 120, 140, 40);
        heal.setBounds(860, 180, 140, 40);
        move.setBounds(860, 240, 140, 40);

        attack.addActionListener(this);
        heal.addActionListener(this);
        move.addActionListener(this);
    }

    private boolean startOfTheBattle(){
        return gameIsRunning = (playerAChampions.isEmpty() && playerBChampions.isEmpty());
    }

    private boolean isGameIsFinished(){
        return ((playerAChampions.isEmpty() && !playerBChampions.isEmpty()) || (!playerAChampions.isEmpty() && playerBChampions.isEmpty()));
    }

    private boolean theClickedPositionIsInTheBoard(int x, int y){
        return ((x >= 0 && x <= 8) && (y >= 0 && y <= 6));
    }

    private boolean isOnTheASideOfTheBoard(int x, int y){
        return ((x >= 0 && x <= 8) && (y == 5 || y == 6));
    }

    private boolean isOnTheBSideOfTheBoard(int x, int y){
        return ((x >= 0 && x <= 8) && (y == 0 || y == 1));
    }
}
