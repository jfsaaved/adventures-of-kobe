package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 26/07/2015.
 */
public class MoveableObject {


    // Gravity acceleration variables
    protected static float GRAVITY = -5f;
    protected static float MAX_ACC = 10f;
    protected float free_fall_timer = 0f;

    // The move-able object properties
    protected Vector2 position;
    protected float width;
    protected float height;

    protected MoveableObject(float x, float y){

        position = new Vector2(x, y);

    }

    protected Vector2 velocity(float init_x, float init_y, boolean x_vel){
        Vector2 newPos;
        float final_x;
        if(x_vel == true)
            final_x = init_x + 5;
        else{
            final_x = init_x;
        }
        float final_y = init_y + (GRAVITY * free_fall_timer);

        if(x_vel == true) {
            if (final_x > 960) {
                final_x = 0;
            }
        }

        if(init_y > 0){
            newPos = new Vector2(final_x, final_y);

            if(free_fall_timer <= MAX_ACC) {
                free_fall_timer += 0.05f;
            }
        }
        else{
            newPos = new Vector2(final_x,0);
            free_fall_timer = 0;
        }
        return newPos;
    }

    public boolean contains(Vector2 vector){
        return vector.x > this.position.x - width / 2 &&
                vector.x < this.position.x + width / 2 &&
                vector.y > this.position.y - height / 2 &&
                vector.y < this.position.y + height / 2;
    }

    public Vector2 getPosition(){
        return position;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

}