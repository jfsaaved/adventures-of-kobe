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
    private boolean boostActivated;
    private float boostValue;

    // Attributes
    private int health_counter;
    private int coins;

    // Fly mechanics
    private boolean fly;
    private float flyTimer;
    private float flyHeight;

    // Moving mechanics
    private float speed;
    private boolean isStopped;


    // Animation handler
    private boolean standing;
    private boolean running;
    private boolean stopping;
    private boolean jumping;

    private TextureRegion[][] spriteSheet;
    private int rowIndex;
    private int colIndex;
    private float animationDelay;

    private boolean stopFades;

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image){

        super(x, y, image, Types.Hero);

        // Animation test
        int width = 36;
        int height = 54;
        rowIndex = 0;
        colIndex = 0;
        animationDelay = 1f;
        resize(width,height);
        int rows = image.getRegionWidth() / width;
        int cols = image.getRegionHeight() / height;

        spriteSheet = new TextureRegion[rows][cols];

        for(int i = 0 ; i < rows ; i ++){
            for(int j = 0 ; j < cols ; j ++){
                spriteSheet[i][j] = new TextureRegion(image, width * i, height * j, width, height);
            }
        }

        // Hit mechanics
        health_counter = 3;

        // Moving mechanics
        speed = MAX_SPEED;

        // Items
        coins = Main.pref.getGold();

        // Fly mechanics
        fly = false;
        flyTimer = 0;
        flyHeight = Main.GROUND_LEVEL;

    }

    public void interact(){
        if(!boostActivated){
            boostValue = 250f;
            boostActivated = true;
        }
    }

    public void setFly(boolean b){
        fly = b;
        if(fly){
            flyTimer = 0;
            flyHeight = Main.GROUND_LEVEL;
        }
    }

    public void setFade(boolean b){
        stopFades = b;
    }

    public boolean getJump(){
        return  jump_acceleration == 225f;
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

    public boolean isFlying(){
        return fly;
    }
    public void reduceHealth(){
        health_counter--;
    }

    public void restoreHealth() {
        health_counter = 3;
    }

    public boolean getBoost(){
        return boostActivated;
    }

    public float getSpeed(){
        return speed;
    }

    public void toggleStop(){
        if(isStopped){
            speed = MAX_SPEED;
            isStopped = false;

            if(stopping){
                stopping = false;
            }
        }else{
            isStopped = true;
        }
    }

    public void toggleStop(boolean b){
        if(b){
            speed = 0;
        }else{
            speed = MAX_SPEED;
            if(stopping){
                stopping = false;
            }
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

        if(stopFades)
            hide = false;
    }

    public void jump(){
        if(inAir == false && isStopped == false){
            jump_acceleration = 225f;
        }
    }


    private void decelerate(float dt){
        stopping = true;
        if(speed > 0){
            speed -= 300f * dt;
        }
        else if(speed <= 0){
            stopping = false;
            speed = 0;
        }
    }

    private void updateState(){

        if(speed < MAX_SPEED){
            standing = true;
            running = false;
            jumping = false;
        }else if(speed >= MAX_SPEED){
            running = true;
            standing = false;
            jumping = false;
        }

        if(inAir){
            running = false;
            standing = false;
            jumping = true;
            if(fly) {
                running = true;
                jumping = false;
                standing = false;
            }
        }
    }

    private void updateAnimation(float dt){
        if(animationDelay > 0){
            if(standing)
                animationDelay -= 5f * dt;
            else
                animationDelay -= 10f * dt;
        }else{
            rowIndex++;
            animationDelay = 1f;
            if(standing){
                if(rowIndex >= 4)
                    rowIndex = 0;
            }else if(running){
               if(rowIndex <= 4 || rowIndex >= 15)
                   rowIndex = 5;
            }else if(jumping){
               rowIndex = 4;
            }
        }
    }

    public void update(float dt){

        if(!fly)
            super.update(dt);

        float init_x = this.position.x;
        float init_y = this.position.y;

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
                final_y = init_y + flyHeight * dt;
                if(final_y > Main.GROUND_LEVEL)
                    flyHeight -= 50f * dt;
                else{
                    flyHeight = 0;
                    final_y = Main.GROUND_LEVEL;
                    inAir = false;
                }
            }else{
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

            if(flyHeight < 100){ // Originally 139
                flyHeight += 100 * dt;
            }

            final_y = flyHeight + (flyTimer * dt);

        }

        this.position.set(final_x,final_y);
        this.rect.setPosition(final_x,final_y);

        updateState();
        updateAnimation(dt);
    }

    @Override
    public void render(SpriteBatch sb){

        if(hide == false)
                sb.draw(spriteSheet[rowIndex][colIndex],position.x, position.y, width, height);

    }


}
