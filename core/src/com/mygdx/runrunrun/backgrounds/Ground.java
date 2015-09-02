package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 31/08/2015.
 */
public class Ground extends Background{


    public Ground(float x, float y, TextureRegion image, int mapLength){
        super(x,y,image, mapLength);
    }

    public void currentRender(SpriteBatch sb, float x1, float x2){
        super.currentRender(sb,x1,x2);
    }

    public void currentRender(SpriteBatch sb, float x0, float x1, float x2){
        super.currentRender(sb,x0,x1,x2);
    }

    public void update(float dt, float playerSpeed){
        super.update(dt,playerSpeed);
    }


    public void render(SpriteBatch sb) {
        super.render(sb);
    }

}
