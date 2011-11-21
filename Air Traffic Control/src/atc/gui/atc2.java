package atc.gui;

import SysBar.UnityBar;
import SysBar.UnityItem;
import atc.cli.jpTerminal;
import atc.gui.Audio.Statusses;
import atc.logic.ACC;
import atc.logic.Airplane;
import atc.logic.Airport;
import atc.logic.CTA;
import atc.logic.Flightplan;
import atc.logic.GeoSector;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceSector;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import gov.nasa.worldwindx.examples.LayerPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.prefs.*;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.Timer;

/**
 *
 * @author Johan Benschop
 */
public final class atc2 extends atc {

    private static CTA cta = new CTA(new GeoSector(40, 60, -10, 10));
    public static ACC acc = new ACC(343, cta);
    private static Preferences prefs = Preferences.userRoot().node("/atc/gui");

    public static class AppFrame extends atc.AppFrame {

        private AirportRenderable currentAirportAnnotation;
        private AirplaneRenderable currentAirplaneAnnotation;
        protected RenderableLayer airspaceLayer;
        protected RenderableLayer airportLayer;
        protected RenderableLayer airplaneLayer;
        private GlobeAnnotation tooltipAnnotation;
        private Timer timerAirplane;
        private ArrayList<Airplane> addedAirplanes;
        private final UnityBar menuBar;
        private final View view;
        private final Timer timerColision;

        public AppFrame() {
            if (prefs.getBoolean("APP_START-MAXIMIZED", false)) {
                this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            }
            
            final jpTerminal cli = new jpTerminal();
            cli.setVisible(prefs.getBoolean("CLI_VISIBLE", false));
            super.getWwjPanel().add(cli, java.awt.BorderLayout.SOUTH);
            KeyListener kl = new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.isAltDown() && e.getKeyChar() == '/' || e.getKeyChar() == '`') {
                        if (cli.isVisible()) {
                            cli.setVisible(false);
                        } else {
                            cli.setVisible(true);
                        }
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            };
            this.getWwd().addKeyListener(kl);
            this.addKeyListener(kl);
            this.getWwjPanel().addKeyListener(kl);
            cli.addKeyListener(kl);

            // Create our custom made menu system bar thingy.
            menuBar = new UnityBar();
            this.getContentPane().add(menuBar, java.awt.BorderLayout.LINE_START);

            view = this.getWwd().getView();

            // The menu items
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
                                    LayerPanel layerPanel = new LayerPanel(wwjPanel.getWwd(), null);
                                    jfSettings settings = new jfSettings(null, false);
                                    settings.setWwd(wwjPanel.getWwd());
                                    settings.setVisible(true);
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
                                    Airport goToAirport = new jfSelectAirport(null, true).getValue();

                                    // Use a PanToIterator to iterate view to target position
                                    if (view != null && goToAirport != null) {
                                        Position targetPos = goToAirport.getLocation().toPosition();
                                        // The elevation component of 'targetPos' here is not the surface elevation,
                                        // so we ignore it when specifying the view center position.
                                        view.goTo(new Position(targetPos, 0),
                                                targetPos.getElevation() + 20000); // 1000 = 100 meter
                                    }
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
                                    Flightplan plan = new jfSelectFlight(null, true).getValue();

                                    // Use a PanToIterator to iterate view to target position
                                    if (view != null && plan != null) {
                                        Position targetPos = plan.getAirplane().getLocation().toPosition();
                                        // The elevation component of 'targetPos' here is not the surface elevation,
                                        // so we ignore it when specifying the view center position.
                                        view.goTo(new Position(targetPos, 0),
                                                targetPos.getElevation() + 100); // 1000 = 100 meter
                                    }
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

