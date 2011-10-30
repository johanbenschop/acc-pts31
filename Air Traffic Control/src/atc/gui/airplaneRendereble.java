/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import atc.logic.Airplane;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Box;

/**
 *
 * @author johan
 */
public class airplaneRendereble extends Box {
    private final Airplane airplane;

    public airplaneRendereble(Airplane airplane) {
        super(airplane.getLocation().toPosition(), airplane.getPlaneLength(), airplane.getPlaneHeight(), airplane.getPlaneWidth());
        this.airplane = airplane;
    }
    
    
}
