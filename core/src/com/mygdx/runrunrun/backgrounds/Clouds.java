package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/08/2015.
 */
public class Clouds extends Background {

    private float current_bg_x_clouds;
    private float currentArea, nextArea;
    private float x1, x2, x0;

    public Clouds(float x, float y, TextureRegion image){
        super(x,y,image);
        current_bg_x_clouds = 0 ;
        this.x1 = 0;
        this.x2 = 400;
        this.x0 = -400;

        currentArea = x1;
        nextArea = x2;
    }

    @Override
    public void currentRender(SpriteBatch sb, float x1, float x2){
        for(int i = 0; i < 2 ; i++){
            if(i == 0) {
                sb.draw(image, x1 + current_bg_x_clouds, Main.GROUND_LEVEL);
                this.x1 = x1;
            }
            else {
                sb.draw(image, x2 + current_bg_x_clouds, Main.GROUND_LEVEL);
                this.x2 = x2;
            }
        }
    }

    @Override
    public void currentRender(SpriteBatch sb, float x0, float x1, float x2){
        float deltaPos;

        for(int i = 0; i < 3 ; i++){
            if(i == 0) {
                this.x0 = x0;
                sb.draw(image, x0 + current_bg_x_clouds, Main.GROUND_LEVEL);
            }
            else if(i == 1){
                this.x1 = x1;
                sb.draw(image, x1 + current_bg_x_clouds, Main.GROUND_LEVEL);
            } else {
                this.x2 = x2;
                sb.draw(image, x2 + current_bg_x_clouds, Main.GROUND_LEVEL);
            }
        }
    }

    public void update(float dt, float playerSpeed){

        //Add velocity to the bg, to make bg look further away
        if(playerSpeed > 0) {
            current_bg_x_clouds += 40f * dt;
        }
        else{
            current_bg_x_clouds += 10f * dt;
        }

        if (current_bg_x_clouds >= 400) {
            x0 = x1;
            x1 = x2;
            x2 = (x1 * 2) - x2;
            current_bg_x_clouds = 0;
        }

    }


    @Override
    public void render(SpriteBatch sb, float playerPosX, float camOffset, float maxOffset) {
        //   0   1   2    3     4     5     6
        // -400, 0, 400, 800, 1200, 1600, 2000
        float[] area = new float[7];
        float areaStartingPoint = -400;
        float heroPosX = playerPosX;

        System.out.println(heroPosX);

        for(int i = 0; i < 7; i ++){
            area[i] = areaStartingPoint;
            areaStartingPoint += 400;
        }

        for(int i = 1; i <= 5; i++){
            if(heroPosX >= area[i] && heroPosX < area[i+1]) {
                currentRender(sb, area[i - 1], area[i], area[i + 1]);
            }
        }
    }
}
