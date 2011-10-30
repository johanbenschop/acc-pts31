package atc.gui;

import SysBar.UnityBar;
import SysBar.UnityItem;
import atc.logic.ACC;
import atc.logic.Airplane;
import atc.logic.Airport;
import atc.logic.CTA;
import atc.logic.Flightplan;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AirspaceLayer;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Box;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.PatternFactory;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.airspaces.Airspace;
import gov.nasa.worldwind.render.airspaces.Polygon;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import gov.nasa.worldwindx.examples.LayerPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Johan Benschop
 */
public final class atc2 extends atc {

    private static CTA cta = new CTA(null, 5, 4);
    public static ACC acc = new ACC(343, cta);

    public static class AppFrame extends atc.AppFrame {

        private apAnnotation mouseEq, latestEq;
        protected AirspaceLayer airspaceLayer;
        protected RenderableLayer airportLayer;
        protected RenderableLayer airplaneLayer;
        private GlobeAnnotation tooltipAnnotation;

        public AppFrame() {

            // Create our custom made menu system bar thingy.
            final UnityBar menuBar = new UnityBar();
            this.getContentPane().add(menuBar, java.awt.BorderLayout.WEST);

            final View view = this.getWwd().getView();

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

//                                    JDialog jdSettings = new JDialog();
//                                    jdSettings.getContentPane().add(layerPanel);
//                                    jdSettings.setVisible(true);

                                    jfSettings settings = new jfSettings(null, false);
                                    //settings.addLayerPanel(layerPanel);
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

            // Add the airport & airspace layers
            buildAirportLayer();
            buildAirspaceLayer();
            buildAirplaneLayer();

            // Add the graticule layer
            LatLonGraticuleLayer graticuleLayer = new LatLonGraticuleLayer();
            insertBeforePlacenames(getWwd(), graticuleLayer);
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
            // Add the airspace layer with our ACC in it
            this.airspaceLayer = new AirspaceLayer();
            this.airspaceLayer.setName("Airspaces");
            this.airspaceLayer.setEnableBatchPicking(false);
            insertBeforePlacenames(this.getWwd(), this.airspaceLayer);

            Polygon poly = new Polygon();

            poly.setLocations(Arrays.asList(
                    LatLon.fromDegrees(60, -10),
                    LatLon.fromDegrees(60, 10),
                    LatLon.fromDegrees(40, 10),
                    LatLon.fromDegrees(40, -10)));
            poly.setAltitudes(100000.0, 500000.0);
            poly.setTerrainConforming(true, true);
            poly.setValue(AVKey.DISPLAY_NAME, "CTA - Greater Europe");
            this.setupDefaultMaterial(poly, Color.RED);
            airspaceLayer.addAirspace(poly);
        }

        /**
         * Sets up default material.
         * @param a Airspace
         * @param color Colour
         */
        protected void setupDefaultMaterial(Airspace a, Color color) {
            a.getAttributes().setDrawOutline(true);
            a.getAttributes().setMaterial(new Material(color));
            a.getAttributes().setOutlineMaterial(new Material(WWUtil.makeColorBrighter(color)));
            a.getAttributes().setOpacity(0.8);
            a.getAttributes().setOutlineOpacity(0.9);
            a.getAttributes().setOutlineWidth(3.0);
        }

        /**
         * Builds the airport layer.
         */
        private void buildAirportLayer() {
            // Add the airport layer
            this.airportLayer = new RenderableLayer();
            this.airportLayer.setName("Airports");
            insertBeforePlacenames(this.getWwd(), this.airportLayer);

            // Init tooltip annotation
            this.tooltipAnnotation = new GlobeAnnotation("", Position.fromDegrees(0, 0, 0));
            Font font = Font.decode("Arial-Plain-16");
            this.tooltipAnnotation.getAttributes().setFont(font);
            this.tooltipAnnotation.getAttributes().setSize(new Dimension(270, 0));
            this.tooltipAnnotation.getAttributes().setDistanceMinScale(1);
            this.tooltipAnnotation.getAttributes().setDistanceMaxScale(1);
            this.tooltipAnnotation.getAttributes().setVisible(false);
            this.tooltipAnnotation.setAlwaysOnTop(true);
            airportLayer.addRenderable(this.tooltipAnnotation);

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
                    this.getWwd(), apAnnotation.class, 20000));

