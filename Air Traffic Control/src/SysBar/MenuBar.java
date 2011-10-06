/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SysBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 *
 * @author Johan Benschop
 */
public class MenuBar extends JPanel {

    private ArrayList<Button> menuItems;

//    public MenuBar(ArrayList<MenuItem> menuItems) {
//        this.menuItems = menuItems;
//    }
    public MenuBar() {
        //this.setSize(66, 720);
        setPreferredSize(new Dimension(65, 720));
        this.setBackground(Color.yellow);
        //this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Testing items, to be removed!
        this.add(new Button("Start", Color.BLUE, 0, null, null));
        this.add(new Button("Add Flight", Color.BLUE, 0, null, null));
        this.add(new Button("Crash Flight", Color.BLUE, 0, null, null));
    }

    public Iterator<Button> getMenuItems() {

        return menuItems.iterator();
    }

    public void addItem(Button menuItem) {
        menuItems.add(menuItem);
    }

    public void addItem(String title, Color colour, int weight, Image icon, Event onClick) {
        menuItems.add(new Button(title, colour, weight, icon, onClick));
    }
}
