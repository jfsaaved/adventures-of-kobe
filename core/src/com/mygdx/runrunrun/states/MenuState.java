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

    private TextImage highScore;
    private TextImage title;
    private TextImage startButton;

    public MenuState(GSM gsm){
        super(gsm);

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        exitTransition = false;

        title = new TextImage("RUN", Main.WIDTH/2, Main.HEIGHT/2 + 80,1);
        startButton = new TextImage("START", Main.WIDTH/2, Main.HEIGHT/2,1);
        highScore = new TextImage("" + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2 - 80,1);
        title.setTextHide(false);
        startButton.setTextHide(false);
        highScore.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.justTouched() && !exitTransition && !enterTransition){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            if(startButton.contains(mouse.x, mouse.y)) {
                Main.sounds.playSound("select");
                exitTransition = true;
                exitTransitionVal = 0f;
            }
        }
    }

    public void update(float dt){

        handleInput();
        onEnterTransition(dt);
        onExitTransition(dt);

    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        title.render(sb);
        startButton.render(sb);
        highScore.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        super.shapeRender(sr);
    }
}
