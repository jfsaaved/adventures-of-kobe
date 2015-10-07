package com.mygdx.runrunrun.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by 343076 on 06/10/2015.
 */
public class Sounds {

    private HashMap<String, Sound> sounds;

    public Sounds(){
        sounds = new HashMap<String, Sound>();
    }

    public void loadSound(String path, String key){
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    public Sound getSound(String key){

        return sounds.get(key);
    }


}
