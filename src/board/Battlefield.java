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

    PlayerChoiceAndScore playerChoiceAndScoreA = new PlayerChoiceAndScore(800, 50, 'A', 800, 400);
    PlayerChoiceAndScore playerChoiceAndScoreB = new PlayerChoiceAndScore(800, 50, 'B', 800, 400);

    JButton knight, elf, dwarf, attack, heal, move, restart;

    int currentXClicked, currentYClicked, numberOfObstacles, numberOfASideKnights, numberOfBSideKnights, numberOfASideElfs,
            numberOfBSideElfs, numberOfASideDwarfs, numberOfBSideDwarfs, numberOfMoves = 1, numberOfTimesTheBoardHasToBeCovered = 12, deadCount = 0, numberOfClicks = 0;
    int secondPositionX, secondPositionY;

    boolean gameIsRunning = false;

    ArrayList<Piece> playerAChampions = new ArrayList<>(6);
    ArrayList<Piece> playerBChampions = new ArrayList<>(6);
    ArrayList<Piece> gameAChampions = new ArrayList<>(6);
    ArrayList<Piece> gameBChampions = new ArrayList<>(6);
    Piece[] deadPieces = new Piece[12];

    Piece[] pickedPieces = new Piece[2];

    Knight firstKnightASide = new Knight('K', 1, "FirstKnightOfASide");
    Knight secondKnightASide = new Knight('K', 1, "SecondKnightOfASide");
    Elf firstElfASide = new Elf('E', 1, "FirstElfOfASide");
    Elf secondElfASide = new Elf('E', 1, "SecondElfOfASide");
    Dwarf firstDwarfASide = new Dwarf('D', 1, "FirstDwarfASide");
    Dwarf secondDwarfASide = new Dwarf('D', 1, "SecondDwarfASide");

    Knight firstKnightBSide = new Knight('k', 2, "FirstKnightOfBSide");
    Knight secondKnightBSide = new Knight('k', 2, "SecondKnightOfBSide");
    Elf firstElfBSide = new Elf('e', 2, "FirstElfOfBSide");
    Elf secondElfBSide = new Elf('e', 2, "SecondElfOfBSide");
    Dwarf firstDwarfBSide = new Dwarf('d', 2, "FirstDwarfBSide");
    Dwarf secondDwarfBSide = new Dwarf('d', 2, "SecondDwarfBSide");

    /**
     * Констурктор създаващ препядствията, полето, фигурите, бутоните за избиране
     */
    public Battlefield(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1100, 700));
        this.setLayout(null);
        this.addMouseListener(this);

        creatingObstacles();
        createTheBattlefield();
        createPieces(playerAChampions, playerBChampions);
        addButtonsChoicesForFigures();
        addButtonsChoicesForActions();
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        currentXClicked = (e.getX() - 70) / 70;
        currentYClicked = (e.getY() - 70) / 70;

        System.out.println("X: " + currentXClicked);
        System.out.println("Y: " + currentYClicked);

        if(numberOfClicks == 1){
            secondPositionX = (e.getX() - 70) / 70;
            secondPositionY = (e.getY() - 70) / 70;
        }

        System.out.println("X2: " + secondPositionX);
        System.out.println("Y2: " + secondPositionY);
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

    /**
     * В зависимост от всеки натиснат бутон изпълнява своята функция
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == knight){
            placingTheKnightsOnTheBoard();
        }
        if(e.getSource() == elf)
            placingTheElfsOnTheBoard();
        if(e.getSource() == dwarf)
            placingTheDwarfsOnTheBoard();

        if(e.getSource() == attack){
            numberOfClicks = 1;
            pieceAttackMove(getPiece(currentXClicked, currentYClicked), getPiece(secondPositionX, secondPositionY), attackerIsInRange(getPiece(currentXClicked, currentYClicked), getPiece(secondPositionX, secondPositionY)));
        }

        if(e.getSource() == heal){
            piecesHealingItself(getPiece(currentXClicked, currentYClicked));
        }

        if(e.getSource() == move){
            numberOfClicks = 1;
            movingThePieces(getPiece(currentXClicked, currentYClicked));
        }
        if(e.getSource() == restart){
            ((Window)getRootPane().getParent()).dispose();
            new GameBoard();
        }

        if(startOfTheBattle()){
            removingTheButtonsForFigures();
            addButtonsChoicesForActions();
            createPieces(gameAChampions, gameBChampions);
            numberOfMoves = 1;
        }

        areChampionsDead();
        endOfTheGame();
    }

    /**
     * Вика метода draw, и рисува цялата дъска
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Рисува полето, фигурите, кутията с бутоните за избор и точките
     * @param g
     */
    private void draw(Graphics g) {
        drawingTheTileBoard(g);
        drawingThePieces(g);
        drawingThePlayerChoice(g);
        coveringTheBoard(g);
    }

    /**
     * Рисува полето
     * @param g
     */
    private void drawingTheTileBoard(Graphics g){
        for (int i = 0; i < BATTLEFIELD_WIDTH_TILE; i++) {
            for (int j = 0; j < BATTLEFIELD_HEIGHT_TILE; j++) {
                battlefieldBoard[i][j].renderTile(g);
            }
        }
    }

    /**
     * Рисува фигурите
     * @param g
     */
    private void drawingThePieces(Graphics g){
        for (int i = 0; i < BATTLEFIELD_WIDTH; i++){
            for(int j = 0; j < BATTLEFIELD_HEIGHT; j++){
                if(piecesBoard[i][j] != null){
                    piecesBoard[i][j].renderPiece(g);
                }
            }
        }
    }

    /**
     * Визуализира полето с избора на играча и неговите точки
     * @param g
     */
    private void drawingThePlayerChoice(Graphics g){
        if(numberOfMoves % 2 == 0){
            playerChoiceAndScoreA.removeRenderedPlayerChoice(g);
            playerChoiceAndScoreB.renderPlayerChoice(g);
            playerChoiceAndScoreB.renderPlayerScore(g);
        }
        else{
            playerChoiceAndScoreB.removeRenderedPlayerChoice(g);
            playerChoiceAndScoreA.renderPlayerChoice(g);
            playerChoiceAndScoreA.renderPlayerScore(g);
        }
    }

    /**
     * Инициализиране на масива за Игровото поле
     */
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

    /**
     * Показва визуално кои части не трябва да се натискат в началото на играта, преди всички фигури да бъдат поставени
     * на полето
     * @param g
     */
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

    /**
     * Метод, който създава препядствията на случаен принцип
     */
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

    /**
     * Създава 2 ArrayList-a в който се поставят фигурите от двата различни отбора
     * @param championsA
     * @param championsB
     */
    private void createPieces(ArrayList championsA, ArrayList championsB){
        numberOfASideKnights = 2;
        numberOfBSideKnights = 2;
        numberOfASideDwarfs = 2;
        numberOfBSideDwarfs = 2;
        numberOfASideElfs = 2;
        numberOfBSideElfs = 2;

        championsA.add(firstKnightASide);
        championsA.add(secondKnightASide);
        championsA.add(firstDwarfASide);
        championsA.add(secondDwarfASide);
        championsA.add(firstElfASide);
        championsA.add(secondElfASide);

        championsB.add(firstKnightBSide);
        championsB.add(secondKnightBSide);
        championsB.add(firstDwarfBSide);
        championsB.add(secondDwarfBSide);
        championsB.add(firstElfBSide);
        championsB.add(secondElfBSide);
    }

    /**
     * Инициализира фигурата в масива от фигури
     * @param thePiece Фигура
     * @param piecesBoard масив от фигури
     * @param isOnTheBoard булева стойност проверяваща дали не сме извън масива
     * @param x
     * @param y
     * @param champions ArrayList-a в който се намира съответната фигура
     */
    private void placingPiecesOnBoard(Piece thePiece, Piece[][] piecesBoard, boolean isOnTheBoard, int x, int y, ArrayList champions){
        if(isOnTheBoard && piecesBoard[x][y] == null){
            thePiece.setXCoordinates(x);
            thePiece.setYCoordinates(y);
            piecesBoard[thePiece.getXCoordinates()][thePiece.getYCoordinates()] = thePiece;
            champions.remove(thePiece);

            repaint();
        }
    }

    /**
     * Следващите три метода вършат една и съща работа, само че с различни фигури, проблемът ми беше че не успях да
     * намеря начин да променям стойностите на numberOfFigureSomeSide
     */
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

        if(numberOfASideKnights > 0 || numberOfBSideKnights > 0)
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

        if(numberOfASideKnights > 0 || numberOfBSideKnights > 0)
            numberOfMoves++;
    }


    /**
     * Искаше ми се да направя този метод, за да си спестя създаването на горните три, но не открих начин, как да
     * запазвам стойността на numberOfPiecesASide и numberOfPiecesBSide, след като метода се извика втори път
     *
     private void placingThePiecesAndLogic(Piece theFirstPieceASide, Piece theSecondPieceASide, Piece theFirstPieceBSide, Piece theSecondPieceBSide, Piece[][] piecesBoard, boolean isOnTheBoard, int x, int y, ArrayList championsA, ArrayList championsB, int numberOfPiecesASide, int numberOfPiecesBSide){
        if(numberOfMoves % 2 != 0 && numberOfPiecesASide > 0){
            if(numberOfPiecesASide == 2)
                placingPiecesOnBoard(theFirstPieceASide, piecesBoard, isOnTheBoard, x, y, championsA);
            if(numberOfPiecesASide == 1)
                placingPiecesOnBoard(theSecondPieceASide, piecesBoard, isOnTheBoard, x, y, championsA);
        }

        if(numberOfMoves % 2 == 0 && numberOfPiecesBSide > 0){
            if(numberOfPiecesBSide == 2)
                placingPiecesOnBoard(theFirstPieceBSide, piecesBoard, isOnTheBoard, x, y, championsB);
            if(numberOfPiecesBSide == 1)
                placingPiecesOnBoard(theSecondPieceBSide, piecesBoard, isOnTheBoard, x, y, championsB);
        }

        if(theFirstPieceASide == firstKnightASide){
            numberOfASideKnights = --numberOfPiecesASide;
            numberOfBSideKnights = --numberOfPiecesBSide;
        }

        if(theFirstPieceASide == firstElfASide){
            numberOfASideElfs =--numberOfPiecesASide;
            numberOfBSideElfs = --numberOfPiecesBSide;
        }

        if(theFirstPieceASide == firstDwarfASide){
            numberOfASideDwarfs = --numberOfPiecesASide;
            numberOfBSideDwarfs = --numberOfPiecesBSide;
        }


        numberOfMoves++;
    } */


    /**
     * Логика за предвижване на рицарите
     * @param theKnight
     */
    private void movingKnight(Piece theKnight){
        if(theKnight == firstKnightASide || theKnight == secondKnightASide || theKnight == firstKnightBSide || theKnight == secondKnightBSide){
            if(theKnight.getXCoordinates() + 1 == secondPositionX || theKnight.getXCoordinates() - 1 == secondPositionX || theKnight.getYCoordinates() + 1 == secondPositionY || theKnight.getYCoordinates() - 1 == secondPositionY){
                piecesBoard[currentXClicked][currentYClicked] = null;
                piecesBoard[secondPositionX][secondPositionY] = theKnight;
            }
        }
    }

    /**
     * Логика за предвижване на джуджетата
     * @param theDwarf
     */
    private void movingDwarf(Piece theDwarf){
        if(theDwarf == firstDwarfASide || theDwarf == secondDwarfASide || theDwarf == firstDwarfBSide || theDwarf == secondDwarfBSide){
            if(theDwarf.getXCoordinates() + 2 == secondPositionX || theDwarf.getXCoordinates() - 2 == secondPositionX ||
                    theDwarf.getYCoordinates() + 2 == secondPositionY || theDwarf.getYCoordinates() - 2 == secondPositionY ||
                    theDwarf.getXCoordinates() + 1 == secondPositionX || theDwarf.getXCoordinates() - 1 == secondPositionX ||
                    theDwarf.getYCoordinates() + 1 == secondPositionY || theDwarf.getYCoordinates() - 1 == secondPositionY ||
                     (theDwarf.getXCoordinates() + 1 == secondPositionX && theDwarf.getYCoordinates() - 1 == secondPositionY) ||
                    (theDwarf.getXCoordinates() + 1 == secondPositionX && theDwarf.getYCoordinates() + 1 == secondPositionY) ||
                    (theDwarf.getXCoordinates() - 1 == secondPositionX && theDwarf.getYCoordinates() - 1 == secondPositionY) ||
                    (theDwarf.getXCoordinates() - 1 == secondPositionX && theDwarf.getYCoordinates() + 1 == secondPositionY)){
                piecesBoard[currentXClicked][currentYClicked] = null;
                piecesBoard[secondPositionX][secondPositionY] = theDwarf;
            }
        }
    }

    /**
     * Логика за предвижване на елфите
     * @param theElf
     */
    private void movingElf(Piece theElf){
        if(theElf == firstDwarfASide || theElf == secondDwarfASide || theElf == firstDwarfBSide || theElf == secondDwarfBSide){
            if(theElf.getXCoordinates() + 2 == secondPositionX || theElf.getXCoordinates() - 2 == secondPositionX ||
                    theElf.getYCoordinates() + 2 == secondPositionY || theElf.getYCoordinates() - 2 == secondPositionY ||
                    theElf.getXCoordinates() + 1 == secondPositionX || theElf.getXCoordinates() - 1 == secondPositionX ||
                    theElf.getYCoordinates() + 1 == secondPositionY || theElf.getYCoordinates() - 1 == secondPositionY ||
                    theElf.getXCoordinates() + 3 == secondPositionX || theElf.getXCoordinates() - 3 == secondPositionX ||
                    theElf.getYCoordinates() + 3 == secondPositionY || theElf.getYCoordinates() - 3 == secondPositionY ||
                    (theElf.getXCoordinates() + 1 == secondPositionX && theElf.getYCoordinates() - 1 == secondPositionY) ||
                    (theElf.getXCoordinates() + 1 == secondPositionX && theElf.getYCoordinates() + 1 == secondPositionY) ||
                    (theElf.getXCoordinates() - 1 == secondPositionX && theElf.getYCoordinates() - 1 == secondPositionY) ||
                    (theElf.getXCoordinates() - 1 == secondPositionX && theElf.getYCoordinates() + 1 == secondPositionY) ||
                    (theElf.getXCoordinates() + 2 == secondPositionX && theElf.getYCoordinates() - 2 == secondPositionY) ||
                    (theElf.getXCoordinates() + 2 == secondPositionX && theElf.getYCoordinates() + 2 == secondPositionY) ||
                    (theElf.getXCoordinates() - 2 == secondPositionX && theElf.getYCoordinates() - 2 == secondPositionY) ||
                    (theElf.getXCoordinates() - 2 == secondPositionX && theElf.getYCoordinates() + 2 == secondPositionY)){
                piecesBoard[currentXClicked][currentYClicked] = null;
                piecesBoard[secondPositionX][secondPositionY] = theElf;
            }
        }
    }

    /**
     * Метод, който обединява горните 3 за по добра четливост на кода
     * @param thePiece
     */
    private void movingThePieces(Piece thePiece){
        movingKnight(thePiece);
        movingDwarf(thePiece);
        movingElf(thePiece);
    }

    /**
     * Логика за атакуването на другите фигури. Проверява дали те са в обсег, хвърля три различни зара и дава възможност
     * за пропуск и полу-атака
     * @param attacker
     * @param theAttackedOne
     * @param theAttackerIsInRange
     */
    private void pieceAttackMove(Piece attacker, Piece theAttackedOne, boolean theAttackerIsInRange){
        int randomDice1 = random.nextInt(6) + 1, randomDice2 = random.nextInt(6) + 1, randomDice3 = random.nextInt(6) + 1;
        int theDamageDoneToTheAttackedOne, randomDicesSum = randomDice1 + randomDice2 + randomDice3;

        if(theAttackerIsInRange && randomDicesSum != theAttackedOne.getHealth()){
            theDamageDoneToTheAttackedOne = attacker.getAttack() - theAttackedOne.getArmor();
            theAttackedOne.setHealth(theAttackedOne.getHealth() - theDamageDoneToTheAttackedOne);

            if(attacker.getTeam() == 1) playerChoiceAndScoreA.setTheTeamScore(playerChoiceAndScoreA.getTheTeamScore() + theDamageDoneToTheAttackedOne);
            else playerChoiceAndScoreB.setTheTeamScore(playerChoiceAndScoreB.getTheTeamScore() + theDamageDoneToTheAttackedOne);
        }

        if(theAttackerIsInRange && randomDicesSum == 3){
            theDamageDoneToTheAttackedOne = (attacker.getAttack() - theAttackedOne.getArmor()) / 2;
            theAttackedOne.setHealth(theAttackedOne.getHealth() - theDamageDoneToTheAttackedOne);

            if(attacker.getTeam() == 1) playerChoiceAndScoreA.setTheTeamScore(playerChoiceAndScoreA.getTheTeamScore() + theDamageDoneToTheAttackedOne);
            else playerChoiceAndScoreB.setTheTeamScore(playerChoiceAndScoreB.getTheTeamScore() + theDamageDoneToTheAttackedOne);
        }

        numberOfMoves++;
    }


    /**
     * Метод, който проверява дали атакувания е в обсега на атакуващия
     * @param attacker
     * @param theAttackedOne
     * @return
     */
    private boolean attackerIsInRange(Piece attacker, Piece theAttackedOne){
        if(attacker != null && theAttackedOne != null) {
            boolean isOnTheBoardHorizontally = (attacker.getXCoordinates() + attacker.getAttackRange() < 9 && attacker.getXCoordinates() - attacker.getAttackRange() >= 0);
            boolean isOnTheBoardVertically = (attacker.getYCoordinates() + attacker.getAttackRange() < 7 && attacker.getYCoordinates() - attacker.getAttackRange() >= 0);
            boolean isOnTheBoard = isOnTheBoardHorizontally && isOnTheBoardVertically;

            boolean isInRangeRight = (theAttackedOne.getXCoordinates() == attacker.getXCoordinates() + attacker.getAttackRange());
            boolean isInRangeLeft = (theAttackedOne.getXCoordinates() == attacker.getXCoordinates() - attacker.getAttackRange());
            boolean isInRangeTop = (theAttackedOne.getYCoordinates() == attacker.getYCoordinates() + attacker.getAttackRange());
            boolean isInRangeDown = (theAttackedOne.getYCoordinates() == attacker.getYCoordinates() - attacker.getAttackRange());

            return ((isInRangeTop || isInRangeRight || isInRangeDown || isInRangeLeft) && isOnTheBoard);
        }

        return false;
    }

    /**
     * Ако кръвта на фигурата стане 0 или по малко връща true, което задейства следващия метод и премахва фигурата от
     * игровото поле, от ArrayList-a и я поставя в масива *гробище*
     * @param champion
     * @return
     */
    private boolean aChampionDied(Piece champion){
        return champion.getHealth() <= 0;
    }

    private void procedureWhenAPieceDie(boolean championDead, Piece champion, ArrayList arrayList){
        if(championDead){
            arrayList.remove(champion);
            piecesBoard[champion.getXCoordinates()][champion.getYCoordinates()] = null;
            deadPieces[deadCount] = champion;
            deadCount++;
        }
    }

    /**
     * Проверка дали някой от фигурите е мъртва
     */
    private void areChampionsDead(){
        procedureWhenAPieceDie(aChampionDied(firstKnightASide), firstKnightASide, playerAChampions);
        procedureWhenAPieceDie(aChampionDied(secondKnightASide), secondKnightASide, playerAChampions);
        procedureWhenAPieceDie(aChampionDied(firstElfASide), firstElfASide, playerAChampions);
        procedureWhenAPieceDie(aChampionDied(secondElfASide), secondElfASide, playerAChampions);
        procedureWhenAPieceDie(aChampionDied(firstDwarfASide), firstDwarfASide, playerAChampions);
        procedureWhenAPieceDie(aChampionDied(secondDwarfASide), secondDwarfASide, playerAChampions);

        procedureWhenAPieceDie(aChampionDied(firstKnightBSide), firstKnightBSide, playerBChampions);
        procedureWhenAPieceDie(aChampionDied(secondKnightBSide), secondKnightBSide, playerBChampions);
        procedureWhenAPieceDie(aChampionDied(firstElfBSide), firstElfBSide, playerBChampions);
        procedureWhenAPieceDie(aChampionDied(secondElfBSide), secondElfBSide, playerBChampions);
        procedureWhenAPieceDie(aChampionDied(firstDwarfBSide), firstDwarfBSide, playerBChampions);
        procedureWhenAPieceDie(aChampionDied(secondDwarfBSide), secondDwarfBSide, playerBChampions);
    }

    /**
     * Метод който дава случаен борй кръв (от 1 до 6) на фигура, като взима нейната отвара в замяна и ѝ дава шанс ако има късмет
     * да повтори хода си
     * @param theHealer
     * @param theHealerHasAPotion
     */
    private void pieceHealingMove(Piece theHealer, boolean theHealerHasAPotion){
        int randomAmountOfHealing = random.nextInt(6) + 1, randomDiceThatDecidesIfYouWillHaveAnotherMove = random.nextInt(6) + 1;

        if(theHealerHasAPotion){
            theHealer.setHealth(theHealer.getHealth() + randomAmountOfHealing);
            theHealer.setHasHealthPotion(false);

            System.out.println("Фигурата получи " + randomAmountOfHealing + " допълнителна кръв");
        }


        if(randomDiceThatDecidesIfYouWillHaveAnotherMove % 2 == 0){
            numberOfMoves++;
        }
        else System.out.println("Имаш още един ход");
    }

    /**
     * Метод, който събира всички фигури на едно място и в зависимост от championTryingToHeal изпълнява определеният метод
     * @param championTryingToHeal
     */
    private void piecesHealingItself(Piece championTryingToHeal){
        if(championTryingToHeal == firstKnightASide) pieceHealingMove(firstKnightASide, firstKnightASide.hasHealthPotion());
        if(championTryingToHeal == firstElfASide) pieceHealingMove(firstElfASide, firstElfASide.hasHealthPotion());
        if(championTryingToHeal == firstDwarfASide) pieceHealingMove(firstDwarfASide, firstDwarfASide.hasHealthPotion());
        if(championTryingToHeal == firstKnightBSide) pieceHealingMove(firstKnightBSide, firstKnightBSide.hasHealthPotion());
        if(championTryingToHeal == firstElfBSide) pieceHealingMove(firstElfBSide, firstElfBSide.hasHealthPotion());
        if(championTryingToHeal == firstDwarfBSide) pieceHealingMove(firstDwarfBSide, firstDwarfBSide.hasHealthPotion());
        if(championTryingToHeal == secondKnightASide) pieceHealingMove(secondKnightASide, secondKnightASide.hasHealthPotion());
        if(championTryingToHeal == secondElfASide) pieceHealingMove(secondElfASide, secondElfASide.hasHealthPotion());
        if(championTryingToHeal == secondDwarfASide) pieceHealingMove(secondDwarfASide, secondDwarfASide.hasHealthPotion());
        if(championTryingToHeal == secondKnightBSide) pieceHealingMove(secondKnightBSide, secondKnightBSide.hasHealthPotion());
        if(championTryingToHeal == secondElfBSide) pieceHealingMove(secondElfBSide, secondElfBSide.hasHealthPotion());
        if(championTryingToHeal == secondDwarfBSide) pieceHealingMove(secondDwarfBSide, secondDwarfBSide.hasHealthPotion());
    }

    /**
     * Прибава бутони за Елф, Рицар и Джудже на игровото поле
     */
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

    /**
     * Премахва бутоните за рицар, елф и джудже
     */
    private void removingTheButtonsForFigures(){
        this.remove(knight);
        this.remove(elf);
        this.remove(dwarf);
    }

    /**
     * Добавя бутози за атака, лекуване и движение
     */
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

    /**
     * Премахва бутоните за атака, лекуване и движение
     */
    private void removingTheButtonsForActions(){
        this.remove(attack);
        this.remove(heal);
        this.remove(move);
    }

    /**
     * Добавяне на бутон за рестартиране
     */
    private void addRestartButton(){
        restart = new JButton("RESTART");
        this.add(restart);

        restart.setBounds(100, 100, 700, 400);

        restart.addActionListener(this);
    }

    /**
     * Започване на играта след поставяне на фигурите
     * @return
     */
    private boolean startOfTheBattle(){
        return gameIsRunning = (playerAChampions.isEmpty() && playerBChampions.isEmpty());
    }

    /**
     * Проверка за приключване на играта
     * @return
     */
    private boolean isGameIsFinished(){
        return ((playerAChampions.isEmpty() && !playerBChampions.isEmpty()) || (!playerAChampions.isEmpty() && playerBChampions.isEmpty()));
    }

    /**
     * Метод за приключване на играта
     */
    private void endOfTheGame(){
        if(isGameIsFinished()){
            System.out.println("All the dead pieces dying in order:");
            for(int i = 0; i < deadPieces.length - 1; i++){
                int count = 1;
                System.out.println(count + " " + deadPieces[i].getNameOfTheChampion());
            }
            System.out.println("Number of rounds: " + numberOfMoves);
            System.out.println("Team A score: " + playerChoiceAndScoreA.getTheTeamScore());
            System.out.println("Team B score: " + playerChoiceAndScoreB.getTheTeamScore());

            addRestartButton();
        }
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

    private boolean checkIfPieceIsThere(int x, int y){
        return piecesBoard[x][y] != null;
    }

    private Piece getPiece(int x, int y){
        if(checkIfPieceIsThere(x, y))
            return piecesBoard[x][y];
        return null;
    }
}
