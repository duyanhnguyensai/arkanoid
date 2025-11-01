package uet.oop.UA;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> soundClips;
    private float volume = 0.7f; // Volume mặc định (0.0 - 1.0)

    private SoundManager() {
        soundClips = new HashMap<>();
        loadAllSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadAllSounds() {
        // SỬA: Dùng đường dẫn đúng và nhất quán
        loadSound("brick_break", "res/sounds/brick_break.wav");
        loadSound("paddle_hit", "res/sounds/paddle_hit.wav");
        loadSound("wall_hit", "res/sounds/wall_hit.wav");
        loadSound("powerup", "res/sounds/powerup.wav");
        loadSound("game_start", "res/sounds/game_start.wav");
        loadSound("game_over", "res/sounds/game_over.wav");
        loadSound("life_lost", "res/sounds/life_lost.wav");
        loadSound("background", "res/sounds/background.wav");
    }

    private void loadSound(String name, String path) {
        try {
            // SỬA: Dùng File trực tiếp để debug
            File soundFile = new File(path);
            System.out.println("Looking for sound: " + path);
            System.out.println("Absolute path: " + soundFile.getAbsolutePath());
            System.out.println("File exists: " + soundFile.exists());

            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + path);
                // Thử tìm trong các đường dẫn khác
                soundFile = new File("../" + path);
                System.out.println("Trying alternative: " + soundFile.getAbsolutePath());
                System.out.println("Alternative exists: " + soundFile.exists());
                if (!soundFile.exists()) {
                    return;
                }
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Set volume control
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float) Math.log10(volume));
            }

            soundClips.put(name, clip);
            System.out.println("Successfully loaded sound: " + name);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + name + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void playSound(String name) {
        playSound(name, false);
    }

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
                System.out.println("Playing sound: " + name);
            } catch (Exception e) {
                System.err.println("Error playing sound: " + name + " - " + e.getMessage());
            }
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    public void stopSound(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));

        // Update volume for all clips
        for (Clip clip : soundClips.values()) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }

    public float getVolume() {
        return volume;
    }

    public void stopAllSounds() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}