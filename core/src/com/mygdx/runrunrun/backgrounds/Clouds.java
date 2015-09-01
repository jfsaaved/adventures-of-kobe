package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 343076 on 31/08/2015.
 */
public class Clouds extends Background {

    public Clouds(float x, float y, TextureRegion image){
        super(x,y,image);
    }

    @Override
    public void currentRender(SpriteBatch sb, float x1, float x2){
        for(int i = 0; i < 2 ; i++){
            if(i == 0)
                sb.draw(image,x1,0);
            else
                sb.draw(image,x2,0);
        }
    }

    @Override
    public void currentRender(SpriteBatch sb, float x1, float x2, float x3){
        for(int i = 0; i < 3 ; i++){
            if(i == 0)
                sb.draw(image,x1,0);
            else if(i == 1)
                sb.draw(image,x2,0);
            else if(i == 2)
                sb.draw(image,x2,0);
        }
    }

    @Override
    public void render(SpriteBatch sb, float playerPosX, float camOffset, float maxOffset) {
        //   0   1   2    3     4     5     6
        // -400, 0, 400, 800, 1200, 1600, 2000
        float[] area = new float[7];
        float areaStartingPoint = -400;
        float heroPosX = playerPosX;

        for(int i = 0; i < 7; i ++){
            area[i] = areaStartingPoint;
            areaStartingPoint += 400;
        }

        for(int i = 1; i <= 5; i++){
            if(heroPosX >= area[i] && heroPosX <= area[i+1]){
                if(camOffset < 125) { // Om hero accelerating
                    if (heroPosX < area[i] + maxOffset)
                        currentRender(sb, area[i - 1], area[i]);
                    else
                        currentRender(sb, area[i], area[i + 1]);
                }else{ // On hero max acceleration
                    if (heroPosX < area[i] + 50)
                        currentRender(sb, area[i - 1], area[i]);
                    else
                        currentRender(sb, area[i], area[i + 1]);
                }
            }
        }
    }
}
