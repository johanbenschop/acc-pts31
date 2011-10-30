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
    private ArrayList<AirplaneFactory> AvailableAirplanes;

    public CTA(GeoLocation location, double width, double length) {
        this.location = location;
        this.width = width;
        this.length = length;

        airportList = new ArrayList<>();

        try {
            loadAirportList();
            loadAvailableAirplaneList();
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

    public Airport GetAirport(int AirportID) {
        for (Airport a : airportList) {
            if (a.getAirportID() == AirportID) {
                airport = a;
                return a;
            }
        }
        return null;
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
    public void loadAirportList() throws FileNotFoundException, IOException {
            FileInputStream fstream = new FileInputStream("airports.dat");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strline;
            while((strline = br.readLine()) != null)
            {
             try {
            String[] props = strline.split("|");
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

    //Loads available airplanes from the AvailableAirplanes.txt file
    public void loadAvailableAirplaneList() throws FileNotFoundException, IOException {
            FileInputStream fstream2 = new FileInputStream("AvailableAirplanes.txt");

            DataInputStream in2 = new DataInputStream(fstream2);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));

            String strline;
            while((strline = br2.readLine()) != null)
            {
             try {
            String[] props2 = strline.split(",");

                int MaxSpeed = Integer.parseInt(props2[0]);
                int MinSpeed = Integer.parseInt(props2[1]);
                int Weight = Integer.parseInt(props2[2]);
                String Type = props2[3];
                String Manufacturer = props2[4];
                int PlaneHeight = Integer.parseInt(props2[5]);
                int PlanWidth = Integer.parseInt(props2[6]);
                int PlaneLength = Integer.parseInt(props2[7]);
                int MaxFuel = Integer.parseInt(props2[8]);
                int FuelUsage = Integer.parseInt(props2[9]);
  
                AirplaneFactory airplaneFactory = new AirplaneFactory(MaxSpeed, MinSpeed, Weight, Type, Manufacturer, PlaneHeight, PlanWidth, PlaneLength, MaxFuel, FuelUsage);
                System.out.println(airplaneFactory.getType());
                AvailableAirplanes.add(airplaneFactory);
  }  catch (NumberFormatException | InputMismatchException e) {
                    System.out.println("Corrupt data line...");
                }}
    }

    public Airplane getCurrentSelectedAirplane() {
        return airplane;
    }

    public ListIterator<AirplaneFactory> getAvailableAirplanes() {
        return AvailableAirplanes.listIterator();
    }
}