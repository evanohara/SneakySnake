package com.tweekgames.sneakysnake.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by EvansDesktop on 4/13/2015.
 */
public class AudioManager {
    public static final AudioManager instance = new AudioManager();

    private Music playingMusic;

    private AudioManager () {
    }

    public void play (Sound sound){
        play(sound, 1);
    }

    public void play (Sound sound, float volume){
        play(sound, volume, 1);
    }

    public void play (Sound sound, float volume, float pitch){
        play(sound, volume, pitch, 0);
    }

    public void play (Sound sound, float volume, float pitch, float pan) {
        sound.play(volume, pitch, pan);
    }

    public void play (Music music, boolean looping) {
        stopMusic();
        playingMusic = music;
        music.setLooping(looping);
        music.setVolume(1.0f);
        music.play();
    }

    public void stopMusic() {
        if (playingMusic != null) playingMusic.stop();
    }
}
