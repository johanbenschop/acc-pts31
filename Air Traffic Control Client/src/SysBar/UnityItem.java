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
import javax.swing.Timer;
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
    private Timer timer;
    private int alphaIntensity;
    private static int x;

    /**
     * Return the alpha intensity for the icon.
     * @return alpha intensity for the icon
     */
    private int getAlphaIntensity() {
        return alphaIntensity;
    }

    /**
     * Sets the alpha intensity for the icon.
     * @param alphaIntensity 
     */
    private void setAlphaIntensity(int alphaIntensity) {
        this.alphaIntensity = alphaIntensity;
    }

    /**
     * Gets the base colour of the icon.
     * @return base colour of icon
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Sets  the base colour of the icon.
     * @param colour the base colour of icon
     */
    public void setColour(Color colour) {
        this.colour = colour;
        this.repaint();
    }

    /**
     * Gets the image inside the icon.
     * @return the image inside the icon
     */
    public Image getIcon() {
        return icon;
    }

    /**
     * Sets the image inside the icon.
     * @param icon the image inside the icon
     */
    public void setIcon(Image icon) {
        this.icon = icon;
        this.repaint();
    }

    /**
     * Returns the title of the icon.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the icon.
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the weight of the icon. (NOT YET OMPLEMENTED)
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the icon. (NOT YET OMPLEMENTED)
     * @param weight 
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns true if icon is lid or flashing.
     * @return 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the icon lid or flashing.
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
        this.repaint();
    }

    /**
     * Gets the type of the icon.
     * @return type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the icon.
     * @param type 
     */
    public void setType(Type type) {
        this.type = type;
        this.repaint();
    }

    /**
     * Creates a new UnityItem with the given parameters.
     * @param title
     * @param colour
     * @param weight
     * @param icon
     * @param type 
     */
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

        switch (this.type) {
            case NORMAL:
                break;
            case NOTIFICATION:
                break;
            case ALERT:
                this.timer = new Timer(50, new ActionListener() {

                    double x = 0;
                    int r = 0;

                    public void actionPerformed(ActionEvent event) {
                        int a = (int) (150 + (50 * Math.sin(x)));
                        if (a == 150 || (int) a == 192) {
                            r++;
                        }

                        setAlphaIntensity(a);
                        x = x + 0.2;
                        repaint();
                    }
                });
                timer.start();
                break;
        }
    }

    /**
     * Add an action listener.
     * @param listener 
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    /**
     * Notify our listeners.
     * @param e 
     */
    private void notifyListeners(MouseEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());

        synchronized (listeners) {
            for (int i = 0; i < listeners.size(); i++) {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
        }
    }

    /**
     * Gets the preferred size of this JComponment.
     * @return 
     */
    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    /**
     * Gets the minium size of this JComponment.
     * @return 
     */
    @Override
    public Dimension getMinimumSize() {
        return dimension;
    }

    /**
     * Gets the maximum size of this JComponment.
     * @return 
     */
    @Override
    public Dimension getMaximumSize() {
        return dimension;
    }

    /**
     * Paint the icon
     * @param g Grap
     */
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

        // Then we draw the very nice shine on it
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_shine_54.png");
        g.drawImage(image, 0, 0, this);

        // Then we draw our icon
        //icon = icon.getScaledInstance(34, 34, Image.SCALE_SMOOTH);
        g.drawImage(icon, 0, 0, this);

        // And at last we draw the edge of the icon
        image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_icon_edge_54.png");
        g.drawImage(image, 0, 0, this);
    }

    /**
     * Gets the Point() of tooltip. This value is always the same regardless of parameter input.
     * @param event
     * @return 
     */
//    @Override
//    public Point getToolTipLocation(MouseEvent event) {
//        // We give the fixed relative position.
//        return new Point(67, 16);
//    }

    /**
     * Create our cool tooltip
     * @return JToolTip
     */
    @Override
    public JToolTip createToolTip() {
        JToolTip tip = new JToolTip();
        tip.setBackground(new Color(0, 0, 0, 200));
        tip.setForeground(Color.WHITE);
        tip.setBorder(new ButtonBorder(colour, colour, colour, colour));
        tip.setFont(new Font("Courier", Font.PLAIN, 13));
        return tip;
    }

    /**
     * Event that gets fired after a mouse click.
     * Its activates the button.
     * @param e 
     */
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
                this.timer = new Timer(50, new ActionListener() {

                    double x = 0;
                    int r = 0;

                    public void actionPerformed(ActionEvent event) {
                        int a = (int) (150 + (50 * Math.sin(x)));
                        if (a == 150 || (int) a == 192) {
                            r++;
                        }

                        setAlphaIntensity(a);
                        x = x + 0.2;
                        repaint();
                    }
                });
                timer.start();
                break;
            case NOTIFICATION:
                break;
            case ALERT:
                break;
        }

    }
    // We have to overide these methods even though we aren't using them.

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
}