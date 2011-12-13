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
    private ArrayList<Airport> airportList = new ArrayList<Airport>();
    /**
     * A class used to determine the status of an airplane for possible collision danger
     */
    private ArrayList<Collision> collision;
    /**
     * Timer used to calculate the collision status every xx miliseconds
     */
    private Timer timer;

    private class collisionTimer extends TimerTask {

        public void run() {
            Collision temp = null;
            if (!collision.isEmpty()) {
                for (Collision coll : collision) {
                    coll.colldetect();
                    if (coll.getTarget().getStatus().equals(Airplane.Statusses.CRASHED) || coll.getCrashobject().getStatus().equals(Airplane.Statusses.CRASHED)) {
                        temp = coll;
                    }
                }
            }
            if (temp != null) {
                collision.remove(temp);
            }
        }
    }

    /***************Constructor**********/
    /**
     * This is a constructor used for making a CTA it contains a location and the width/length
     * @param Location is the location of the CTA
     * @param Width is the width of the CTA
     * @param Length is the length of the CTA
     */
    public CTA(GeoSector location, ArrayList<Airport> airportlist) {
        this.sector = location;

        airplaneList = new ArrayList<>();
        collision = new ArrayList<>();
        this.airportList = airportlist;
        timer = new Timer();
        timer.schedule(new collisionTimer(), 0, 100);

//        try {
//            loadAirportList(sector);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
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

    public Airplane GetAirplane(int AirplaneID) {
        for (Airplane a : airplaneList) {
            if (a.getAirplaneNumber() == AirplaneID) {
                airplane = a;
            }
        }
        return airplane;
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
    }

    /**
     * Adds a airplane to the airplaneList
     * @return
     */
    public void addAirplane(Airplane a) {
        airplaneList.add(a);
        for (Airplane crashobject : airplaneList) {
            if (crashobject != a) {
                collision.add(new Collision(a, crashobject));
            }
        }
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

    public void CreateGreaterSector() {
        double maxLatitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 0, 100)).getLongitude();
        double minLatitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), 180, 100)).getLongitude();
        double maxLongitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 90, 100)).getLatitude();
        double minLongitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), -90, 100)).getLatitude();
        sectorGreater = new GeoSector(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    public void removeAirplane(Airplane airplane) {
        airplaneList.remove(airplane);
    }

    /***************Getters**********/
    public Airplane getCurrentSelectedAirplane() {
        return airplane;
    }

    public ArrayList<Airplane> getAirplaneList() {
        return airplaneList;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public GeoSector getSector() {
        return sector;
    }

    /**
     * Turns the airport list into a Iterator
     * @return Iterator airportList
     */
    public ListIterator<Airport> GetAirports() {
        return airportList.listIterator();
    }

    public Airport getAirport() {
        return airport;
    }
    
    
    
    
    
    /**
     * Loads airports from the airports.dat file
     * @throws FileNotFoundException if the file doesn't exist
     * @throws IOException 
     */
//    public void loadAirportList() throws FileNotFoundException, IOException {
//        FileInputStream fstream = new FileInputStream("airports.dat");
//
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//        String strline;
//        while ((strline = br.readLine()) != null) {
//            try {
//                String[] props = strline.split(",");
//                int id = Integer.parseInt(props[0]);
//                String name = props[1].replaceAll("\"", "");
//                String city = props[2].replaceAll("\"", "");
//                String country = props[3].replaceAll("\"", "");
//                String iata_faa = props[4].replaceAll("\"", "");
//                String icao = props[5].replaceAll("\"", "");
//                double latitude = Double.parseDouble(props[6]);
//                double longitude = Double.parseDouble(props[7]);
//                int altitude = Integer.parseInt(props[8]);
//                double timezone = Double.parseDouble(props[9]);
//                String dst = props[10].replaceAll("\"", "");
//
//                GeoLocation location = new GeoLocation(longitude, latitude, altitude);
//
//                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
//                airportList.add(airport);
//            } catch (NumberFormatException | InputMismatchException e) {
//                System.out.println("Corrupt data line on airports.dat...");
//            }
//        }
//    }
    /**
     * Loads airports from the airports.dat file and filters the data for the GeoSecor
     * @throws FileNotFoundException if the file doesn't exist
     * @throws IOException 
     */
//    public void loadAirportList(GeoSector sector) throws FileNotFoundException, IOException {
//        FileInputStream fstream = new FileInputStream("airports.dat");
//
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//        String strline;
//        while ((strline = br.readLine()) != null) {
//            try {
//                String[] props = strline.split(",");
//                int id = Integer.parseInt(props[0]);
//                String name = props[1].replaceAll("\"", "");
//                String city = props[2].replaceAll("\"", "");
//                String country = props[3].replaceAll("\"", "");
//                String iata_faa = props[4].replaceAll("\"", "");
//                String icao = props[5].replaceAll("\"", "");
//                double latitude = Double.parseDouble(props[6]);
//                double longitude = Double.parseDouble(props[7]);
//                int altitude = Integer.parseInt(props[8]);
//                double timezone = Double.parseDouble(props[9]);
//                String dst = props[10].replaceAll("\"", "");
//
//                GeoLocation location = new GeoLocation(longitude, latitude, altitude);
//                if (!sector.containsGeoLocation(location)) {
//                    continue; // The airport is not within the CTA
//                }
//
//                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
//                airportList.add(airport);
//            } catch (NumberFormatException | InputMismatchException e) {
//                System.out.println("Corrupt data line on airports.dat...");
//            }
//        }
//    }
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
}
