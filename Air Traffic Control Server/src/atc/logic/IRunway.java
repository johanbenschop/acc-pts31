/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

/**
 *
 * @author Henk
 */
public interface IRunway {

    /**
     * @param r is what the availability should become after calling this method.
     * @return Availability
     */
    void ChangeAvailability(boolean r);

    boolean getAvailability();

    int getDirection();

    /**
     *
     * @return
     * @deprecated
     */
    int getLength();

    /**
     * Getters
     */
    GeoLocation getLocation();
    
}
