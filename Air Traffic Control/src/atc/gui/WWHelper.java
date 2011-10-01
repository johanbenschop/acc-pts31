/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;

/**
 *
 * @author johan
 */
public class WWHelper {

    static void setup(WorldWindowGLCanvas worldWindowGLCanvas1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
   
   private WWHelper() {
   }

   public static void setup(final WorldWindow ww) {
       Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
       ww.setModel(m);
   }
}