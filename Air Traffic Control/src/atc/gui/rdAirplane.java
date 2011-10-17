/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import atc.logic.Airplane;
import gov.nasa.worldwind.avlist.AVKey;
//import gov.nasa.worldwind.geom.Box;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Box;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polygon;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import java.util.ArrayList;

/**
 *
 * @author johan
 */
public class rdAirplane {

    Airplane airplane;
    private Polygon pgon;

    public rdAirplane(Airplane airplane)
    {
        //        BasicShapeAttributes basicShapeAttributes = new BasicShapeAttributes();
//        basicShapeAttributes.setInteriorMaterial(Material.RED);
//        basicShapeAttributes.setEnableLighting(true);
//        basicShapeAttributes.setOutlineMaterial(Material.BLUE);
//        basicShapeAttributes.setOutlineWidth(2d);
//        basicShapeAttributes.setDrawInterior(true);
//        basicShapeAttributes.setDrawOutline(false);
//        
//        box = new Box(Position.ZERO, 0x64, 0x64, 0x64);
//        box.setAttributes(basicShapeAttributes);
//        box.setValue(AVKey.DISPLAY_NAME, airplane.toString());
        // Create and set an attribute bundle.
        ShapeAttributes normalAttributes = new BasicShapeAttributes();
        normalAttributes.setInteriorMaterial(Material.YELLOW);
        normalAttributes.setOutlineOpacity(0.5);
        normalAttributes.setInteriorOpacity(0.8);
        normalAttributes.setOutlineMaterial(Material.GREEN);
        normalAttributes.setOutlineWidth(2);
        normalAttributes.setDrawOutline(true);
        normalAttributes.setDrawInterior(true);
        normalAttributes.setEnableLighting(true);
        ShapeAttributes highlightAttributes = new BasicShapeAttributes(normalAttributes);
        highlightAttributes.setOutlineMaterial(Material.WHITE);
        highlightAttributes.setOutlineOpacity(1);

        ArrayList<Position> pathLocations = new ArrayList<Position>();
        pathLocations.add(Position.fromDegrees(28, -110, 5e4));
        pathLocations.add(Position.fromDegrees(35, -108, 5e4));
        pathLocations.add(Position.fromDegrees(35, -111, 5e4));
        pathLocations.add(Position.fromDegrees(28, -111, 5e4));
        pathLocations.add(Position.fromDegrees(28, -110, 5e4));
        pgon = new Polygon(pathLocations);
        pgon.setValue(AVKey.DISPLAY_NAME, "Hello!");
        normalAttributes = new BasicShapeAttributes(normalAttributes);
        normalAttributes.setDrawInterior(true);
        normalAttributes.setInteriorMaterial(Material.WHITE);
        normalAttributes.setInteriorOpacity(1);
        pgon.setAttributes(normalAttributes);
        pgon.setHighlightAttributes(highlightAttributes);
        float[] texCoords = new float[]{0, 0, 1, 0, 1, 1, 0, 1, 0, 0};
        pgon.setTextureImageSource("images/32x32-icon-nasa.png", texCoords, 5);
    }            
            
    public Polygon getRender() {
        return pgon;
    }
}
