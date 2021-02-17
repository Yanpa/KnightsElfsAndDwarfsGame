package pieces;

public abstract class Piece {
    private int attack, armor, health, attackRange, speed;

    public Piece(int attack, int armor, int health, int attackRange, int speed){
        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.attackRange = attackRange;
        this.speed = speed;
    }
}
