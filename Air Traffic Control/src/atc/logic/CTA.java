package atc.logic;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class CTA {

    private GeoLocation location;
    private double width;
    private double length;
    private Airplane airplane;
    private Airport airport;
    private ArrayList<Airplane> airplaneList;
    private ArrayList<Airport> airportList;
    
    public CTA(GeoLocation location, double width, double length) {
        this.location = location;
        this.width = width;
        this.length = length;

        airplaneList = new ArrayList<>();
        airportList = new ArrayList<>();

        try {
           loadAirportList();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
                return a;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public void DetectAirplane() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return
     */
    public void Collision() {
        double lat1 = 0, lat2 = 0, lat3 = 0, lon1 = 0, lon2 = 0, lon3 = 0, bearing1 = 0, bearing2 = 0, bearing13 = 0, bearing12 = 0, bearing21 = 0, bearing23 = 0;
        double dLat = 0, dLon = 0, dLon13 = 0, bearingA =0, bearingB = 0, dist12 = 0, dist13 = 0, alpha1 = 0, alpha2 = 0, alpha3 = 0, distance1 = 0, distance2 = 0, tijd1 = 0, tijd2 = 0;
        
        if (airplaneList.size() > 1) {
            for (Airplane target : airplaneList) {
                if (target.getStatus().equals(Airplane.Statusses.INFLIGHT)) {
                    lat1 = Math.toRadians(target.getLocation().getLatitude());
                    lon1 = Math.toRadians(target.getLocation().getLongitude());
                    bearing1 = Math.toRadians(target.getDirection());
                    for (Airplane crashobject : airplaneList) {
                        if (crashobject.getStatus().equals(Airplane.Statusses.INFLIGHT)) {
                            if (crashobject != target && crashobject.getAltitude() == target.getAltitude()) {
                                lat2 = Math.toRadians(crashobject.getLocation().getLatitude());
                                lon2 = Math.toRadians(crashobject.getLocation().getLongitude());
                                bearing2 = Math.toRadians(crashobject.getDirection());
                                
                                bearing13 = bearing1;
                                
                                bearing23 = bearing2;
                                
                                dLat = lat2 - lat1;
                                dLon = lon2 - lon1;
                                dist12 = 2 * Math.asin(Math.sqrt(Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon /2)));
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
                                
                                
                            }
                        }
                    }
                }
            }
        }
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
            if(a.getAirplaneNumber() == AirplaneNumber)
            {
                airplaneList.remove(a);
            }
        }
    }

    //Loads airports from the airports.dat file
    //Todo : Deleting the "" in all strings gained from the aiport.dat file
    public void loadAirportList() throws FileNotFoundException, IOException {
            FileInputStream fstream = new FileInputStream("airports.dat");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strline;
            while((strline = br.readLine()) != null)
            {
             try {
            String[] props = strline.split(",");
                int id = Integer.parseInt(props[0]);
                String name = props[1];
                String city = props[2];
                String country = props[3];
                String iata_faa = props[4];
                String icao = props[5];
                double latitude = Double.parseDouble(props[6]);
                double longitude = Double.parseDouble(props[7]);
                int altitude = Integer.parseInt(props[8]);
                double timezone = Double.parseDouble(props[9]);
                String dst = props[10];
                
                GeoLocation location = new GeoLocation(longitude, latitude, altitude);
                
                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
                airportList.add(airport);
                }  catch (NumberFormatException | InputMismatchException e) {
                    System.out.println("Corrupt data line on airport.dat...");
                }}
    }

 

    
    //Getters
    public Airplane getCurrentSelectedAirplane() {
        return airplane;
    }
    
    public ArrayList<Airplane> getAirplaneList()
    {
        return airplaneList;
    }
    
    public ArrayList<Airport> getAirportList()
    {
        return airportList;
    }
    
    public Airplane getAirplane()
    {
        return airplane;
    }
    
    public ListIterator<Airport> GetAirports() {
        return airportList.listIterator();
    }
    
    public Airport getAirport()
    {
        return airport;
    }
}
