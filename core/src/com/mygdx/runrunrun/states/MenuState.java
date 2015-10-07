package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;

/**
 * Created by 343076 on 21/09/2015.
 */
public class MenuState extends State {

    private TextImage title;
    private float transitionVal;
    private boolean startTransition;

    public MenuState(GSM gsm){
        super(gsm);

        startTransition = false;
        title = new TextImage("RUN", Main.WIDTH/2, Main.HEIGHT/2,1);
        title.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.justTouched() && !startTransition){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            startTransition = true;
            transitionVal = 0f;
        }
    }

    private void transition(float dt){
        if(startTransition){

            transitionVal += 1f * dt;

            if(transitionVal >= 1f){
                gsm.set(new PlayState(gsm, 5));
            }

        }
    }

    public void update(float dt){

        handleInput();
        transition(dt);

    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        title.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(new Color(0,0,0,transitionVal));
        sr.rect(0,0,Main.WIDTH,Main.HEIGHT);


        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
