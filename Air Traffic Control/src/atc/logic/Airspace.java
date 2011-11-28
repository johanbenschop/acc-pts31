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
        for (int e = -50; e < 70; e += 20) {
            
        
        for (int i = -140; i < 160; i += 20) {
         ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, i, i+20))));  
        
        }
    //}
//
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -180, -160))));
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
   }
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
    
    
    
}
