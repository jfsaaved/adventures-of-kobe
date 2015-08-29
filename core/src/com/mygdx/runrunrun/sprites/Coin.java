package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    public Coin(float x, float y, TextureRegion image){

        super(x,y,image, "coin");
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(image,position.x,position.y);
    }
}
