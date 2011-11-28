/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Robbert
 */
public class Airspace {
    private ArrayList<ACC> ACCs;
    private int ID;
    private ACC currentACC;
    
    //    private static CTA cta2 = new CTA(new GeoSector(40, 60, -10, 10));
    private static CTA cta = new CTA(new GeoSector(40, 60, 10, 30));
    public static ACC acc = new ACC(343, cta);
//    public static ACC acc2 = new ACC(344, cta2);
    public Airspace() {
        ACCs = new ArrayList<ACC>();
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, -10, 10))));
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, 10, 30))));
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, 30, 50))));
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, 50, 70))));
        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, 70, 90))));
        
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
    
    
    
}
