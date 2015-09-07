package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    public Coin(float x, float y, TextureRegion image){

        super(x,y,image, Types.Coin);
    }

    @Override
    public void changePosition(float x, float y){
        super.changePosition(x,y);
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        super.render(sb);
    }
}
