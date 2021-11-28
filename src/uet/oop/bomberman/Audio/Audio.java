package uet.oop.bomberman.Audio;

import javax.sound.sampled.*;
import java.io.File;


public class Audio {
    public static void playSound(String pathSound) {
        String path = System.getProperty("user.dir") + "/res/";
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path + pathSound)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
