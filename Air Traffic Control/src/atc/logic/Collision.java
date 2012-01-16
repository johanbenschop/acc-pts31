/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

/**
 *
 * @author Henk
 */
public class Collision {

    private Airplane target;
    private Airplane crashobject;
    private int AccID;
    private Logging logging;

    public Collision(Airplane target, Airplane crashobject, int AccID) {
        this.target = target;
        this.crashobject = crashobject;
        this.AccID = AccID;
        logging = new Logging(AccID);
    }

    public void colldetect() {
        //Check if airplane are within 50km of each other to see if making a collision detection is worhtwhile.
        if (!target.getStatus().equals(Airplane.Statusses.TAKINGOFF) || !crashobject.getStatus().equals(Airplane.Statusses.TAKINGOFF)) {
        double dist = distFrom(Math.toRadians(target.getLocation().getLatitude()), Math.toRadians(target.getLocation().getLongitude()), Math.toRadians(crashobject.getLocation().getLatitude()), Math.toRadians(crashobject.getLocation().getLongitude()));
            if (dist <= 50) {
                double lat1 = 0, lat2 = 0, lat3 = 0, lon1 = 0, lon2 = 0, lon3 = 0, bearing1 = 0, bearing2 = 0, bearing13 = 0, bearing12 = 0, bearing21 = 0, bearing23 = 0;
                double dLat = 0, dLon = 0, dLon13 = 0, bearingA = 0, bearingB = 0, dist12 = 0, dist13 = 0, alpha1 = 0, alpha2 = 0, alpha3 = 0, distance1 = 0, distance2 = 0;


                //Assign variables for the first airplane.
                lat1 = Math.toRadians(target.getLocation().getLatitude());
                lon1 = Math.toRadians(target.getLocation().getLongitude());
                if (target.getDirection() < 0) {
                    bearing1 = Math.toRadians(target.getDirection() + 360);
                } else {
                    bearing1 = Math.toRadians((target.getDirection()));
                }

                //Assign variables for the second airplane.
                lat2 = Math.toRadians(crashobject.getLocation().getLatitude());
                lon2 = Math.toRadians(crashobject.getLocation().getLongitude());
                if (crashobject.getDirection() < 0) {
                    bearing2 = Math.toRadians(crashobject.getDirection() + 360);
                } else {
                    bearing2 = Math.toRadians(crashobject.getDirection());
                }

                //Calculate at wich geolocation the airplane would crash.
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
                    distance1 = dist;
                    distance2 = dist;
                } else {

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
                    distance2 = distFrom(lat2, lon2, lat3, lon3);
                }

                //Decide what status the airplane should be set to.
                if (distance1 <= 100 && distance2 <= 100) {
                    if ((distance1 <= 1 && target.getStatus() != Airplane.Statusses.CRASHED) || (distance2 <= 1 && target.getStatus() != Airplane.Statusses.CRASHED)) {
                        target.setStatus(Airplane.Statusses.CRASHED);
                        
                        crashobject.setStatus(Airplane.Statusses.CRASHED);
                        try{
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashed" );
                        }
                        catch(Exception e){
                            System.out.println("logging failed");
                        }
                    } 
                    else if ((distance1 <= 5 && target.getStatus() != Airplane.Statusses.CRASHING2)|| (distance2 <= 5 && target.getStatus() != Airplane.Statusses.CRASHING2)) {
                        target.setStatus(Airplane.Statusses.CRASHING2);
                        crashobject.setStatus(Airplane.Statusses.CRASHING2);
                        try{
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashing 2" );
                        }
                        catch(Exception e){
                            System.out.println("logging failed");
                        }
                    } 
                    else if ((distance1 <= 10 && target.getStatus() != Airplane.Statusses.CRASHING1) || (distance2 <= 10 && target.getStatus() != Airplane.Statusses.CRASHING1)) {
                        target.setStatus(Airplane.Statusses.CRASHING1);
                        crashobject.setStatus(Airplane.Statusses.CRASHING1);
                        try{
                        logging.WriteCollision(target.getAirplaneNumber(), crashobject.getAirplaneNumber(), "sab", "crashing 1" );
                        }
                        catch(Exception e){
                            System.out.println("logging failed");
                        }
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

    public Airplane getCrashobject() {
        return crashobject;
    }

    public Airplane getTarget() {
        return target;
    }    
}
