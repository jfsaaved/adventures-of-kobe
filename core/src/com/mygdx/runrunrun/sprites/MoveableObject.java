package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 26/07/2015.
 */
public class MoveableObject {


    // Gravity acceleration variables
    protected static float GRAVITY = -10f;
    protected static float MAX_ACC = 60f;
    protected float free_fall_timer = 0f;

    // The move-able object properties
    protected Vector2 position;
    protected TextureRegion image;
    protected float width;
    protected float height;

    public MoveableObject(float x, float y, TextureRegion image){

        position = new Vector2(x, y);

        if(image != null) {
            this.image = image;
            width = image.getRegionWidth();
            height = image.getRegionHeight();
        }

    }

    public void update(float dt){

        //float init_x = this.position.x;
        float init_y = this.position.y;

        if(init_y > 0){
            position.y = init_y + (GRAVITY * free_fall_timer * dt);

            if(free_fall_timer <= MAX_ACC) {
                free_fall_timer += 50f * dt;
            }
        }
        else{
            position.y = 0;
            free_fall_timer = 0;
        }

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
