package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 11/09/2015.
 */
public class MovingBlock extends MoveableObject {

    private float speed;

    public MovingBlock(float x, float y, TextureRegion image, float speed){
        super(x,y,image, Types.MovingBlock);
        this.speed = speed;
    }

    public void update(float dt){
        super.update(dt);
        float init_pos_x = this.position.x;
        float init_pos_y = this.position.y;

        float final_position_x = init_pos_x - (speed) * dt;
        float final_position_y = init_pos_y;

        this.position.set(final_position_x, final_position_y);
    }

    public void render(SpriteBatch sb){
        super.render(sb);
    }

}
