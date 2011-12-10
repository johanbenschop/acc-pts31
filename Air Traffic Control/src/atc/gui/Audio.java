package atc.gui;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

/**
 * Class with static member play() to play an audio file.
 * Added some basic scheduling so files do not play through each other.
 * @author Robbert Jansen & Johan Benschop
 */
public class Audio {

    private static Clip clip;
    private static ArrayList<queueItem> queue = new ArrayList<>();

    public static enum Sound {

        ALARM1, ALARM2, ALARM3, ALARM4, ALARM5, ALARM6;
    }

    /**
     * Plays the sound file an X number of times. Queue's the sound if one is currently playing.
     * @param sound The sound to be played.
     * @param times Amount of times to play this in a loop.
     */
    public static synchronized void play(final Sound sound, final int times) {
        init();

        try {
            String fileName;
            switch (sound) {
                case ALARM1:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-1.wav";
                    break;
                case ALARM2:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-2.wav";
                    break;
                case ALARM3:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-3.wav";
                    break;
                case ALARM4:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-4.wav";
                    break;
                case ALARM5:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-5.wav";
                    break;
                case ALARM6:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-6.wav";
                    break;
                default:
                    fileName = "src/atc/gui/resources/Alarms/Alarm-1.wav";
                    break;
            }

            if (clip.isOpen()) {
                queue.add(new queueItem(fileName, times));
                System.out.println("Adding to queu: " + fileName);
            } else {
                _playSound(fileName, times);
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
        }
    }

    /**
     * Stops the current playing sound, if any.
     * If there are sounds in the queue waiting they will get played next.
     */
    public static void stop() {
        clip.close();
    }

    /**
     * Stops the current playing sound and clears the queue.
     * No sounds will be played.
     */
    public static void stopAll() {
        queue.clear();
        clip.close();
    }

    private static void _playSound(String fileName, int times) {
        try {
            // Read audio data from source
            InputStream audioSrc = new FileInputStream(fileName);
            // Add buffer for mark/reset support
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            clip.open(audioStream);
            clip.start();
            clip.loop(times - 1);

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void init() {
        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.addLineListener(new LineListener() {

                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP && !queue.isEmpty()) {
                            clip.close();
                            queueItem item = queue.get(0);
                            queue.remove(item);
                            System.out.println("Removing to queu: " + item.fileName);
                            _playSound(item.fileName, item.times);
                        }
                        else if (event.getType() == LineEvent.Type.STOP && queue.isEmpty()) {
                            clip.close();
                        }
                    }
                });
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class queueItem {

        private String fileName;
        private int times;

        public queueItem(String fileName, int times) {
            this.fileName = fileName;
            this.times = times;
        }
    }
}