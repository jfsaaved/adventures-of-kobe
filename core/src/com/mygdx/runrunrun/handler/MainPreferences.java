package com.mygdx.runrunrun.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by 343076 on 20/10/2015.
 */
public class MainPreferences {

    private Preferences prefs;

    public MainPreferences(){
        prefs = Gdx.app.getPreferences("save");
    }

    public Preferences getPref(){
        return prefs;
    }

    public void setHighScore(int score){
        prefs.putInteger("High Score",score);
        prefs.flush();
    }

    public int getHighScore(){
        if(prefs.getInteger("High Score") >= 0) {
            return prefs.getInteger("High Score");
        }
        else{
            return 0;
        }
    }

}
