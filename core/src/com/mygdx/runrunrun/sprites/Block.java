package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 02/08/2015.
 */
public class Block extends MoveableObject{

    private float interactValue;
    private boolean interacted;

    public Block(float x, float y, TextureRegion image){
        super(x,y,image, Types.Block);
    }

    public void interact(){
        if(!interacted){
            interactValue = 400f;
            interacted = true;
        }
    }

    @Override
    public void changePosition(float x, float y){
        interactValue = 0;
        super.changePosition(x,y);
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
