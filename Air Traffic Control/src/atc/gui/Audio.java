/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import sun.audio.*;    //import the sun.audio package
import java.io.*;

/**
 *
 * @author Robbert
 */
public class Audio {

    static String fileName;

    static enum Statusses {

        ALARM1, ALARM2, ALARM3, ALARM4, ALARM5,
        ALARM6;
    }

    public static void play(Statusses s) throws FileNotFoundException, IOException {

        switch (s) {
            case ALARM1:
                fileName = "src/atc/gui/resources/Alarm-1.wav";
                break;
            case ALARM2:
                fileName = "src/atc/gui/resources/Alarm-2.wav";
                break;
            case ALARM3:
                fileName = "src/atc/gui/resources/Alarm-3.wav";
                break;
            case ALARM4:
                fileName = "src/atc/gui/resources/Alarm-4.wav";
                break;
            case ALARM5:
                fileName = "src/atc/gui/resources/Alarm-5.wav";
                break;
            case ALARM6:
                fileName = "src/atc/gui/resources/Alarm-6.wav";
                break;
        }
        InputStream in = new FileInputStream(fileName);
        AudioStream as = new AudioStream(in);
        AudioPlayer.player.start(as);
    }

}
/*import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

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
*/