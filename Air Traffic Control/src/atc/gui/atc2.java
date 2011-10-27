package atc.gui;

import SysBar.UnityBar;
import SysBar.UnityItem;
import atc.logic.Airport;
import atc.logic.CTA;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.PatternFactory;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwindx.examples.LayerPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;
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
            final UnityItem uiSettings = menuBar.addItem(new UnityItem("Settings", Color.BLUE, 0, "src/atc/gui/resources/settings.png", UnityBar.Type.NORMAL));
            uiSettings.addActionListener(
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
                                    uiSettings.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem uiGoToAirport = menuBar.addItem(new UnityItem("Go to airport", Color.BLUE, 0, "src/atc/gui/resources/airport.png", UnityBar.Type.NORMAL));
            uiGoToAirport.addActionListener(
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
                                    
                                    uiGoToAirport.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem uiGoToFlight = menuBar.addItem(new UnityItem("Go to flight", Color.BLUE, 0, "src/atc/gui/resources/find_airplane.png", UnityBar.Type.NORMAL));
            uiGoToFlight.addActionListener(
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
                                    uiGoToFlight.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem uiCommandFlight = menuBar.addItem(new UnityItem("Command flight", Color.BLUE, 0, "src/atc/gui/resources/command.png", UnityBar.Type.NORMAL));
            uiCommandFlight.addActionListener(
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
                                    uiCommandFlight.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem uiLandFlight = menuBar.addItem(new UnityItem("Land flight", Color.BLUE, 0, "src/atc/gui/resources/land.png", UnityBar.Type.NORMAL));
            uiLandFlight.addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO
                            uiLandFlight.setActive(false);
                        }
                    });

            final UnityItem uiInFlight = menuBar.addItem(new UnityItem("Show in-flight airplanes", Color.BLUE, 0, "", UnityBar.Type.NORMAL));
            uiInFlight.addActionListener(
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
                                    uiInFlight.setActive(false);
                                }
                            });
                        }
                    });

            final UnityItem uiNewFlight = menuBar.addItem(new UnityItem("Add new flight", Color.BLUE, 0, "src/atc/gui/resources/add_airplane.png", UnityBar.Type.NORMAL));
            uiNewFlight.addActionListener(
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
                                    uiNewFlight.setActive(false);
                                }
                            });
                        }
                    });


            final View view = this.getWwd().getView();
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //.goTo(Position position, double distance);
                            // This object class we handle and we have an orbit view
                            Position targetPos = Position.fromDegrees(52.09153109717759, 5.1381683349609375);
                            
                            // Use a PanToIterator to iterate view to target position
                            if (view != null) {
                                // The elevation component of 'targetPos' here is not the surface elevation,
                                // so we ignore it when specifying the view center position.
                                view.goTo(new Position(targetPos, 0),
                                        targetPos.getElevation() + 20000); // 1000 = 100 meter
                            }
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Collision detected!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            
            //buildAirportLayer(); // TODO unncomment to add airport to the planet
        }

        public LayerPanel getLayerPanel() {
            return this.layerPanel;
        }
        
        private Layer buildAirportLayer() {
            RenderableLayer layer = new RenderableLayer();
            layer.setName("Airports");
            
            List<Airport> airports = null; // TODO import airports here
            
            for (Airport i : airports) {
                addAirport(layer, i);
            }
            
            return layer;
        }
        
        private AnnotationAttributes apAttributes;
        private void addAirport(RenderableLayer layer, Airport airport) {
            if (apAttributes == null)
            {
                // Init default attributes for all eq
                apAttributes = new AnnotationAttributes();
                apAttributes.setLeader(AVKey.SHAPE_NONE);
                apAttributes.setDrawOffset(new Point(0, -16));
                apAttributes.setSize(new Dimension(32, 32));
                apAttributes.setBorderWidth(0);
                apAttributes.setCornerRadius(0);
                apAttributes.setBackgroundColor(new Color(0, 0, 0, 0));
            }
            
            apAnnotation ea = new apAnnotation(airport, apAttributes);
            
            ea.getAttributes().setImageSource(PatternFactory.createPattern(PatternFactory.PATTERN_CIRCLE, .8f, Color.BLUE));
            ea.getAttributes().setTextColor(Color.BLUE);
            ea.getAttributes().setScale(1);
            
            layer.addRenderable(ea);
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
