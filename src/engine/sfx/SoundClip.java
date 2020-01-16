package engine.sfx;

import engine.ResourceLoader;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip {
    private Clip clip;
    private FloatControl gainControl;

    public SoundClip(String path) {
        clip = ResourceLoader.getSound(path).getClip();
        gainControl = ResourceLoader.getSound(path).getGainControl();
    }

    public void play() {
        if(clip==null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        while(!clip.isRunning()) {
            clip.start();
        }
    }

    public void stop() {
        if(clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(int volume) {
        if(volume > 100) volume = 100;
        float newGain = gainControl.getMinimum() + volume * (gainControl.getMaximum() - gainControl.getMinimum()) / 100f;
        gainControl.setValue(newGain);
    }
}
