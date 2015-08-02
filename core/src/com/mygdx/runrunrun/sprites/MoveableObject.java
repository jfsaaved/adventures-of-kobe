package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 26/07/2015.
 */
public class MoveableObject {

    protected static float GRAVITY = -5f;
    protected static float MAX_ACC = 10f;
    protected float free_fall_timer = 0f;

    protected Vector2 position;
    protected float width;
    protected float height;

    protected MoveableObject(){
        position = new Vector2(0,0);
    }

    protected MoveableObject(float x, float y){
        position = new Vector2(x, y);
    }

    public boolean contains(float x, float y){
        return x > this.position.x - width / 2 &&
                x < this.position.x + width / 2 &&
                y > this.position.y - height / 2 &&
                y < this.position.y + height / 2;
    }

    protected Vector2 velocity(float init_x, float init_y){
        Vector2 newPos;
        float final_x = init_x;
        float final_y = init_y + (GRAVITY * free_fall_timer);

        if(init_y > 0){
            newPos = new Vector2(final_x, final_y);

            if(free_fall_timer <= MAX_ACC) {
                free_fall_timer += 0.05f;
            }
        }
        else{
            newPos = new Vector2(init_x,0);
            free_fall_timer = 0;
        }
        return newPos;
    }

}
