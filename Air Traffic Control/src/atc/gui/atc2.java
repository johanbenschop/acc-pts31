package atc.gui;

import SysBar.UnityBar;
import SysBar.UnityItem;
import gov.nasa.worldwindx.examples.LayerPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Johan Benschop
 */
public final class atc2 extends atc {

    public static class AppFrame extends atc.AppFrame {

        public AppFrame() {
            // Create our custom made menu system bar thingy.
            final UnityBar menuBar = new UnityBar();
            this.getContentPane().add(menuBar, java.awt.BorderLayout.WEST);
            //this.getLayerPanel().add(menuBar, java.awt.BorderLayout.WEST);

            // Testing items, to be removed or to be subsituted!
            final UnityItem ui = menuBar.addItem(new UnityItem("Settings", Color.BLUE, 0, "src/atc/gui/resources/SETTINGS.png", UnityBar.Type.NORMAL));
            ui.addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    menuBar.contains(3, 3);
                                    new jfSettings(null, true).setVisible(true);
                                    ui.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem ui2 = menuBar.addItem(new UnityItem("Go to airport", Color.BLUE, 0, "", UnityBar.Type.NORMAL));
            ui2.addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    new jfSelectAirport(null, true).setVisible(true);
                                    ui2.setActive(false);
                                }
                            });
                        }
                    });

            menuBar.addItem(new UnityItem("Go to flight", Color.BLUE, 0, "src/atc/gui/resources/airplane_icon.gif", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    new jfSelectFlight(null, true).setVisible(true);
                                }
                            });
                        }
                    });

            menuBar.addItem(new UnityItem("Command flight", Color.BLUE, 0, "", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    new jfCommandFlight(null, true).setVisible(true);
                                }
                            });
                        }
                    });

            menuBar.addItem(new UnityItem("Land flight", Color.BLUE, 0, "src/atc/gui/resources/airplane_land.gif", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO
                        }
                    });

            menuBar.addItem(new UnityItem("Show in-flight airplanes", Color.BLUE, 0, "", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    new jfSelectAirplane(null, true).setVisible(true);
                                }
                            });
                        }
                    });

            menuBar.addItem(new UnityItem("Add new flight", Color.BLUE, 0, "src/atc/gui/resources/add_airplane.gif", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    new jfAddFlight(null, true).setVisible(true);
                                }
                            });
                        }
                    });

            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.gif", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.gif", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.gif", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.gif", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
        }

        public LayerPanel getLayerPanel() {
            return this.layerPanel;
        }
    }

    public static void main(String[] args) {
        // We need to force Java to use the native look and feel.
        // If we wish to use a Java theme see http://stackoverflow.com/questions/1656168/java-netbeans-how-come-the-gui-looks-different
        String laf = UIManager.getSystemLookAndFeelClassName();

        try {
            try {
                UIManager.setLookAndFeel(laf);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        // Because the jogl.jar libary does not (yet) play nice to 64 bit systems
        // we need to check whether the system uses 32 bit JVM.
        Object[] options = {"Override", "Cancel"};
        switch (System.getProperty("sun.arch.data.model").toString()) {
            case "32":
                System.out.println("Detecting a 32 bit Java virtual machine, all fine.");
                break;
            case "64":
                System.out.println("Detecting a 64 bit Java virtual machine, oh oh!");
                System.out.println("Please use a 32 bit JVM.");
                int n1 = JOptionPane.showOptionDialog(null,
                        "You seem to be running a 64 bit version of the Java virtual machine."
                        + "\nThe included JOGL libary doesn't work with 64 bit JVMs."
                        + "\nWould you like to ingore this warning?",
                        "Wrong architecture!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, // This is for custom icon
                        options, // The titles of buttons
                        options[0]); // Default button title
                if (n1 == 1) {
                    return;
                } else {
                    System.out.println("Overridden. Starting on 64 bit JVM. Stay safe.");
                }
                break;
            default:
                System.out.println("Unkown architecture detected: " + System.getProperty("sun.arch.data.model").toString());
                int n2 = JOptionPane.showOptionDialog(null,
                        "You seem to be running an unknown architecture. (" + System.getProperty("sun.arch.data.model").toString() + ")"
                        + "\nWe cannot guarantee the safety of the universe when the application starts, so please contact Support."
                        + "\nWould you like to ingore this warning?",
                        "Wrong architecture!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, // This is for custom icon
                        options, // The titles of buttons
                        options[0]); // Default button title
                if (n2 == 1) {
                    return;
                } else {
                    System.out.println("Overridden. Stay safe.");
                }
                break;
        }

        // Call the static start method like this from the main method of your derived class.
        // Substitute your application's name for the first argument.
        start("Airtraffic Control Centre", AppFrame.class);
    }
}