            ListIterator<Airport> litr = acc.GetCTA().GetAirports();

            while (litr.hasNext()) {
                addAirportToLayer(airportLayer, litr.next());
            }
        }
        private AnnotationAttributes apAttributes;

        /**
         * Adds an airport to the airport layer.
         * @param layer Airport Layer
         * @param airport Airport
         */
        private void addAirportToLayer(RenderableLayer layer, Airport airport) {
            if (apAttributes == null) {
                // Init default attributes for all eq
                apAttributes = new AnnotationAttributes();
                apAttributes.setLeader(AVKey.SHAPE_NONE);
                apAttributes.setDrawOffset(new Point(0, 0));
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

        /** 
         * Shows the annotation of an airport when param o is indeed a apAnnotation.
         * @param o Object under the mouse
         */
        private void highlightAirport(Object o) {
            if (this.mouseEq == o) {
                return; // same thing selected
            }
            if (this.mouseEq != null) {
                this.mouseEq.getAttributes().setHighlighted(false);
                this.mouseEq = null;
                this.tooltipAnnotation.getAttributes().setVisible(false);
            }

            if (o != null && o instanceof apAnnotation) {
                this.mouseEq = (apAnnotation) o;
                this.mouseEq.getAttributes().setHighlighted(true);
                this.tooltipAnnotation.setText("<p><b>" + this.mouseEq.airport.getAirportName() + "</b> ("
                        + this.mouseEq.airport.getAirportID() + ") </p>"
                        + "City = " + this.mouseEq.airport.getCity() + "<br>"
                        + "Country = " + this.mouseEq.airport.getCountry() + "<br>"
                        + "IATA/FAA = " + this.mouseEq.airport.getIATA_FAA() + "<br>"
                        + "ICAO = " + this.mouseEq.airport.getICAO() + "<br>"
                        + "Timezone = " + this.mouseEq.airport.getTimezone() + "<br>");
                this.tooltipAnnotation.setPosition(this.mouseEq.airport.getLocation().toPosition());
                this.tooltipAnnotation.getAttributes().setVisible(true);
                this.getWwd().repaint();
            }
        }

        /**
         * Build the layer with all of the airplanes.
         */
        private void buildAirplaneLayer() {
            // Add the airplane layer
            this.airplaneLayer = new RenderableLayer();
            this.airplaneLayer.setName("Airplanes");
            insertBeforePlacenames(this.getWwd(), this.airplaneLayer);

            // Add click-and-go select listener for airplanes
            this.getWwd().addSelectListener(new ClickAndGoSelectListener(
                    this.getWwd(), airplaneRendereble.class, 500)); // last value is height

            ListIterator<Flightplan> litr = acc.getFlightplans();

            while (litr.hasNext()) {
                addAirplaneToLayer(airplaneLayer, litr.next());
            }
        }

        /**
         * Adds an airplane to the airplane layer.
         */
        private void addAirplaneToLayer(RenderableLayer layer, Flightplan flightplan) {
            Airplane airplane = flightplan.getAirplane();

            // Create and set an attribute bundle.
            ShapeAttributes attrs = new BasicShapeAttributes();
            attrs.setInteriorMaterial(Material.GREEN);
            attrs.setInteriorOpacity(1);
            attrs.setEnableLighting(true);
            attrs.setOutlineMaterial(Material.RED);
            attrs.setOutlineWidth(2d);
            attrs.setDrawInterior(true);
            attrs.setDrawOutline(false);

            // We create our airplane renderables
            airplaneRendereble rend = new airplaneRendereble(airplane);
            rend.setAltitudeMode(WorldWind.ABSOLUTE);
            rend.setAttributes(attrs);
            rend.setVisible(true);
            rend.setValue(AVKey.DISPLAY_NAME, "Flight " + flightplan.getFlightnumber() + "");
            rend.setHeading(Angle.fromDegrees(airplane.getDirection()));
            layer.addRenderable(rend);
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
