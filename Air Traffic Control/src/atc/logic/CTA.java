package atc.logic;

import atc.gui.atc2;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class CTA {

    /**************Datafields***********/
    /**
     * The GeoSector of the CTA
     */
    public GeoSector sector;
    public GeoSector sectorGreater;
    /**
     * A airplane used for getting focus of a selected airplane within the CTA
     */
    private Airplane airplane;
    /**
     * A airport used for getting focus of a selected airport within the CTA
     */
    private Airport airport;
    /**
     * A list used for collecting all the active airplanes within the CTA  
     */
    private ArrayList<Airplane> airplaneList;
    /**
     * A list used for collecting all the airports within the CTA
     */
    private ArrayList<Airport> airportList;

    /***************Constructor**********/
    /**
     * This is a constructor used for making a CTA it contains a location and the width/length
     * @param Location is the location of the CTA
     * @param Width is the width of the CTA
     * @param Length is the length of the CTA
     */
    public CTA(GeoSector location) {
        this.sector = location;

        airplaneList = new ArrayList<>();
        airportList = new ArrayList<>();

        try {
            loadAirportList(sector);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the GeoSector of this CTA
     * @return 
     */
    public GeoSector getSector() {
        return sector;
    }

    /**
     * Returns airplane with the given airplaneNumber
     * @return 
     */
    public void GetActiveAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            if (a.getAirplaneNumber() == AirplaneNumber) {
                airplane = a;
            }
        }
    }

    /**
     * Returns the airport with the given AirportID
     * @return
     */
    public Airport GetAirport(int AirportID) {
        for (Airport a : airportList) {
            if (a.getAirportID() == AirportID) {
                airport = a;
            }
        }
        return airport;
    }

    /**
     * Not yet implemented
     * @return
     */
    public void DetectAirplane() {
        throw new UnsupportedOperationException();
    }

    /**
     * This is the collision detection, it checks if the airplanes within the CTA are on a collision course, if they are it will send a message to the GUI.
     * @return if the airplanes are on a collision course it will send a message to the GUI and sets the status of both airplanes to CRASHING
     */
    public void Collision() {
        double lat1 = 0, lat2 = 0, lat3 = 0, lon1 = 0, lon2 = 0, lon3 = 0, bearing1 = 0, bearing2 = 0, bearing13 = 0, bearing12 = 0, bearing21 = 0, bearing23 = 0;
        double dLat = 0, dLon = 0, dLon13 = 0, bearingA = 0, bearingB = 0, dist12 = 0, dist13 = 0, alpha1 = 0, alpha2 = 0, alpha3 = 0, distance1 = 0, distance2 = 0, time1 = 0, time2 = 0;

        if (airplaneList.size() > 1) {
            for (Airplane target : airplaneList) {
                if (target.getStatus().equals(Airplane.Statusses.INFLIGHT) || target.getStatus().equals(Airplane.Statusses.CRASHING1) || target.getStatus().equals(Airplane.Statusses.CRASHING2)) {
                    lat1 = Math.toRadians(target.getLocation().getLatitude());
                    lon1 = Math.toRadians(target.getLocation().getLongitude());
                    if (target.getDirection() < 0) {
                        bearing1 = Math.toRadians(target.getDirection() + 360);
                    } else {
                        bearing1 = Math.toRadians((target.getDirection()));
                    }

                    for (Airplane crashobject : airplaneList) {
                        if (crashobject.getStatus().equals(Airplane.Statusses.INFLIGHT) || crashobject.getStatus().equals(Airplane.Statusses.CRASHING1) || crashobject.getStatus().equals(Airplane.Statusses.CRASHING2)) {
                            if (crashobject != target && crashobject.getAltitude() == target.getAltitude() && crashobject.isCollcheck() == false) {
                                lat2 = Math.toRadians(crashobject.getLocation().getLatitude());
                                lon2 = Math.toRadians(crashobject.getLocation().getLongitude());
                                if (crashobject.getDirection() < 0) {
                                    bearing2 = Math.toRadians(crashobject.getDirection() + 360);
                                } else {
                                    bearing2 = Math.toRadians(crashobject.getDirection());
                                }

                                bearing13 = bearing1;

                                bearing23 = bearing2;

                                dLat = lat2 - lat1;
                                dLon = lon2 - lon1;
                                dist12 = 2 * Math.asin(Math.sqrt(Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2)));
                                bearingA = Math.acos((Math.sin(lat2) - Math.sin(lat1) * Math.cos(dist12))
                                        / (Math.sin(dist12) * Math.cos(lat1)));
                                bearingB = Math.acos((Math.sin(lat1) - Math.sin(lat2) * Math.cos(dist12))
                                        / (Math.sin(dist12) * Math.cos(lat2)));

                                if (Math.sin(lon2 - lon1) > 0) {
                                    bearing12 = bearingA;
                                    bearing21 = 2 * Math.PI - bearingB;
                                } else {
                                    bearing12 = 2 * Math.PI - bearingA;
                                    bearing21 = bearingB;
                                }

                                alpha1 = (bearing13 - bearing12 + Math.PI) % (2 * Math.PI) - Math.PI;
                                alpha2 = (bearing21 - bearing23 + Math.PI) % (2 * Math.PI) - Math.PI;
                                if (Math.sin(alpha1) == 0 && Math.sin(alpha2) == 0) {
                                    //TODO when 2 airplane fly behind each other or towards each other.
                                }

                                alpha3 = Math.acos(-Math.cos(alpha1) * Math.cos(alpha2)
                                        + Math.sin(alpha1) * Math.sin(alpha2) * Math.cos(dist12));

                                dist13 = Math.atan2(Math.sin(dist12) * Math.sin(alpha1) * Math.sin(alpha2),
                                        Math.cos(alpha2) + Math.cos(alpha1) * Math.cos(alpha3));

                                lat3 = Math.asin(Math.sin(lat1) * Math.cos(dist13)
                                        + Math.cos(lat1) * Math.sin(dist13) * Math.cos(bearing13));

                                dLon13 = Math.atan2(Math.sin(bearing13) * Math.sin(dist13) * Math.cos(lat1),
                                        Math.cos(dist13) - Math.sin(lat1) * Math.sin(lat3));

                                lon3 = lon1 + dLon13;
                                lon3 = (lon3 + 3 * Math.PI) % (2 * Math.PI) - Math.PI;

                                distance1 = distFrom(lat1, lon1, lat3, lon3);
                                time1 = distance1 / ((double) target.getSpeed() / 3600);
                                distance2 = distFrom(lat2, lon2, lat3, lon3);
                                time2 = distance2 / ((double) crashobject.getSpeed() / 3600);

                                if (distance1 <= 10000 && distance2 <= 10000) {
                                    if (distance1 <= 100 || distance2 <= 100) {
                                        target.setStatus(Airplane.Statusses.CRASHED);
                                        crashobject.setStatus(Airplane.Statusses.CRASHED);
                                    } else if (distance1 <= 5000 || distance2 <= 5000) {
                                        target.setStatus(Airplane.Statusses.CRASHING2);
                                        crashobject.setStatus(Airplane.Statusses.CRASHING2);
                                    } else if (distance1 <= 10000 || distance2 <= 10000) {
                                        target.setStatus(Airplane.Statusses.CRASHING1);
                                        crashobject.setStatus(Airplane.Statusses.CRASHING1);
                                    }
                                }
                                //haxxorz method but it works for 2 airplanes.
                                //more then 2 doesnt though same issue random collisions, less then the first though.
                                //numbers are based on trial and error.
//                                if (time1 >= time2) {
//                                    if (time1 - time2 <= 1 && time1 - time2 >= -1) {
//                                        target.setStatus(Airplane.Statusses.CRASHED);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHED);
//                                    } else if (time1 - time2 <= 400 && time1 - time2 >= -400) {
//                                        target.setStatus(Airplane.Statusses.CRASHING2);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING2);
//                                    } else if (time1 - time2 <= 450 && time1 - time2 >= -450) {
//                                        target.setStatus(Airplane.Statusses.CRASHING1);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING1);
//                                    }
//                                } else {
//                                    if (time1 - time2 <= 1 && time1 - time2 >= -1) {
//                                        target.setStatus(Airplane.Statusses.CRASHED);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHED);
//                                    } else if (time1 - time2 <= 400 && time1 - time2 >= -400) {
//                                        target.setStatus(Airplane.Statusses.CRASHING2);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING2);
//                                    } else if (time1 - time2 <= 450 && time1 - time2 >= -450) {
//                                        target.setStatus(Airplane.Statusses.CRASHING1);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING1);
//                                    }
//                                }

//                                    if (time1 - time2 <= 3 / ((double) crashobject.getSpeed() / 3600) && time1 - time2 >= -3000 / ((double) crashobject.getSpeed() / 3.6)) {
//                                            target.setStatus(Airplane.Statusses.CRASHED);
//                                            crashobject.setStatus(Airplane.Statusses.CRASHED);
//                                            System.out.println("boem optie1");
//                                            System.out.println(Double.toString(time1 - time2));
//                                            System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                            System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                    } else {
//                                        target.setStatus(Airplane.Statusses.INFLIGHT);
//                                        crashobject.setStatus(Airplane.Statusses.INFLIGHT);
//                                        System.out.println("Blijven vliegen optie 1");
//                                        System.out.println(Double.toString(time1 - time2));
//                                        System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                        System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                    }
//                                } else if (time1 - time2 <= 3 / ((double) crashobject.getSpeed() / 3600) && time1 - time2 >= -3000 / ((double) crashobject.getSpeed() / 3.6)) {
//                                    if (time1 - time2 <= 3 / ((double) target.getSpeed() / 3.6) && time1 - time2 >= -3000 / ((double) target.getSpeed() / 3.6)) {
//                                        if (time1 - time2 <= 0.5 / ((double) target.getSpeed() / 3.6)) {
//                                            target.setStatus(Airplane.Statusses.CRASHED);
//                                            crashobject.setStatus(Airplane.Statusses.CRASHED);
//                                            System.out.println("boem optie 2");
//                                            System.out.println(Double.toString(time1 - time2));
//                                            System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                            System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                            System.out.println(0.5 / ((double) target.getSpeed() / 3.6));
//                                        } else if (time1 - time2 <= 1 / ((double) target.getSpeed() / 3.6)) {
//                                            target.setStatus(Airplane.Statusses.CRASHING2);
//                                            crashobject.setStatus(Airplane.Statusses.CRASHING2);
//                                            System.out.println("nog net geen boem optie 2");
//                                            System.out.println(Double.toString(time1 - time2));
//                                            System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                            System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                        } else {
//                                            target.setStatus(Airplane.Statusses.CRASHING1);
//                                            crashobject.setStatus(Airplane.Statusses.CRASHING1);
//                                            System.out.println("nog lang geen boem optie 2");
//                                            System.out.println(Double.toString(time1 - time2));
//                                            System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                            System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                        }
//                                    } else {
//                                        target.setStatus(Airplane.Statusses.INFLIGHT);
//                                        crashobject.setStatus(Airplane.Statusses.INFLIGHT);
//                                        System.out.println("Wij blijven vliegen optie 2");
//                                        System.out.println(Double.toString(time1 - time2));
//                                        System.out.println(30 / ((double) crashobject.getSpeed() / 3600));
//                                        System.out.println(-30000 / ((double) crashobject.getSpeed() / 3.6));
//                                    }
//                                }

//                                if ((time1 == time2) && time1 <= 12000) {
//                                    if (time1 <= 5) {
//                                        target.setStatus(Airplane.Statusses.CRASHED);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHED);
//                                    } else if (time1 <= 3600) {
//                                        target.setStatus(Airplane.Statusses.CRASHING2);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING2);
//                                    } else if (time1 <= 12000) {
//                                        target.setStatus(Airplane.Statusses.CRASHING1);
//                                        crashobject.setStatus(Airplane.Statusses.CRASHING1);
//                                    } 
//                                    else if ((time1 >= 12000) || !target.getStatus().equals(Airplane.Statusses.CRASHING1) || !target.getStatus().equals(Airplane.Statusses.CRASHING2) || !target.getStatus().equals(Airplane.Statusses.CRASHED)) {
//                                        target.setStatus(Airplane.Statusses.INFLIGHT);
//                                        crashobject.setStatus(Airplane.Statusses.INFLIGHT);
//                                    }
//                                } else if (time1 != time2) {
//                                    System.err.println("time not equal!");
//                                    System.err.println(time1);
//                                    System.err.println(time2);
//                                }

//                            } else {
//                                target.setStatus(Airplane.Statusses.INFLIGHT);
//                                crashobject.setStatus(Airplane.Statusses.INFLIGHT);
                            }
                        }
                    }
                    target.setCollcheck(true);
                }
            }
        }
        for (Airplane ap : airplaneList) {
            ap.setCollcheck(false);
        }
    }

    /**
     * Checks the distance between 2 given points
     * @param lat1 is the first given latitude
     * @param lon1 is the first given longitude
     * @param lat2 is the second given latitude
     * @param lon2 is the second given longitude
     * @return a double with the calculated distance
     */
    public double distFrom(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371;
        double distance;
        distance = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon2 - lon1)) * earthRadius;
        distance = distance * 1000;
        return distance;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLng = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double dist = earthRadius * c;

