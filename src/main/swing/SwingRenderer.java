package main.swing;

import main.Player;
import main.item.Item;
import main.item.PureAppleItem;
import main.item.relic.Relic;
import main.room.PureWaterRoom;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwingRenderer extends JFrame {

    public static JFrame componentFactory() {
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("default_background.png"));

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
        backgroundImageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundImageLabel.setName("background");
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundImageLabel);
        layeredPane.setLayer(backgroundImageLabel, -5);

        DungeonLabel descriptionTextLabel = new DungeonLabel();
        descriptionTextLabel.setName("description");
        layeredPane.add(descriptionTextLabel);
        layeredPane.setLayer(descriptionTextLabel, 2);
        ShadowLabel descriptionShadow = new ShadowLabel(layeredPane);

        DungeonLabel statusTextLabel = new DungeonLabel();
        statusTextLabel.setName("status");
        layeredPane.add(statusTextLabel);
        layeredPane.setLayer(statusTextLabel, 2);
        ShadowLabel statusShadow = new ShadowLabel(layeredPane);

        DungeonTextPane inventoryPane = new DungeonTextPane();
        inventoryPane.setName("inventory");
        layeredPane.add(inventoryPane);
        layeredPane.setLayer(inventoryPane, 1);
        ShadowLabel inventoryShadow = new ShadowLabel(layeredPane);

        DungeonTextPane relicPane = new DungeonTextPane();
        relicPane.setName("relics");
        relicPane.setVisible(false);
        layeredPane.add(relicPane);
        layeredPane.setLayer(relicPane, 3);

        DungeonPanel inventorySwitches = new DungeonPanel();
        inventorySwitches.setBackground(Color.black);
        inventorySwitches.setForeground(Color.white);
        inventorySwitches.setOpaque(false);
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
        userInput.setBorder(new LineBorder(new Color(0,0,0,0)));
        userInput.setForeground(Color.white);
        userInput.setOpaque(false);
        userInput.setCaretColor(Color.white);
        userInput.addActionListener(_ -> setTempText(userInput, tempText));
        userInput.requestFocusInWindow();
        layeredPane.add(userInput);
        layeredPane.setLayer(userInput, 2);
        ShadowLabel userInputShadow = new ShadowLabel(layeredPane);

        DungeonLabel errorTextLabel = new DungeonLabel();
        errorTextLabel.setName("error");
        layeredPane.add(errorTextLabel);
        layeredPane.setLayer(errorTextLabel, 2);
        ShadowLabel errorShadow = new ShadowLabel(layeredPane);

        DungeonLabel mainTextLabel = new DungeonLabel();
        mainTextLabel.setName("main");
        layeredPane.add(mainTextLabel);
        layeredPane.setLayer(mainTextLabel, 2);
        ShadowLabel mainShadow = new ShadowLabel(layeredPane);

        JPanel yesOrNo = new JPanel();
        yesOrNo.setOpaque(false);
        yesOrNo.setVisible(false);

        InventoryButton yes = new InventoryButton();
        yes.setText("Yes");
        yes.addActionListener(_ -> tempText.setText("yes"));
        yesOrNo.add(yes);

        InventoryButton no = new InventoryButton();
        no.setText("No");
        no.addActionListener(_ -> tempText.setText("no"));
        yesOrNo.add(no);

        layeredPane.add(yesOrNo);
        layeredPane.setLayer(yesOrNo, 11);

        DungeonTextPane healthPane = new DungeonTextPane();
        healthPane.setName("health");
        layeredPane.add(healthPane);
        layeredPane.setLayer(healthPane, 67);
        ShadowLabel healthShadow = new ShadowLabel(layeredPane);

        DungeonPanel popupPanel = new DungeonPanel();
        popupPanel.setName("popup");
        popupPanel.setVisible(false);
        layeredPane.add(popupPanel);
        layeredPane.setLayer(popupPanel, 80);

        InventoryButton popupButton = new InventoryButton();
        popupButton.setText("Close");

        layeredPane.add(popupButton);
        layeredPane.setLayer(popupButton, 81);

        DungeonTextPane popupPane = new DungeonTextPane();
        layeredPane.add(popupPane);
        layeredPane.setLayer(popupPane, 81);

        ShadowLabel popupShadow = new ShadowLabel(layeredPane);
        layeredPane.setLayer(popupShadow, 79);
        popupShadow.setVisible(false);

        popupButton.addActionListener(_ -> hidePopup(popupShadow, popupPanel));

        popupPanel.add(popupPane);
        popupPanel.add(popupButton);

        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                renderer(frame, backgroundImage, backgroundImageLabel, descriptionTextLabel, descriptionShadow, statusTextLabel, statusShadow, inventoryPane, inventoryShadow, relicPane, inventorySwitches, userInput, userInputShadow, errorTextLabel, errorShadow, mainTextLabel, mainShadow, yesOrNo, healthPane, healthShadow, popupPanel, popupPane, popupButton, popupShadow);
            }
        });
        renderer(frame, backgroundImage, backgroundImageLabel, descriptionTextLabel, descriptionShadow, statusTextLabel, statusShadow, inventoryPane, inventoryShadow, relicPane, inventorySwitches, userInput, userInputShadow, errorTextLabel, errorShadow, mainTextLabel, mainShadow, yesOrNo, healthPane, healthShadow, popupPanel, popupPane, popupButton, popupShadow);
        return frame;
    }

    public static void renderer(JFrame frame, Icon backgroundImage, JLabel background, JLabel description, JLabel descriptionShadow, JLabel status, JLabel statusShadow, JTextPane inventory, JLabel inventoryShadow, JTextPane relics, JPanel invSwitches, JTextField input, JLabel inputShadow, JLabel error, JLabel errorShadow, JLabel main, JLabel mainShadow, JPanel yesOrNo, JTextPane health, JLabel healthShadow, JPanel popupPanel, JTextPane popupPane, JButton popupButton, JLabel popupShadow) {
        int imageWidth = backgroundImage.getIconWidth();
        int imageHeight = backgroundImage.getIconHeight();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        background.setSize(imageWidth, imageHeight);

        description.setSize(300, 200);
        descriptionShadow.setSize(300, 200);

        int statusLabelWidth = 200;
        int labelHeight = 200;
        status.setBounds(frameWidth - statusLabelWidth, 0, statusLabelWidth, labelHeight);
        statusShadow.setBounds(frameWidth - statusLabelWidth, 0, statusLabelWidth, labelHeight);

        int labelWidth = 400;
        labelHeight = 450;
        inventory.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);
        inventoryShadow.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);

        relics.setBounds(frameWidth - labelWidth, frameHeight - labelHeight, labelWidth, labelHeight);

        invSwitches.setBounds(frameWidth - labelWidth, frameHeight - labelHeight - 25, labelWidth, 25);

        int userInputHeight = 100;
        int userInputWidth = 600;
        input.setBounds(0, frameHeight - userInputHeight, userInputWidth, userInputHeight);
        inputShadow.setBounds(0, frameHeight - userInputHeight, userInputWidth, userInputHeight);

        int errorTextLabelWidth = 600;
        int errorTextLabelHeight = 50;
        error.setBounds(0, frameHeight - errorTextLabelHeight - userInputHeight - 25, errorTextLabelWidth, errorTextLabelHeight);
        errorShadow.setBounds(0, frameHeight - errorTextLabelHeight - userInputHeight - 25, errorTextLabelWidth, errorTextLabelHeight);

        int mainTextLabelHeight = 300;
        int mainTextLabelWidth = 600;
        main.setBounds(0, frameHeight - mainTextLabelHeight - errorTextLabelHeight - userInputHeight - 25, mainTextLabelWidth, mainTextLabelHeight);
        mainShadow.setBounds(0, frameHeight - mainTextLabelHeight - errorTextLabelHeight - userInputHeight - 25, mainTextLabelWidth, mainTextLabelHeight);

        yesOrNo.setBounds(0, frameHeight - errorTextLabelHeight - userInputHeight - 50, mainTextLabelWidth, 25);

        int healthLabelHeight = 200;
        int healthLabelWidth = 300;
        health.setBounds(frameWidth - healthLabelWidth - statusLabelWidth, 0, healthLabelWidth, healthLabelHeight);
        healthShadow.setBounds(frameWidth - healthLabelWidth - statusLabelWidth, 0, healthLabelWidth, healthLabelHeight);

        int popupPanelWidth = 500;
        int popupPanelHeight = 400;
        popupPanel.setBounds(frameWidth/2 - popupPanelWidth/2, frameHeight/2 - popupPanelHeight/2, popupPanelWidth, popupPanelHeight);
        popupShadow.setBounds(0, 0, frameWidth, frameHeight);
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

            while (textLines.size() > 6) {
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
        setInputFocus(frame);
        inventoryPane.setVisible(true);
        relicPane.setVisible(false);
    }

    public static void makeRelicsVisible(JFrame frame) {
        JTextPane inventoryPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(1)[0];
        JTextPane relicPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(3)[0];
        setInputFocus(frame);
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
        setInputFocus(frame);
        return output;
    }

    //if called after a button is pressed, the button click animation does not function properly
    public static void setInputFocus(JFrame frame) {
         Arrays.stream(frame.getLayeredPane().getComponentsInLayer(2))
                 .filter(component -> component.getName().equals("input"))
                 .forEach(Component::requestFocusInWindow);
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

    public static void addInventoryLabel(JFrame frame, String newItemText, Player player, int itemIndex, int layer, Color color) {
        InventoryButton useButton = new InventoryButton();
        InventoryButton dropButton = new InventoryButton();
        if (player.getCurrentRoom() instanceof PureWaterRoom pureRoom && !pureRoom.getFountainUsed()) {
            useButton.setText("Cleanse");
            if (layer == 1) {
                useButton.addActionListener(_ -> cleanseItem(frame, itemIndex, player));
            } else {
                useButton.addActionListener(_ -> cleanseRelic(frame, itemIndex, player));
            }
        } else if (layer == 1) {
            useButton.addActionListener(_ -> useItem(frame, itemIndex, player));
            dropButton.addActionListener(_ -> player.discardItem(frame, player.getInventory().get(itemIndex).getFirst()));
            if (player.getInventory().get(itemIndex).getFirst() instanceof Relic) {
                useButton.setText("Equip");
            } else {
                useButton.setText("Use");
            }
        } else {
            useButton.addActionListener(_ -> unequipRelic(frame, itemIndex, player));
            useButton.setText("Unequip");
        }
        useButton.setHorizontalAlignment(SwingConstants.LEFT);
        dropButton.setHorizontalAlignment(SwingConstants.LEFT);
        dropButton.setText("Drop");

        JTextPane inventoryPane = (JTextPane) frame.getLayeredPane().getComponentsInLayer(layer)[0];
        Document doc = inventoryPane.getStyledDocument();
        inventoryPane.setCaretPosition(doc.getLength());
        inventoryPane.insertComponent(useButton);
        if (layer == 1) {
            inventoryPane.setCaretPosition(doc.getLength());
            inventoryPane.insertComponent(dropButton);
        }
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        StyleConstants.setForeground(attributeSet, color);
        try {
            doc.insertString(doc.getLength(), newItemText, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanseItem(JFrame frame, int itemIndex, Player player) {
        Item item = player.getInventory().get(itemIndex).getFirst();
        if (item instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            changeLabelText(frame, "The " + relic.getName() + " was cured!", LabelType.ERROR);
        } else if (item.getName().equals("Apple")) {
            player.discardItem(frame, item);
            player.addItemToInventory(new PureAppleItem());
            changeLabelText(frame, "The apple was purified!", LabelType.ERROR);
        } else {
            changeLabelText(frame, "You put the " + item.getName() + " in the fountain, but nothing happened.", LabelType.ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        player.checkInventory(frame);
        appendMainLabelText(frame, "The fountain ran dry!");
    }

    public static void cleanseRelic(JFrame frame, int itemIndex, Player player) {
        Relic relic = player.getEquippedRelics().get(itemIndex);
        if (relic.isCursed()) {
            relic.setCursed(false);
            changeLabelText(frame, "The " + relic.getName() + " was cured!", LabelType.ERROR);
        } else {
            changeLabelText(frame, "That relic wasn't cursed...", LabelType.ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        player.checkRelics(frame);
        appendMainLabelText(frame, "The fountain ran dry!");
    }

    public static void useItem(JFrame frame, int itemIndex, Player player) {
        ArrayList<Item> items = player.getInventory().get(itemIndex);
        items.getFirst().useItem(frame, player);
        player.checkInventory(frame);
        player.checkRelics(frame);
        setInputFocus(frame);
    }

    public static void unequipRelic(JFrame frame, int itemIndex, Player player) {
        player.getEquippedRelics().get(itemIndex).useItem(frame, player);
        player.checkInventory(frame);
        player.checkRelics(frame);
        setInputFocus(frame);
    }

    public static void changeBackgroundImage(JFrame frame, String fileName) {
        ((JLabel) frame.getLayeredPane().getComponentsInLayer(-5)[0]).setIcon(new ImageIcon(ClassLoader.getSystemResource(fileName)));
    }

    public static void createPopup(JFrame frame, String popupText) {
        JPanel panel = (JPanel) frame.getLayeredPane().getComponentsInLayer(80)[0];
        JLabel shadow = (JLabel) frame.getLayeredPane().getComponentsInLayer(79)[0];
        JTextPane pane = (JTextPane) panel.getComponent(0);
        Document doc = pane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, popupText, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        panel.setVisible(true);
        shadow.setVisible(true);
    }

    public static void hidePopup(JLabel shadow, JPanel panel) {
        panel.setVisible(false);
        shadow.setVisible(false);
    }
}
