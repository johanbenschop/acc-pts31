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
}