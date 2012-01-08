package atc.gui;

import atc.logic.Airport;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.render.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.nio.DoubleBuffer;
import javax.media.opengl.GL;

/**
 *
 * @author Johan Benschop
 */
public class AirportRenderable extends GlobeAnnotation {

    public Airport airport;

    public AirportRenderable(Airport airport) {
        super("", airport.getLocation().toPosition());
        this.airport = airport;

        this.getAttributes().setLeader(AVKey.SHAPE_NONE);
        //this.getAttributes().setDrawOffset(new Point(0, 0));
        this.getAttributes().setSize(new Dimension(32, 32));
        this.getAttributes().setBorderWidth(0);
        this.getAttributes().setCornerRadius(0);
        this.getAttributes().setBackgroundColor(new Color(0, 0, 0, 0));

        this.getAttributes().setImageSource(PatternFactory.createPattern(PatternFactory.PATTERN_CIRCLE, .8f, Color.BLUE));
        this.getAttributes().setDrawOffset(new Point(0, -32 / 2)); // Center the image
        this.getAttributes().setTextColor(Color.BLUE);
        this.getAttributes().setScale(1);

        // If we wish to set min and max altitudes we can use:
        this.setMinActiveAltitude(5000d);
        this.setMaxActiveAltitude(1500000d);
    }

//    protected void applyScreenTransform(DrawContext dc, int x, int y, int width, int height, double scale) {
//        double finalScale = scale * this.computeScale(dc);
//
//        GL gl = dc.getGL();
//        gl.glTranslated(x, y, 0);
//        gl.glScaled(finalScale, finalScale, 1);
//    }
//    // Override annotation drawing for a simple circle
//    private DoubleBuffer shapeBuffer;
//
//    protected void doDraw(DrawContext dc, int width, int height, double opacity, Position pickPosition) {
//        // Draw colored circle around screen point - use annotation's text color
//        if (dc.isPickingMode()) {
//            this.bindPickableObject(dc, pickPosition);
//        }
//
//        this.applyColor(dc, this.getAttributes().getTextColor(), 0.6 * opacity, true);
//
//        // Draw 32x32 shape from its bottom left corner
//        int size = 32;
//        if (this.shapeBuffer == null) {
//            this.shapeBuffer = FrameFactory.createShapeBuffer(AVKey.SHAPE_ELLIPSE, size, size, 0, null);
//        }
//        dc.getGL().glTranslated(-size / 2, -size / 2, 0);
//
//        FrameFactory.drawBuffer(dc, GL.GL_TRIANGLE_FAN, this.shapeBuffer);
//    }
    
    
    public String getAnnotationText() {
        return "<p><b><font>"
                + "" + airport.getAirportName() + " (" + airport.getAirportID() + ")"
                + "</font></b>"
                + "<br />" + airport.getCity() + ", " + airport.getCountry() + ""
                + "<br />"
                + "<br />IATA/FAA: " + airport.getIATA_FAA() + ""
                + "<br />ICAO: " + airport.getICAO() + ""
                + "<br />Timezone: " + airport.getTimezone() + ""
                + "</p>";
    }
}