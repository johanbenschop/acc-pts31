package atc.logic;

import java.util.*;

public class CTA {

    private GeoLocation location;
    private double width;
    private double length;
    private Airplane airplane;
    private List<Airplane> airplaneList;
    private Iterator<Airplane> airplaneIterator;
    private int airplaneNumber = 1;

    public CTA(GeoLocation location, double width, double length) {
        this.location = location;
        this.width = width;
        this.length = length;
        airplaneIterator = airplaneList.iterator();
    }

    /**
     * 
     * @return 
     */
    public Airplane GetActiveAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            // als a.airplaneNumber == AirplaneNumber 
            return a;
        }
        return null; // geef airplane terug of niets als er geen is gevodnen 
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
        //a.airplaneNumber = airplaneNumber;
        //airplaneNumber ++;
        airplaneList.add(a);

    }

    public void deleteAirplane(int AirplaneNumber) {
        for (Airplane a : airplaneList) {
            // als a.airplaneNumber == AirplaneNumber dan verwijder.. anders niets
        }
    }
}