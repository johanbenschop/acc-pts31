/*
Copyright (C) 2001, 2011 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
 */
package atc.gui;

import SysBar.*;
import atc.logic.Airplane;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwindx.examples.util.*;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import gov.nasa.worldwindx.examples.LayerPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Provides a base application framework for simple WorldWind examples. Examine other examples in this package to see
 * how it's used.
 */
public class atc {

    public static class AppPanel extends JPanel {

        protected WorldWindowGLCanvas wwd;
        protected StatusBar statusBar;
        protected ToolTipController toolTipController;
        protected HighlightController highlightController;

        public AppPanel(Dimension canvasSize, boolean includeStatusBar) {
            super(new BorderLayout());

            this.wwd = this.createWorldWindow();
            this.wwd.setPreferredSize(canvasSize);

            // Create the default model as described in the current worldwind properties.
            Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
            this.wwd.setModel(m);

            // Setup a select listener for the worldmap click-and-go feature
            this.wwd.addSelectListener(new ClickAndGoSelectListener(this.getWwd(), WorldMapLayer.class));

            this.add(this.wwd, BorderLayout.CENTER);
            if (includeStatusBar) {
                this.statusBar = new StatusBar();
                this.add(statusBar, BorderLayout.PAGE_END);
                this.statusBar.setEventSource(wwd);
            }

            // Add controllers to manage highlighting and tool tips.
            this.toolTipController = new ToolTipController(this.getWwd(), AVKey.DISPLAY_NAME, null);
            this.highlightController = new HighlightController(this.getWwd(), SelectEvent.ROLLOVER);
        }

        protected WorldWindowGLCanvas createWorldWindow() {
            return new WorldWindowGLCanvas();
        }

        public WorldWindowGLCanvas getWwd() {
            return wwd;
        }

        public StatusBar getStatusBar() {
            return statusBar;
        }
    }

    protected static class AppFrame extends JFrame {

        private Dimension canvasSize = new Dimension(1280, 720);
        protected AppPanel wwjPanel;
        protected LayerPanel layerPanel;
        protected StatisticsPanel statsPanel;
        protected RenderableLayer renderLayer;

        public AppFrame() {
            this.initialize(true, true, false); // debug...
        }

        public AppFrame(boolean includeStatusBar, boolean includeLayerPanel, boolean includeStatsPanel) {
            this.initialize(includeStatusBar, includeLayerPanel, includeStatsPanel);
        }

