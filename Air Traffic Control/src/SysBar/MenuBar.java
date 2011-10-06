/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SysBar;

import java.awt.Color;
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
    private ArrayList<MenuItem> menuItems;
    
    public MenuBar(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
        
        
        this.setSize(120, 20);
        this.setBackground(Color.yellow);
        LayoutManager overlay = new OverlayLayout(this);
        this.setLayout(overlay);
    }
    
    public Iterator<MenuItem> getMenuItems() {
        
        return menuItems.iterator();
    }
    
}