//        int meterConversion = 1609;

//        return dist;
    }

    /**
     * Adds a airplane to the airplaneList
     * @return
     */
    public void addAirplane(Airplane a) {
        airplaneList.add(a);
    }

    /**
     * Adds a airport to the airportList
     * @return
     */
    public void addAirport(Airport a) {
        airportList.add(a);
    }

    /**
     * Deletes the airplane with the corresponding AirplaneNumber from the airplaneList
     * @return
     */
    public void deleteAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            if (a.getAirplaneNumber() == AirplaneNumber) {
                airplaneList.remove(a);
            }
        }
    }

    /**
     * Loads airports from the airports.dat file
     * @throws FileNotFoundException if the file doesn't exist
     * @throws IOException 
     */
    public void loadAirportList() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("airports.dat");

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strline;
        while ((strline = br.readLine()) != null) {
            try {
                String[] props = strline.split(",");
                int id = Integer.parseInt(props[0]);
                String name = props[1].replaceAll("\"", "");
                String city = props[2].replaceAll("\"", "");
                String country = props[3].replaceAll("\"", "");
                String iata_faa = props[4].replaceAll("\"", "");
                String icao = props[5].replaceAll("\"", "");
                double latitude = Double.parseDouble(props[6]);
                double longitude = Double.parseDouble(props[7]);
                int altitude = Integer.parseInt(props[8]);
                double timezone = Double.parseDouble(props[9]);
                String dst = props[10].replaceAll("\"", "");

                GeoLocation location = new GeoLocation(longitude, latitude, altitude);

                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
                airportList.add(airport);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Corrupt data line on airports.dat...");
            }
        }
    }

    /**
     * Loads airports from the airports.dat file and filters the data for the GeoSecor
     * @throws FileNotFoundException if the file doesn't exist
     * @throws IOException 
     */
    public void loadAirportList(GeoSector sector) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("airports.dat");

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strline;
        while ((strline = br.readLine()) != null) {
            try {
                String[] props = strline.split(",");
                int id = Integer.parseInt(props[0]);
                String name = props[1].replaceAll("\"", "");
                String city = props[2].replaceAll("\"", "");
                String country = props[3].replaceAll("\"", "");
                String iata_faa = props[4].replaceAll("\"", "");
                String icao = props[5].replaceAll("\"", "");
                double latitude = Double.parseDouble(props[6]);
                double longitude = Double.parseDouble(props[7]);
                int altitude = Integer.parseInt(props[8]);
                double timezone = Double.parseDouble(props[9]);
                String dst = props[10].replaceAll("\"", "");

                GeoLocation location = new GeoLocation(longitude, latitude, altitude);
                if (!sector.containsGeoLocation(location)) {
                    continue; // The airport is not within the CTA
                }

                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
                airportList.add(airport);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Corrupt data line on airports.dat...");
            }
        }
    }

    public void CreateGreaterSector()
    { 
                    double maxLatitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 0, 100)).getLongitude();
                    double minLatitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), 180, 100)).getLongitude();
                    double maxLongitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 90, 100)).getLatitude();
                    double minLongitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), -90, 100)).getLatitude();
                    sectorGreater = new GeoSector(minLatitude, maxLatitude, minLongitude, maxLongitude);
        }
    
    
    /*public void CheckAirplaneATC()
    {
        for (Airplane airplane : airplaneList) {
            if (sectorGreater.containsGeoLocation(airplane.getLocation())) {
                airplane.setVisibleCTA(this.cta);
                if (sector.containsGeoLocation(airplane.getLocation())) {
                    airplane.setControlCTA(this.cta);
                }
            }
        }
    }*/
    
    
    /**
     * Gets the current airplane
     * @return airplane
     */
    public Airplane getCurrentSelectedAirplane() {
        return airplane;
    }

    /**
     * Gets the airplane list
     * @return airplaneList
     */
    public ArrayList<Airplane> getAirplaneList() {
        return airplaneList;
    }

    /**
     * Gets the airport list
     * @return airportList
     */
    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    /**
     * Might not be necessary
     * @return airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * Turns the airport list into a Iterator
     * @return Iterator airportList
     */
    public ListIterator<Airport> GetAirports() {
        return airportList.listIterator();
    }

    /**
     * might not be necessary
     * @return airport
     */
    public Airport getAirport() {
        return airport;
    }
    
    public void removeAirplane(Airplane airplane)
    {
        airplaneList.remove(airplane);
    }
}
