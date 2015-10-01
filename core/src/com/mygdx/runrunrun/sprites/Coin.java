package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    private int value;
    private float interactValue;
    private boolean interacted;

    public Coin(float x, float y, TextureRegion image){

        super(x,y,image, Types.Coin);
        randomValue();

    }

    private void randomValue(){
        Random rand = new Random();
        value = rand.nextInt(4) + 1;
    }

    public void interact(){
        if(!interacted){
            interactValue = 400f;
            interacted = true;
        }
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
        if(interactValue > 0){
            this.position.y += interactValue * dt;
            interactValue -= 100f * dt;
        }else{
            interactValue = 0;
            interacted = false;
        }
    }

    public void render(SpriteBatch sb){
        super.render(sb);
    }
}
