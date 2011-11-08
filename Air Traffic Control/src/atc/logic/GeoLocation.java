package atc.logic;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

public class GeoLocation {

    private double Longitude;
    private double Latitude;
    private double Altitude;

    public GeoLocation(double Longitude, double Latitude, double Altitude) {
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Altitude = Altitude;
    }

    public double getAltitude() {
        return Altitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    
    
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
     * 
     * @return 
     */
    public String ToString() {
        String gegevens;
        gegevens = "Longitude: " + Longitude + " , Latitude: " + Latitude + " , Altitude" + Altitude;
        return gegevens;
    }
    
    public LatLon toLatLon() {
        return LatLon.fromDegrees(Latitude, Longitude);
    }
    
    public Position toPosition() {
        return Position.fromDegrees(Latitude, Longitude, (double) Altitude);
    }
    
    
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