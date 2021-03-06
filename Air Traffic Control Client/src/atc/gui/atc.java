/*
Copyright (C) 2001, 2011 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
 */
package atc.gui;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwindx.examples.util.*;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwind.view.orbit.OrbitView;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import gov.nasa.worldwindx.examples.LayerPanel;
import javax.swing.*;
import java.awt.*;

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

        /**
         * Returns the WorldWindowGLCanvas that's in the GUI.
         * @return WorldWindowGLCanvas
         */
        public WorldWindowGLCanvas getWwd() {
            return wwd;
        }

        /**
         * Gets the status bar that's in the GUI.
         * @return StatusBar
         */
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

        /**
         * Constructor for the AppFrame.
         */
        public AppFrame() {
            this.initialize(false, false, false); // Layer panel, status bar, other panel
        }

        /**
         * Constructor for the AppFrame.
         * @param includeStatusBar True includes the status bar
         * @param includeLayerPanel True includes the layer panel
         * @param includeStatsPanel True includes statistical panel
         */
        public AppFrame(boolean includeStatusBar, boolean includeLayerPanel, boolean includeStatsPanel) {
            this.initialize(includeStatusBar, includeLayerPanel, includeStatsPanel);
        }

        protected void initialize(boolean includeStatusBar, boolean includeLayerPanel, boolean includeStatsPanel)
        {
            // Create the WorldWindow.
            this.wwjPanel = this.createAppPanel(this.canvasSize, includeStatusBar);
            this.wwjPanel.setPreferredSize(canvasSize);

            // Put the pieces together.
            this.getContentPane().add(wwjPanel, BorderLayout.CENTER);
            if (includeLayerPanel)
            {
                this.layerPanel = new LayerPanel(this.wwjPanel.getWwd(), null);
                this.getContentPane().add(this.layerPanel, BorderLayout.WEST);
            }

            if (includeStatsPanel || System.getProperty("gov.nasa.worldwind.showStatistics") != null)
            {
                this.statsPanel = new StatisticsPanel(this.wwjPanel.getWwd(), new Dimension(250, canvasSize.height));
                this.getContentPane().add(this.statsPanel, BorderLayout.EAST);
            }

            // Create and install the view controls layer and register a controller for it with the World Window.
            ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
            insertBeforeCompass(getWwd(), viewControlsLayer);
            this.getWwd().addSelectListener(new ViewControlsSelectListener(this.getWwd(), viewControlsLayer));

            // Register a rendering exception listener that's notified when exceptions occur during rendering.
            this.wwjPanel.getWwd().addRenderingExceptionListener(new RenderingExceptionListener()
            {
                public void exceptionThrown(Throwable t)
                {
                    if (t instanceof WWAbsentRequirementException)
                    {
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
            for (Layer layer : this.wwjPanel.getWwd().getModel().getLayers())
            {
                if (layer instanceof SelectListener)
                {
                    this.getWwd().addSelectListener((SelectListener) layer);
                }
            }

            this.pack();

            // Center the application on the screen.
            WWUtil.alignComponent(null, this, AVKey.CENTER);
            this.setResizable(true);
        }

        protected AppPanel createAppPanel(Dimension canvasSize, boolean includeStatusBar) {
            return new AppPanel(canvasSize, includeStatusBar);
        }

        /**
         * Gets the canvas size
         * @return canvas size in Dimension
         */
        public Dimension getCanvasSize() {
            return canvasSize;
        }
        
        /**
         * Gets the Orbit View of the WWD.
         * @return OrbitView if WWD's view happens to be an OrbitView, otherwise null
         */
        public OrbitView getOrbitView()
        {
            View view = this.getWwd().getView();
            return (view != null && view instanceof OrbitView) ? (OrbitView) view : null;
        }
        
        /**
         * Gets the Word Wind Java panel.
         * @return AppPanel
         */
        public AppPanel getWwjPanel() {
            return wwjPanel;
        }

        /**
         * Returns the WorldWindowGLCanvas that's in the GUI.
         * @return WorldWindowGLCanvas
         */
        public WorldWindowGLCanvas getWwd() {
            return this.wwjPanel.getWwd();
        }

        /**
         * Gets the status bar that's in the GUI.
         * @return StatusBar
         */
        public StatusBar getStatusBar() {
            return this.wwjPanel.getStatusBar();
        }

        /**
         * Gets the layer panel that's in the GUI.
         * @return LayerPanel
         */
        public LayerPanel getLayerPanel() {
            return layerPanel;
        }

        /**
         * Gets the Statistics panel.
         * @return StatisticsPanel
         */
        public StatisticsPanel getStatsPanel() {
            return statsPanel;
        }

        /**
         * Sets the tooltip controller.
         * @param controller ToolTipController
         */
        public void setToolTipController(ToolTipController controller) {
            if (this.wwjPanel.toolTipController != null) {
                this.wwjPanel.toolTipController.dispose();
            }

            this.wwjPanel.toolTipController = controller;
        }

        /**
         * Sets the highlight controller.
         * @param controller setHighlightController
         */
        public void setHighlightController(HighlightController controller) {
            if (this.wwjPanel.highlightController != null) {
                this.wwjPanel.highlightController.dispose();
            }

            this.wwjPanel.highlightController = controller;
        }
    }

    /**
     * Insert the layer into the layer list just before the compass.
     * @param wwd
     * @param layer 
     */
    public static void insertBeforeCompass(WorldWindow wwd, Layer layer) {
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof CompassLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition, layer);
    }

    /**
     * Insert the layer into the layer list just before the placenames.
     * @param wwd
     * @param layer 
     */
    public static void insertBeforePlacenames(WorldWindow wwd, Layer layer) {
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof PlaceNameLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition, layer);
    }

    /**
     * Insert the layer into the layer list just after the placenames.
     * @param wwd
     * @param layer 
     */
    public static void insertAfterPlacenames(WorldWindow wwd, Layer layer) {
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof PlaceNameLayer) {
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition + 1, layer);
    }

    /**
     * Insert the layer into the layer list just before the target layer.
     * @param wwd
     * @param layer
     * @param targetName 
     */
    public static void insertBeforeLayerName(WorldWindow wwd, Layer layer, String targetName) {
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

    /**
     * Starts the AppFrame
     * @param appName Name of the application
     * @param appFrameClass
     * @return An very nice AppFrame.
     */
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
}