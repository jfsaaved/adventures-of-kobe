package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 343076 on 27/08/2015.
 */
public class HitBlock extends MoveableObject {

    private float float_value;
    private boolean interacted;

    private TextureRegion[][] spriteSheet;
    private int rowIndex;
    private int colIndex;
    private float animationDelay;

    public HitBlock(float x, float y, TextureRegion image){
        super(x, y, image, Types.HitBlock);

        int width = 64;
        int height = 64;
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
        if(!interacted) {
            //float_value = 350f;
            float_value = 400f;
            interacted = true;
        }
    }

    public void floatUP(float dt){
        if(float_value > 0){
            this.position.y += float_value * dt;
            float_value -= 100f * dt;
        }
        else{
            float_value = 0;
            interacted = false;
        }

    }

    @Override
    public void changePosition(float x, float y){
        float_value = 0;
        super.changePosition(x,y);
    }

    public void update(float dt){
        super.update(dt);
        floatUP(dt);

        updateAnimation(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        if(hide == false)
            sb.draw(spriteSheet[rowIndex][colIndex],position.x, position.y, width, height);
    }
}
