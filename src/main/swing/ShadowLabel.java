package main.swing;

import javax.swing.*;

public class ShadowLabel extends JLabel {
    public ShadowLabel(JLayeredPane layeredPane) {
        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("component_shadow.png")));
        this.setOpaque(false);
        this.setVisible(true);
        layeredPane.add(this);
        layeredPane.setLayer(this, -1);
    }
}
