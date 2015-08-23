package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    private boolean hide;

    public Coin(float x, float y, TextureRegion image){
        super(x,y,image);
    }

    public void setHide(boolean b){
        hide = b;
    }

    public boolean getHide(){
        return hide;
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(image,position.x,position.y);
    }
}
