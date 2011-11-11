package atc.logic;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

/**
 * The coordinates on WorldWind of an object
 * 
 * 
 */
public class GeoLocation {

    
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

    
        /**A GeoLocation is made with the following parameters:
     * @param Longitude: The longitude of GeoLocation.
     * @param Latitude: The latitude of GeoLocation.
     * @param Altitude: The altitude of GeoLocation.
     */
    public GeoLocation(double Longitude, double Latitude, double Altitude) {
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Altitude = Altitude;
    }
    
    /**************Getters**************/
    
    public double getAltitude() {
        return Altitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    
    /**************Setters**************/
    
    public void setAltitude(double Altitude) {
        this.Altitude = Altitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
    
    

    /**
     * Method to return a string value with the longitude, latitude and altitude.
     * 
     * @return A string value with the longitude, latitude and altitude is returned.
     */
    public String ToString() {
        String gegevens;
        gegevens = "Longitude: " + Longitude + " , Latitude: " + Latitude + " , Altitude" + Altitude;
        return gegevens;
    }
     /**
     * Method to return the Latlon made from the latitude and longitude
     * 
     * @return A LatLon value in degrees.
     */
    public LatLon toLatLon() {
        return LatLon.fromDegrees(Latitude, Longitude);
    }
    
    public Position toPosition() {
        return Position.fromDegrees(Latitude, Longitude, (double) Altitude);
    }
    
     /**
     * Method to return a double value of the direction calculated between Airport a and Airport b.
     * 
     * @return A double value direction of Airport B from Airport A.
     */
    public static double CalcDirection(Airport a, Airport b){
        GeoLocation locationA = new GeoLocation(0,0,0);
        GeoLocation locationB = new GeoLocation(0,0,0);
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
        public static double CalcDirection(GeoLocation locationA, GeoLocation locationB) {
        double dLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double dLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude());
        double lat1 = Math.toRadians(locationA.getLatitude());
        double lat2 = Math.toRadians(locationB.getLatitude());
        
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double direction = Math.toDegrees(Math.atan2(y, x));
        return direction;
    }
}