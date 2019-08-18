package com.glitchedcode.ct.sound;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public enum Sound {

    BALL_HIT_1("/sound/ball_hit_1.wav"),
    BALL_HIT_2("/sound/ball_hit_2.wav"),
    BALL_HIT_3("/sound/ball_hit_3.wav"),
    BALL_HIT_4("/sound/ball_hit_4.wav"),
    MENU_SELECT("/sound/menu_select.wav"),
    MENU_MOVE("/sound/menu_move.wav"),
    PAUSE("/sound/pause.wav"),
    UPGRADE_PURCHASE("/sound/upgrade_purchase.wav"),
    NEGATIVE_NOISE("/sound/permission_denied.wav"),
    TIME_WARNING_1("/sound/time_warning.wav"),
    PLAYER_WIN("/sound/player_win.wav"),
    PLAYER_SCORE("/sound/player_score.wav"),
    THEME_SONG("/sound/theme_song.wav");

    private final String fileName;

    Sound(String fileName) {
        this.fileName = fileName;
    }

    public void play() {
        play(Volume.REGULAR);
    }

    public void play(Volume volume) {
        if (volume != Volume.MUTE) {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream inS = CoolThing.class.getResourceAsStream(getFileName());
                BufferedInputStream bIn = new BufferedInputStream(inS);
                AudioInputStream aIn = AudioSystem.getAudioInputStream(bIn);
                if (clip.isRunning())
                    clip.stop();
                clip.open(aIn);
                FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                floatControl.setValue(volume.getValue());
                clip.start();
            } catch (Exception e) {
                Logger logger = CoolThing.getLogger();
                logger.error(Ansi.Color.RED, "Failed to play the sound file \"" + getFileName() + "\": " + e.getMessage());
                logger.handleError(Thread.currentThread(), e);
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    private AudioInputStream setupStream(InputStream stream) throws UnsupportedAudioFileException, IOException {
        AudioInputStream aIs = AudioSystem.getAudioInputStream(stream);
        AudioFormat audioFormat = aIs.getFormat();
        if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            aIs = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                            audioFormat.getSampleRate(), 16, audioFormat.getChannels() * 2,
                            audioFormat.getFrameSize(), audioFormat.getSampleRate(), false),
                    aIs);
        }
        return aIs;
    }
}