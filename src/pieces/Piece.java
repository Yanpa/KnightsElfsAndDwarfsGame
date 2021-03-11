package pieces;

import java.awt.*;

public abstract class Piece {
    private final int PIECE_SIZE = 70;
    private int attack, armor, health, attackRange, speed, team, xCoordinates, yCoordinates;
    private char characterIdentity;

    public Piece(){

    }

    public Piece(int attack, int armor, int health, int attackRange, int speed, char character, int team){
        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.attackRange = attackRange;
        this.speed = speed;
        this.team = team;

        this.characterIdentity = character;
    }

    public int getXCoordinates() {
        return xCoordinates;
    }

    public void setXCoordinates(int xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public int getYCoordinates() {
        return yCoordinates;
    }

    public void setYCoordinates(int yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public char getCharacterIdentity() {
        return characterIdentity;
    }

    public void setCharacterIdentity(char characterIdentity) {
        this.characterIdentity = characterIdentity;
    }

    public void renderPiece(Graphics g){
        if(characterIdentity == 'K' || characterIdentity == 'E' || characterIdentity == 'D')
            g.setColor(new Color(54, 13, 73));
        else g.setColor(new Color(89, 224, 132));

        g.fillRect((xCoordinates * PIECE_SIZE) + 70, (yCoordinates * PIECE_SIZE) + 70, PIECE_SIZE, PIECE_SIZE);

        g.setColor(new Color(253, 205, 5));
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        g.drawString(String.valueOf(characterIdentity), (xCoordinates * PIECE_SIZE) + 90, (yCoordinates * PIECE_SIZE) + 115);
    }
}
