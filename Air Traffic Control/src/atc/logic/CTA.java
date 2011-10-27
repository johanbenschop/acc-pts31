package atc.logic;

import java.util.*;

public class CTA {

    private GeoLocation location;
    private double width;
    private double length;
    private Airplane airplane;
    private Airport airport;
    private List<Airplane> airplaneList;
    private List<Airport> airportList;

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
    
    public void GetAirport(int AirportID){
        for(Airport a : airportList){
            if(a.getAirportID() == AirportID){
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

    public void addAirport(Airport a){
        airportList.add(a);
    }
    
    public void deleteAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            // als a.airplaneNumber == AirplaneNumber dan verwijder.. anders niets
        }
    }
    
    //Getters
    public Airplane getCurrentSelectedAirplane()
    {
        return airplane;
    }
}