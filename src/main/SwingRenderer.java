package main;

import main.item.Item;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setName("inventory");
        inventoryPanel.setBorder(new LineBorder(Color.lightGray));
        inventoryPanel.setBackground(Color.black);
        inventoryPanel.setForeground(Color.white);
        inventoryPanel.setOpaque(true);
        labelHeight = 300;
        inventoryPanel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(inventoryPanel);
        layeredPane.setLayer(inventoryPanel, 1);

        JPanel relicPanel = new JPanel();
        relicPanel.setName("relics");
        relicPanel.setBorder(new LineBorder(Color.lightGray));
        relicPanel.setBackground(Color.black);
        relicPanel.setForeground(Color.white);
        relicPanel.setOpaque(true);
        relicPanel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        relicPanel.setVisible(false);
        layeredPane.add(relicPanel);
        layeredPane.setLayer(relicPanel, 3);

        JPanel inventorySwitches = new JPanel();
//        inventorySwitches.setBorder(new LineBorder(Color.lightGray));
        inventorySwitches.setBackground(Color.black);
        inventorySwitches.setForeground(Color.white);
        inventorySwitches.setOpaque(false);
        inventorySwitches.setBounds(frameWidth - labelWidth, frameHeight - labelHeight - 25, labelWidth, 25);
        layeredPane.add(inventorySwitches);
        layeredPane.setLayer(inventorySwitches, 10);

        InventoryButton switchInventory = new InventoryButton();
        switchInventory.addActionListener(_ -> makeInventoryVisible(frame));
        switchInventory.setText("Inventory");
        inventorySwitches.add(switchInventory);

        InventoryButton switchRelics = new InventoryButton();
        switchRelics.addActionListener(_ -> makeRelicsVisible(frame));
        switchRelics.setText("Relics");
        inventorySwitches.add(switchRelics);

        //deprecated
        JLabel battleTextLabel = new JLabel();
        battleTextLabel.setVisible(false);
        battleTextLabel.setName("battle");
        battleTextLabel.setBorder(new LineBorder(Color.lightGray));
        battleTextLabel.setBackground(Color.black);
        battleTextLabel.setForeground(Color.white);
        battleTextLabel.setOpaque(true);
        labelHeight = 300;
        battleTextLabel.setBounds(frameWidth/2 - labelWidth/2, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(battleTextLabel);
        layeredPane.setLayer(battleTextLabel, 2);

        JLabel tempText = new JLabel();
        tempText.setName("temp");
        tempText.setVisible(false);
        layeredPane.add(tempText);
        layeredPane.setLayer(tempText, 2);

        JTextField userInput = new JTextField();
        userInput.setName("input");
        userInput.setBorder(new LineBorder(Color.lightGray));
        userInput.setBackground(Color.black);
        userInput.setForeground(Color.white);
        userInput.setOpaque(true);
        int userInputHeight = 100;
        int userInputWidth = 600;
        userInput.setBounds(frameWidth/2 - userInputWidth/2, frameHeight - userInputHeight, userInputWidth, userInputHeight);
        userInput.addActionListener(_ -> setTempText(userInput, tempText));
        layeredPane.add(userInput);
        layeredPane.setLayer(userInput, 2);

        JLabel errorTextLabel = new JLabel();
        errorTextLabel.setName("error");
        errorTextLabel.setBorder(new LineBorder(Color.lightGray));
        errorTextLabel.setBackground(Color.black);
        errorTextLabel.setForeground(Color.white);
        errorTextLabel.setOpaque(true);
        int errorTextLabelWidth = 600;
        int errorTextLabelHeight = 50;
        errorTextLabel.setBounds(frameWidth/2 - errorTextLabelWidth/2, frameHeight - errorTextLabelHeight - userInputHeight, errorTextLabelWidth, errorTextLabelHeight);
        layeredPane.add(errorTextLabel);
        layeredPane.setLayer(errorTextLabel, 2);

        JLabel mainTextLabel = new JLabel();
        mainTextLabel.setName("main");
        mainTextLabel.setBorder(new LineBorder(Color.lightGray));
        mainTextLabel.setBackground(Color.black);
        mainTextLabel.setForeground(Color.white);
        mainTextLabel.setOpaque(true);
        int mainTextLabelHeight = 300;
        int mainTextLabelWidth = 600;
        mainTextLabel.setBounds(frameWidth/2 - mainTextLabelWidth/2, frameHeight - mainTextLabelHeight - errorTextLabelHeight - userInputHeight, mainTextLabelWidth, mainTextLabelHeight);
        layeredPane.add(mainTextLabel);
        layeredPane.setLayer(mainTextLabel, 2);


        frame.setLayeredPane(layeredPane);

        return frame;
    }
    public static void changeLabelText(JFrame frame, String newText, LabelType label) {
        newText = HTMLifyString(newText);

        String targetComponent = "";

        switch (label) {
            case STATUS -> targetComponent = "status";
            case INVENTORY -> targetComponent = "inventory";
            case RELICS -> targetComponent = "relics";
            case BATTLE -> targetComponent = "battle";
            case MAIN -> targetComponent = "main";
            case DESCRIPTION -> targetComponent = "description";
            case ERROR -> targetComponent = "error";
        }

        ((JLabel) getComponentFromFrame(frame, targetComponent)).setText(newText);

    }

    public static void appendMainLabelText(JFrame frame, String addedText) {
        JLabel mainLabel = (JLabel) getComponentFromFrame(frame, "main");
        String oldText = mainLabel.getText().replaceAll("</html>", "");
        addedText = HTMLifyString(addedText).replaceAll("<html>", "");
        String newText = oldText + "<br>" + addedText;
        mainLabel.setText(newText);
    }

    public static void makeInventoryVisible(JFrame frame) {
        JPanel inventoryPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(1)[0];
        JPanel relicPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(3)[0];
        inventoryPanel.setVisible(true);
        relicPanel.setVisible(false);
    }

    public static void makeRelicsVisible(JFrame frame) {
        JPanel inventoryPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(1)[0];
        JPanel relicPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(3)[0];
        inventoryPanel.setVisible(false);
        relicPanel.setVisible(true);
    }

    public static String HTMLifyString(String input) {
        input = input.replaceAll("\n", "<br>");
        return "<html>" + input + "</html>";
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

    public static void setTempText(JTextField userInput, JLabel temp) {
        temp.setText(userInput.getText());
        userInput.setText("");
    }

    public static String getTempText(JFrame frame) {
        JLabel tempLabel = (JLabel) getComponentFromFrame(frame, "temp");
        String output = tempLabel.getText();
        tempLabel.setText("");
        return output;
    }

    public static void clearInventoryPanel(JFrame frame, int layer) {
        JPanel panel = (JPanel) frame.getLayeredPane().getComponentsInLayer(layer)[0];
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    public static void addInventoryButton(JFrame frame, String labelText, Player player, int itemIndex, int layer) {
        InventoryButton newButton = new InventoryButton();
        if (layer == 1) {
            newButton.addActionListener(_ -> useItem(frame, itemIndex, player));
        } else {
            newButton.addActionListener(_ -> unequipRelic(frame, itemIndex, player));
        }
        //add button and label to a new panel, force button left, somehow get wrapping going
        JPanel itemPanel = new JPanel();
        itemPanel.setOpaque(false);
        JLabel itemLabel = new JLabel();
        itemLabel.setForeground(Color.white);
        itemLabel.setOpaque(false);
        labelText = HTMLifyString(labelText);
        newButton.setText("Use");
        newButton.setHorizontalAlignment(SwingConstants.LEFT);
        itemLabel.setText(labelText);
        JPanel inventoryPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(layer)[0];
        itemPanel.setSize(inventoryPanel.getSize());
        itemLabel.setSize(inventoryPanel.getSize());
        itemPanel.add(newButton);
        itemPanel.add(itemLabel);
        inventoryPanel.add(itemPanel);
    }

    public static void useItem(JFrame frame, int itemIndex, Player player) {
        ArrayList<Item> items = player.getInventory().get(itemIndex);
        items.getFirst().useItem(frame, player);
        player.checkInventory(frame, false);
        player.checkRelics(frame, false);
    }

    public static void unequipRelic(JFrame frame, int itemIndex, Player player) {
        player.getEquippedRelics().get(itemIndex).useItem(frame, player);
        player.checkInventory(frame, false);
        player.checkRelics(frame, false);
    }
}
