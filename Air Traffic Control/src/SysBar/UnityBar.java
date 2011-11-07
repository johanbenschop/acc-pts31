/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Sources: 
 * http://stackoverflow.com/questions/2158/creating-a-custom-button-in-java
 * 
 */
package SysBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Johan Benschop
 */
public final class UnityBar extends JPanel {

    JPanel Top;
    JPanel Bottom;

    public enum Type {

        NORMAL, ALERT, NOTIFICATION;
    }

    /**
     * Create a new UnityBar().
     */
    public UnityBar() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(66, 720));
        this.setBackground(Color.BLACK);
        Top = new JPanel();
        Bottom = new JPanel();
        Top.setOpaque(false);
        Bottom.setOpaque(false);
        this.add(Top, java.awt.BorderLayout.NORTH);
        this.add(Bottom, java.awt.BorderLayout.SOUTH);
    }

    /**
     * Adds an item to this bar.
     * @param menuItem
     * @return UnityItem just added
     */
    public UnityItem addItem(UnityItem menuItem) {
        // We check the type and then add it to the correct panel.
        switch (menuItem.getType()) {
            case NORMAL:
                Top.add(menuItem);
                break;
            case NOTIFICATION:
                Bottom.add(menuItem);
                break;
            case ALERT:
                Bottom.add(menuItem);
                break;
        }

        // We need to (re)calculate the height of the part of the bar.
        int TopH = 0;
        for (int i = 0; i < Top.getComponentCount(); i++) {
            TopH = TopH + 60;
        }
        int BottomH = 0;
        for (int i = 0; i < Bottom.getComponentCount(); i++) {
            BottomH = BottomH + 60;
        }
        Top.setPreferredSize(new Dimension(65, TopH));
        Bottom.setPreferredSize(new Dimension(65, BottomH));

        return menuItem;
    }

    /**
     * Adds a new item to the bar.
     * @param title
     * @param colour
     * @param weight
     * @param icon
     * @param type
     * @return the newly created UnityItem
     */
    public UnityItem addItem(String title, Color colour, int weight, String icon, Type type) {
        UnityItem menuItem = new UnityItem(title, colour, weight, icon, type);
        this.add(menuItem, java.awt.BorderLayout.SOUTH);
        return menuItem;
    }

    /**
     * This clears all the icons of the type ALERT. 
     */
    public void clearAlerts() {
        this.Bottom.removeAll();
    }

    /**
     * Paints the menu bar.
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_back_65.png");
        for (int i = 0; i < this.getHeight(); i = i + 4) {
            g.drawImage(image, 0, i, this);
        }
    }
}
