package main;

import main.item.Item;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        JTextPane inventoryPane = new JTextPane();
        inventoryPane.setName("inventory");
        inventoryPane.setEditable(false);
        inventoryPane.setBorder(new LineBorder(Color.lightGray));
        inventoryPane.setBackground(black);
        inventoryPane.setForeground(Color.white);
        inventoryPane.setOpaque(true);
        int labelWidth = 400;
        labelHeight = 450;
        inventoryPane.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        layeredPane.add(inventoryPane);
        layeredPane.setLayer(inventoryPane, 1);

        JTextPane relicPane = new JTextPane();
        relicPane.setName("relics");
        relicPane.setEditable(false);
        relicPane.setBorder(new LineBorder(Color.lightGray));
        relicPane.setBackground(black);
        relicPane.setForeground(Color.white);
        relicPane.setOpaque(true);
        relicPane.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        relicPane.setVisible(false);
        layeredPane.add(relicPane);
        layeredPane.setLayer(relicPane, 3);

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


        JTextPane healthPane = new JTextPane();
        healthPane.setName("health");
        healthPane.setBackground(Color.black);
        healthPane.setForeground(Color.white);
        healthPane.setOpaque(true);
        healthPane.setBorder(new LineBorder(Color.lightGray));
        int healthLabelHeight = 200;
        int healthLabelWidth = 300;
        healthPane.setBounds(frameWidth - healthLabelWidth - statusLabelWidth, 0, healthLabelWidth, healthLabelHeight);
        layeredPane.add(healthPane);
        layeredPane.setLayer(healthPane, 67);

        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);
        return frame;
    }

    public static void addHealthText(JFrame frame, String newText) {
        newText = newText + "\n";
        JTextPane healthPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(67)[0];
        Document doc = healthPane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            doc.insertString(doc.getLength(), newText, attributeSet);
            HealthLineRenderer(healthPane);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void HealthLineRenderer(JTextPane healthPane) throws BadLocationException {
        Document doc = healthPane.getDocument();
            String text = doc.getText(0, doc.getLength());
            ArrayList<String> textLines = new ArrayList<>(List.of(text.split("\n")));

            if (textLines.size() > 6) {
                textLines.removeFirst();
            }
            doc.remove(0, doc.getLength());
            for (int i = 0; i < textLines.size(); i++) {
                Style style = getHealthTextStyle(healthPane, i + 6 - textLines.size());
                doc.insertString(doc.getLength(), textLines.get(i) + "\n", style);
            }
    }

    public static Style getHealthTextStyle(JTextPane healthPane, int line) {
        Style style = healthPane.addStyle("style", null);
        StyleConstants.setBold(style, true);
        Color color = Color.white;
        switch (line) {
            case 3, 4, 5:
                break;
            case 2:
                color = Color.lightGray;
                break;
            case 1:
                color = Color.gray;
                break;
            case 0:
                color = Color.darkGray;
                break;
        }
        StyleConstants.setForeground(style, color);
        return style;
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
            case MAIN -> targetComponent = "main";
            case DESCRIPTION -> targetComponent = "description";
            case ERROR -> targetComponent = "error";
        }
        JLabel label = (JLabel) getComponentFromFrame(frame, targetComponent);
        label.setText(newText);
    }

    public static void appendMainLabelText(JFrame frame, String addedText) {
        JLabel mainLabel = (JLabel) getComponentFromFrame(frame, "main");
        String newText = combineHTMLStrings(mainLabel.getText(), HTMLifyString(addedText));
        mainLabel.setText(newText);
    }

    public static String combineHTMLStrings(String oldText, String addedText) {
        oldText = oldText.replaceAll("</html>", "");
        addedText = addedText.replaceAll("<html>", "");
        return oldText + "<br>" + addedText;
    }

    public static void makeInventoryVisible(JFrame frame) {
        JTextPane inventoryPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(1)[0];
        JTextPane relicPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(3)[0];
        inventoryPane.setVisible(true);
        relicPane.setVisible(false);
    }

    public static void makeRelicsVisible(JFrame frame) {
        JTextPane inventoryPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(1)[0];
        JTextPane relicPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(3)[0];
        inventoryPane.setVisible(false);
        relicPane.setVisible(true);
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

    public static void clearInventoryPane(JFrame frame, int layer) {
        JTextPane pane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(layer)[0];
        Document doc = pane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        pane.removeAll();
        pane.revalidate();
        pane.repaint();
    }
    //TODO: make cursed relics a different color (purple) if you have the relic equipped (or if the relic is equipped)
    public static void addInventoryButton(JFrame frame, String newItemText, Player player, int itemIndex, int layer) {
        InventoryButton newButton = new InventoryButton();
        if (layer == 1) {
            newButton.addActionListener(_ -> useItem(frame, itemIndex, player));
        } else {
            newButton.addActionListener(_ -> unequipRelic(frame, itemIndex, player));
        }
        //add button and label to a new panel, force button left, somehow get wrapping going
        newButton.setText("Use");
        newButton.setHorizontalAlignment(SwingConstants.LEFT);

        JTextPane inventoryPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(layer)[0];
        Document doc = inventoryPane.getStyledDocument();
        inventoryPane.setCaretPosition(doc.getLength());
        inventoryPane.insertComponent(newButton);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            doc.insertString(doc.getLength(), newItemText, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
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
