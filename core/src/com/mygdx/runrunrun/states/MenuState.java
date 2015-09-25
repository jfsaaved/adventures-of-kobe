package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by 343076 on 21/09/2015.
 */
public class MenuState extends State {

    public MenuState(GSM gsm){
        super(gsm);
    }

    public void handleInput(){

    }

    public void update(float dt){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            gsm.set(new PlayState(gsm,5));

        }
    }

    public void render(SpriteBatch sb){

    }

    public void shapeRender(ShapeRenderer sr){

    }
}