        protected void initialize(boolean includeStatusBar, boolean includeLayerPanel, boolean includeStatsPanel) {
            // Create the WorldWindow.
            this.wwjPanel = this.createAppPanel(this.canvasSize, includeStatusBar);
            this.wwjPanel.setPreferredSize(canvasSize);

            // Put the pieces together.
            this.getContentPane().add(wwjPanel, BorderLayout.CENTER);
            if (includeLayerPanel) {
                this.layerPanel = new LayerPanel(this.wwjPanel.getWwd(), null);
                this.getContentPane().add(this.layerPanel, BorderLayout.WEST);
            }

            if (includeStatsPanel || System.getProperty("gov.nasa.worldwind.showStatistics") != null) {
                this.statsPanel = new StatisticsPanel(this.wwjPanel.getWwd(), new Dimension(250, canvasSize.height));
                this.getContentPane().add(this.statsPanel, BorderLayout.EAST);
            }

            // Create and install the view controls layer and register a controller for it with the World Window.
            ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
            insertBeforeCompass(getWwd(), viewControlsLayer);
            this.getWwd().addSelectListener(new ViewControlsSelectListener(this.getWwd(), viewControlsLayer));

            // Register a rendering exception listener that's notified when exceptions occur during rendering.
            this.wwjPanel.getWwd().addRenderingExceptionListener(new RenderingExceptionListener() {

                public void exceptionThrown(Throwable t) {
                    if (t instanceof WWAbsentRequirementException) {
                        String message = "Computer does not meet minimum graphics requirements.\n";
                        message += "Please install up-to-date graphics driver and try again.\n";
                        message += "Reason: " + t.getMessage() + "\n";
                        message += "This program will end when you press OK.";

                        JOptionPane.showMessageDialog(AppFrame.this, message, "Unable to Start Program",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(-1);
                    }
                }
            });

            // Search the layer list for layers that are also select listeners and register them with the World
            // Window. This enables interactive layers to be included without specific knowledge of them here.
            for (Layer layer : this.wwjPanel.getWwd().getModel().getLayers()) {
                if (layer instanceof SelectListener) {
                    this.getWwd().addSelectListener((SelectListener) layer);
                }
            }
            
            renderLayer = new RenderableLayer();
            renderLayer.setName("Airplanes");
            insertBeforePlacenames(getWwd(), renderLayer);
            Object n = getWwd();
            this.getLayerPanel().update(getWwd());
            renderLayer.addRenderable(new rdAirplane(new Airplane(3000, 1000, 1000, "100P", "Boeing", 15, 500, 300, 400, 2, 5, 150, 50, 50)).getRender());

            // Create our custom made menu system bar thingy.
            UnityBar menuBar = new UnityBar();
            this.getContentPane().add(menuBar, java.awt.BorderLayout.WEST);

            // Testing items, to be removed or to be subsituted!
            menuBar.addItem(new UnityItem("Start", Color.BLUE, 0, "F", UnityBar.Type.NORMAL)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Go to Airport", Color.GREEN, 0, "", UnityBar.Type.NORMAL)).addActionListener(
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
                                }
                            });
                        }
                    });
            menuBar.addItem(new UnityItem("Colision detected!", Color.RED, 0, "", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Colision detected!", Color.RED, 0, "", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Colision detected!", Color.RED, 0, "", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });
            menuBar.addItem(new UnityItem("Colision detected!", Color.RED, 0, "", UnityBar.Type.ALERT)).addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                    });

            this.pack();

            // Center the application on the screen.
            WWUtil.alignComponent(null, this, AVKey.CENTER);
            this.setResizable(true);
        }

        protected AppPanel createAppPanel(Dimension canvasSize, boolean includeStatusBar) {
            return new AppPanel(canvasSize, includeStatusBar);
        }

        public Dimension getCanvasSize() {
            return canvasSize;
        }

        public AppPanel getWwjPanel() {
            return wwjPanel;
        }

        public WorldWindowGLCanvas getWwd() {
            return this.wwjPanel.getWwd();
        }

        public StatusBar getStatusBar() {
            return this.wwjPanel.getStatusBar();
        }

        public LayerPanel getLayerPanel() {
            return layerPanel;
        }

        public StatisticsPanel getStatsPanel() {
            return statsPanel;
        }

        public void setToolTipController(ToolTipController controller) {
            if (this.wwjPanel.toolTipController != null) {
                this.wwjPanel.toolTipController.dispose();
            }

            this.wwjPanel.toolTipController = controller;
        }

        public void setHighlightController(HighlightController controller) {
            if (this.wwjPanel.highlightController != null) {
                this.wwjPanel.highlightController.dispose();
            }

            this.wwjPanel.highlightController = controller;
        }
    }

    public static void insertBeforeCompass(WorldWindow wwd, Layer layer) {
        // Insert the layer into the layer list just before the compass.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof CompassLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition, layer);
    }

    public static void insertBeforePlacenames(WorldWindow wwd, Layer layer) {
        // Insert the layer into the layer list just before the placenames.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof PlaceNameLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition, layer);
    }

    public static void insertAfterPlacenames(WorldWindow wwd, Layer layer) {
        // Insert the layer into the layer list just after the placenames.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof PlaceNameLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition + 1, layer);
    }

    public static void insertBeforeLayerName(WorldWindow wwd, Layer layer, String targetName) {
        // Insert the layer into the layer list just before the target layer.
        int targetPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l.getName().indexOf(targetName) != -1) {
                targetPosition = layers.indexOf(l);
                break;
            }
        }
        layers.add(targetPosition, layer);
    }

    static {
        System.setProperty("java.net.useSystemProxies", "true");
        if (Configuration.isMacOS()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "World Wind Application");
            System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
            System.setProperty("apple.awt.brushMetalLook", "true");
        } else if (Configuration.isWindowsOS()) {
            System.setProperty("sun.awt.noerasebackground", "true"); // prevents flashing during window resizing
        }
    }

    public static AppFrame start(String appName, Class appFrameClass) {
        if (Configuration.isMacOS() && appName != null) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", appName);
        }

        try {
            final AppFrame frame = (AppFrame) appFrameClass.newInstance();
            frame.setTitle(appName);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    frame.setVisible(true);
                }
            });

            return frame;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // We need to force Java to use the native look and feel.
        // If we wish to use a Java theme see http://stackoverflow.com/questions/1656168/java-netbeans-how-come-the-gui-looks-different
        String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
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
        atc.start("Airtraffic Control Centre", AppFrame.class);
    }
}
