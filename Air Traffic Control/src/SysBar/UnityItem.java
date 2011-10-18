/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SysBar;

import SysBar.UnityBar.Type;
import com.sun.java.swing.plaf.motif.MotifBorders.ButtonBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 *
 * @author Johan Benschop
 */
public class UnityItem extends JComponent implements MouseListener {

    private String title;
    private Color colour;
    private int weight;
    private Image icon;
    private final Dimension dimension;
    private final ArrayList<ActionListener> listeners = new ArrayList<>();
    private boolean active;
    private Type type;
    private static Timer timer;
    private int alphaIntensity;

    public int getAlphaIntensity() {
        return alphaIntensity;
    }

    public void setAlphaIntensity(int alphaIntensity) {
        this.alphaIntensity = alphaIntensity;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
        this.repaint();
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
        this.repaint();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.repaint();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.repaint();
    }

    public UnityItem(String title, Color colour, int weight, String icon, Type type) {
        super();
        this.enableInputMethods(true);
        this.addMouseListener(this);
        this.title = title;
        this.setToolTipText(title);
        if (colour == null) {
            this.colour = Color.RED;
        } else {
            this.colour = colour;
        }
        this.type = type;

        this.weight = weight;
        this.icon = Toolkit.getDefaultToolkit().getImage(icon);
        dimension = new Dimension(54, 54);
        timer = new Timer();

        switch (this.type) {
            case NORMAL:
                break;
            case NOTIFICATION:
                timer.schedule(new pulseTask(this), 0, 50);
                break;
            case ALERT:
                timer.schedule(new pulseTask(this), 0, 50);
                break;
        }

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Turn on anti-alias mode
        Graphics2D antiAlias = (Graphics2D) g;
        antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Image image;

        switch (this.type) {
            case NORMAL:
                if (active) {
                    // First we draw the background
                    image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_back_54.png");
                    g.drawImage(image, 0, 0, this);
                    // Then the color overlay
                    g.setColor(new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), alphaIntensity));
                    g.fillRoundRect(1, 1, 52, 52, 11, 11);
                }
                break;
            case NOTIFICATION:
                break;
            case ALERT:
                // First we draw the background
                image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_back_54.png");
                g.drawImage(image, 0, 0, this);
                // Then the color overlay
                g.setColor(new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), alphaIntensity));
                g.fillRoundRect(1, 1, 52, 52, 11, 11);
                break;
        }

        // Then we draw the edge
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_edge_54.png");
        g.drawImage(image, 0, 0, this);

        // Then we draw the very nice shine on it
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_shine_54.png");
        g.drawImage(image, 0, 0, this);

        // Last but not least we draw our icon
        //icon = icon.getScaledInstance(34, 34, Image.SCALE_SMOOTH);
        g.drawImage(icon, 10, 10, this);
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        // We give the fixed relative position.
        return new Point(67, 16);
    }

    @Override
    public JToolTip createToolTip() {
        JToolTip tip = new JToolTip();
        tip.setBackground(new Color(0, 0, 0, 200));
        tip.setForeground(Color.WHITE);
        tip.setBorder(new ButtonBorder(colour, colour, colour, colour));
        tip.setFont(new Font("Courier", Font.PLAIN, 13));
        return tip;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyListeners(e);

        if (!active) {
            active = true;
            this.repaint();
        } else {
            active = false;
            this.repaint();
        }

        switch (this.type) {
            case NORMAL:
                timer.schedule(new pulseTask(this), 0, 50);
                break;
            case NOTIFICATION:
                break;
            case ALERT:
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    static class pulseTask extends TimerTask {

        UnityItem item;
        static double x;
        int r;

        public pulseTask(UnityItem item) {
            this.item = item;
        }

        public void run() {
            int a = (int) (150 + (50 * Math.sin(x)));
            if (a == 150 || (int) a == 192) {
                r++;
            }
            item.alphaIntensity = a;
            x = x + 0.2;
            item.repaint();

            if (r == 4 && item.getType().equals(Type.NORMAL)) {
                this.cancel();
            }
        }
    }
}
