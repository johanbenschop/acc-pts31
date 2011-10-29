package atc.gui;

import atc.logic.Airport;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.*;
import java.nio.DoubleBuffer;
import javax.media.opengl.GL;

/**
 *
 * @author Johan Benschop
 */
public class apAnnotation extends GlobeAnnotation {

    public Airport airport;

    public apAnnotation(Airport airport, AnnotationAttributes defaults) {
        super("", airport.getLocation().toPosition(), defaults);
        this.airport = airport;
    }

    protected void applyScreenTransform(DrawContext dc, int x, int y, int width, int height, double scale) {
        double finalScale = scale * this.computeScale(dc);

        GL gl = dc.getGL();
        gl.glTranslated(x, y, 0);
        gl.glScaled(finalScale, finalScale, 1);
    }
    // Override annotation drawing for a simple circle
    private DoubleBuffer shapeBuffer;

    protected void doDraw(DrawContext dc, int width, int height, double opacity, Position pickPosition) {
        // Draw colored circle around screen point - use annotation's text color
        if (dc.isPickingMode()) {
            this.bindPickableObject(dc, pickPosition);
        }

        this.applyColor(dc, this.getAttributes().getTextColor(), 0.6 * opacity, true);

        // Draw 32x32 shape from its bottom left corner
        int size = 32;
        if (this.shapeBuffer == null) {
            this.shapeBuffer = FrameFactory.createShapeBuffer(AVKey.SHAPE_ELLIPSE, size, size, 0, null);
        }
        dc.getGL().glTranslated(-size / 2, -size / 2, 0);
        FrameFactory.drawBuffer(dc, GL.GL_TRIANGLE_FAN, this.shapeBuffer);
    }
}
