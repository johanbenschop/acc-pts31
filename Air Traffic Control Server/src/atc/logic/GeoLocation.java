package atc.logic;

import atc.interfaces.IGeoLoc;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The coordinates on WorldWind of an object
 */
public class GeoLocation extends UnicastRemoteObject implements IGeoLoc, Serializable {

    /**************Datafields***********/
    /**
     * The Longitude of Geolocation.
     */
    private double Longitude;
    /**
     * The Latitude of Geolocation.
     */
    private double Latitude;
    /**
     * The Altitude of Geolocation.
     */
    private double Altitude;

    /***************Constructor**********/
    /**A GeoLocation is made with the following parameters:
     * @param Longitude: The longitude of GeoLocation.
     * @param Latitude: The latitude of GeoLocation.
     * @param Altitude: The altitude of GeoLocation.
     */
    public GeoLocation(double Longitude, double Latitude, double Altitude) throws RemoteException {
        // TODO The long and lat are in the wrong order. (is a bit confusing)                   TODO
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Altitude = Altitude;
    }

    /**A GeoLocation is made with the following parameters:
     * @param Longitude: The longitude of GeoLocation.
     * @param Latitude: The latitude of GeoLocation.
     */
    public GeoLocation(double Latitude, double Longitude)  throws RemoteException {
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Altitude = 0;
    }

    /**
     * Due to minimal importance of an unit test for this method there is none.
     * 
     * Method to return a string value with the longitude, latitude and altitude.
     * 
     * @return A string value with the longitude, latitude and altitude is returned.
     * @deprecated 
     */
//    @Override
//    public String ToString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("(");
//        sb.append(this.Latitude);
//        sb.append(", ");
//        sb.append(this.Longitude);
//        sb.append(")");
//
//        sb.append(", ");
//
//        sb.append("[");
//        sb.append(this.Altitude);
//        sb.append("]");
//
//        return sb.toString();
//    }

    /**
     * Method to return the Latlon made from the latitude and longitude
     * 
     * @return A LatLon value in degrees.
     * @deprecated 
     */
    @Override
    public LatLon toLatLon() {
        return LatLon.fromDegrees(Latitude, Longitude);
    }

    /**
     *Converts the geolocation to the location used in WWJ. 
     * @return position.
     */
    @Override
    public Position toPosition() {
        return Position.fromDegrees(Latitude, Longitude, (double) Altitude);
    }

    /**
     * Method to return a double value of the direction calculated between Airport a and Airport b.
     * 
     * @return A double value direction of Airport B from Airport A.
     * @deprecated 
     */
    public static double CalcDirection(Airport a, Airport b) throws RemoteException {
        IGeoLoc locationA = new GeoLocation(0, 0, 0);
        IGeoLoc locationB = new GeoLocation(0, 0, 0);
        locationA = a.getLocation();
        locationB = b.getLocation();
        double dLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double dLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude());
        double lat1 = Math.toRadians(locationA.getLatitude());
        double lat2 = Math.toRadians(locationB.getLatitude());

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double direction = Math.toDegrees(Math.atan2(y, x));
        return direction;
    }

    /**
     * Method to return a double value of the direction calculated between GeoLocation a and GeoLocation b.
     * 
     * @return A double value direction of GeoLocation B from GeoLocation A.
     */    
    @Override
    public  double CalcDirection(IGeoLoc locationA, IGeoLoc locationB) throws RemoteException {
        double dLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double dLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude());
        double lat1 = Math.toRadians(locationA.getLatitude());
        double lat2 = Math.toRadians(locationB.getLatitude());

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double direction = Math.toDegrees(Math.atan2(y, x));
        return direction;
    }
    
    /**
     * This calculates the new position based on the curren position, direction and distance.
     * @return GeoLocation
     */
    public static IGeoLoc CalcPosition(double lon1, double lat1, double direction, double distance) throws RemoteException {
        double Distance = distance / 3958.75;
        double Direction = direction;

        double Lat1 = Math.toRadians(lat1);
        double Lon1 = Math.toRadians(lon1);
        double Lat2 = Math.asin(Math.sin(Lat1) * Math.cos(Distance) + Math.cos(Lat1) * Math.sin(Distance) * Math.cos(Direction));
        double Lon2 = Lon1 + Math.atan2(Math.sin(Direction) * Math.sin(Distance) * Math.cos(Lat1), Math.cos(Distance) - Math.sin(Lat1) * Math.sin(Lat2));
        return new GeoLocation(Math.toDegrees(Lon2), Math.toDegrees(Lat2));
    }

    /**************Getters**************/
    @Override
    public double getAltitude() {
        return Altitude;
    }

    @Override
    public double getLatitude() {
        return Latitude;
    }

    @Override
    public double getLongitude() {
        return Longitude;
    }

    @Override
    public IGeoLoc getNewGeoLocation() throws RemoteException {
        return new GeoLocation(Longitude, Latitude, Altitude);
    }

    /**************Setters**************/
    @Override
    public void setAltitude(double Altitude) {
        this.Altitude = Altitude;
    }

    @Override
    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    @Override
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
}