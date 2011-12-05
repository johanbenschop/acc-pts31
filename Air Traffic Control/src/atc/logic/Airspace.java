/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Robbert
 */
public class Airspace {

    private ArrayList<ACC> ACCs;
    private int ID = 0;
    ///!!!!!private int IDStart = 1000;
    private ACC currentACC;
    //    private static CTA cta2 = new CTA(new GeoSector(40, 60, -10, 10));
    private static CTA cta = new CTA(new GeoSector(40, 60, 10, 30));
    public static ACC acc = new ACC(343, cta);
//    public static ACC acc2 = new ACC(344, cta2);

    public Airspace() {
        ACCs = new ArrayList<ACC>();
//        for (int e = -50; e < 70; e += 20) {
//            !!!!!!!!ID = IDStart;
//        
//        for (int i = -140; i < 160; i += 20) {
//         ACCs.add(new ACC(ID, new CTA(new GeoSector(e, e+20, i, i+20))));  
//        !!!!!ID++;
//        }
//       !!!!! IDStart+= 100;
//    //}
//
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, -10, 10))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -160, -140))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -140, -120))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -120, -100))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -100, -80))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -80, -60))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -60, -40))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -40, -20))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -20, 0))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 0, 20))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 20, 40))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 40, 60))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 60, 80))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 80, 100))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 100, 120))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 120, 140))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 140, 160))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 160, 180))));
        //}
//        
    }

    public ACC getACC(int ID) {
        for (ACC acc : ACCs) {
            if (acc.GetID() == ID) {
                return acc;
            }
        }
        return null;
    }

    public ListIterator<ACC> GetACCs() {
        return ACCs.listIterator();
    }

    public ACC getCurrentACC() {
        return currentACC;
    }

    public void setCurrentACC(ACC currentACC) {
        this.currentACC = currentACC;
    }

    public void setCurrentACC(int ID) {
        for (ACC acc : ACCs) {
            if (acc.GetID() == ID) {
                this.currentACC = acc;
            }
        }
        this.currentACC = null;
    }
    
    /**
     * Gets all the adjacent ACC's from the current ACC and returns this ArrayList.
     * @param ACCID
     * @return Arraylist with adjacent ACC's
     */
    public ArrayList getAdjacentACCs(int CurrentACCID) {
            ArrayList<ACC> adjacentACCList = new ArrayList();
        for (ACC acc : ACCs) {
            if (((CurrentACCID - 101) == acc.GetID()) || ((CurrentACCID - 100) == acc.GetID())
                    || ((CurrentACCID - 99) == acc.GetID()) || ((CurrentACCID - 1) == acc.GetID())
                    || ((CurrentACCID + 1) == acc.GetID()) || ((CurrentACCID + 99) == acc.GetID())
                    || ((CurrentACCID + 100) == acc.GetID()) || ((CurrentACCID + 101) == acc.GetID())) {
                adjacentACCList.add(acc);
            }
        }
        return adjacentACCList;
    }

    public void BorderControl() {
        for (ACC adjacentACC : currentACC.getAdjacentACCList()) {
            for (Iterator<Flightplan> it = adjacentACC.getFlightplans(); it.hasNext();) {
                Flightplan flightplan = it.next();
                if (this.currentACC.GetCTA().sectorGreater.containsGeoLocation(flightplan.getAirplane().getLocation())) {
                    if (this.currentACC.GetCTA().sector.containsGeoLocation(flightplan.getAirplane().getLocation())) {
                        if (!adjacentACC.ContainsFlightplan(flightplan)) {
                            currentACC.unassignFlightFromController(flightplan);
                            currentACC.assignFlightToController(flightplan);
                            adjacentACC.removeFlightPlan(flightplan);
                            adjacentACC.GetCTA().removeAirplane(flightplan.getAirplane());
                        }
                    } else {
                        if (!currentACC.ContainsFlightplan(flightplan)) {
                            currentACC.addFlightPlan(flightplan);
                            currentACC.GetCTA().addAirplane(flightplan.getAirplane());
                        }
                    }
                }
            }
        
        ///DIT BEWAREN (PAUL)
        /*for(ACC adjacentACC : currentACC.getAdjacentACCList())
        {
        for (Iterator<Flightplan> it = adjacentACC.getFlightplans(); it.hasNext();) {
            Flightplan flightplan = it.next();
            if (this.currentACC.GetCTA().sectorGreater.containsGeoLocation(flightplan.getAirplane().getLocation())) {
                if (this.currentACC.GetCTA().sector.containsGeoLocation(flightplan.getAirplane().getLocation())) {
                    currentACC.unassignFlightFromController(flightplan);
                    currentACC.assignFlightToController(flightplan);
                    adjacentACC.removeFlightPlan(flightplan);
                    currentACC.addFlightPlan(flightplan);
                    adjacentACC.GetCTA().removeAirplane(flightplan.getAirplane());
                    currentACC.GetCTA().addAirplane(flightplan.getAirplane());
                    //flightplan.getAirplane().setControlCTA(this.currentACC.GetCTA());
                } else {
                    //flightplan.getAirplane().setVisibleCTA(this.currentACC.GetCTA())
                    currentACC.GetCTA().addAirplane(flightplan.getAirplane());
                }
            }
        }*/
        }
    }
}
