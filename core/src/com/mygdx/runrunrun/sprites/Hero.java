package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    private TextureRegion hero;

    public Hero(){

        hero = Main.resource.getAtlas("assets").findRegion("Hero");

    }

    public void update(float dt){


    }

    public void render(SpriteBatch sb){
        sb.draw(hero, Main.WIDTH / 2, Main.HEIGHT / 2);
    }

}
