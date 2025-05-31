package main.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InventoryButton extends JButton {
    InventoryButton() {
        this.setBorder(new LineBorder(Color.lightGray));
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setOpaque(true);
    }
}
