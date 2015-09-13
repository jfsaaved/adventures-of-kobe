package com.mygdx.runrunrun.ui;

/**
 * Created by 343076 on 13/09/2015.
 */
public class Button extends Box{

    private float x;
    private float y;
    private float width;
    private float height;

    public Button(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

}
