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
    private boolean flyTimerBoolean;

    // Moving mechanics
    private float speed;
    private boolean isStopped;


    // Animation handler
    private boolean standing;
    private boolean stopping;
    private boolean jumpingUp;
    private boolean jumpingDown;

    private TextureRegion[][] fontSheet;
    private int rowIndex;
    private int colIndex;
    private float animationDelay;

    private boolean stopFades;

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image){

        super(x, y, image, Types.Hero);

        // Animation test
        int size = 89;
        rowIndex = 0;
        colIndex = 0;
        animationDelay = 1f;
        resize(size,size);
        int rows = image.getRegionWidth() / size;
        int cols = image.getRegionHeight() / size;

        fontSheet = new TextureRegion[rows][cols];

        for(int i = 0 ; i < rows ; i ++){
            for(int j = 0 ; j < cols ; j ++){
                fontSheet[i][j] = new TextureRegion(image, size * i, size * j, size, size);
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
        flyTimerBoolean = false;
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
            flyTimerBoolean = false;
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

    private void updateAnimation(float dt){

        if(animationDelay > 0){
            animationDelay -= 10f * dt;
        }else{

            animationDelay = 1f;
            rowIndex++;

            if(speed > 0){
                standing = false;
            }else{
                standing = true;
            }
            if(jump_acceleration > 1){
                if(jump_acceleration > 200){
                    jumpingDown = false;
                    jumpingUp = true;
                }else{
                    jumpingUp = false;
                    jumpingDown = true;
                }
            }else{
                if(flyHeight > Main.GROUND_LEVEL && !fly){
                    jumpingUp = false;
                    jumpingDown = true;
                }else{
                    jumpingUp = false;
                    jumpingDown = false;
                }
            }

            if(standing || fly) {
                colIndex = 0;
                if (rowIndex >= 5) {
                    rowIndex = 0;
                }
            }
            else if(stopping || isStopped){
                colIndex = 1;
                rowIndex = 6;
            }
            else if(jumpingUp || jumpingDown){
                colIndex = 2;
                if(jumpingUp){
                    if(rowIndex >= 2) rowIndex = 0;
                }else{
                    rowIndex = 3;
                }
            }
            else{
                colIndex = 1;
                if(rowIndex >= 6){
                    rowIndex = 0;
                }
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


            /*if(!flyTimerBoolean){
                if(flyTimer >= 300)
                    flyTimerBoolean = true;
                else{
                    flyTimer += 300f * dt;
                }
            }else{
                if(flyTimer <= 0)
                    flyTimerBoolean = false;
                else{
                    flyTimer -= 300f * dt;
                }
            }*/

            final_y = flyHeight + (flyTimer * dt);

        }

        this.position.set(final_x,final_y);
        this.rect.setWidth(30);
        this.rect.setPosition(final_x + 30,final_y);
        updateAnimation(dt);

    }



    @Override
    public void render(SpriteBatch sb){

        if(hide == false)
                sb.draw(fontSheet[rowIndex][colIndex],position.x, position.y, width, height);

    }


}
