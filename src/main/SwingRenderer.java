package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SwingRenderer extends JFrame {

    public static JFrame renderer() {
        ImageIcon backgroundImage = new ImageIcon("assets/background.png");
        int imageWidth = backgroundImage.getIconWidth();
        int imageHeight = backgroundImage.getIconHeight();
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setBackground(Color.black);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setTitle("Dungeon Crawler");
        frame.setBounds(0, 0, screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel backgroundImageLabel = new JLabel(backgroundImage);
        backgroundImageLabel.setSize(imageWidth, imageHeight);
        backgroundImageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundImageLabel.setName("background");
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundImageLabel, JLayeredPane.DEFAULT_LAYER);

        Dimension frameSize = frame.getSize();
        int frameHeight = (int) frameSize.getHeight();
        int frameWidth = (int) frameSize.getWidth();

        JLabel descriptionTextLabel = new JLabel();
        descriptionTextLabel.setName("description");
        descriptionTextLabel.setSize(200, 200);
        descriptionTextLabel.setBorder(new LineBorder(Color.lightGray));
        descriptionTextLabel.setBackground(Color.black);
        descriptionTextLabel.setForeground(Color.white);
        descriptionTextLabel.setOpaque(true);
        layeredPane.add(descriptionTextLabel);
        layeredPane.setLayer(descriptionTextLabel, 2);

        JLabel statusTextLabel = new JLabel();
        statusTextLabel.setName("status");
        statusTextLabel.setBorder(new LineBorder(Color.lightGray));
        statusTextLabel.setBackground(Color.black);
        statusTextLabel.setForeground(Color.white);
        statusTextLabel.setOpaque(true);
        int labelWidth = 300;
        int labelHeight = 200;
        statusTextLabel.setBounds(frameWidth - labelWidth, 0, labelWidth, labelHeight);
        layeredPane.add(statusTextLabel);
        layeredPane.setLayer(statusTextLabel, 2);

        JLabel inventoryTextLabel = new JLabel();
        inventoryTextLabel.setName("inventory");
        inventoryTextLabel.setBorder(new LineBorder(Color.lightGray));
        inventoryTextLabel.setBackground(Color.black);
        inventoryTextLabel.setForeground(Color.white);
        inventoryTextLabel.setOpaque(true);
        labelHeight = 300;
        inventoryTextLabel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(inventoryTextLabel);
        layeredPane.setLayer(inventoryTextLabel, 2);

        JLabel relicTextLabel = new JLabel();
        relicTextLabel.setName("relics");
        relicTextLabel.setBorder(new LineBorder(Color.lightGray));
        relicTextLabel.setBackground(Color.black);
        relicTextLabel.setForeground(Color.white);
        relicTextLabel.setOpaque(true);
        relicTextLabel.setBounds(0, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(relicTextLabel);
        layeredPane.setLayer(relicTextLabel, 2);

        JLabel battleTextLabel = new JLabel();
        battleTextLabel.setName("battle");
        battleTextLabel.setBorder(new LineBorder(Color.lightGray));
        battleTextLabel.setBackground(Color.black);
        battleTextLabel.setForeground(Color.white);
        battleTextLabel.setOpaque(true);
        labelWidth = 600;
        battleTextLabel.setBounds(frameWidth/2 - labelWidth/2, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(battleTextLabel);
        layeredPane.setLayer(battleTextLabel, 2);

        JLabel navigationTextLabel = new JLabel();
        navigationTextLabel.setName("navigation");
        navigationTextLabel.setBorder(new LineBorder(Color.lightGray));
        navigationTextLabel.setBackground(Color.black);
        navigationTextLabel.setForeground(Color.white);
        navigationTextLabel.setOpaque(true);
        navigationTextLabel.setBounds(frameWidth/2 - labelWidth/2, 0, labelWidth, labelHeight);
        layeredPane.add(navigationTextLabel);
        layeredPane.setLayer(navigationTextLabel, 2);

        frame.setLayeredPane(layeredPane);

        return frame;
    }
    public static void changeLabelText(JFrame frame, String newText, LabelType label) {
        newText = newText.replaceAll("\n", "<br>");
        newText = "<html>" + newText + "</html>";

        String targetComponent = "";

        switch (label) {
            case STATUS -> targetComponent = "status";
            case INVENTORY -> targetComponent = "inventory";
            case RELICS -> targetComponent = "relics";
            case BATTLE -> targetComponent = "battle";
            case NAVIGATION -> targetComponent = "navigation";
            case DESCRIPTION -> targetComponent = "description";
        }

        ((JLabel) getComponentFromFrame(frame, targetComponent)).setText(newText);

    }
    public static Component getComponentFromFrame(JFrame frame, String targetComponentName) {
        for (int i = 0; i < frame.getLayeredPane().getComponentsInLayer(2).length; i++) {
            Component tempComponent = frame.getLayeredPane().getComponentsInLayer(2)[i];
            if (tempComponent.getName().equals(targetComponentName)) {
                return tempComponent;
            }
        }
        return null;
    }
}
