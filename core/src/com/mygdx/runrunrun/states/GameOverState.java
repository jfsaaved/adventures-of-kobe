package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;

/**
 * Created by 343076 on 12/08/2015.
 */
public class GameOverState extends State {

    private TextImage game_over_text;

    public GameOverState(GSM gsm){
        super(gsm);
        game_over_text = new TextImage("GAME OVER!",Main.WIDTH/2, Main.HEIGHT/2, 1f);
        game_over_text.setHide(false);
    }

    public void handleInput(){
        if(Gdx.input.isTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        game_over_text.render(sb);

        sb.end();
    }
}
