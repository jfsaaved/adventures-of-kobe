package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 02/08/2015.
 */
public class Block extends MoveableObject{

    private float interactValue;
    private boolean interacted;

    private TextureRegion[][] spriteSheet;
    private int rowIndex;
    private int colIndex;
    private float animationDelay;

    public Block(float x, float y, TextureRegion image){
        super(x,y,image, Types.Block);

        int width = 32;
        int height = 32;
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
            if(rowIndex >= 5)
                rowIndex = 0;
        }
    }

    public void interact(){
        if(!interacted){
            interactValue = 400f;
            interacted = true;
        }
    }

    @Override
    public void changePosition(float x, float y){
        interactValue = 0;
        super.changePosition(x,y);
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

        this.rect.setHeight(16);
        updateAnimation(dt);
    }

    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(spriteSheet[rowIndex][colIndex],position.x, position.y, width, height);
    }

}
