package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
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

    public GameOverState(GSM gsm, int score){
        super(gsm);

        currentScore = new TextImage("" + score, Main.WIDTH/2, Main.HEIGHT/2 - 100, 1f );
        highScore = new TextImage("" + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2 - 140, 1f);
        game_over_text = new TextImage("GAME OVER!",Main.WIDTH/2, Main.HEIGHT/2, 1f);

        if(score > Main.pref.getHighScore()) {
            Main.pref.setHighScore(score);
            currentScore.update("NEW HIGH SCORE: " + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2 - 100, 1f);
            currentScore.setTextHide(false);
        }else{
            currentScore.setTextHide(false);
            highScore.setTextHide(false);
        }

        game_over_text.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.isTouched()){
            gsm.set(new PlayState(gsm, 5));
        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        currentScore.render(sb);
        highScore.render(sb);
        game_over_text.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){

    }
}
