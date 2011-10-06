/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SysBar;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;

/**
 *
 * @author Johan Benschop
 */
public class MenuItem {
    private String title;
    private Color colour;
    private int weight;
    private Image icon;
    private Event onClick;
    private final int height;
    private final int width;
    
    public MenuItem(String title, Color colour, int weight, Image icon, Event onClick) {
        this.title = title;
        if (colour == null) {
            this.colour = Color.RED;
        }
        else {
            this.colour = colour;
        }
        
        this.weight = weight;
        this.icon = icon;
        this.onClick = onClick;
        height = 104;
        width = 104;
    }
    
    
}
