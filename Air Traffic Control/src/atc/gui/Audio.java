/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author johan
 */
public class Audio {
    private AudioClip alarm;
    
    
    public void soundAlarm() {
        try {
            alarm = Applet.newAudioClip(new URL("src/atc/logic/resources/Alarm-6.wav"));
            alarm.play();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
}
