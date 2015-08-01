package com.mygdx.runrunrun.sprites;

import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 26/07/2015.
 */
public class MoveableObject {

    protected static int GRAVITY = -4;

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected MoveableObject(){
        this.x = 0;
        this.y = 0;
    }

    public boolean contains(float x, float y){
        return x > this.x - width / 2 &&
                x < this.x + width / 2 &&
                y > this.y - height / 2 &&
                y < this.y + height / 2;
    }

    protected float yVelocity(float p_y){
        if(p_y <= 0){
            return 0;
        }
        else{
            return p_y + GRAVITY;
        }
    }

    protected float xVelocity(float p_x){
        return p_x;
    }


}
