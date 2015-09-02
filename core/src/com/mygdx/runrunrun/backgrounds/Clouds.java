package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/08/2015.
 */
public class Clouds extends Background {


    public Clouds(float x, float y, TextureRegion image, int mapLength){
        super(x,y,image, mapLength);
        parallaxSpeed = 40f;
    }

    public void currentRender(SpriteBatch sb, float x1, float x2){
        super.currentRender(sb,x1,x2);
    }

    protected void currentRender(SpriteBatch sb, float x0, float x1, float x2){
       super.currentRender(sb,x0,x1,x2);
    }

    public void update(float dt, float playerSpeed){
       super.update(dt,playerSpeed);
    }


    public void render(SpriteBatch sb) {
        super.render(sb);
    }


}
