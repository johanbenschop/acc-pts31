package atc.logic;

import atc.interfaces.IAirplane;
import java.rmi.RemoteException;

/**
 *
 * @author Henk
 */
public class Collision {

    private Airplane target;
    private Airplane crashobject;
    private Logging logging;

    public Collision(Airplane target, Airplane crashobject, int AccID) {
        this.target = target;
        this.crashobject = crashobject;
        logging = new Logging(AccID);
    }

    public void colldetect() throws RemoteException {
        //Check if airplane are within 50km of each other to see if making a collision detection is worhtwhile.
        if (target.getStatus().equals(Airplane.Statusses.INFLIGHT) || crashobject.getStatus().equals(Airplane.Statusses.INFLIGHT)
                || target.getStatus().equals(Airplane.Statusses.CRASHING1) || crashobject.getStatus().equals(Airplane.Statusses.CRASHING1)
                || target.getStatus().equals(Airplane.Statusses.CRASHING2) || crashobject.getStatus().equals(Airplane.Statusses.CRASHING2)) {
            double distance = distFrom(Math.toRadians(target.getLocation().getLatitude()), Math.toRadians(target.getLocation().getLongitude()), Math.toRadians(crashobject.getLocation().getLatitude()), Math.toRadians(crashobject.getLocation().getLongitude()));
            System.out.println(distance);
            if (distance <= 1) {
                if (target.getStatus() != Airplane.Statusses.CRASHED) {
                    target.setStatus(Airplane.Statusses.CRASHED);
                    crashobject.setStatus(Airplane.Statusses.CRASHED);
                    try {
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashed");
                    } catch (Exception e) {
                        System.out.println("logging failed");
                    }
                }
            } else if (distance <= 5) {
                if (target.getStatus() != Airplane.Statusses.CRASHING2) {
                    target.setStatus(Airplane.Statusses.CRASHING2);
                    crashobject.setStatus(Airplane.Statusses.CRASHING2);
                    try {
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashing 2");
                    } catch (Exception e) {
                        System.out.println("logging failed");
                    }
                }
            } else if (distance <= 10) {
                if (target.getStatus() != Airplane.Statusses.CRASHING1) {
                    target.setStatus(Airplane.Statusses.CRASHING1);
                    crashobject.setStatus(Airplane.Statusses.CRASHING1);
                    try {
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashing 1");
                    } catch (Exception e) {
                        System.out.println("logging failed");
                    }
                }
            }
        }
    }

    /**
     * Checks the distance between 2 given points lat and lon need to be given in radians.
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
//        distance = distance * 1000;
        return distance;
    }

    public IAirplane getCrashobject() {
        return crashobject;
    }

    public IAirplane getTarget() {
        return target;
    }
}
