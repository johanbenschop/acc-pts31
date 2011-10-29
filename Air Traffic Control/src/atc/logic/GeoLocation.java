package atc.logic;

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

    /**
     * 
     * @return 
     */
    public String ToString() {
        String gegevens;
        gegevens = "Longitude: " + Longitude + " , Latitude: " + Latitude + " , Altitude" + Altitude;
        return gegevens;
    }
}