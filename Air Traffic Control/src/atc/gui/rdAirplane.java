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

/**
 *
 * @author johan
 */
public class rdAirplane implements Renderable {

    Airplane airplane;
    private final Box box;
    private Polygon pgon;

    public rdAirplane(Airplane airplane) {
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

    }
    
    public Polygon getRender() {
        return pgon;
    }

    @Override
    public void render(DrawContext dc) {
        dc.addOrderedRenderable(box);
    }
}
