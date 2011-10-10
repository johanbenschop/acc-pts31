package atc.logic;

public class GeoLocation {

    private int Longitude;
    private int Latitude;
    private int Altitude;

    public GeoLocation(int Longitude, int Latitude, int Altitude) {
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Altitude = Altitude;
    }

    public int getAltitude() {
        return Altitude;
    }

    public int getLatitude() {
        return Latitude;
    }

    public int getLongitude() {
        return Longitude;
    }

    /**
     * 
     * @return 
     */
    public void ToString() {
        throw new UnsupportedOperationException();
    }
}