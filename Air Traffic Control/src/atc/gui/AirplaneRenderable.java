package atc.gui;

import atc.logic.Airplane;
import atc.logic.Flightplan;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.GlobeAnnotation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
    private static BufferedImage originalImage;
    private GlobeAnnotation tooltip;

    public AirplaneRenderable(Flightplan flightplan) {
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
                double direction = airplane.getDirection();
                Position position = airplane.getLocation().toPosition();

                if (direction != lastDirection) {
                    getAttributes().setImageSource(drawHeading());
                    lastDirection = direction;
                }
                moveTo(position);
                if (tooltip != null && tooltip.getAttributes().isVisible()) {
                    tooltip.moveTo(position);
                    tooltip.setText(updateText());
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
                DecimalFormat DF = new DecimalFormat("#.##");
        return "<p><b><font color=\"#664400\">Flight " + flightplan.getFlightnumber() + "</font></b>"
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
                + "<br />["
                + DF.format(airplane.getSpeed()) + " km/h], ["
                + airplane.getAltitude() + " feet | UNK], ["
                + airplane.getDirection() + "&deg;]"
                + "<br />Model: " + airplane.getManufacturer() + ", " + airplane.getType()
                + "</p>";
    }
}
