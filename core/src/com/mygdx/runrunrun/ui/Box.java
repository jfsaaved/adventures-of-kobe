package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by 343076 on 25/07/2015.
 */
public class Box {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected Rectangle rect;

    public Box(){

    }

    public Box(float x,float y,float width,float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        rect = new Rectangle(x,y,width,height);
    }


    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }

    public boolean contains(float x, float y){
        return x > this.x - width / 2 &&
               x < this.x + width / 2 &&
               y > this.y - height / 2 &&
               y < this.y + height / 2;
    }

}
