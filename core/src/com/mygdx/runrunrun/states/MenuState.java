package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;

/**
 * Created by 343076 on 21/09/2015.
 */
public class MenuState extends State {

    private TextImage title;

    public MenuState(GSM gsm){
        super(gsm);

        title = new TextImage("RUN", Main.WIDTH/2, Main.HEIGHT/2,1);
        title.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            gsm.set(new PlayState(gsm,5));

        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        title.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){

    }
}
