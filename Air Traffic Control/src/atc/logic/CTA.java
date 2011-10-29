package atc.logic;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileReader;

public class CTA {

    private GeoLocation location;
    private double width;
    private double length;
    private Airplane airplane;
    private Airport airport;
    private ArrayList<Airplane> airplaneList;
    private ArrayList<Airport> airportList;
    private ArrayList<AirplaneFactory> AvailableAirplanes;

    public CTA(GeoLocation location, double width, double length) {
        this.location = location;
        this.width = width;
        this.length = length;

        airportList = new ArrayList<>();

        try {
            loadAirportList();
        } catch (FileNotFoundException ex) {
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

    public void GetAirport(int AirportID) {
        for (Airport a : airportList) {
            if (a.getAirportID() == AirportID) {
                airport = a;
            }
        }
    }

    public ListIterator<Airport> GetAirports() {
        return airportList.listIterator();
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
        for (Airplane a : airplaneList) {
            //int xposa = a.xpos;
            //if(a.Status == INFLIGHT || a.Status == CRASHING)
            //{
            //a.posx + 300;
            //  for (Airplane b : airplaneList) {
            //      if(a.posx == b.posx){
            //      set a.status = CRASHED;
            //  }
            //}
        }
    }

    public void addAirplane(Airplane a) {
        airplaneList.add(a);
    }

    public void addAirport(Airport a) {
        airportList.add(a);
    }

    public void deleteAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            // als a.airplaneNumber == AirplaneNumber dan verwijder.. anders niets
        }
    }

    //Loads airports from the airports.dat file
    //Todo : Deleting the "" in all strings gained from the aiport.dat file
    public void loadAirportList() throws FileNotFoundException {
        Scanner s = new Scanner(new FileReader("airports.dat"));
        while (s.hasNext()) {
            String line = s.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            while (lineScanner.hasNext()) {
                try {
                int id = lineScanner.nextInt();
                String name = lineScanner.next();
                String city = lineScanner.next();
                String country = lineScanner.next();
                String iata_faa = lineScanner.next();
                String icao = lineScanner.next();
                double latitude = Double.parseDouble(lineScanner.next());
                double longitude = Double.parseDouble(lineScanner.next());
                int altitude = lineScanner.nextInt();
                double timezone = lineScanner.nextDouble();
                String dst = lineScanner.next();
                
                GeoLocation location = new GeoLocation(longitude, latitude, altitude);
                
                //Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
                airportList.add(airport);
                } catch (NumberFormatException e) {
                    System.out.println("Corrupt data line...");
                } catch (InputMismatchException e) {
                    System.out.println("Corrupt data line...");
                }
            }
            lineScanner.close();
        }
        s.close();
    }

    //Loads available airplanes from the AvailableAirplanes.txt file
    public void loadAvailableAirplaneList() throws FileNotFoundException {
        Scanner s = new Scanner(new FileReader("AvailableAirplanes.txt"));
        while (s.hasNext()) {
            String line = s.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter("|");
            while (lineScanner.hasNext()) {
                AirplaneFactory airplaneFactory = new AirplaneFactory(lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.next(), lineScanner.next(), lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt());
                AvailableAirplanes.add(airplaneFactory);
            }
            lineScanner.close();
        }
        s.close();
    }
    //Getters

    public Airplane getCurrentSelectedAirplane() {
        return airplane;
    }

    public List<AirplaneFactory> getAvailableAirplanes() {
        return AvailableAirplanes;
    }
}