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
    private float jump_coolDown;
    private boolean inAir;

    private float height_var;
    private float x_var_from_getting_hit;
    private boolean height_anim_interval_status;
    private boolean hide;
    private boolean in_hit_animation;

    private int health_counter;

    public Hero(float x, float y, TextureRegion image){

        super(x, y, image);

        height_var = 0f;
        height_anim_interval_status = true;
        health_counter = 3;
        hide = false;

    }

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image, float bg_width_reference){

        super(x, y, image, bg_width_reference);

        height_var = 0f;
        height_anim_interval_status = true;
        health_counter = 3;

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

    public boolean isJumping(){
        return inAir;
    }

    public void hit_animation(float t){
        in_hit_animation = true;
        if(t%2 == 0){
            hide = false;
        }
        else{
            hide = true;
        }

        // Slow down a bit
        if(t > 0f){
            x_var_from_getting_hit -= 0.1f;
        }
        else{
            in_hit_animation = false;
        }
    }

    public void jump(){
        if(inAir == false && jump_coolDown <= 0f) {
            jump_acceleration = 8f; // Initial jump acceleration
            jump_potential_energy = 1f;
            jump_coolDown = 8f;
        }
    }

    public void update(float dt){

        if(in_hit_animation == false && x_var_from_getting_hit < 0f){
            x_var_from_getting_hit += 0.05f;
            if(x_var_from_getting_hit > 0){ // Reset the variable, to continue normal velocity
                x_var_from_getting_hit = 0;
            }
        }

        if(!inAir) {
            if (height_anim_interval_status) {
                height_var++;
                if (height_var > 5f) {
                    height_anim_interval_status = false;
                }
            } else{
                height_var--;
                if (height_var < 0f) {
                    height_anim_interval_status = true;
                }
            }
        }
        else{
            height_var = 0;
        }


        jump_velocity = jump_acceleration * jump_potential_energy;

        if(jump_velocity > 0){
            inAir = true;
            if(jump_acceleration < 16f)
                jump_acceleration += 0.05f;
            if(jump_potential_energy > 0)
                jump_potential_energy -= 0.05f;
        }
        else if(jump_velocity <= 0){
            inAir = false;
            if(jump_coolDown > 0){
                jump_coolDown--;
            }
            jump_acceleration = 0;
            jump_potential_energy = 0;
            jump_velocity = 0;
        }


        position = velocity(position.x + x_var_from_getting_hit, position.y + jump_velocity, true);
    }

    public void render(SpriteBatch sb){

        if(hide == false)
            sb.draw(image, position.x, position.y, width, height - height_var);

    }


}
