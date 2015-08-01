package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    private TextureRegion hero;
    private float x;
    private float y;

    public Hero(){

        super();
        this.x = Main.WIDTH / 2;
        this.y = Main.HEIGHT / 2;
        hero = Main.resource.getAtlas("assets").findRegion("Hero");

    }

    public void update(float dt){

        this.y = yVelocity(this.y);
        this.x = xVelocity(this.x);

    }

    public void render(SpriteBatch sb){

        sb.draw(hero, x, y);

    }

}
