package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 343076 on 27/08/2015.
 */
public class HitBlock extends MoveableObject {

    private float float_value;
    private boolean interacted;

    public HitBlock(float x, float y, TextureRegion image){
        super(x, y, image, "hitblock");
        rect = new Rectangle(this.position.x, this.position.y, this.width, this.height);
    }

    public void interact(){
        if(!interacted) {
            float_value = 350f;
            interacted = true;
        }
    }

    public void floatUP(float dt){
        if(float_value > 0){
            this.position.y += float_value * dt;
            float_value -= 100f * dt;
        }
        else{
            float_value = 0;
            interacted = false;
        }
    }

    @Override
    public void changePosition(float x, float y){
        super.changePosition(x,y);
        float_value = 0;
        interacted = false;
    }

    public void update(float dt){

        super.update(dt);
        floatUP(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(image, position.x, position.y, width, height);
    }
}
