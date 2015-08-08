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

    private float height_var;
    private float x_var;
    private boolean up_or_down;
    private boolean hide;
    private boolean still_hit;

    private int health_counter;

    public Hero(float x, float y, TextureRegion image){

        super(x, y, image);

        height_var = 0f;
        up_or_down = true;
        health_counter = 3;
        hide = false;

    }

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image, float bg_width_reference){

        super(x, y, image, bg_width_reference);

        height_var = 0f;
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

    public void hit_animation(float t){
        still_hit = true;
        if(t%2 == 0){
            hide = false;
        }
        else{
            hide = true;
        }

        if(t > 0f){
            x_var -= 0.1f;
        }
        else{
            still_hit = false;
        }
    }

    public void update(float dt){

        if(still_hit == false && x_var < 0f){
            x_var += 0.05f;
            if(x_var == 0){
                still_hit = true;
            }
        }

        if(up_or_down == true && inAir == false){
            height_var++;
            if(height_var > 5f){
                up_or_down = false;
            }
        }
        else if(up_or_down == false && inAir == false){
            height_var--;
            if(height_var < 0f){
                up_or_down = true;
            }
        }

        jump_velocity = jump_acceleration * jump_potential_energy;

        if(jump_velocity > 0f){
            inAir = true;
            jump_acceleration += 0.05f;
            jump_potential_energy -= 0.015f;

            if(jump_velocity > 7)
                height_var -= 1.5;
            else if(jump_velocity < 7 && jump_velocity > 3 ){
                height_var += 0.7;
            }
            else if(jump_velocity <= 3 && jump_velocity > 0){
                height_var = 0;
            }
        }
        else{
            inAir = false;
            jump_acceleration = 0f;
            jump_potential_energy = 0f;
            jump_velocity = 0f;
        }

       position = velocity(position.x + x_var, position.y + jump_velocity, true);
    }

    public void render(SpriteBatch sb){

        if(hide == false)
            sb.draw(image, position.x, position.y, width, height - height_var);

    }


}
