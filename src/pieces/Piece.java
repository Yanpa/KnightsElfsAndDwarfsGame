package pieces;

import java.awt.*;

public abstract class Piece {
    private final int PIECE_SIZE = 50;
    private int attack, armor, health, attackRange, speed;
    private char characterIdentity;

    public Piece(int attack, int armor, int health, int attackRange, int speed, char character){
        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.attackRange = attackRange;
        this.speed = speed;

        this.characterIdentity = character;
    }

    public void renderPiece(Graphics g, int x, int y){
        if(characterIdentity == 'K' || characterIdentity == 'E' || characterIdentity == 'D')
            g.setColor(new Color(54, 13, 73));
        else g.setColor(new Color(89, 224, 132));

        g.fillRect((x * PIECE_SIZE) + 85, (y * PIECE_SIZE) + 60, PIECE_SIZE, PIECE_SIZE);
        g.setFont(new Font("Ink Free", Font.BOLD, 10));
        g.drawString(String.valueOf(characterIdentity), (x * PIECE_SIZE) + 95, (y * PIECE_SIZE) + 70);
    }
}
