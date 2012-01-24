package atc.gui;

import SysBar.*;
import atc.cli.jpTerminal;
import atc.gui.Audio.Sound;
import atc.interfaces.*;
import atc.logic.*;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.view.orbit.*;
import gov.nasa.worldwindx.examples.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.prefs.*;
import javax.swing.*;
import javax.swing.Timer;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan Benschop
 */
public final class atc2 extends atc {
    //haal Airspace ergens anders vandaan...

    public static IAirspace airspace;
    public static FlightControllerIF FC;
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
        private ArrayList<IAirplane> addedAirplanes;
        private final UnityBar menuBar;
        private final View view;
        private final Timer timerCollision;
        private boolean initDoneAirportLayer;
        private boolean initDoneAirplaneLayer;
        private boolean initDoneAirspaceLayer;
        //   private static FlightControllerIF flightController;
        private ReentrantLock lock = new ReentrantLock();

        public AppFrame() {
            //prefs.putDouble("SIM_SPEED", 1);
            if (prefs.getBoolean("APP_START-MAXIMIZED", false)) {
                this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            }

            this.addWindowListener(new java.awt.event.WindowAdapter() {

                public void windowClosing(WindowEvent winEvt) {
                    try {
                        FC.exit();
                    } catch (RemoteException re) {
                        re.printStackTrace();
                    }
                    System.exit(0);
                }
            });

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
                                    try {
                                        IAirport goToAirport = new jfSelectAirport(null, true, false).getValue();

                                        // Use a PanToIterator to iterate view to target position
                                        if (view != null && goToAirport != null) {
                                            Position targetPos = new Position(Angle.fromDegrees(goToAirport.getLocation().getLatitude()),
                                                    Angle.fromDegrees(goToAirport.getLocation().getLongitude()), goToAirport.getLocation().getAltitude());
                                            // The elevation component of 'targetPos' here is not the surface elevation,
                                            // so we ignore it when specifying the view center position.
                                            view.goTo(new Position(targetPos, 0),
                                                    targetPos.getElevation() + 20000); // 1000 = 100 meter
                                        }
                                        uiGoToAirport.setActive(false);
                                    } catch (RemoteException rex) {
                                        rex.printStackTrace();
                                    }
                                }
                            });
                        }
                    });

            final UnityItem uiSwitchCTA = menuBar.addItem(new UnityItem("Switch CTA", Color.BLUE, 0, "", UnityBar.Type.NORMAL));
            uiSwitchCTA.addActionListener(
                    new java.awt.event.ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // When we open a new dialog we should do it in it's own thread.
                            // Doing so allows the main application to continue
                            // it's work, like buzzing you when a collision is detected.
                            java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        timerAirplane.stop();
                                        getOrbitView().setOrbitViewLimits(new BasicOrbitViewLimits());
                                        Position targetPos = new Position(view.getCurrentEyePosition(), 20000000); // 1000 = 100 meter
                                        view.goTo(new Position(targetPos, 0), targetPos.getElevation());
                                        airspaceLayer.removeAllRenderables();
                                        addedAirplanes.clear();
                                        airportLayer.removeAllRenderables();
                                        airplaneLayer.removeAllRenderables();
                                        airplaneLineLayer.removeAllRenderables();
                                        //FC.getChosenACC().removeFlightController((IFC) FC); // TODO
                                        FC.setChosenACC(null);
                                        airspacesLayer.setEnabled(true);
                                    } catch (RemoteException rex) {
                                        rex.printStackTrace();
                                    }
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
                                    try {
                                        IFlightplan plan = new jfSelectFlight(null, true).getValue();

                                        // Use a PanToIterator to iterate view to target position
                                        if (view != null && plan != null) {
                                            Position targetPos = new Position(Angle.fromDegrees(plan.getAirplane().getLocation().getLatitude()),
                                                    Angle.fromDegrees(plan.getAirplane().getLocation().getLongitude()), plan.getAirplane().getLocation().getAltitude());


                                            // The elevation component of 'targetPos' here is not the surface elevation,
                                            // so we ignore it when specifying the view center position.
                                            view.goTo(new Position(targetPos, 0),
                                                    targetPos.getElevation() + 100); // 1000 = 100 meter
                                        }
                                        uiGoToFlight.setActive(false);
                                    } catch (RemoteException rex) {
                                        rex.printStackTrace();
                                    }
                                }
                            });
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
                                    try {
                                        new jfSelectAirplane(null, true).setVisible(true);
                                    } catch (RemoteException ex) {
                                        ex.printStackTrace();
                                    }
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
                    try {
                        // TODO fix this bug so all airplanes will die when crashed or haslanded...
                        IACC acc = FC.getChosenACC();
                        IFlightplan temp = null;

                        if (acc == null) {
                            return;
                        }

                        for (Iterator<IFlightplan> it = acc.getFlightplans().listIterator(); it.hasNext();) {
                            IFlightplan fp = it.next();
                            IAirplane ap = fp.getAirplane();
                            if (fp.getAirplane().getStatus().equals(IAirplane.Statusses.CRASHED)
                                    || fp.getAirplane().getStatus().equals(IAirplane.Statusses.HASLANDED)) {
                                removeAirplane(fp);
                                if (fp.getAssignedController() != null) {
                                    fp.getAssignedController().unassignFlight(fp);
                                }
                                temp = fp;
                                addedAirplanes.remove(ap);
                                acc.GetCTA().removeAirplane(ap);
                            }
                        }
                        if (temp != null) {
                            acc.removeFlightPlan(temp);
                        }
                        findCollisions();
                        getWwd().redraw();
                    } catch (RemoteException rex) {
                        rex.printStackTrace();
                    }
                }
            });
            timerCollision.start();
            // Add the graticule layer
            LatLonGraticuleLayer graticuleLayer = new LatLonGraticuleLayer();

            insertBeforePlacenames(getWwd(), graticuleLayer);
            graticuleLayer.setEnabled(
                    false);

            // Add the selecteble airspaces/CTA layers
            try {
                buildSelectebleAirspaceLayer();
            } catch (RemoteException rex) {
                rex.printStackTrace();
            }
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
//            try {
//                flightController = new FlightControllerIF();
//            } catch (RemoteException rex) {
//                rex.printStackTrace();
//            }
        }

        /**
         * Finds collisions and gives a warning about it.
         */
        public void findCollisions() throws RemoteException {
            if (addedAirplanes == null) {
                return;
            }

            menuBar.clearAlerts();
            for (final IAirplane p : addedAirplanes) {
                if (p.getStatus() == IAirplane.Statusses.CRASHING2) {
                    //      Audio.play(Sound.ALARM5, 3);
                    menuBar.addItem(new UnityItem("Collision detected! Mayor!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                            new java.awt.event.ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        //.goTo(Position position, double distance);
                                        // This object class we handle and we have an orbit view
                                        Position targetPos = new Position(Angle.fromDegrees(p.getLocation().getLatitude()),
                                                Angle.fromDegrees(p.getLocation().getLongitude()), p.getLocation().getAltitude());
                                        // Use a PanToIterator to iterate view to target position
                                        if (view != null) {
                                            // The elevation component of 'targetPos' here is not the surface elevation,
                                            // so we ignore it when specifying the view center position.
                                            view.goTo(new Position(targetPos, 0),
                                                    targetPos.getElevation() + 300); // 1000 = 100 meter
                                        }
                                    } catch (RemoteException rex) {
                                        rex.printStackTrace();
                                    }
                                }
                            });
                } else if (p.getStatus() == IAirplane.Statusses.CRASHING1) {
                    //          Audio.play(Sound.ALARM4, 3);
                    menuBar.addItem(new UnityItem("Collision detected! Minor!", Color.RED, 0, "src/atc/gui/resources/collision.png", UnityBar.Type.ALERT)).addActionListener(
                            new java.awt.event.ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        //.goTo(Position position, double distance);
                                        // This object class we handle and we have an orbit view
                                        Position targetPos = new Position(Angle.fromDegrees(p.getLocation().getLatitude()),
                                                Angle.fromDegrees(p.getLocation().getLongitude()), p.getLocation().getAltitude());

                                        // Use a PanToIterator to iterate view to target position
                                        if (view != null) {
                                            // The elevation component of 'targetPos' here is not the surface elevation,
                                            // so we ignore it when specifying the view center position.
                                            view.goTo(new Position(targetPos, 0),
                                                    targetPos.getElevation() + 300); // 1000 = 100 meter
                                        }
                                    } catch (RemoteException rex) {
                                        rex.printStackTrace();
                                    }
                                }
                            });
                } else if (p.getStatus() == IAirplane.Statusses.CRASHED) {
                    //     Audio.play(Sound.ALARM3, 3);
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
         * Static method to get the current flightCntroller object.
         * @return 
         */
        public static FlightControllerIF getFlightController() {
            return FC;
        }

        /**
         * Build the airspace layer.
         */
        private void buildSelectebleAirspaceLayer() throws RemoteException {
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
            for (Iterator<IACC> it = airspace.GetACCs().listIterator(); it.hasNext();) {
                IACC acc = it.next();

                SurfaceSector surfaceSector = new SurfaceSector(Sector.fromDegrees(acc.GetCTA().getSector().getMinLatitude(),
                        acc.GetCTA().getSector().getMaxLatitude(), acc.GetCTA().getSector().getMinLongitude(), acc.GetCTA().getSector().getMaxLongitude()));
                surfaceSector.setAttributes(attr);
                surfaceSector.setPathType(AVKey.RHUMB_LINE);
                surfaceSector.setValue("IACC", acc); // We bind the surfaceSector and it's ACC together.
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
                            Object o2 = surfaceSector.getValue("IACC");
                            if (o2 != null && o2 instanceof IACC) {
                                // We found the correct object! Now we need to cast and build the needed layer.
                                IACC acc = (IACC) o2;
                                try {
                                    buildAirspaceLayer(acc);
                                    //airspace.setCurrentACC(acc);
                                    atc2.airspace.getACC(acc.GetID()).setAdjacentACCList(airspace.getAdjacentACCs(acc.GetID()));


                                    // Since the user has selected his or hers CTA we don't need to show this layer anymore.
                                    buildAirportLayer();
                                    buildAirplaneLayer();
                                } catch (RemoteException rex) {
                                    rex.printStackTrace();
                                }
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
        private void buildAirspaceLayer(IACC acc) throws RemoteException {
            // Init part
            if (!initDoneAirspaceLayer) {
                // We make a new layer for the selected CTA.
                airspaceLayer = new RenderableLayer();
                insertBeforePlacenames(this.getWwd(), airspaceLayer);
                airspaceLayer.setName("Airspace");
                airspaceLayer.setPickEnabled(false); // We don't want picking.
                initDoneAirspaceLayer = true;
            }

            // Set the attributes for the surfaceSector and instanciate the objects.
            SurfaceSector surfaceSector = new SurfaceSector(Sector.fromDegrees(acc.GetCTA().getSector().getMinLatitude(),
                    acc.GetCTA().getSector().getMaxLatitude(), acc.GetCTA().getSector().getMinLongitude(), acc.GetCTA().getSector().getMaxLongitude()));
            ShapeAttributes attributesSector = new BasicShapeAttributes();
            attributesSector.setOutlineMaterial(Material.PINK);
            attributesSector.setInteriorOpacity(0);
            attributesSector.setOutlineOpacity(0.8);
            attributesSector.setOutlineWidth(3);
            attributesSector.setEnableAntialiasing(true);
            surfaceSector.setAttributes(attributesSector);
            surfaceSector.setPathType(AVKey.RHUMB_LINE);

            // Set the attributes for the greater surfaceSector and instanciate the objects.
            IGeoSec greSec = acc.GetCTA().getGreaterSector();
            SurfaceSector surfaceSectorGreater = new SurfaceSector(Sector.fromDegrees(greSec.getMinLatitude(),
                    greSec.getMaxLatitude(), greSec.getMinLongitude(), greSec.getMaxLongitude()));
            ShapeAttributes attributesGreaterSector = new BasicShapeAttributes();
            attributesGreaterSector.setOutlineMaterial(Material.ORANGE);
            attributesGreaterSector.setInteriorOpacity(0);
            attributesGreaterSector.setOutlineOpacity(0.8);
            attributesGreaterSector.setOutlineWidth(3);
            attributesGreaterSector.setEnableAntialiasing(true);
            surfaceSectorGreater.setAttributes(attributesGreaterSector);
            surfaceSectorGreater.setPathType(AVKey.RHUMB_LINE);

            // Add the two SurfaceSectors to the airspace layer.
            airspaceLayer.addRenderable(surfaceSector);
            airspaceLayer.addRenderable(surfaceSectorGreater);

            // Set the current ACC to the selected one.
            //airspace.setCurrentACC(acc);
            FC.setChosenACC(acc);

            // We register the controller to this ACC.
            FC.getChosenACC().addFlightController(FC.GetFlightController());

            // Playing the boss here. We limit the users ability to go outside of their job area.
            // We don't want any FlightControllers spending time on useless stuff.
            OrbitView view = this.getOrbitView();
            if (view != null) {
                OrbitViewLimits limits = view.getOrbitViewLimits();
                if (limits != null) {
                    Globe globe = this.getWwd().getModel().getGlobe();

                    limits.setCenterLocationLimits(Sector.fromDegrees(acc.GetCTA().getSector().getMinLatitude(),
                            acc.GetCTA().getSector().getMaxLatitude(), acc.GetCTA().getSector().getMinLongitude(), acc.GetCTA().getSector().getMaxLongitude()));
                    limits.setZoomLimits(10000, 5000000);
                    BasicOrbitViewLimits.applyLimits(view, limits);
                }
                surfaceSectorGreater.setSector(limits.getCenterLocationLimits());
            }
            // Loads the airports into an ArrayList;
            FC.loadAirportsInACC();
            FC.loadAirportsEverywhere();
        }

        /**
         * Builds the airport layer.
         */
        private void buildAirportLayer() throws RemoteException {
            // The init part
            if (!initDoneAirportLayer) {
                // Add the airport layer
                this.airportLayer = new RenderableLayer();
                this.airportLayer.setName("Airports");
                insertBeforePlacenames(this.getWwd(), this.airportLayer);

                // Add select listener for airport picking
                this.getWwd().addSelectListener(new SelectListener() {

                    public void selected(SelectEvent event) {
                        try {
                            if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
                                highlightAirport(event.getTopObject());
                            }
                        } catch (RemoteException rex) {
                            rex.printStackTrace();
                        }
                    }
                });

                // Add click-and-go select listener for airports
                this.getWwd().addSelectListener(new ClickAndGoSelectListener(
                        this.getWwd(), AirportRenderable.class, 20000));

                initDoneAirportLayer = true;
            }

            airportLayer.addRenderable(this.tooltipAnnotation);

            IACC acc = FC.getChosenACC();
            ICTA cta = acc.GetCTA();

            ListIterator<IAirport> litr = cta.GetAirports().listIterator();

            if (litr != null) {
                while (litr.hasNext()) {
                    IAirport airport = litr.next();
                    addAirportToLayer(airportLayer, airport);
                }
            }
        }

        /**
         * Adds an airport to the airport layer.
         * @param layer Airport Layer
         * @param airport Airport
         */
        private void addAirportToLayer(RenderableLayer layer, IAirport airport) throws RemoteException {
            layer.addRenderable(new AirportRenderable(airport));
        }

        /**
         * Shows the annotation of an airport when param o is indeed a AirportRenderable.
         * @param o Object under the mouse
         */
        private void highlightAirport(Object o) throws RemoteException {
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
                tooltipAnnotation.setPosition(new Position(Angle.fromDegrees(currentAirportAnnotation.airport.getLocation().getLatitude()),
                        Angle.fromDegrees(currentAirportAnnotation.airport.getLocation().getLongitude()), currentAirportAnnotation.airport.getLocation().getAltitude()));
                tooltipAnnotation.getAttributes().setVisible(true);
                this.getWwd().repaint();
            }
        }

        /**
         * Build the layer with all of the airplanes.
         */
        private void buildAirplaneLayer() {
            // The init part
            if (!initDoneAirplaneLayer) {
                // Add the airplane layer
                this.addedAirplanes = new ArrayList<>();
                this.airplaneLayer = new RenderableLayer();
                this.airplaneLayer.setName("Airplanes");
                insertBeforePlacenames(this.getWwd(), this.airplaneLayer);

                // Add the airplane line layer
                airplaneLineLayer = new RenderableLayer();
                insertBeforePlacenames(this.getWwd(), airplaneLineLayer);
                airplaneLineLayer.setName("Airpline lines");

                // Add click-and-go select listener for airplanes
                this.getWwd().addSelectListener(new ClickAndGoSelectListener(
                        this.getWwd(), AirplaneRenderable.class, 50000)); // last value is height

                // Add select listener for airport picking
                this.getWwd().addSelectListener(new SelectListener() {

                    public void selected(SelectEvent event) {
                        try {
                            if (event.getEventAction().equals(SelectEvent.LEFT_DOUBLE_CLICK)) {
                                clickAirplane(event.getTopObject());
                            }
                            if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
                                highlightAirplane(event.getTopObject());
                            }
                            if (event.getEventAction().equals(SelectEvent.RIGHT_CLICK)) {
                                drawLineAirplane(event.getTopObject());
                            }
                        } catch (RemoteException rex) {
                            rex.printStackTrace();
                        }
                    }
                });

                this.timerAirplane = new Timer(2000, new ActionListener() {

                    public synchronized void actionPerformed(ActionEvent event) {
                        try {
                            if (FC.getChosenACC() != null && FC.getChosenACC().GetID() != 1000) {
                                try {
                                    airspace.BorderControl();
                                } catch (Exception e) {
                                    System.err.println(e);
                                }
                            }
//                             pull method as a safety backup
//                            for (Iterator<IFlightplan> it = airspace.getCurrentACC().getFlightplans().listIterator(); it.hasNext();) {
//                                addAirplaneToLayer(airplaneLayer, it.next());
//                            }
                            try {
                                for (Iterator<IFlightplan> it = FC.getFlightplans().listIterator(); it.hasNext();) {
                                    addAirplaneToLayer(airplaneLayer, it.next());
                                }
                            } catch (ConcurrentModificationException e) {
//                                // swallow it!
                            }

                            // Remove renderebles when they are not needed anymore...
                            for (final Renderable renderable : airplaneLayer.getRenderables()) {
                                AirplaneRenderable airplaneRendereble = (AirplaneRenderable) renderable;

                                //if (addedAirplanes.contains(airplaneRendereble.getAirplane())) {
                                if (!FC.getChosenACC().GetCTA().getGreaterSector().containsGeoLocation(airplaneRendereble.getAirplane().getLocation())) {
                                    System.out.println("Found one to remove!");
                                    airplaneLayer.removeRenderable(renderable);
                                    return;
                                }
                                else if (airplaneRendereble.getAirplane().getStatus() == IAirplane.Statusses.CRASHED
                                        || airplaneRendereble.getAirplane().getStatus() == IAirplane.Statusses.HASLANDED) {
                                    if (!airplaneRendereble.isScheduledForDeletion()) {

                                        new Timer(10000, new ActionListener() {
                                            final Renderable render = renderable;
                                            
                                            public void actionPerformed(ActionEvent event) {
                                                airplaneLayer.removeRenderable(renderable);
                                            }
                                        }).start();

                                        airplaneRendereble.setScheduledForDeletion(true);
                                    }
                                    return;
                                }

                                return;
                                // }

                            }

                        } catch (RemoteException rex) {
                            rex.printStackTrace();
                        }
                    }
                });

                initDoneAirplaneLayer = true;
            }

            timerAirplane.start();

        }

        /**
         * Adds an airplane to the airplane layer.
         * @param layer
         * @param flightplan
         */
        private void addAirplaneToLayer(RenderableLayer layer, IFlightplan flightplan) throws RemoteException {
            IAirplane airplane = flightplan.getAirplane();

            if (!addedAirplanes.contains(airplane)) {
                addedAirplanes.add(airplane);

                // We define a set of attributes that all the SurfaceSectors share.
                ShapeAttributes attr = new BasicShapeAttributes();
                attr.setOutlineMaterial(Material.WHITE);
                attr.setOutlineOpacity(0.7);
                attr.setOutlineWidth(3);
                attr.setEnableAntialiasing(true);

                SurfacePolyline path = new SurfacePolyline();
                path.setAttributes(attr);
                airplaneLineLayer.addRenderable(path);

                layer.addRenderable(new AirplaneRenderable(flightplan, path));
            }


//                for (Airplane airplane1 : addedAirplanes) {
//                    if (!airspace.getCurrentACC().GetCTA().sectorGreater.containsGeoLocation(airplane1.getLocation())) {
//                        System.out.println("Out!");
//                        // Airplane is not in the area, we may remove it from the GUI.
//                        addedAirplanes.remove(airplane1);
//                        //AirplaneRenderable airplaneRenderebleToRemove = null;
//                        for (Renderable renderable : airplaneLayer.getRenderables()) {
//                            AirplaneRenderable airplaneRendereble = (AirplaneRenderable) renderable;
//                            if (airplaneRendereble.getAirplane().equals(airplane1)) {
//                                System.out.println("Found one to remove!");
//                                //airplaneRenderebleToRemove = airplaneRendereble;
//                                airplaneLayer.removeRenderable(renderable);
//                                return;
//                            }
//                        }
//                        System.out.println("No one found!!!");
//                    }
//                }

        }

        /**
         * Shows the annotation of an airport when param o is indeed a AirportRenderable.
         * @param o Object under the mouse
         */
        private void highlightAirplane(Object o) throws RemoteException {
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

        private void drawLineAirplane(Object o) {
//            if (currentAirplaneAnnotation == o) {
//                return; // same thing selected
//            }
            if (o != null && o instanceof AirplaneRenderable) {
                AirplaneRenderable rend = (AirplaneRenderable) o;


                boolean bool;

                if (rend.getValue("TRUE_DRAW_LINE") == null) {
                    rend.setValue("TRUE_DRAW_LINE", true);
                    bool = false;
                } else {
                    bool = (boolean) rend.getValue("TRUE_DRAW_LINE");
                }

                System.out.println(bool);
                if (bool == true) {
                    rend.setValue("TRUE_DRAW_LINE", false);
                    System.out.println("2");
                } else {
                    rend.setValue("TRUE_DRAW_LINE", true);
                    System.out.println("3");
                }
            }
        }

        private void clickAirplane(Object o) throws RemoteException {
            if (o.getClass() != AirplaneRenderable.class) {
                return; // The selected object isn't our airplane.
            }
            AirplaneRenderable rend = (AirplaneRenderable) o;
            //Has to be !rend.isMayControl otherwise it can't have control....
            if (rend.isMayControl()) {
                // The user may control this airplane.
                IFlightplan flightplan = rend.getFlightplan();
                new jfCommandFlight(this, true).setFlightplan(flightplan);
            }
        }

        private void removeAirplane(IFlightplan flightplan) throws RemoteException {
            for (Iterator<Renderable> it = airplaneLayer.getRenderables().iterator(); it.hasNext();) {
                Object object = it.next();
                if (object instanceof AirplaneRenderable) {
                    AirplaneRenderable ar = (AirplaneRenderable) object;
                    if (ar.getFlightplan() == flightplan) {
                        airplaneLayer.removeRenderable(ar);
                        addedAirplanes.remove(flightplan.getAirplane());
                        ar.dispose();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            FC = new FlightControllerIF();
            airspace = FC.getAirspace();
        } catch (RemoteException ex) {
            Logger.getLogger(atc2.class.getName()).log(Level.SEVERE, null, ex);
        }

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
