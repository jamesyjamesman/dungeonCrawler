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

        Color black = Color.black;

        JLabel descriptionTextLabel = new JLabel();
        descriptionTextLabel.setName("description");
        descriptionTextLabel.setSize(300, 200);
        descriptionTextLabel.setBorder(new LineBorder(Color.lightGray));
        descriptionTextLabel.setBackground(black);
        descriptionTextLabel.setForeground(Color.white);
        descriptionTextLabel.setOpaque(true);
        layeredPane.add(descriptionTextLabel);
        layeredPane.setLayer(descriptionTextLabel, 2);

        JLabel statusTextLabel = new JLabel();
        statusTextLabel.setName("status");
        statusTextLabel.setBorder(new LineBorder(Color.lightGray));
        statusTextLabel.setBackground(black);
        statusTextLabel.setForeground(Color.white);
        statusTextLabel.setOpaque(true);
        int statusLabelWidth = 200;
        int labelHeight = 200;
        statusTextLabel.setBounds(frameWidth - statusLabelWidth, 0, statusLabelWidth, labelHeight);
        layeredPane.add(statusTextLabel);
        layeredPane.setLayer(statusTextLabel, 2);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setName("inventory");
        inventoryPanel.setBorder(new LineBorder(Color.lightGray));
        inventoryPanel.setBackground(black);
        inventoryPanel.setForeground(Color.white);
        inventoryPanel.setOpaque(true);
        int labelWidth = 600;
        labelHeight = 450;
        inventoryPanel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(inventoryPanel);
        layeredPane.setLayer(inventoryPanel, 1);

        JPanel relicPanel = new JPanel();
        relicPanel.setName("relics");
        relicPanel.setBorder(new LineBorder(Color.lightGray));
        relicPanel.setBackground(black);
        relicPanel.setForeground(Color.white);
        relicPanel.setOpaque(true);
        relicPanel.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        relicPanel.setVisible(false);
        layeredPane.add(relicPanel);
        layeredPane.setLayer(relicPanel, 3);

        JPanel inventorySwitches = new JPanel();
        inventorySwitches.setBackground(black);
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
        userInput.setBackground(black);
        userInput.setForeground(Color.white);
        userInput.setOpaque(true);
        int userInputHeight = 100;
        int userInputWidth = 600;
        userInput.setBounds(0, frameHeight - userInputHeight, userInputWidth, userInputHeight);
        userInput.addActionListener(_ -> setTempText(userInput, tempText));
        layeredPane.add(userInput);
        layeredPane.setLayer(userInput, 2);

        JLabel errorTextLabel = new JLabel();
        errorTextLabel.setName("error");
        errorTextLabel.setBorder(new LineBorder(Color.lightGray));
        errorTextLabel.setBackground(black);
        errorTextLabel.setForeground(Color.white);
        errorTextLabel.setOpaque(true);
        int errorTextLabelWidth = 600;
        int errorTextLabelHeight = 50;
        errorTextLabel.setBounds(0, frameHeight - errorTextLabelHeight - userInputHeight, errorTextLabelWidth, errorTextLabelHeight);
        layeredPane.add(errorTextLabel);
        layeredPane.setLayer(errorTextLabel, 2);

        JLabel mainTextLabel = new JLabel();
        mainTextLabel.setName("main");
        mainTextLabel.setBorder(new LineBorder(Color.lightGray));
        mainTextLabel.setBackground(black);
        mainTextLabel.setForeground(Color.white);
        mainTextLabel.setOpaque(true);
        int mainTextLabelHeight = 300;
        int mainTextLabelWidth = 600;
        mainTextLabel.setBounds(0, frameHeight - mainTextLabelHeight - errorTextLabelHeight - userInputHeight, mainTextLabelWidth, mainTextLabelHeight);
        layeredPane.add(mainTextLabel);
        layeredPane.setLayer(mainTextLabel, 2);

        JPanel yesOrNo = new JPanel();
        yesOrNo.setOpaque(false);
        yesOrNo.setBounds(0, frameHeight - errorTextLabelHeight - userInputHeight - 25, mainTextLabelWidth, 25);
        yesOrNo.setVisible(false);

        JLabel questionText = new JLabel();
        questionText.setVisible(false);
        layeredPane.add(questionText);
        layeredPane.setLayer(questionText, 12);

        InventoryButton yes = new InventoryButton();
        yes.setText("Yes");
        yes.addActionListener(_ -> questionText.setText("yes"));
        yesOrNo.add(yes);

        InventoryButton no = new InventoryButton();
        no.setText("No");
        no.addActionListener(_ -> questionText.setText("no"));
        yesOrNo.add(no);

        layeredPane.add(yesOrNo);
        layeredPane.setLayer(yesOrNo, 11);


        JPanel healthPanel = new JPanel();
        healthPanel.setName("health");
        healthPanel.setBackground(Color.black);
        healthPanel.setForeground(Color.white);
        healthPanel.setOpaque(true);
        healthPanel.setBorder(new LineBorder(Color.lightGray));
        int healthLabelHeight = 200;
        int healthLabelWidth = 300;
        healthPanel.setBounds(frameWidth - healthLabelWidth - statusLabelWidth, 0, healthLabelWidth, healthLabelHeight);
        layeredPane.add(healthPanel);
        layeredPane.setLayer(healthPanel, 67);

        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);
        return frame;
    }

    //TODO: find a way to stop labels from grouping on the same line, same for inventory AND figure out wrapping
    public static void addHealthText(JFrame frame, String newText) {
        newText = HTMLifyString(newText);
        JPanel healthPanel = (JPanel) frame.getLayeredPane().getComponentsInLayer(67)[0];
        JLabel newLabel = new JLabel();
        newLabel.setForeground(Color.white);
        newLabel.setText(newText);
        healthPanel.add(newLabel);
        removeHealthText(healthPanel);
        changeHealthTextColor(healthPanel);
    }
    public static void removeHealthText(JPanel healthPanel) {
        if (healthPanel.getComponents().length > 6) {
            healthPanel.remove(healthPanel.getComponent(0));
            healthPanel.revalidate();
            healthPanel.repaint();
        }
    }

    //the FIRST should be white, but they start dark and get brighter
    //I'm not sure how to fix this easily
    public static void changeHealthTextColor(JPanel healthPanel) {
        for (int i = 0; i < healthPanel.getComponents().length; i++) {
            JLabel textLabel = (JLabel) healthPanel.getComponent(i);
            switch (i) {
                case 5, 4, 3 -> textLabel.setForeground(Color.white);
                case 2 -> textLabel.setForeground(Color.lightGray);
                case 1 -> textLabel.setForeground(Color.gray);
                case 0 -> textLabel.setForeground(Color.darkGray);
            }
        }
    }

        public static void changeAnswerVisibility(JFrame frame, boolean visible) {
        frame.getLayeredPane().getComponentsInLayer(11)[0].setVisible(visible);
    }

    public static String getAnswerText(JFrame frame) {
        JLabel answerLabel = (JLabel) frame.getLayeredPane().getComponentsInLayer(12)[0];
        String output = answerLabel.getText();
        answerLabel.setText("");
        return output;
    }
    public static void changeLabelText(JFrame frame, String newText, LabelType labelType) {
        newText = HTMLifyString(newText);

        String targetComponent = "";

        switch (labelType) {
            case STATUS -> targetComponent = "status";
            case INVENTORY -> targetComponent = "inventory";
            case RELICS -> targetComponent = "relics";
            case BATTLE -> targetComponent = "battle";
            case MAIN -> targetComponent = "main";
            case DESCRIPTION -> targetComponent = "description";
            case ERROR -> targetComponent = "error";
        }
        JLabel label = (JLabel) getComponentFromFrame(frame, targetComponent);
        label.setText(newText);
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
    //TODO: make cursed relics a different color (purple) if you have the relic equipped (or if the relic is equipped)
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
        itemLabel.setMaximumSize(new Dimension(100, 300));
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
