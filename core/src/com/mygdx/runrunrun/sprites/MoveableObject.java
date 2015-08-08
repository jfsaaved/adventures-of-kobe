package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected TextureRegion image;
    protected float width;
    protected float height;
    protected float bg_width_reference;

    public MoveableObject(float x, float y, TextureRegion image){

        position = new Vector2(x, y);

        if(image != null) {
            this.image = image;
            width = image.getRegionWidth();
            height = image.getRegionHeight();
        }

    }

    public MoveableObject(float x, float y, TextureRegion image, float bg_width_reference){

        position = new Vector2(x, y);
        this.bg_width_reference = bg_width_reference;

        if(image != null) {
            this.image = image;
            width = image.getRegionWidth();
            height = image.getRegionHeight();
        }

    }

    protected Vector2 velocity(float init_x, float init_y, boolean is_this_a_hero){
        Vector2 newPos;
        float final_x, final_y;

        // If it's the hero, add value to x vector
        if(is_this_a_hero == true) {
            final_x = init_x + 5f;
            if (final_x >= bg_width_reference) {
                final_x = 0;
            }
        }
        else{
            final_x = init_x;
        }

        final_y = init_y + (GRAVITY * free_fall_timer);

        // If the object's position is above 0 (in the air) apply gravity
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
