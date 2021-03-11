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

public class Battlefield extends JPanel implements MouseListener, ActionListener {

    private final int BATTLEFIELD_HEIGHT = 9;
    private final int BATTLEFIELD_WIDTH = 7;
    Tile[][] battlefieldBoard = new Tile[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];
    Piece[][] piecesBoard = new Piece[BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];

    PlayerChoice playerChoiceA = new PlayerChoice(800, 50, 'A');
    PlayerChoice playerChoiceB = new PlayerChoice(800, 50, 'B');

    JButton knight, elf, dwarf, attack, heal, move;

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
        addButtonsChoicesForFigures();
        addButtonsChoicesForActions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == knight){
            System.out.println("Knight was clicked");
        }

        if(e.getSource() == attack){
            System.out.println("Attack was clicked");
        }

        if(startOfTheBattle()){
            removingTheButtonsForFigures();
            addButtonsChoicesForActions();
        }
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


    }

    private boolean startOfTheBattle(){
        return gameIsRunning = (playerAChampions.isEmpty() && playerBChampions.isEmpty());
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
}
