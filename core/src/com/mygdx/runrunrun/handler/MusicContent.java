package com.mygdx.runrunrun.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

/**
 * Created by 343076 on 07/10/2015.
 */
public class MusicContent {

    private HashMap<String, Music> musics;

    public MusicContent(){
        musics = new HashMap<String, Music>();
    }

    public void loadMusic(String path, String key){
        musics.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    public Music getMusic(String key) { return musics.get(key); }

    public void playMusic(String key){

        musics.get(key).setLooping(true);
        musics.get(key).play();

    }

}
