package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    private TextureRegion hero;

    // Jump mechanics
    private float jump_acceleration;
    private float jump_potential_energy;
    private float jump_velocity;
    private boolean inAir;

    public Hero(float x, float y){

        super(x, y);
        hero = Main.resource.getAtlas("assets").findRegion("Hero");
        width = hero.getRegionWidth();
        height = hero.getRegionHeight();

    }

    public void jump(){
        if(inAir == false) {
            jump_acceleration = 8f;
            jump_potential_energy = 1f;
        }
    }

    public void update(float dt){

        jump_velocity = jump_acceleration * jump_potential_energy;

        if(jump_velocity > 0f){
            inAir = true;
            jump_acceleration += 0.05f;
            jump_potential_energy -= 0.015f;
        }
        else{
            inAir = false;
            jump_acceleration = 0f;
            jump_potential_energy = 0f;
            jump_velocity = 0f;
        }

       position = velocity(position.x, position.y + jump_velocity, true);
    }

    public void render(SpriteBatch sb){

        sb.draw(hero, position.x, position.y);

    }

}
