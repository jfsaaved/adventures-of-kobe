package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 02/08/2015.
 */
public class Block extends MoveableObject{

    private boolean spawned;

    public Block(float x, float y, TextureRegion image){
        super(x,y,image, "block");
        hide = false;
        spawned = false;
    }

    public void setSpawned(boolean b){
        spawned = b;
    }

    public boolean getSpawned(){
        return spawned;
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false) {
            sb.draw(image, position.x, position.y);
        }
    }

}
