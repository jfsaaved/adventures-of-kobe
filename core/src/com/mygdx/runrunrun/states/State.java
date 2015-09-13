package com.mygdx.runrunrun.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 23/07/2015.
 */
public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera cam;
    protected Vector3 mouse;


    protected State(GSM gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        mouse = new Vector3();

    }

    protected abstract void handleInput();
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);
    protected abstract void shapeRender(ShapeRenderer sr);

}
