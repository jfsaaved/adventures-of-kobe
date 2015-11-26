package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by 343076 on 23/08/2015.
 */
public class Coin extends MoveableObject {

    private int value;
    private float interactValue;
    private boolean interacted;

    private TextureRegion[][] spriteSheet;
    private int rowIndex;
    private int colIndex;
    private float animationDelay;

    public Coin(float x, float y, TextureRegion image){

        super(x,y,image, Types.Coin);
        randomValue();

        int width = 16;
        int height = 16;
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

    }

    private void updateAnimation(float dt){
        if(animationDelay > 0){
            if(interacted){
                animationDelay -= 100f * dt;
            }else{
                animationDelay -= 10f * dt;
            }
        }else{
            animationDelay = 1f;
            rowIndex++;
            if(rowIndex >= 4)
                rowIndex = 0;
        }
    }

    private void randomValue(){
        Random rand = new Random();
        value = rand.nextInt(4) + 1;
    }

    public void interact(){
        if(!interacted){
            interactValue = 400f;
            interacted = true;
        }
    }

    public int getValue(){
        return value;
    }

    @Override
    public void changePosition(float x, float y){

        super.changePosition(x,y);
        randomValue();
    }

    public void update(float dt){

        super.update(dt);
        if(interactValue > 0){
            this.position.y += interactValue * dt;
            interactValue -= 100f * dt;
        }else{
            interactValue = 0;
            interacted = false;
        }

        updateAnimation(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(spriteSheet[rowIndex][colIndex],position.x, position.y, width, height);
    }
}
