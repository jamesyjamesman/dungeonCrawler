package main.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DungeonTextPane extends JTextPane {
    public DungeonTextPane() {
        this.setForeground(Color.white);
        this.setBackground(Color.black);
        this.setOpaque(false);
        this.setEditable(false);
        this.setBorder(new EmptyBorder(8, 8, 8, 8)); //top,left,bottom,right
    }
}
