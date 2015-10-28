package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

    protected float exitTransitionVal;
    protected boolean exitTransition;

    protected boolean enterTransition;
    protected float enterTransitionVal;
    protected float getEnterTransitionValHelper;

    protected State(GSM gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        mouse = new Vector3();
    }

    protected void onEnterTransition(float dt){
        if(enterTransition){
            if(getEnterTransitionValHelper > 0)
                getEnterTransitionValHelper -= 1f * dt;
            else
                enterTransitionVal -= 0.5f * dt;

            if(enterTransitionVal <= 0f){
                enterTransition = false;
            }
        }
    }

    protected void onExitTransition(float dt){
        if(exitTransition){

            exitTransitionVal += 1f * dt;

            if(exitTransitionVal >= 1f){
                gsm.set(new PlayState(gsm, 5));
            }

        }
    }

    protected abstract void handleInput();
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);

    protected void shapeRender(ShapeRenderer sr){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if(enterTransition)
            sr.setColor((new Color(0,0,0,enterTransitionVal)));
        else if(exitTransition)
            sr.setColor(new Color(0,0,0, exitTransitionVal));

        sr.rect(0,0,Main.WIDTH,Main.HEIGHT);


        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
