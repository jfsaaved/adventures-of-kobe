package com.mygdx.runrunrun.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Hero;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private Hero hero;

    public PlayState(GSM gsm){
        super(gsm);

        hero = new Hero();

    }

    public void handleInput(){

    }

    public void update(float dt){

        hero.update(dt);

    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        hero.render(sb);

        sb.end();

    }
}
