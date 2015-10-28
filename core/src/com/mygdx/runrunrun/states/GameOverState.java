package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;

/**
 * Created by 343076 on 12/08/2015.
 */
public class GameOverState extends State {

    private TextImage game_over_text;
    private TextImage highScore;
    private TextImage currentScore;

    private TextImage menu;
    private TextImage play;

    private float transitionVal;
    private boolean startTransition;

    public GameOverState(GSM gsm, int score){
        super(gsm);

        startTransition = false;

        currentScore = new TextImage("" + score, Main.WIDTH/2, Main.HEIGHT/2, 1f );
        highScore = new TextImage("" + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2 - 100, 1f);
        game_over_text = new TextImage("GAME OVER!",Main.WIDTH/2, Main.HEIGHT/2 + 100, 1f);

        if(score > Main.pref.getHighScore()) {
            Main.pref.setHighScore(score);
            currentScore.update("NEW HIGH SCORE: " + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2, 1f);
            currentScore.setTextHide(false);
        }else{
            currentScore.setTextHide(false);
            highScore.setTextHide(false);
        }

        game_over_text.setTextHide(false);

        menu = new TextImage("MENU", Main.WIDTH/2 + 100, Main.HEIGHT/2 - 150, 1f);
        play = new TextImage("PLAY", Main.WIDTH/2 - 100, Main.HEIGHT/2 - 150, 1f);
        menu.setTextHide(false);
        play.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.isTouched()){
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

        currentScore.render(sb);
        highScore.render(sb);
        game_over_text.render(sb);

        menu.render(sb);
        play.render(sb);

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
