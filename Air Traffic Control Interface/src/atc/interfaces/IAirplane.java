/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

/**
 *
 * @author Henk
 */
public interface IAirplane {

    double getAimedAltitude();

    double getAimedDirection();

    double getAimedSpeed();

    int getAirplaneNumber();

    double getAltitude();

    double getDirection();

    GeoLocation getLocation();

    double getLongitudeTravelled();

    String getManufacturer();

    int getMaxFuel();

    int getMaxSpeed();

    int getMinSpeed();

    int getPlaneHeight();

    int getPlaneLength();

    int getPlaneWidth();

    double getSpeed();

    Airplane.Statusses getStatus();

    void setAimedAltitude(double altitude);

    void setAimedDirection(double direction);

    void setAimedSpeed(double speed);

    void setCollcheck(boolean collcheck);

    void setStatus(Airplane.Statusses Status);
    
}
