package main.swing;

import main.App;
import main.Player;
import main.enemy.Enemy;
import main.item.Item;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.item.weapon.Weapon;
import main.room.PureWaterRoom;
import main.room.ShopRoom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SwingRenderer extends JFrame {

    public static JFrame componentFactory() {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        JFrame frame = new JFrame();
        frame.setBackground(Color.black);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setTitle("Dungeon Crawler");
        frame.setBounds(0, 0, screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(ClassLoader.getSystemResource("torch.png")).getImage());

        JLayeredPane layeredPane = new JLayeredPane();

        // Background
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("default_background.png"));
        JLabel backgroundImageLabel = new JLabel(resizeBackground(backgroundImage, frame.getWidth()));
        backgroundImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundImageLabel.setName("background");
        layeredPane.add(backgroundImageLabel);
        layeredPane.setLayer(backgroundImageLabel, -5);

        // Description pane
        DungeonLabel descriptionTextLabel = new DungeonLabel();
        descriptionTextLabel.setName("description");
        layeredPane.add(descriptionTextLabel);
        layeredPane.setLayer(descriptionTextLabel, 2);
        ShadowLabel descriptionShadow = new ShadowLabel(layeredPane);
        descriptionShadow.setName("descriptionShadow");

        // Status pane
        DungeonLabel statusTextLabel = new DungeonLabel();
        statusTextLabel.setName("status");
        layeredPane.add(statusTextLabel);
        layeredPane.setLayer(statusTextLabel, 2);
        ShadowLabel statusShadow = new ShadowLabel(layeredPane);
        statusShadow.setName("statusShadow");

        // Inventory pane
        DungeonTextPane inventoryPane = new DungeonTextPane();
        inventoryPane.setName("inventory");
        layeredPane.add(inventoryPane);
        layeredPane.setLayer(inventoryPane, 1);
        ShadowLabel inventoryShadow = new ShadowLabel(layeredPane);
        inventoryShadow.setName("inventoryShadow");

        // Relics pane
        DungeonTextPane relicPane = new DungeonTextPane();
        relicPane.setName("relics");
        relicPane.setVisible(false);
        layeredPane.add(relicPane);
        layeredPane.setLayer(relicPane, 3);

        // Inventory toggle controls
        DungeonPanel inventorySwitches = new DungeonPanel();
        inventorySwitches.setBackground(Color.black);
        inventorySwitches.setForeground(Color.white);
        inventorySwitches.setOpaque(false);
        inventorySwitches.setName("inventorySwitches");
        layeredPane.add(inventorySwitches);
        layeredPane.setLayer(inventorySwitches, 78);

        InventoryButton switchInventory = new InventoryButton();
        switchInventory.addActionListener(_ -> makeInventoryVisible());
        switchInventory.setName("switchInventory");
        switchInventory.setText("Inventory");
        inventorySwitches.add(switchInventory);

        InventoryButton switchRelics = new InventoryButton();
        switchRelics.addActionListener(_ -> makeRelicsVisible());
        switchRelics.setText("Relics");
        switchRelics.setName("switchRelics");
        inventorySwitches.add(switchRelics);

        // Temporary text panel
        JLabel tempText = new JLabel();
        tempText.setName("temp");
        tempText.setVisible(false);
        layeredPane.add(tempText);
        layeredPane.setLayer(tempText, 2);

        // User input pane
        JTextField userInput = new JTextField();
        userInput.setName("input");
        userInput.setBorder(new EmptyBorder(8, 8, 8, 8)); //top,left,bottom,right
        userInput.setForeground(Color.white);
        userInput.setOpaque(false);
        userInput.setCaretColor(Color.white);
        userInput.addActionListener(_ -> setTempText(userInput, tempText));
        layeredPane.add(userInput);
        layeredPane.setLayer(userInput, 2);
        ShadowLabel userInputShadow = new ShadowLabel(layeredPane);
        userInputShadow.setName("userInputShadow");

        // Error pane
        DungeonLabel errorTextLabel = new DungeonLabel();
        errorTextLabel.setName("error");
        layeredPane.add(errorTextLabel);
        layeredPane.setLayer(errorTextLabel, 2);
        ShadowLabel errorShadow = new ShadowLabel(layeredPane);
        errorShadow.setName("errorShadow");

        // Main pane
        DungeonTextPane mainTextPane = new DungeonTextPane();
        mainTextPane.setName("main");
        layeredPane.add(mainTextPane);
        layeredPane.setLayer(mainTextPane, 77);
        ShadowLabel mainShadow = new ShadowLabel(layeredPane);
        mainShadow.setName("mainShadow");

        // Yes/no panel
        JPanel yesOrNo = new JPanel();
        yesOrNo.setName("yesOrNo");
        yesOrNo.setOpaque(false);
        yesOrNo.setVisible(false);

        InventoryButton yes = new InventoryButton();
        yes.setName("yes");
        yes.setText("Yes");
        yes.addActionListener(_ -> tempText.setText("yes"));
        yesOrNo.add(yes);

        InventoryButton no = new InventoryButton();
        no.setName("no");
        no.setText("No");
        no.addActionListener(_ -> tempText.setText("no"));
        yesOrNo.add(no);

        layeredPane.add(yesOrNo);
        layeredPane.setLayer(yesOrNo, 83);

        // Health pane
        DungeonTextPane healthPane = new DungeonTextPane();
        healthPane.setName("health");
        layeredPane.add(healthPane);
        layeredPane.setLayer(healthPane, 67);
        ShadowLabel healthShadow = new ShadowLabel(layeredPane);
        healthShadow.setName("healthShadow");

        // Popup panel
        DungeonPanel popupPanel = new DungeonPanel();
        popupPanel.setName("popup");
        popupPanel.setVisible(false);
        layeredPane.add(popupPanel);
        layeredPane.setLayer(popupPanel, 80);

        InventoryButton popupButton = new InventoryButton();
        popupButton.setName("popupButton");
        popupButton.setText("Close");

        layeredPane.add(popupButton);
        layeredPane.setLayer(popupButton, 81);

        DungeonTextPane popupPane = new DungeonTextPane();
        popupPane.setName("popupPane");
        layeredPane.add(popupPane);
        layeredPane.setLayer(popupPane, 81);

        ShadowLabel popupShadow = new ShadowLabel(layeredPane);
        popupShadow.setName("popupShadow");
        layeredPane.setLayer(popupShadow, 79);
        popupShadow.setVisible(false);

        popupButton.addActionListener(_ -> hidePopup(popupShadow, popupPanel));
        popupPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    hidePopup(popupShadow, popupPanel);
                    setInputFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        popupPanel.add(popupPane);
        popupPanel.add(popupButton);

        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                renderer(frame);
            }
        });

        return frame;
    }

    public static void renderer(JFrame frame) {
        int frameHeight = frame.getHeight() - 37;
        int frameWidth = frame.getWidth();

        int boxPad = 8;

        JLabel background = (JLabel) componentGrabber(ComponentType.LABEL_IMAGE_BACKGROUND);
        background.setSize(frameWidth, frameHeight);

        Component description = componentGrabber(ComponentType.LABEL_DESCRIPTION);
        description.setBounds(boxPad, boxPad, 300, 200);

        Component descriptionShadow = componentGrabber(ComponentType.SHADOW_DESCRIPTION);
        descriptionShadow.setBounds(boxPad, boxPad, 300, 200);

        int statusLabelWidth = 200;
        int statusLabelHeight = 200;

        Component status = componentGrabber(ComponentType.LABEL_STATUS);
        status.setBounds(frameWidth - statusLabelWidth - boxPad, boxPad, statusLabelWidth, statusLabelHeight);

        Component statusShadow = componentGrabber(ComponentType.SHADOW_STATUS);
        statusShadow.setBounds(frameWidth - statusLabelWidth - boxPad, boxPad, statusLabelWidth, statusLabelHeight);

        int invWidth = 400;
        int invHeight = 458;
        int invCtrlHeight = 25;

        Component inventory = componentGrabber(ComponentType.PANE_INVENTORY);
        inventory.setBounds(frameWidth - invWidth - boxPad, frameHeight - invHeight - boxPad, invWidth, invHeight);

        Component inventoryShadow = componentGrabber(ComponentType.SHADOW_INVENTORY);
        inventoryShadow.setBounds(frameWidth - invWidth - boxPad, frameHeight - invHeight - boxPad, invWidth, invHeight);

        Component relics = componentGrabber(ComponentType.PANE_RELIC);
        relics.setBounds(frameWidth - invWidth - boxPad, frameHeight - invHeight - boxPad, invWidth, invHeight);

        Component invSwitches = componentGrabber(ComponentType.PANEL_INVENTORY_SWITCH);
        invSwitches.setBounds(frameWidth - invWidth - boxPad, frameHeight - invHeight - invCtrlHeight - boxPad, invWidth, invCtrlHeight);

        int userInputWidth = 600;
        int userInputHeight = 32;

        Component input = componentGrabber(ComponentType.TEXTFIELD_INPUT);
        input.setBounds(boxPad, frameHeight - userInputHeight - boxPad, userInputWidth, userInputHeight);

        Component inputShadow = componentGrabber(ComponentType.SHADOW_INPUT);
        inputShadow.setBounds(boxPad, frameHeight - userInputHeight - boxPad, userInputWidth, userInputHeight);

        int errorWidth = 600;
        int errorHeight = 32;

        Component error = componentGrabber(ComponentType.LABEL_ERROR);
        error.setBounds(boxPad, frameHeight - errorHeight - userInputHeight - boxPad, errorWidth, errorHeight);

        Component errorShadow = componentGrabber(ComponentType.SHADOW_ERROR);
        errorShadow.setBounds(boxPad, frameHeight - errorHeight - userInputHeight - boxPad, errorWidth, errorHeight);

        int mainWidth = 600;
        int mainHeight = 300;

        Component main = componentGrabber(ComponentType.PANE_MAIN);
        main.setBounds(boxPad, frameHeight - mainHeight - errorHeight - userInputHeight - boxPad * 2, mainWidth, mainHeight);

        Component mainShadow = componentGrabber(ComponentType.SHADOW_MAIN);
        mainShadow.setBounds(boxPad, frameHeight - mainHeight - errorHeight - userInputHeight - boxPad * 2, mainWidth, mainHeight);

        Component yesOrNo = componentGrabber(ComponentType.PANEL_YES_OR_NO);
        yesOrNo.setBounds(boxPad, frameHeight - errorHeight - userInputHeight - 50, mainWidth, 25);

        int healthWidth = 300;
        int healthHeight = 200;

        Component health = componentGrabber(ComponentType.PANE_HEALTH);
        health.setBounds(frameWidth - healthWidth - statusLabelWidth - boxPad, boxPad, healthWidth, healthHeight);

        Component healthShadow = componentGrabber(ComponentType.SHADOW_HEALTH);
        healthShadow.setBounds(frameWidth - healthWidth - statusLabelWidth - boxPad, boxPad, healthWidth, healthHeight);

        int popupPanelWidth = 500;
        int popupPanelHeight = 400;

        Component popupPanel = componentGrabber(ComponentType.PANEL_POPUP);
        popupPanel.setBounds(frameWidth/2 - popupPanelWidth/2, frameHeight/2 - popupPanelHeight/2, popupPanelWidth, popupPanelHeight);

        Component popupShadow = componentGrabber(ComponentType.SHADOW_POPUP);
        popupShadow.setBounds(0, 0, frameWidth, frameHeight);
    }

    public static Component componentGrabber(ComponentType type) {
        return switch (type) {
            case PANE_MAIN -> getComponentByName("main");
            case SHADOW_MAIN -> getComponentByName("mainShadow");
            case BUTTON_NO -> getComponentByName("no");
            case BUTTON_YES -> getComponentByName("yes");
            case LABEL_TEMP -> getComponentByName("temp");
            case PANE_POPUP -> getComponentByName("popupPane");
            case PANE_RELIC -> getComponentByName("relics");
            case LABEL_ERROR -> getComponentByName("error");
            case PANE_HEALTH -> getComponentByName("health");
            case PANEL_POPUP -> getComponentByName("popup");
            case BUTTON_POPUP -> getComponentByName("popupButton");
            case BUTTON_RELIC -> getComponentByName("switchRelics");
            case LABEL_STATUS -> getComponentByName("status");
            case SHADOW_ERROR -> getComponentByName("errorShadow");
            case SHADOW_INPUT -> getComponentByName("userInputShadow");
            case SHADOW_POPUP -> getComponentByName("popupShadow");
            case SHADOW_HEALTH -> getComponentByName("healthShadow");
            case SHADOW_STATUS -> getComponentByName("statusShadow");
            case PANE_INVENTORY -> getComponentByName("inventory");
            case TEXTFIELD_INPUT -> getComponentByName("input");
            case BUTTON_INVENTORY -> getComponentByName("switchInventory");
            case SHADOW_INVENTORY -> getComponentByName("inventoryShadow");
            case LABEL_DESCRIPTION -> getComponentByName("description");
            case SHADOW_DESCRIPTION -> getComponentByName("descriptionShadow");
            case PANEL_YES_OR_NO -> getComponentByName("yesOrNo");
            case LABEL_IMAGE_BACKGROUND -> getComponentByName("background");
            case PANEL_INVENTORY_SWITCH -> getComponentByName("inventorySwitches");
        };
    }

    public static Component getComponentByName(String name) {
        JFrame frame = App.INSTANCE.getFrame();
        return Arrays.stream(frame.getLayeredPane().getComponents())
                .filter(component -> component.getName().equals(name))
                .toList()
                .getFirst();
    }

    public static void UIUpdater(Player player) {
        player.checkInventory();
        player.checkRelics();
        player.checkStatus();
        setInputFocus();
    }

    public static void addHealthText(String newText) {
        newText = newText + "\n";
        JTextPane healthPane = (JTextPane) componentGrabber(ComponentType.PANE_HEALTH);
        Document doc = healthPane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            doc.insertString(doc.getLength(), newText, attributeSet);
            healthLineRenderer(healthPane);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void healthLineRenderer(JTextPane healthPane) throws BadLocationException {
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

        public static void changeAnswerVisibility(boolean visible) {
        componentGrabber(ComponentType.PANEL_YES_OR_NO).setVisible(visible);
    }

    public static void appendLabelText(String newText, boolean clear, ComponentType componentType) {
        JLabel label = (JLabel) componentGrabber(componentType);
        if (!clear) {
            String oldText = unHTMLifyString(label.getText());
            newText = oldText + newText;
        }
        newText = HTMLifyString(newText);

        label.setText(newText);
    }

    public static void setTempText(String text) {
        JLabel tempText = (JLabel) getComponentByName("temp");
        tempText.setText(text);
    }

    public static void appendTextPane(String addedText, boolean clear, ComponentType componentType) {
        JTextPane mainPane = (JTextPane) componentGrabber(componentType);
        Document doc = mainPane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            if (clear) {
                doc.remove(0, doc.getLength());
                mainPane.removeAll();
            }
            doc.insertString(doc.getLength(), addedText, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static String combineHTMLStrings(String oldText, String addedText) {
        oldText = oldText.replaceAll("</html>", "");
        addedText = addedText.replaceAll("<html>", "");
        return oldText + "<br>" + addedText;
    }

    public static void makeInventoryVisible() {
        JTextPane inventoryPane = (JTextPane) componentGrabber(ComponentType.PANE_INVENTORY);
        JTextPane relicPane = (JTextPane) componentGrabber(ComponentType.PANE_RELIC);
        setInputFocus();
        inventoryPane.setVisible(true);
        relicPane.setVisible(false);
    }

    public static void makeRelicsVisible() {
        JTextPane inventoryPane = (JTextPane) componentGrabber(ComponentType.PANE_INVENTORY);
        JTextPane relicPane = (JTextPane) componentGrabber(ComponentType.PANE_RELIC);
        setInputFocus();
        inventoryPane.setVisible(false);
        relicPane.setVisible(true);
    }

    public static String HTMLifyString(String input) {
        input = input.replaceAll("\n", "<br>");
        return "<html>" + input + "</html>";
    }

    public static String unHTMLifyString(String input) {
        input = input.replaceAll("<br>", "\n");
        input = input.replaceAll("<html>", "");
        return input.replaceAll("</html>", "");
    }

    public static void setTempText(JTextField userInput, JLabel temp) {
        temp.setText(userInput.getText());
        userInput.setText("");
    }

    public static String getTempText() {
        JLabel tempLabel = (JLabel) getComponentByName("temp");
        String output = tempLabel.getText();
        tempLabel.setText("");
        setInputFocus();
        return output;
    }

    //if called after a button is pressed, the button click animation does not function properly
    public static void setInputFocus() {
        if (componentGrabber(ComponentType.PANEL_POPUP).isVisible()) {
            return;
        }
        componentGrabber(ComponentType.TEXTFIELD_INPUT).requestFocusInWindow();
    }

    public static void clearInventoryPane(boolean relics) {
        JTextPane pane;
        if (relics) {
            pane = (JTextPane) componentGrabber(ComponentType.PANE_RELIC);
        } else {
            pane = (JTextPane) componentGrabber(ComponentType.PANE_INVENTORY);
        }
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

    public static void addItemLabel(String newItemText, Player player, Item item, Color color) {
        if (item instanceof Relic relic && relic.isEquipped(player)) {
            addRelicLabel(newItemText, player, relic, color);
        } else if (item instanceof Weapon weapon) {
            addWeaponLabel(newItemText, player, weapon);
        } else {
            addInventoryLabel(newItemText, player, item, color);
        }
    }

    public static void addInventoryLabel(String newText, Player player, Item item, Color color) {
        InventoryButton useButton = new InventoryButton();
        InventoryButton dropButton = new InventoryButton();

        if (player.getCurrentRoom() instanceof PureWaterRoom pureRoom && !pureRoom.getFountainUsed()) {
            useButton.setText(" Cleanse ");
            useButton.addActionListener(_ -> item.cleanseItem(player));
        } else {
            useButton.addActionListener(_ -> item.useItem(player));
            if (player.getCurrentRoom() instanceof ShopRoom) {
                dropButton.setText(" Sell ");
                dropButton.addActionListener(_ -> {
                    player.sellItem(item);
                    SwingRenderer.appendLabelText("The " + item.getName() + " was sold!", true, ComponentType.LABEL_ERROR);
                });
            } else {
                dropButton.setText(" Drop ");
                dropButton.addActionListener(_ -> {
                    player.discardItem(item);
                    SwingRenderer.appendLabelText("The " + item.getName() + " was dropped!", true, ComponentType.LABEL_ERROR);
                });
            }
            if (item instanceof Relic) {
                useButton.setText(" Equip ");
            } else {
                useButton.setText(" Use ");
            }
        }
            useButton.setHorizontalAlignment(SwingConstants.LEFT);
            dropButton.setHorizontalAlignment(SwingConstants.LEFT);

            JTextPane inventoryPane = (JTextPane) componentGrabber(ComponentType.PANE_INVENTORY);
            Document doc = inventoryPane.getStyledDocument();
            inventoryPane.setCaretPosition(doc.getLength());
            inventoryPane.insertComponent(useButton);
            inventoryPane.setCaretPosition(doc.getLength());
            inventoryPane.insertComponent(dropButton);

            insertSimpleText(doc, newText, color);
    }

    public static void addWeaponLabel(String newText, Player player, Weapon weapon) {
        InventoryButton useButton = new InventoryButton();
        InventoryButton dropButton = new InventoryButton();

        if (!weapon.isEquipped(player)) {
            useButton.setText(" Equip ");
        } else {
            useButton.setText(" Unequip ");
        }
        if (player.getCurrentRoom() instanceof ShopRoom) {
            dropButton.setText(" Sell ");
            dropButton.addActionListener(_ -> {
                player.sellItem(weapon);
                SwingRenderer.appendLabelText("The " + weapon.getName() + " was sold!", true, ComponentType.LABEL_ERROR);
            });
        } else {
            dropButton.setText(" Drop ");
            dropButton.addActionListener(_ -> {
                player.discardItem(weapon);
                SwingRenderer.appendLabelText("The " + weapon.getName() + " was dropped!", true, ComponentType.LABEL_ERROR);
            });
        }

        useButton.addActionListener(_ -> weapon.useItem(player));

        useButton.setHorizontalAlignment(SwingConstants.LEFT);
        dropButton.setHorizontalAlignment(SwingConstants.LEFT);

        JTextPane inventoryPane = (JTextPane) componentGrabber(ComponentType.PANE_INVENTORY);
        Document doc = inventoryPane.getStyledDocument();
        inventoryPane.setCaretPosition(doc.getLength());
        inventoryPane.insertComponent(useButton);

        if (!weapon.isEquipped(player)) {
            inventoryPane.setCaretPosition(doc.getLength());
            inventoryPane.insertComponent(dropButton);
        }

        insertSimpleText(doc, newText);
    }

    public static void addRelicLabel(String newText, Player player, Relic relic, Color color) {
        InventoryButton useButton = new InventoryButton();

        if (player.getCurrentRoom() instanceof PureWaterRoom pureRoom && !pureRoom.getFountainUsed()) {
            useButton.setText(" Cleanse ");
            useButton.addActionListener(_ -> relic.cleanseItem(player));
        } else {
            useButton.setText(" Unequip ");
            useButton.addActionListener(_ -> relic.useItem(player));
        }

        JTextPane inventoryPane = (JTextPane) componentGrabber(ComponentType.PANE_RELIC);
        Document doc = inventoryPane.getStyledDocument();
        inventoryPane.setCaretPosition(doc.getLength());
        inventoryPane.insertComponent(useButton);

        insertSimpleText(doc, newText, color);
    }

    public static void addEnemyLabel(String enemyText, Player player, Enemy enemy, int enemyIndex) {
        InventoryButton attackButton = new InventoryButton();
        InventoryButton checkButton = new InventoryButton();

        attackButton.setText(" Attack ");
        checkButton.setText(" Check ");

        attackButton.addActionListener(_ -> setTempText(Integer.toString(enemyIndex + 1)));
        checkButton.addActionListener(_ -> enemy.checkInformation());

        attackButton.setHorizontalAlignment(SwingConstants.LEFT);
        checkButton.setHorizontalAlignment(SwingConstants.LEFT);

        JTextPane mainPane = (JTextPane) componentGrabber(ComponentType.PANE_MAIN);
        Document doc = mainPane.getStyledDocument();
        mainPane.setCaretPosition(doc.getLength());
        mainPane.insertComponent(attackButton);
        if (player.equippedRelicIndex(RelicID.ENEMY_INFORMATION) > -1) {
            mainPane.setCaretPosition(doc.getLength());
            mainPane.insertComponent(checkButton);
        }

        insertSimpleText(doc, enemyText);
    }

    public static void addRoomLabel(int roomIndex, String roomAppearance) {
        InventoryButton roomButton = new InventoryButton();
        roomButton.setText(" Go ");
        roomButton.addActionListener(_ -> setTempText(Integer.toString(roomIndex + 1)));
        roomButton.setHorizontalAlignment(SwingConstants.LEFT);

        JTextPane mainPane = (JTextPane) componentGrabber(ComponentType.PANE_MAIN);
        Document doc = mainPane.getStyledDocument();
        mainPane.setCaretPosition(doc.getLength());
        mainPane.insertComponent(roomButton);

        insertSimpleText(doc, roomAppearance);
    }

    public static void addShopLabel(Player player, Item item, ShopRoom shopRoom) {
        InventoryButton buyButton = new InventoryButton();
        buyButton.setText(" Buy ");
        buyButton.addActionListener(_ -> shopRoom.sellItem(item, player));
        buyButton.setHorizontalAlignment(SwingConstants.LEFT);

        JTextPane mainPane = (JTextPane) componentGrabber(ComponentType.PANE_MAIN);
        Document doc = mainPane.getStyledDocument();
        mainPane.setCaretPosition(doc.getLength());
        mainPane.insertComponent(buyButton);

        String itemName = item.getName();
        String itemDescription = item.getDescription();
        int itemValue = item.getValue();

        String itemInformation = itemName + " (" + itemValue + "G): " + itemDescription + "\n";

        insertSimpleText(doc, itemInformation);

    }

    public static void insertSimpleText(Document doc, String text) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        StyleConstants.setForeground(attributeSet, Color.white);
        StyleConstants.setSpaceAbove(attributeSet, 16f);
        try {
            doc.insertString(doc.getLength(), " " + text, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSimpleText(Document doc, String text, Color color) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        StyleConstants.setForeground(attributeSet, color);
        StyleConstants.setSpaceAbove(attributeSet, 16f);
        try {
            doc.insertString(doc.getLength(), " " + text, attributeSet);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeBackgroundImage(String fileName) {
        JFrame frame = App.INSTANCE.getFrame();
        int frameWidth = frame.getWidth();

        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource(fileName));
        ((JLabel) componentGrabber(ComponentType.LABEL_IMAGE_BACKGROUND)).setIcon(resizeBackground(backgroundImage, frameWidth));
    }

    public static ImageIcon resizeBackground(ImageIcon backgroundImage, int frameWidth) {
        int imageWidth = backgroundImage.getIconWidth();
        int imageHeight = backgroundImage.getIconHeight();

        // Resize the image by starting with the width of the frame and then
        // use the ratio between the width of the frame and the width of the
        // image to calculate the correct height of the image. This preserves
        // the aspect ratio.
        float ratio = (float) frameWidth / imageWidth;

        Image resizedImage = backgroundImage.getImage().getScaledInstance(frameWidth, (int) (imageHeight * ratio), java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void createPopup(String popupText) {
        JPanel panel = (JPanel) componentGrabber(ComponentType.PANEL_POPUP);
        JLabel shadow = (JLabel) componentGrabber(ComponentType.SHADOW_POPUP);
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
        pane.requestFocusInWindow();
    }

    public static void hidePopup(JLabel shadow, JPanel panel) {
        panel.setVisible(false);
        shadow.setVisible(false);
    }
}
