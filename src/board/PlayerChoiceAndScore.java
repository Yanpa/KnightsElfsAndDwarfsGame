package board;

import java.awt.*;

public class PlayerChoiceAndScore {
    private int xCoordinatesOfTheChoice, yCoordinatesOfTheChoice, xCoordinatesOfTheScore, yCoordinatesOfTheScore, theTeamScore = 0;
    private char player;

    public PlayerChoiceAndScore(int xChoice, int yChoice, char player, int xScore, int yScore){
        this.xCoordinatesOfTheChoice = xChoice;
        this.yCoordinatesOfTheChoice = yChoice;

        this.xCoordinatesOfTheScore = xScore;
        this.yCoordinatesOfTheScore = yScore;

        this.player = player;
    }

    public int getTheTeamScore() {
        return theTeamScore;
    }

    public void setTheTeamScore(int theTeamScore) {
        this.theTeamScore = theTeamScore;
    }

    /**
     * Рисува полето за избора на играча
     * @param g
     */
    public void renderPlayerChoice(Graphics g){
        g.setColor(Color.black);
        g.drawRect(xCoordinatesOfTheChoice, yCoordinatesOfTheChoice, 250, 300);

        g.setFont(new Font("Ariel", Font.BOLD, 20));
        g.drawString("Player " + player + " turn", xCoordinatesOfTheChoice + 60, yCoordinatesOfTheChoice + 30);
    }

    /**
     * Рисува точките на играча
     * @param g
     */
    public void renderPlayerScore(Graphics g){
        g.setColor(Color.black);
        g.drawRect(xCoordinatesOfTheScore, yCoordinatesOfTheScore, 250, 100);

        if(player == 'A')
            g.setColor(new Color(88, 1, 75));
        else g.setColor(new Color(92, 215, 130));
        g.setFont(new Font("Ariel", Font.BOLD, 20));
        g.drawString( "Team " + player + " score: " + theTeamScore, xCoordinatesOfTheScore + 60, yCoordinatesOfTheScore + 30);
    }

    /**
     * Прикрива точките и избора на играча
     * @param g
     */
    public void removeRenderedPlayerChoice(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(xCoordinatesOfTheChoice, yCoordinatesOfTheChoice, 250, 300);
        g.drawRect(xCoordinatesOfTheScore, yCoordinatesOfTheScore, 250, 300);

        g.setFont(new Font("Ariel", Font.BOLD, 20));
        g.drawString("Player " + player + " turn", xCoordinatesOfTheChoice + 60, yCoordinatesOfTheChoice + 30);
        g.drawString( "Team " + player + " score: " + theTeamScore, xCoordinatesOfTheScore + 60, yCoordinatesOfTheScore + 30);
    }
}
