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
                    System.out.println("Corrupt data line...");
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