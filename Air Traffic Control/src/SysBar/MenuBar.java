/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Sources: 
 * http://stackoverflow.com/questions/2158/creating-a-custom-button-in-java
 * 
 */
package SysBar;

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
public final class MenuBar extends JPanel {

    public enum Type {

        NORMAL, ALERT, NOTIFICATION;
    }

    public MenuBar() {
        //this.setSize(66, 720);
        setPreferredSize(new Dimension(66, 720));
        this.setBackground(Color.BLACK);
        //this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Button addItem(Button menuItem) {
        //menuItem.addActionListener(this);
        this.add(menuItem, java.awt.BorderLayout.SOUTH);
        return menuItem;
    }

    public Button addItem(String title, Color colour, int weight, String icon, Type type) {
        Button menuItem = new Button(title, colour, weight, icon, type);
        //menuItem.addActionListener(this);
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
