package atc.gui;

import SysBar.UnityBar;
import SysBar.UnityItem;
import atc.cli.jpTerminal;
import atc.gui.Audio.Sound;
import atc.logic.ACC;
import atc.logic.Airplane;
import atc.logic.Airport;
import atc.logic.Airspace;
import atc.logic.CTA;
import atc.logic.Flightplan;
import atc.logic.GeoLocation;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceSector;
import gov.nasa.worldwind.view.orbit.BasicOrbitViewLimits;
import gov.nasa.worldwind.view.orbit.OrbitView;
import gov.nasa.worldwind.view.orbit.OrbitViewLimits;
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
import java.util.Iterator;
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

//    private static CTA cta2 = new CTA(new GeoSector(40, 60, -10, 10));
//    private static CTA cta = new CTA(new GeoSector(40, 60, 10, 30));
//    public static ACC acc = new ACC(343, cta);
//    public static ACC acc2 = new ACC(344, cta2);
    public static Airspace airspace = new Airspace();
    private static Preferences prefs = Preferences.userRoot().node("/atc/gui");

    public static class AppFrame extends atc.AppFrame {

        private AirportRenderable currentAirportAnnotation;
        private AirplaneRenderable currentAirplaneAnnotation;
        protected RenderableLayer airspaceLayer;
        protected RenderableLayer airspacesLayer;
        protected RenderableLayer airportLayer;
        protected RenderableLayer airplaneLayer;
        protected RenderableLayer airplaneLineLayer;
        private GlobeAnnotation tooltipAnnotation;
        private Timer timerAirplane;
        private ArrayList<Airplane> addedAirplanes;
        private final UnityBar menuBar;
        private final View view;
        private final Timer timerCollision;
        private double TimeOfLine;

        public AppFrame() {
            prefs.putDouble("SIM_SPEED", 1);
            airplaneLineLayer = new RenderableLayer();
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
            //this.getContentPane().add(menuBar, java.awt.BorderLayout.LINE_START);
            super.getWwjPanel().add(menuBar, java.awt.BorderLayout.WEST);
            //super.getContentPane().add(menuBar, java.awt.BorderLayout.WEST);

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

            this.timerCollision = new Timer(prefs.getInt("WWD_REFRESHRATE", 500), new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    for (Iterator<ACC> it = airspace.GetACCs(); it.hasNext();) {
                        ACC acc = it.next();
                        // TODO have to be made working in order to delete inactive airplanes
//                        Flightplan fp = currentAirplaneAnnotation.getFlightplan();
//                        Airplane airplane = fp.getAirplane();
//                        if (airplane.getStatus().equals(Airplane.Statusses.CRASHED) |
//                                airplane.getStatus().equals(Airplane.Statusses.HASLANDED)) {
//                            removeAirplaneFromLayer(airplaneLayer, fp);
//                        }

                    }
                    findCollisions();
                    getWwd().redraw();
                }
            });
            timerCollision.start();

            // Add the graticule layer

            LatLonGraticuleLayer graticuleLayer = new LatLonGraticuleLayer();
            insertBeforePlacenames(getWwd(), graticuleLayer);
            graticuleLayer.setEnabled(false);

            // Add the airport & airspace layers
            buildAirportLayer();
            buildSelectebleAirspaceLayer();
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

            atc2.airspace.setCurrentACC(atc2.airspace.getACC(1000)); //DIT WAS 0 maar heeft paul verandert omdat het ID begint op 1000.
        }

        /**
         * Finds collisions and gives a warning about it.
         */
        public void findCollisions() {
            menuBar.clearAlerts();
            for (final Airplane p : addedAirplanes) {
                if (p.getStatus() == Airplane.Statusses.CRASHING2) {
                    Audio.play(Sound.ALARM5, 3);
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
                } else if (p.getStatus() == Airplane.Statusses.CRASHING1) {
                    Audio.play(Sound.ALARM4, 3);
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
                } else if (p.getStatus() == Airplane.Statusses.CRASHED) {
                    Audio.play(Sound.ALARM3, 3);
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
         */
        private void buildSelectebleAirspaceLayer() {
            // We make a new layer for the selecteble CTA's.
            airspacesLayer = new RenderableLayer();
            insertBeforePlacenames(this.getWwd(), airspacesLayer);
            airspacesLayer.setName("Airspaces");
            airspacesLayer.setPickEnabled(true); // This makes it selecteble....

            // We define a set of attributes that all the SurfaceSectors share.
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setInteriorMaterial(Material.WHITE);
            attr.setOutlineMaterial(Material.RED);
            attr.setInteriorOpacity(0.5);
            attr.setOutlineOpacity(0.7);
            attr.setOutlineWidth(3);
            attr.setEnableAntialiasing(true);

            // We go through all the ACC's to draw the SurfaceSector inside the airspaceLayer.
            for (Iterator<ACC> it = airspace.GetACCs(); it.hasNext();) {
                ACC acc = it.next();

                SurfaceSector surfaceSector = new SurfaceSector(acc.GetCTA().getSector().toSector());
                surfaceSector.setAttributes(attr);
                surfaceSector.setPathType(AVKey.RHUMB_LINE);
                surfaceSector.setValue("ACC", acc); // We bind the surfaceSector and it's ACC together.
                airspacesLayer.addRenderable(surfaceSector);
            }

            // Add select listener for CTA picking
            this.getWwd().addSelectListener(new SelectListener() {

                public void selected(SelectEvent event) {
                    if (event.getEventAction().equals(SelectEvent.LEFT_DOUBLE_CLICK)) {
                        // Let's check if we got the right object picked.
                        Object o = event.getTopObject();
                        if (o != null && o instanceof SurfaceSector) {
                            SurfaceSector surfaceSector = (SurfaceSector) o;
                            Object o2 = surfaceSector.getValue("ACC");
                            if (o2 != null && o2 instanceof ACC) {
                                // We found the correct object! Now we need to cast and build the needed layer.
                                ACC acc = (ACC) o2;
                                buildAirspaceLayer(acc);
                                
                               //
                                airspace.setCurrentACC(acc);
                                atc2.airspace.getACC(acc.GetID()).setAdjacentACCList(airspace.getAdjacentACCs(acc.GetID()));
                                //
                                // Since the user has selected his or hers CTA we don't need to show this layer anymore.
                                buildAirportLayer();
                                airspacesLayer.setEnabled(false);
                                // NOTE: we keep this alive since RAM is more abundant than CPU.
                                // The user can always decide to switch CTA and then we would need to remake the CTA, wich is CPU intesive.
                            }
                        }
                    }
                    if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
                    }
                }
            });

        }

        /**
         * Build the airspace layer based on a ACC.
         */
        private void buildAirspaceLayer(ACC acc) {
            // We make a new layer for the selected CTA.
            airspaceLayer = new RenderableLayer();
            insertBeforePlacenames(this.getWwd(), airspaceLayer);
            airspaceLayer.setName("Airspace");
            airspaceLayer.setPickEnabled(false); // We don't want picking.
            // Set the attributes for the surfaceSector and instanciate the objects.
            SurfaceSector surfaceSector = new SurfaceSector(acc.GetCTA().getSector().toSector());
            ShapeAttributes attributesSector = new BasicShapeAttributes();
            attributesSector.setOutlineMaterial(Material.RED);
            attributesSector.setInteriorOpacity(0);
            attributesSector.setOutlineOpacity(0.7);
            attributesSector.setOutlineWidth(3);
            attributesSector.setEnableAntialiasing(true);
            surfaceSector.setAttributes(attributesSector);
            surfaceSector.setPathType(AVKey.RHUMB_LINE);

            // Set the attributes for the greater surfaceSector and instanciate the objects.
            acc.GetCTA().CreateGreaterSector(); // TODO why do we need to do this manually?
            SurfaceSector surfaceSectorGreater = new SurfaceSector(acc.GetCTA().sectorGreater.toSector());
            ShapeAttributes attributesGreaterSector = new BasicShapeAttributes();
            attributesGreaterSector.setOutlineMaterial(Material.GREEN);
            attributesGreaterSector.setInteriorOpacity(0);
            attributesGreaterSector.setOutlineOpacity(0.7);
            attributesGreaterSector.setOutlineWidth(3);
            attributesGreaterSector.setEnableAntialiasing(true);
            surfaceSectorGreater.setAttributes(attributesGreaterSector);
            surfaceSectorGreater.setPathType(AVKey.RHUMB_LINE);

            // Add the two SurfaceSectors to the airspace layer.
            airspaceLayer.addRenderable(surfaceSector);
            airspaceLayer.addRenderable(surfaceSectorGreater);

            // Set the current ACC to the selected one.
            airspace.setCurrentACC(acc);
            
            // TODO Need to remove this and add the based on usage!
            airspace.getCurrentACC().addFlightController();
            //airspace.getCurrentACC().addFlightController();
            //airspace.getCurrentACC().addFlightController();
            
            // Playing the boss here. We limit the users ability to go outside of their job area.
            // We don't want any FlightControllers spending time on useless stuff.
            OrbitView view = this.getOrbitView();
            if (view != null) {
                OrbitViewLimits limits = view.getOrbitViewLimits();
                if (limits != null) {
                    Globe globe = this.getWwd().getModel().getGlobe();

                    limits.setCenterLocationLimits(acc.GetCTA().sectorGreater.toSector());
                    limits.setZoomLimits(0, 5000000);
                    BasicOrbitViewLimits.applyLimits(view, limits);
                }
                surfaceSectorGreater.setSector(limits.getCenterLocationLimits());
            }
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


//            for (Iterator<ACC> it = airspace.GetACCs(); it.hasNext();) {
//                ACC acc = it.next();
            //airspace.setCurrentACC(0);
            ACC acc = airspace.getCurrentACC();
            CTA cta = acc.GetCTA();
            if (cta.GetAirports() != null) {
                ListIterator<Airport> litr = cta.GetAirports();
                while (litr.hasNext()) {
                    Airport airport = litr.next();
                    addAirportToLayer(airportLayer, airport);
                    System.err.println(airport.getAirportID());
                }

            } else {
                System.err.println("it dun work");
            }

//            while (litr.hasNext()) {
//                addAirportToLayer(airportLayer, litr.next());


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
        
        //Teken nu voor de airplanes een lijn van 100 km.
         public void createAirplaneLines(){
            // We make a new layer for the lines.

            airplaneLineLayer.removeAllRenderables();
            insertBeforePlacenames(this.getWwd(), airplaneLineLayer);
            airplaneLineLayer.setName("AirplineLines");

            // We define a set of attributes that all the SurfaceSectors share.
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setInteriorMaterial(Material.WHITE);
            attr.setOutlineMaterial(Material.WHITE);
            attr.setInteriorOpacity(0.5);
            attr.setOutlineOpacity(0.7);
            attr.setOutlineWidth(3);
            attr.setEnableAntialiasing(true);
            
            for(Airplane a : atc2.airspace.getCurrentACC().GetCTA().getAirplaneList())
            {
            ArrayList<Position> pathPositions = new ArrayList<Position>();           
            pathPositions.add(Position.fromDegrees(a.getLocation().getLatitude(), a.getLocation().getLongitude()));               
            
            double d = (a.getSpeed() / 60) * 5;
            //double d = 100;
            //double d = (a.getSpeed() / 36000d)*300;
        double θ = a.getDirection() / 180d * Math.PI;
        double R = 6371; // Mean radius / radius of the Earh

        double lat = a.getLocation().getLatitude() / 180d * Math.PI;
        double lon = a.getLocation().getLongitude() / 180d * Math.PI;

        double destLat = Math.asin(Math.sin(lat) * Math.cos(d / R)
                + Math.cos(lat) * Math.sin(d / R) * Math.cos(θ));
        double destLon = lon + Math.atan2(Math.sin(θ) * Math.sin(d / R) * Math.cos(lat),
                Math.cos(d / R) - Math.sin(lat) * Math.sin(destLat));
        GeoLocation newGeoLoc;
        newGeoLoc = new GeoLocation((destLat * 180 / Math.PI), (destLon * 180 / Math.PI));
        pathPositions.add(newGeoLoc.toPosition());
        
                    Path path = new Path(pathPositions);
            path.setAttributes(attr);
            path.setPathType(AVKey.RHUMB_LINE);
            airplaneLineLayer.addRenderable(path);
            }
            

        
             
             /*this.airplaneLineLayer = new RenderableLayer();
            this.airplaneLayer.setName("AirplaneLines");
            insertBeforePlacenames(this.getWwd(), this.airplaneLayer);
            
            
             ShapeAttributes attrs = new BasicShapeAttributes();                 
            attrs.setOutlineMaterial(new Material(Color.WHITE));
            attrs.setOutlineWidth(2d);
            attrs.setInteriorOpacity(0);
            attrs.setOutlineOpacity(0.7);
            attrs.setOutlineWidth(3);
            for(Airplane a : atc2.airspace.getCurrentACC().GetCTA().getAirplaneList())
            {
            ArrayList<Position> pathPositions = new ArrayList<Position>();           
            pathPositions.add(Position.fromDegrees(a.getLocation().getLatitude(), a.getLocation().getLongitude()));               
            
            double d = 100;
            //double d = (a.getSpeed() / 36000d)*300;
        double θ = a.getDirection() / 180d * Math.PI;
        double R = 6371; // Mean radius / radius of the Earh

        double lat = a.getLocation().getLatitude() / 180d * Math.PI;
        double lon = a.getLocation().getLongitude() / 180d * Math.PI;

        double destLat = Math.asin(Math.sin(lat) * Math.cos(d / R)
                + Math.cos(lat) * Math.sin(d / R) * Math.cos(θ));
        double destLon = lon + Math.atan2(Math.sin(θ) * Math.sin(d / R) * Math.cos(lat),
                Math.cos(d / R) - Math.sin(lat) * Math.sin(destLat));
        GeoLocation newGeoLoc;
        newGeoLoc = new GeoLocation((destLat * 180 / Math.PI), (destLon * 180 / Math.PI));
        pathPositions.add(newGeoLoc.toPosition());
            Path path = new Path(pathPositions);
            airplaneLayer.addRenderable(path);
            //airplaneLineLayer.addRenderable(path);
            }*/
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
                    createAirplaneLines();
                    if(airspace.getCurrentACC() != null && airspace.getCurrentACC().GetID() != 1000)
                    {
                    System.out.println(airspace.getCurrentACC().GetID());
                    airspace.BorderControl2();        
                    }
                    for (Iterator<ACC> it = airspace.GetACCs(); it.hasNext();) {
                        ACC acc = it.next();
                        ListIterator<Flightplan> litr = acc.getFlightplans();

                        while (litr.hasNext()) {
                            addAirplaneToLayer(airplaneLayer, litr.next());
                        }
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
            AirplaneRenderable airplanerenderable = null;

            if (!addedAirplanes.contains(airplane)) {
                addedAirplanes.add(airplane);
                airplanerenderable = new AirplaneRenderable(flightplan);
                layer.addRenderable(airplanerenderable);
            }
        }

        /**
         * Removes an airplane from the airplane layer.
         * @param layer
         * @param flightplan
         */
        private void removeAirplaneFromLayer(RenderableLayer layer, Flightplan flightplan) {
            Airplane airplane = flightplan.getAirplane();

            if (airplane.getStatus().equals(Airplane.Statusses.CRASHED)
                    | airplane.getStatus().equals(Airplane.Statusses.HASLANDED)) {
                if (addedAirplanes.contains(airplane)) {
                    addedAirplanes.remove(airplane);
                    layer.removeRenderable(currentAirplaneAnnotation);
                    airplane.interrupt();
                    currentAirplaneAnnotation = null;
                    airplane = null;
                }
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