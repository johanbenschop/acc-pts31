/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import atc.logic.Airplane;
import atc.logic.Flightplan;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Box;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;
import javax.swing.Timer;

/**
 *
 * @author johan
 */
public class airplaneRendereble extends Box {
    private final Airplane airplane;
    private final Flightplan flightplan;
    private final Timer timerAirplane;
    

    public airplaneRendereble(Flightplan flightplan) {
//        super(flightplan.getAirplane().getLocation().toPosition(),
//                flightplan.getAirplane().getPlaneLength(),
//                flightplan.getAirplane().getPlaneHeight(),
//                flightplan.getAirplane().getPlaneWidth());
        super(flightplan.getAirplane().getLocation().toPosition(), 50000, 50000, 50000);
        this.flightplan = flightplan;
        this.airplane = flightplan.getAirplane();
        
        this.timerAirplane = new Timer(500, new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    moveTo(airplane.getLocation().toPosition());
                }
            });
            timerAirplane.start();
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Flightplan getFlightplan() {
        return flightplan;
    }
    
    
    
}
