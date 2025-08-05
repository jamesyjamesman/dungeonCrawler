package main.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DungeonLabel extends JLabel {
    public DungeonLabel() {
        this.setForeground(Color.white);
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(8, 8, 8, 8)); //top,left,bottom,right
    }
}
