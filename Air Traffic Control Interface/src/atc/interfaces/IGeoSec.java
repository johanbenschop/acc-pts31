/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

/**
 *
 * @author Henk
 */
public interface IGeoSec {

    /**
     * Determines whether a GeoLocation is within the GeoSector.
     * @param location
     * @return true if GeoPosition is within the GeoSecor, false otherwise.
     */
    boolean containsGeoLocation(GeoLocation location);

    /**
     * Tests the equality of the sectors' angles. Sectors are equal if all of their corresponding angles are equal.
     *
     * @param o the sector to compareTo with <code>this</code>.
     *
     * @return <code>true</code> if the four corresponding angles of each sector are equal, <code>false</code>
     * otherwise.
     */
    boolean equals(Object o);

    /**
     * Returns the latitude and longitude of the sector's angular center: (minimum latitude + maximum latitude) / 2,
     * (minimum longitude + maximum longitude) / 2.
     *
     * @return The latitude and longitude of the sector's angular center
     * @deprecated
     */
    GeoLocation getCenterLocation();

    /**
     * @deprecated
     * @return
     */
    double getDeltaLatitude();

    /**
     * @deprecated
     * @return
     */
    double getDeltaLongitude();

    double getMaxLatitude();

    double getMaxLongitude();

    double getMinLatitude();

    double getMinLongitude();

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
    boolean intersects(GeoSector that);

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
    boolean intersectsInterior(GeoSector that);

    /**
     *
     * @return
     * @deprecated
     */
    boolean isWithinLatLonLimits();

    /**
     * Convets this GeoSector to an Sector for World Wind.
     * @return World Wind Sector
     */
    Sector toSector();

    /**
     * Due to minimal importance of an unit test for this method there is none.
     *
     * Returns a string indicating the sector's angles.
     *
     * @return A string indicating the sector's angles.
     */
    String toString();
    
}
