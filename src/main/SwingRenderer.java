package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SwingRenderer extends JFrame {

    public static JFrame renderer() {
        ImageIcon backgroundImage = new ImageIcon("assets/background.png");
        int imageWidth = backgroundImage.getIconWidth();
        int imageHeight = backgroundImage.getIconHeight();
//        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        JFrame frame = new JFrame();
        frame.setTitle("Dungeon Crawler");
        frame.setBounds(0, 0, imageWidth, imageHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel backgroundImageLabel = new JLabel(backgroundImage);
        backgroundImageLabel.setSize(1625, 955);
        backgroundImageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundImageLabel.setName("background");
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundImageLabel, JLayeredPane.DEFAULT_LAYER);

        Dimension frameSize = frame.getSize();
        int frameHeight = (int) frameSize.getHeight();
        int frameWidth = (int) frameSize.getWidth();

        JLabel descriptionTextLabel = new JLabel("Placeholder text");
        descriptionTextLabel.setName("description");
        descriptionTextLabel.setSize(200, 200);
        descriptionTextLabel.setBorder(new LineBorder(Color.gray));
        descriptionTextLabel.setBackground(Color.white);
        descriptionTextLabel.setOpaque(true);
        layeredPane.add(descriptionTextLabel);
        layeredPane.setLayer(descriptionTextLabel, 2);

        JLabel statusTextLabel = new JLabel("<html>Placeholder<br>newline!</html>");
        statusTextLabel.setName("status");
        statusTextLabel.setBorder(new LineBorder(Color.gray));
        statusTextLabel.setBackground(Color.white);
        statusTextLabel.setOpaque(true);
        int labelWidth = 300;
        int labelHeight = 200;
        statusTextLabel.setBounds(frameWidth - labelWidth, 0, labelWidth, labelHeight);
        layeredPane.add(statusTextLabel);
        layeredPane.setLayer(statusTextLabel, 2);

        JLabel inventoryTextLabel = new JLabel("hi!");
        inventoryTextLabel.setName("inventory");
        inventoryTextLabel.setBorder(new LineBorder(Color.gray));
        inventoryTextLabel.setBackground(Color.white);
        inventoryTextLabel.setOpaque(true);
        labelWidth = 300;
        labelHeight = 300;
        inventoryTextLabel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight - 100, labelWidth, labelHeight);
        layeredPane.add(inventoryTextLabel);
        layeredPane.setLayer(inventoryTextLabel, 2);

        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);

        return frame;
    }
    public static void changeLabelText(JFrame frame, String newText, LabelType label) {
        newText = newText.replaceAll("\n", "<br>");
        newText = "<html>" + newText + "</html>";

        switch (label) {
            case STATUS -> ((JLabel) getComponentFromFrame(frame, "status")).setText(newText);
            case INVENTORY -> ((JLabel) getComponentFromFrame(frame, "inventory")).setText(newText);
//            case RELICS ->
//            case BATTLE ->
//            case NAVIGATION ->
            case DESCRIPTION -> ((JLabel) getComponentFromFrame(frame, "description")).setText(newText);
        }

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
