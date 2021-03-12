package board;

import javax.swing.*;

public class GameBoard extends JFrame {
    /**
     * Констурктор създаващ приложението (играта)
     */
    public GameBoard(){
        this.add(new Battlefield());
        this.setSize(1100, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setName("Knights, Elfs and Dwarfs");
        this.pack();
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
