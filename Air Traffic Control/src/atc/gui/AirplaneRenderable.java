package atc.gui;

import atc.logic.Airplane;
import atc.logic.Flightplan;
import atc.logic.GeoSector;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.GlobeAnnotation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author Johan Benschop
 */
public class AirplaneRenderable extends GlobeAnnotation {

    private final Airplane airplane;
    private final static Timer locationUpdateTimer = new Timer();
    private final Flightplan flightplan;
    private BufferedImage originalImage;
    private GlobeAnnotation tooltip;
    private boolean mayControl;

    public AirplaneRenderable(final Flightplan flightplan) {
        super("", flightplan.getAirplane().getLocation().toPosition());

        if (originalImage == null) {
            try {
                originalImage = ImageIO.read(new File("src/atc/gui/resources/airplane.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.flightplan = flightplan;
        this.airplane = this.flightplan.getAirplane();

        this.getAttributes().setLeader(AVKey.SHAPE_NONE);
        this.getAttributes().setDrawOffset(new Point(0, 0));
        this.getAttributes().setSize(new Dimension(0, 0));
        this.getAttributes().setBorderWidth(0);
        this.getAttributes().setCornerRadius(0);
        this.getAttributes().setBackgroundColor(new Color(0, 0, 0, 0));
        this.getAttributes().setDistanceMinScale(.5);
        this.getAttributes().setDistanceMaxScale(2);

        this.getAttributes().setDrawOffset(new Point(0, -40 / 2)); // Center the image
        this.getAttributes().setImageSource(drawHeading());
        this.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
        //this.getAttributes().setInsets(new Insets(5, 40, 5, 5));
        this.getAttributes().setFont(Font.decode("Arial-NORMAL-12"));
        //this.setText(String.valueOf(flightplan.getFlightnumber()));
        this.getAttributes().setTextColor(Color.WHITE);
        this.getAttributes().setTextAlign(AVKey.LEFT);
        //this.getAttributes().setDrawOffset(new Point(this.getAttributes().getSize().width / 2 , this.getAttributes().getSize().height / 2));
        // We update the icons position every 300 milisecond.
        locationUpdateTimer.schedule(new TimerTask() {

            private double lastDirection;

            @Override
            public void run() {
                if (atc2.airspace.getCurrentACC() == null) {
                    return;
                }
                
                double direction = airplane.getDirection();
                Position position = airplane.getLocation().toPosition();
                GeoSector sector = atc2.airspace.getCurrentACC().GetCTA().sector;
                GeoSector greaterSector = atc2.airspace.getCurrentACC().GetCTA().sectorGreater;
                
                try {
                    // If the airplane is not in the sector but is in the greater sector it must be in the 100 km buffer area.
                    if (!sector.containsGeoLocation(airplane.getLocation()) && greaterSector.containsGeoLocation(airplane.getLocation())) {
                        originalImage = ImageIO.read(new File("src/atc/gui/resources/plainegrey.png"));
                        if (flightplan.getAssignedController() != atc2.AppFrame.getFlightController()) {
                            mayControl = false;
                        }
                        else {
                            mayControl = true;
                        }
                        
                    } else if (!sector.containsGeoLocation(airplane.getLocation()) && !greaterSector.containsGeoLocation(airplane.getLocation())) {
                        // The airplane is at a place where we don't care molucules about it, remove it.
                        dispose(); // (Not sure what this does)
                    } else {
                        switch (airplane.getStatus()) {
                            default:
                                originalImage = ImageIO.read(new File("src/atc/gui/resources/airplane.png"));
                                break;
                            case CRASHING1:
                                originalImage = ImageIO.read(new File("src/atc/gui/resources/plaineyellow.png"));
                                break;
                            case CRASHING2:
                                originalImage = ImageIO.read(new File("src/atc/gui/resources/plaineorange.png"));
                                break;
                            case CRASHED:
                                originalImage = ImageIO.read(new File("src/atc/gui/resources/plainered.png"));
                                break;
                            case HASLANDED:
                                originalImage = ImageIO.read(new File("src/atc/gui/resources/plainegrey.png"));
                                break;
                        }
                    }
                    getAttributes().setImageSource(drawHeading());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                if (direction != lastDirection) {
                    getAttributes().setImageSource(drawHeading());
                    lastDirection = direction;
                }
                moveTo(position);
                if (tooltip != null && tooltip.getAttributes().isVisible()) {
                    tooltip.moveTo(position);
                    tooltip.setText(updateText());
                }
                if (airplane.getStatus().equals(Airplane.Statusses.HASLANDED)) {
                    try {
                        airplane.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    airplane.interrupt();
                }
            }
        }, 10, 300);
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Flightplan getFlightplan() {
        return flightplan;
    }

    public boolean isMayControl() {
        return mayControl;
    }

    private BufferedImage drawHeading() {
        BufferedImage image = null;
        try {
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.toRadians(airplane.getDirection()), originalImage.getWidth() / 2, originalImage.getHeight() / 2);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(originalImage, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setHoverAnnotation(GlobeAnnotation tooltip) {
        this.tooltip = tooltip;
        if (this.tooltip != null) {

            this.tooltip.setText(updateText());
            this.tooltip.moveTo(airplane.getLocation().toPosition());
        }
    }

    public void unsetHoverAnnotation() {
        this.tooltip = null;
    }

    private String updateText() {
        Locale l = new Locale("en_US");
        NumberFormat NF = NumberFormat.getNumberInstance(l);
        DecimalFormat DF = (DecimalFormat) NF;
        DF.applyPattern("#.##");
        return "<p><b><font>Flight " + flightplan.getFlightnumber() + "</font></b>"
                + "<br />Departure: "
                + flightplan.getTakeoffAirport().getCity() + ", "
                + flightplan.getTakeoffAirport().getCountry() + " ["
                + flightplan.getTakeoffAirport().getIATA_FAA() + "]"
                + "<br />Arrival: "
                + flightplan.getDestinationAirport().getCity() + ", "
                + flightplan.getDestinationAirport().getCountry() + " ["
                + flightplan.getDestinationAirport().getIATA_FAA() + "]"
                + "<br />"
                + "<br /><b>Airplane</b>"
                + "<br />[Speed: "
                + DF.format(airplane.getSpeed()) + " km/h] <br />[Altitude: "
                + airplane.getAltitude() + " feet]"
                //           +"<br />[Flightlevel: " + airplane.getFlightLevel() + "]"
                + "<br />[Direction: " + DF.format(airplane.getDirection()) + "°]"
                + "<br />Model: " + airplane.getManufacturer() + ", " + airplane.getType()
                + "<br >Current state: " + airplane.getStatus()
                + "<br> Lat/lon: (" + DF.format(airplane.getLocation().getLatitude()) + "," + DF.format(airplane.getLocation().getLongitude()) + ")"
                + "</p>";
    }
}
