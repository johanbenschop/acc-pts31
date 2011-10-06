/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SysBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Johan Benschop
 */
public class Button extends JComponent implements MouseListener {

    private String title;
    private Color colour;
    private int weight;
    private Image icon;
    private Event onClick;
    private final Dimension dimension;
    private final ArrayList<ActionListener> listeners = new ArrayList<>();
    private boolean isMouseEntered;

    public Button(String title, Color colour, int weight, Image icon, Event onClick) {
        super();
        this.enableInputMethods(true);
        this.addMouseListener(this);
        this.title = title;
        if (colour == null) {
            this.colour = Color.RED;
        } else {
            this.colour = colour;
        }

        this.weight = weight;
        this.icon = icon;
        this.onClick = onClick;
        dimension = new Dimension(52, 52);
    }

    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    @Override
    public Dimension getMinimumSize() {
        return dimension;
    }

    @Override
    public Dimension getMaximumSize() {
        return dimension;
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(MouseEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());

        synchronized (listeners) {
            for (int i = 0; i < listeners.size(); i++) {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // turn on anti-alias mode
        Graphics2D antiAlias = (Graphics2D) g;
        antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw white rectangle
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, 54, 54);
        
        
        Image image;
        // First we draw the background
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_back_54.png");
        g.drawImage(image, 0, 0, this);
        
        // Then we draw the edge
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_edge_54.png");
        g.drawImage(image, 0, 0, this);
        
        // Then we draw the very nice shine on it
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_shine_54.png");
        g.drawImage(image, 0, 0, this);
        
        // Last but not least we draw our icon
        g.drawImage(icon, 0, 0, this);

        if (isMouseEntered) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseEntered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseEntered = false;
    }
}
