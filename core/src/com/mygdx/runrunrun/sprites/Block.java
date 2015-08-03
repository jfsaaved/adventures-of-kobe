package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 02/08/2015.
 */
public class Block extends MoveableObject{

    private Vector2 position;
    private TextureRegion block;

    public Block(){

    }
    public Block(float x, float y){

        super(x,y);
        this.position = super.position;
        block = Main.resource.getAtlas("assets").findRegion("block");

    }

    public void update(float dt){

        this.position = velocity(this.position.x, this.position.y, false);

    }

    public void render(SpriteBatch sb){

        sb.draw(block,this.position.x,this.position.y);

    }

}
