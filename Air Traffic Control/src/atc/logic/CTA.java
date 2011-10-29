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
    private List<Airplane> airplaneList;
    private List<Airport> airportList;
    private List<AirplaneFactory> AvailableAirplanes;

    public CTA(GeoLocation location, double width, double length) {
        this.location = location;
        this.width = width;
        this.length = length;
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
                Airport airport = new Airport(lineScanner.nextInt(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.next(), lineScanner.nextInt(), lineScanner.next());
                airportList.add(airport);
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