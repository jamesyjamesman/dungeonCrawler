package main.swing;

import javax.swing.*;
import java.awt.*;

public class DungeonTextPane extends JTextPane {
    public DungeonTextPane() {
        this.setForeground(Color.white);
        this.setBackground(Color.black);
        this.setOpaque(false);
        this.setEditable(false);
    }
}
