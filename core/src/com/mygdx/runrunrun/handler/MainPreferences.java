package com.mygdx.runrunrun.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by 343076 on 20/10/2015.
 */
public class MainPreferences {

    private Preferences prefs;

    public MainPreferences(){
        prefs = Gdx.app.getPreferences("RunRunRun-Save");
    }

    public Preferences getPrefs(){
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

    public void setName(String name){
        prefs.putString("Name",name);
        prefs.flush();
    }

    public String getName(){
        if(!prefs.getString("Name").equals(null)){
            return prefs.getString("Name");
        }else
            return "noname";
    }

    public void setGold(int gold){
        prefs.putInteger("Gold",gold);
        prefs.flush();
    }

    public int getGold(){
        if(prefs.getInteger("Gold") >= 0){
            return prefs.getInteger("Gold");
        }else
            return 0;
    }

}
