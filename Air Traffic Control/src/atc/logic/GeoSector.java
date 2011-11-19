package atc.logic;

import gov.nasa.worldwind.geom.Sector;

/**
 * Thanks to NASA for providing their Secor class as a basis for our GeoSector
 * @author Johan Benschop
 */
public class GeoSector {

    private final double minLatitude;
    private final double maxLatitude;
    private final double minLongitude;
    private final double maxLongitude;
    private final double deltaLatitude;
    private final double deltaLongitude;

    public GeoSector(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
        this.deltaLatitude = maxLatitude - minLatitude;
        this.deltaLongitude = maxLongitude - minLongitude;
    }

    /**
     * Returns the angular difference between the sector's minimum and maximum latitudes: max - min
     *
     * @return The angular difference between the sector's minimum and maximum latitudes.
     */
    public double getDeltaLatitude() {
        return deltaLatitude;
    }

    /**
     * Returns the angular difference between the sector's minimum and maximum longitudes: max - min.
     *
     * @return The angular difference between the sector's minimum and maximum longitudes
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

    public boolean isWithinLatLonLimits() {
        return minLatitude >= -90 && maxLatitude <= 90
                && minLongitude >= -180 && maxLongitude <= 180;
    }

    /**
     * Returns the latitude and longitude of the sector's angular center: (minimum latitude + maximum latitude) / 2,
     * (minimum longitude + maximum longitude) / 2.
     *
     * @return The latitude and longitude of the sector's angular center
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
    public boolean containsGeoLocation(GeoLocation location) {
        return (location.getLatitude() >= minLatitude
                && location.getLatitude() <= maxLatitude
                && location.getLongitude() >= minLongitude
                && location.getLongitude() <= maxLongitude);
    }
}