package board;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {
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
