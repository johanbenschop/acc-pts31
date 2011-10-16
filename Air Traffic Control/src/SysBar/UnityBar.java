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
import javax.swing.BoxLayout;
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

    public UnityBar() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(66, 720));
        this.setBackground(Color.BLACK);
        Top = new JPanel();
        Bottom = new JPanel();
        Top.setOpaque(false);
        Bottom.setOpaque(false);
        this.add(Top);
        this.add(Bottom);
    }

    public UnityItem addItem(UnityItem menuItem) {
        switch (menuItem.getType()) {
            case NORMAL:
                System.out.println("Adding a NORMAL one here!");
                Top.add(menuItem);
                break;
            case NOTIFICATION:
                System.out.println("Adding a NOTIFICATION one here!");
                break;
            case ALERT:
                System.out.println("Adding a ALERT one here!");
                Bottom.add(menuItem);
                break;
        }

        return menuItem;
    }

    public UnityItem addItem(String title, Color colour, int weight, String icon, Type type) {
        UnityItem menuItem = new UnityItem(title, colour, weight, icon, type);
        this.add(menuItem, java.awt.BorderLayout.SOUTH);
        return menuItem;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_back_65.png");
        for (int i = 0; i < this.getHeight(); i = i + 4) {
            g.drawImage(image, 0, i, this);
        }
    }
}
