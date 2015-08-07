package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    // Jump mechanics
    private float jump_acceleration;
    private float jump_potential_energy;
    private float jump_velocity;
    private boolean inAir;

    private float sprinting;
    private boolean up_or_down;

    private int health_counter;

    public Hero(float x, float y, TextureRegion image){

        super(x, y, image);

        sprinting = 0f;
        up_or_down = true;
        health_counter = 3;

    }

    public void jump(){
        if(inAir == false) {
            jump_acceleration = 8f;
            jump_potential_energy = 1f;
        }
    }

    public int getHealth_counter(){
        return health_counter;
    }

    public void reduceHealth(){
        health_counter--;
    }

    public void addHealth(){
        health_counter++;
    }

    public void update(float dt){

        if(up_or_down == true){
            sprinting++;
            if(sprinting > 5f){
                up_or_down = false;
            }
        }
        else if(up_or_down == false){
            sprinting--;
            if(sprinting < 0f){
                up_or_down = true;
            }
        }

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

        sb.draw(image, position.x, position.y, width, height - sprinting);

    }


}
