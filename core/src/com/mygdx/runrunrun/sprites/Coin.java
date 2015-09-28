package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    private int value;

    public Coin(float x, float y, TextureRegion image){

        super(x,y,image, Types.Coin);
        randomValue();

    }

    private void randomValue(){
        Random rand = new Random();
        value = rand.nextInt(4) + 1;
    }

    public int getValue(){
        return value;
    }

    @Override
    public void changePosition(float x, float y){

        super.changePosition(x,y);
        randomValue();
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        super.render(sb);
    }
}
