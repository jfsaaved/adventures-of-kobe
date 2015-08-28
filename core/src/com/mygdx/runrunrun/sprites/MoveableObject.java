package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
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
    protected Rectangle rect;
    protected float width;
    protected float height;

    public MoveableObject(float x, float y, TextureRegion image){

        position = new Vector2(x, y);

        if(image != null) {
            this.image = image;
            width = image.getRegionWidth();
            height = image.getRegionHeight();
        }

        rect = new Rectangle(position.x, position.y, width, height);

    }

    public void update(float dt){

        //float init_x = this.position.x;
        float init_y = this.position.y;

        if(init_y > 32){
            position.y = init_y + (GRAVITY * free_fall_timer * dt);

            if(free_fall_timer <= MAX_ACC) {
                free_fall_timer += 50f * dt;
            }
        }
        else{
            position.y = 32;
            free_fall_timer = 0;
        }

        rect.set(position.x, position.y, width, height);

    }

    public boolean contains(Vector2 vector){
        return rect.contains(vector);
    }

    public Vector2 getPosition(){
        return position;
    }

    public boolean contains(Rectangle newRect){
        return rect.contains(newRect);
    }

    public Rectangle getRectangle(){
        return this.rect;
    }

    public boolean overlaps(Rectangle newRect){
        return rect.overlaps(newRect);
    }

    public float getWidth(){

        return width;
    }

    public float getHeight(){

        return height;
    }

}
