package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 31/08/2015.
 */
public abstract class Background {

    protected float x;
    protected float y;
    protected TextureRegion image;

    public Background(float x, float y, TextureRegion image){
        this.x = x;
        this.y = y;
        this.image = image;
    }

    protected abstract void currentRender(SpriteBatch sb, float x1, float x2);
    protected abstract void currentRender(SpriteBatch sb, float x1, float x2, float x3);
    public abstract void render(SpriteBatch sb, float playerPosX, float camOffset, float maxOffset);

}
