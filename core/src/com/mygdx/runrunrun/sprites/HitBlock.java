package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 343076 on 27/08/2015.
 */
public class HitBlock extends MoveableObject {

    private boolean hide;

    public HitBlock(float x, float y, TextureRegion image){
        super(x, y, image);
        hide = false;
        rect = new Rectangle(this.position.x, this.position.y, this.width, this.height);
    }

    public void setHide(boolean b){
        hide = b;
    }

    public boolean getHide(){
        return hide;
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(image, position.x, position.y, width, height);
    }
}
