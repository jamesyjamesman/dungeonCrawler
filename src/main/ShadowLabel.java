package main;

import javax.swing.*;

public class ShadowLabel extends JLabel {
    public ShadowLabel(JLayeredPane layeredPane) {
        this.setIcon(new ImageIcon("assets/blackish.png"));
        this.setOpaque(false);
        this.setVisible(true);
        layeredPane.add(this);
        layeredPane.setLayer(this, -1);
    }
}
