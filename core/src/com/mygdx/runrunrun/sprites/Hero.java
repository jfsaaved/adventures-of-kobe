package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    private static float MAX_SPEED = 150f;

    // Jump mechanics
    private float jump_acceleration;
    private boolean inAir;

    // Moving animations
    private float height_var;
    private boolean height_anim_interval_status;
    private boolean boostActivated;
    private float boostValue;

    // Attributes
    private int health_counter;
    private int coins;
    private boolean invisibility;

    // Fly mechanics
    private boolean fly;
    private float flyTimer;
    private boolean flyTimerBoolean;
    private int flyHeight;

    // Moving mechanics
    private float speed;
    private boolean isStopped;

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image){

        super(x, y, image, Types.Hero);

        // Moving animations
        height_var = 0f;
        height_anim_interval_status = true;

        // Hit mechanics
        health_counter = 3;

        // Moving mechanics
        speed = MAX_SPEED;

        // Items
        coins = 0;

        // Fly mechanics
        fly = false;
        flyTimerBoolean = false;
        flyTimer = 0;
        flyHeight = Main.GROUND_LEVEL;
        invisibility = false;

    }

    public void interact(){
        if(!boostActivated){
            boostValue = 250f;
            boostActivated = true;
        }
    }

    public void setFly(boolean b){
        fly = b;
    }

    public boolean getFlyStatus(){
        return fly;
    }

    public void addCoin(int value){
        coins += value;
    }

    public void subtractCoins(int value){
        coins -= value;
    }

    public int getCoins(){
        return coins;
    }

    public int getHealth_counter(){
        return health_counter;
    }

    public void reduceHealth(){
        if(!invisibility)
            health_counter--;
    }

    public void restoreHealth() {
        health_counter = 3;
    }

    public float getSpeed(){
        return speed;
    }

    public void toggleStop(){
        if(isStopped){
            speed = MAX_SPEED;
            isStopped = false;
        }else{
            isStopped = true;
        }
    }

    public void toggleStop(boolean b){
        if(b){
            speed = 0;
        }else{
            speed = MAX_SPEED;
        }
    }

    public boolean getStop(){
        return isStopped;
    }

    public void hit_animation(float t){
        if(t%2 == 0){
            hide = false;
        }
        else{
            hide = true;
        }
    }

    public void jump(){
        if(inAir == false && isStopped == false){
            jump_acceleration = 225f;
        }
    }


    private void decelerate(float dt){
        if(speed > 0){
            speed -= 300f * dt;
        }
        else if(speed <= 0){
            speed = 0;
        }
    }

    private void updateAnimation(){
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
        else {
            height_var = 0;
        }

        if(isStopped){
            height_var = 0;
        }
    }

    public void update(float dt){

        if(!fly)
            super.update(dt);

        float init_x = this.position.x;
        float init_y = this.position.y;

        updateAnimation();

        if(isStopped){
            decelerate(dt);
        }

        if(boostValue > 0){
            boostValue -= 300 * dt;
        }else{
            boostValue = 0;
            boostActivated = false;
        }

        float final_x = init_x + (speed + boostValue) * dt;
        float final_y;

        if(!fly) {
            if (flyHeight > Main.GROUND_LEVEL) {
                flyHeight -= 10f * dt;
                final_y = init_y + flyHeight * dt;
            }else{
                if(flyHeight != Main.GROUND_LEVEL) {
                    flyHeight = Main.GROUND_LEVEL;
                    flyTimer = 0;
                }

                final_y = init_y + (jump_acceleration) * dt;

                if (final_y > Main.GROUND_LEVEL) {
                    inAir = true;
                    jump_acceleration -= 100f * dt;
                } else {
                    jump_acceleration = 0;
                    final_y = Main.GROUND_LEVEL;
                    inAir = false;
                }
            }
        }
        else {
            inAir = true;
            if(flyHeight < 139){
                flyHeight += 100 * dt;
            }
            final_y = flyHeight + flyTimer * dt;
            if(!flyTimerBoolean){
                flyTimer += 300f * dt;
                if(flyTimer > 500){
                    flyTimerBoolean = true;
                }
            }else{
                flyTimer -= 300f * dt;
                if(flyTimer < 0){
                    flyTimerBoolean = false;
                }
            }
        }

        this.position.set(final_x,final_y);
        this.rect.setPosition(final_x,final_y);

    }

    @Override
    public void render(SpriteBatch sb){

        if(hide == false)
            sb.draw(image, position.x, position.y, width, height - height_var);

    }


}
