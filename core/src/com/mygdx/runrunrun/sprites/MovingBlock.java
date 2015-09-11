package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 11/09/2015.
 */
public class MovingBlock extends MoveableObject {

    public MovingBlock(float x, float y, TextureRegion image){
        super(x,y,image, Types.MovingBlock);
    }

    public void update(float dt){
        super.update(dt);
        this.position.x += 5f;
    }

    public void render(SpriteBatch sb){
        super.render(sb);
    }

}
