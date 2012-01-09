package atc.logic;

import atc.interfaces.IGeoLoc;
import atc.interfaces.IGeoSec;
import gov.nasa.worldwind.geom.Sector;
import java.rmi.RemoteException;

/**
 * Thanks to NASA for providing their Secor class as a basis for our GeoSector
 * @author Johan Benschop
 */
public class GeoSector implements IGeoSec {

    /**************Datafields***********/
    /**
     * The minimal latitude of the GeoSector
     */
    private final double minLatitude;
    /**
     * The maximal latitude of the GeoSector
     */
    private final double maxLatitude;
    /**
     * The minimal longitude of the GeoSector
     */
    private final double minLongitude;
    /**
     * The maximal longitude of the GeoSector
     */
    private final double maxLongitude;
    /**
     * The angular difference between the sector's minimum and maximum latitudes.
     */
    private final double deltaLatitude;
    /**
     * The angular difference between the sector's minimum and maximum longitudes
     */
    private final double deltaLongitude;

    /***************Constructor**********/
    /**
     * This is a constructor used for making a GeoSector it contains a min/max Latitude and min/max Longitude
     * @param minLatitude is the minimal latitude of the GeoSector
     * @param maxLatitude is the maximal latitude of the GeoSector
     * @param minLongitude is the minimal longitude of the GeoSector
     * @param maxLongitude is the maximal longitude of the GeoSector
     */
    public GeoSector(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
        this.deltaLatitude = maxLatitude - minLatitude;
        this.deltaLongitude = maxLongitude - minLongitude;
    }

    /**
     * 
     * @return 
     * @deprecated 
     */
    public boolean isWithinLatLonLimits() {
        return minLatitude >= -90 && maxLatitude <= 90
                && minLongitude >= -180 && maxLongitude <= 180;
    }

    /**
     * Returns the latitude and longitude of the sector's angular center: (minimum latitude + maximum latitude) / 2,
     * (minimum longitude + maximum longitude) / 2.
     *
     * @return The latitude and longitude of the sector's angular center
     * @deprecated 
     */
    public GeoLocation getCenterLocation() {
        double latitude = 0.5 * (maxLatitude + minLatitude);
        double longitude = 0.5 * (maxLongitude + minLongitude);
        return new GeoLocation(latitude, longitude);
    }

    /**
     * Determines whether this sector intersects another sector's range of latitude and longitude. The sector's angles
     * are assumed to be normalized to +/- 90 degrees latitude and +/- 180 degrees longitude. The result of the
     * operation is undefined if they are not.
     *
     * @param that the sector to test for intersection.
     *
     * @return <code>true</code> if the sectors intersect, otherwise <code>false</code>.
     * @deprecated 
     */
    public boolean intersects(GeoSector that) {
        if (that == null) {
            return false;
        }

        // Assumes normalized angles -- [-180, 180], [-90, 90]
        if (that.maxLongitude < this.minLongitude) {
            return false;
        }
        if (that.minLongitude > this.maxLongitude) {
            return false;
        }
        if (that.maxLatitude < this.minLatitude) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (that.minLatitude > this.maxLatitude) {
            return false;
        }
        return true;
    }

    /**
     * Determines whether the interiors of this sector and another sector intersect. The sector's angles are assumed to
     * be normalized to +/- 90 degrees latitude and +/- 180 degrees longitude. The result of the operation is undefined
     * if they are not.
     *
     * @param that the sector to test for intersection.
     *
     * @return <code>true</code> if the sectors' interiors intersect, otherwise <code>false</code>.
     *
     * @see #intersects(Sector)
     * @deprecated 
     */
    public boolean intersectsInterior(GeoSector that) {
        if (that == null) {
            return false;
        }

        // Assumes normalized angles -- [-180, 180], [-90, 90]
        if (that.maxLongitude <= this.minLongitude) {
            return false;
        }
        if (that.minLongitude >= this.maxLongitude) {
            return false;
        }
        if (that.maxLatitude <= this.minLatitude) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (that.minLatitude >= this.maxLatitude) {
            return false;
        }
        return true;
    }

    /**
     * Convets this GeoSector to an Sector for World Wind.
     * @return World Wind Sector
     */
    public Sector toSector() {
        return Sector.fromDegrees(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    /**
     * Due to minimal importance of an unit test for this method there is none.
     * 
     * Returns a string indicating the sector's angles.
     *
     * @return A string indicating the sector's angles.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.minLatitude);
        sb.append(", ");
        sb.append(this.minLongitude);
        sb.append(")");

        sb.append(", ");

        sb.append("(");
        sb.append(this.maxLatitude);
        sb.append(", ");
        sb.append(this.maxLongitude);
        sb.append(")");

        return sb.toString();
    }

    /**
     * Tests the equality of the sectors' angles. Sectors are equal if all of their corresponding angles are equal.
     *
     * @param o the sector to compareTo with <code>this</code>.
     *
     * @return <code>true</code> if the four corresponding angles of each sector are equal, <code>false</code>
     *         otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GeoSector sector = (GeoSector) o;

        if (maxLatitude != sector.maxLatitude) {
            return false;
        }
        if (maxLongitude != sector.maxLongitude) {
            return false;
        }
        if (minLatitude != sector.minLatitude) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (minLongitude != sector.minLongitude) {
            return false;
        }
        return true;
    }

    /**
     * Determines whether a GeoLocation is within the GeoSector.
     * @param location
     * @return true if GeoPosition is within the GeoSecor, false otherwise.
     */
    @Override
    public boolean containsGeoLocation(IGeoLoc location) throws RemoteException{
        return (location.getLatitude() >= minLatitude
                && location.getLatitude() <= maxLatitude
                && location.getLongitude() >= minLongitude
                && location.getLongitude() <= maxLongitude);
    }

    /**************Getters**************/
    /**
     * @deprecated 
     * @return 
     */
    public double getDeltaLatitude() {
        return deltaLatitude;
    }

    /**
     * @deprecated 
     * @return 
     */
    public double getDeltaLongitude() {
        return deltaLongitude;
    }

    
    public double getMaxLatitude() {
        return maxLatitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }
}