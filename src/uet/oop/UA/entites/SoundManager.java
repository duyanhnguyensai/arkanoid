package uet.oop.UA.entites;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages audio playback for the game using a singleton pattern.
 * Handles loading, playing, stopping, and volume control for all game sounds.
 */
public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> soundClips;
    private float volume = 0.7f;

    /**
     * Private constructor for singleton pattern.
     * Initializes the sound clips map and loads all game sounds.
     */
    private SoundManager() {
        soundClips = new HashMap<>();
        loadAllSounds();
    }

    /**
     * Returns the singleton instance of SoundManager.
     *
     * @return the singleton SoundManager instance
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Loads all game sounds into the sound clips map.
     * Includes sounds for game events, collisions, and background music.
     */
    private void loadAllSounds() {
        loadSound("brick_break", "res/sounds/brick_break.wav");
        loadSound("paddle_hit", "res/sounds/paddle_hit.wav");
        loadSound("wall_hit", "res/sounds/wall_hit.wav");
        loadSound("powerup", "res/sounds/powerup.wav");
        loadSound("game_start", "res/sounds/game_start.wav");
        loadSound("game_over", "res/sounds/game_over.wav");
        loadSound("life_lost", "res/sounds/life_lost.wav");
        loadSound("background", "res/sounds/background.wav");
        loadSound("victory", "res/sounds/victory.wav");
    }

    /**
     * Loads a single sound file and stores it in the sound clips map.
     * Handles volume control setup and error reporting for missing files.
     *
     * @param name the identifier for the sound
     * @param path the file path to the sound resource
     */
    private void loadSound(String name, String path) {
        try {
            File soundFile = new File(path);

            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + path);
                soundFile = new File("../" + path);
                if (!soundFile.exists()) {
                    return;
                }
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float) Math.log10(volume));
            }

            soundClips.put(name, clip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + name + " - " + e.getMessage());
        }
    }

    /**
     * Plays a sound once with the current volume settings.
     *
     * @param name the identifier of the sound to play
     */
    public void playSound(String name) {
        playSound(name, false);
    }

    /**
     * Plays a sound with optional looping.
     * Stops the sound if it's already playing before restarting.
     *
     * @param name the identifier of the sound to play
     * @param loop true to loop continuously, false to play once
     */
    public void playSound(String name, boolean loop) {
        Clip clip = soundClips.get(name);
        if (clip != null) {
            try {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
            } catch (Exception e) {
                System.err.println("Error playing sound: " + name + " - " + e.getMessage());
            }
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Stops a currently playing sound.
     *
     * @param name the identifier of the sound to stop
     */
    public void stopSound(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Sets the volume for all sounds.
     * Volume is clamped between 0.0 (silent) and 1.0 (maximum).
     *
     * @param volume the volume level between 0.0 and 1.0
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));

        for (Clip clip : soundClips.values()) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }

    /**
     * Returns the current volume level.
     *
     * @return the current volume between 0.0 and 1.0
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Stops all currently playing sounds.
     */
    public void stopAllSounds() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}