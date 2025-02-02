package Utils;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundPlayer {

    public static void playSound(String resourcePath) {
        try {
            // Debug: Print the resource path
            System.out.println("Trying to load sound from: " + resourcePath);

            // Load the file as a resource
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                throw new IllegalArgumentException("Sound file not found: " + resourcePath);
            }

            // Convert the InputStream to an AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound

            System.out.println("Sound played successfully: " + resourcePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