            this.timerColision = new Timer(prefs.getInt("WWD_REFRESHRATE", 500), new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    findCollisions();

                    getWwd().redraw();
                }
            });
            timerColision.start();

            // Add the graticule layer

            LatLonGraticuleLayer graticuleLayer = new LatLonGraticuleLayer();
            insertBeforePlacenames(getWwd(), graticuleLayer);
            graticuleLayer.setEnabled(false);

            // Add the airport & airspace layers
            buildAirportLayer();
            buildAirspaceLayer();
            buildAirplaneLayer();

            // Init tooltip annotation
            this.tooltipAnnotation = new GlobeAnnotation("", Position.fromDegrees(0, 0, 0));
            Font font = Font.decode(prefs.get("TT_FONT", "Arial-Plain-16"));
            this.tooltipAnnotation.getAttributes().setFont(font);
            this.tooltipAnnotation.getAttributes().setTextColor(Color.WHITE);
            this.tooltipAnnotation.getAttributes().setSize(new Dimension(270, 0));
            this.tooltipAnnotation.getAttributes().setDistanceMinScale(1);
            this.tooltipAnnotation.getAttributes().setDistanceMaxScale(1);
            this.tooltipAnnotation.getAttributes().setBackgroundColor(new Color(0f, 0f, 0f, .7f));
            //this.tooltipAnnotation.getAttributes().setImageSource(PatternFactory.createPattern(PatternFactory.PATTERN_CIRCLES,
            //        (float) 0.1, Color.LIGHT_GRAY));
            this.tooltipAnnotation.getAttributes().setImageScale(.4);
            this.tooltipAnnotation.getAttributes().setVisible(false);
            this.tooltipAnnotation.setAlwaysOnTop(true);
            airportLayer.addRenderable(this.tooltipAnnotation);
            Audio.play(Statusses.ALARM5);
        }

        /**
         * Finds collisions and gives a warning about it.
         */
        public void findCollisions() {
            menuBar.clearAlerts();
            for (final Airplane p : addedAirplanes) {
                if (p.getStatus() == Airplane.Statusses.CRASHING1) {
                    menuBar.addItem(new UnityItem("Collision detected! Mayor!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                            new java.awt.event.ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //.goTo(Position position, double distance);
                                    // This object class we handle and we have an orbit view
                                    Position targetPos = p.getLocation().toPosition();

                                    // Use a PanToIterator to iterate view to target position
                                    if (view != null) {
                                        // The elevation component of 'targetPos' here is not the surface elevation,
                                        // so we ignore it when specifying the view center position.
                                        view.goTo(new Position(targetPos, 0),
                                                targetPos.getElevation() + 300); // 1000 = 100 meter
                                    }
                                }
                            });
                } else if (p.getStatus() == Airplane.Statusses.CRASHING2) {
                    menuBar.addItem(new UnityItem("Collision detected! Minor!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                            new java.awt.event.ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //.goTo(Position position, double distance);
                                    // This object class we handle and we have an orbit view
                                    Position targetPos = p.getLocation().toPosition();

                                    // Use a PanToIterator to iterate view to target position
                                    if (view != null) {
                                        // The elevation component of 'targetPos' here is not the surface elevation,
                                        // so we ignore it when specifying the view center position.
                                        view.goTo(new Position(targetPos, 0),
                                                targetPos.getElevation() + 300); // 1000 = 100 meter
                                    }
                                }
                            });
                }
            }
        }

        /**
         * Returns the layer panel. This is the panel in wich you can enable and disable layers.
         * @return the layer panel
         */
        public LayerPanel getLayerPanel() {
            return this.layerPanel;
        }

        /**
         * Build the airspace layer.
         * TODO build the airspace according to the CTA and not directly as done.
         */
        private void buildAirspaceLayer() {
            SurfaceSector surfaceSector = new SurfaceSector(acc.GetCTA().getSector().toSector());
            ShapeAttributes attr = new BasicShapeAttributes();
            //attr.setInteriorMaterial(Material.WHITE);
            attr.setOutlineMaterial(Material.RED);
            attr.setInteriorOpacity(0);
            attr.setOutlineOpacity(0.7);
            attr.setOutlineWidth(3);
            surfaceSector.setAttributes(attr);
            surfaceSector.setPathType(AVKey.RHUMB_LINE);
            attr.setEnableAntialiasing(true);

            airspaceLayer = new RenderableLayer();
            airspaceLayer.setName("Airspaces");
            airspaceLayer.setPickEnabled(false);
            airspaceLayer.addRenderable(surfaceSector);
            insertBeforePlacenames(this.getWwd(), airspaceLayer);

//            OrbitView view = this.getOrbitView();
//            if (view != null) {
//                OrbitViewLimits limits = view.getOrbitViewLimits();
//                if (limits != null) {
//                    Globe globe = this.getWwd().getModel().getGlobe();
//
//                    limits.setCenterLocationLimits(acc.GetCTA().getSector().toSector());
//                    limits.setZoomLimits(0, 5000000);
//                    BasicOrbitViewLimits.applyLimits(view, limits);
//                }
//                surfaceSector.setSector(limits.getCenterLocationLimits());
//            }
        }

        /**
         * Builds the airport layer.
         */
        private void buildAirportLayer() {
            // Add the airport layer
            this.airportLayer = new RenderableLayer();
            this.airportLayer.setName("Airports");
            insertBeforePlacenames(this.getWwd(), this.airportLayer);

            // Add select listener for airport picking
            this.getWwd().addSelectListener(new SelectListener() {

                public void selected(SelectEvent event) {
                    if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
                        highlightAirport(event.getTopObject());
                    }
                }
            });

            // Add click-and-go select listener for airports
            this.getWwd().addSelectListener(new ClickAndGoSelectListener(
                    this.getWwd(), AirportRenderable.class, 20000));

            ListIterator<Airport> litr = acc.GetCTA().GetAirports();

            while (litr.hasNext()) {
                addAirportToLayer(airportLayer, litr.next());
            }
        }

        /**
         * Adds an airport to the airport layer.
         * @param layer Airport Layer
         * @param airport Airport
         */
        private void addAirportToLayer(RenderableLayer layer, Airport airport) {
            layer.addRenderable(new AirportRenderable(airport));
        }

        /** 
         * Shows the annotation of an airport when param o is indeed a AirportRenderable.
         * @param o Object under the mouse
         */
        private void highlightAirport(Object o) {
            if (currentAirportAnnotation == o) {
                return; // same thing selected
            }
            if (currentAirportAnnotation != null) {
                currentAirportAnnotation.getAttributes().setHighlighted(false);
                currentAirportAnnotation = null;
                tooltipAnnotation.getAttributes().setVisible(false);
            }

            if (o != null && o instanceof AirportRenderable) {
                currentAirportAnnotation = (AirportRenderable) o;
                currentAirportAnnotation.getAttributes().setHighlighted(true);
                tooltipAnnotation.setText(currentAirportAnnotation.getAnnotationText());
                tooltipAnnotation.setPosition(currentAirportAnnotation.airport.getLocation().toPosition());
                tooltipAnnotation.getAttributes().setVisible(true);
                this.getWwd().repaint();
            }
        }

        /**
         * Build the layer with all of the airplanes.
         */
        private void buildAirplaneLayer() {
            // Add the airplane layer
            this.addedAirplanes = new ArrayList<>();
            this.airplaneLayer = new RenderableLayer();
            this.airplaneLayer.setName("Airplanes");
            insertBeforePlacenames(this.getWwd(), this.airplaneLayer);

            // Add click-and-go select listener for airplanes
            this.getWwd().addSelectListener(new ClickAndGoSelectListener(
                    this.getWwd(), airplaneRenderableOLD.class, 500)); // last value is height

            // Add select listener for airport picking
            this.getWwd().addSelectListener(new SelectListener() {

                public void selected(SelectEvent event) {
                    if (event.getEventAction().equals(SelectEvent.LEFT_DOUBLE_CLICK)) {
                        clickAirplane(event.getTopObject());
                    }
                    if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
                        highlightAirplane(event.getTopObject());
                    }
                }
            });

            this.timerAirplane = new Timer(1000, new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    ListIterator<Flightplan> litr = acc.getFlightplans();

                    while (litr.hasNext()) {
                        addAirplaneToLayer(airplaneLayer, litr.next());
                    }
                }
            });
            timerAirplane.start();
        }

        /**
         * Adds an airplane to the airplane layer.
         * @param layer
         * @param flightplan 
         */
        private void addAirplaneToLayer(RenderableLayer layer, Flightplan flightplan) {
            Airplane airplane = flightplan.getAirplane();

            if (!addedAirplanes.contains(airplane)) {
                addedAirplanes.add(airplane);
                layer.addRenderable(new AirplaneRenderable(flightplan));
            }
        }

        /** 
         * Shows the annotation of an airport when param o is indeed a AirportRenderable.
         * @param o Object under the mouse
         */
        private void highlightAirplane(Object o) {
            if (currentAirplaneAnnotation == o) {
                return; // same thing selected
            }
            if (currentAirplaneAnnotation != null) {
                currentAirplaneAnnotation.getAttributes().setHighlighted(false);
                currentAirplaneAnnotation.unsetHoverAnnotation();
                currentAirplaneAnnotation = null;
                tooltipAnnotation.getAttributes().setVisible(false);
            }

            if (o != null && o instanceof AirplaneRenderable) {
                currentAirplaneAnnotation = (AirplaneRenderable) o;
                currentAirplaneAnnotation.getAttributes().setHighlighted(true);
                tooltipAnnotation.getAttributes().setVisible(true);
                currentAirplaneAnnotation.setHoverAnnotation(tooltipAnnotation);
                this.getWwd().repaint();
            }
        }

        private void clickAirplane(Object o) {
            if (o.getClass() != AirplaneRenderable.class) {
                return; // The selected object isn't our airplane.
            }
            AirplaneRenderable rend = (AirplaneRenderable) o;
            Flightplan flightplan = rend.getFlightplan();

            new jfCommandFlight(this, true).setFlightplan(flightplan);
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

        if (prefs.getBoolean("APP_IGNORE-AT", false)) {
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
        }

        // Call the static start method like this from the main method of your derived class.
        // Substitute your application's name for the first argument.
        start("Airtraffic Control Centre", AppFrame.class);
    }
}