package com.mygdx.runrunrun.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/08/2015.
 */
public abstract class Background {

    //   0   1   2    3     4     5     6
    // -400, 0, 400, 800, 1200, 1600, 2000
    protected float area[];
    protected float heroPosX;
    protected float parallax_bg;
    protected float x,y;
    protected float x1, x2, x0;
    protected float parallaxSpeed;
    protected TextureRegion image;



    public Background(float x, float y, TextureRegion image, int mapLength){
        this.x = x;
        this.y = y;
        this.image = image;

        area = new float[7];
        float areaStartingPoint = -image.getRegionWidth();

        for(int i = 0; i < mapLength + 2; i ++){
            area[i] = areaStartingPoint;
            areaStartingPoint += image.getRegionWidth();
        }

        this.x0 = area[0];
        this.x1 = area[1];
        this.x2 = area[2];
        this.heroPosX = 0;
        this.parallaxSpeed = 0 ;

    }

    protected void currentRender(SpriteBatch sb, float x1, float x2){
        for(int i = 0; i < 2 ; i++){
            if(i == 0) {
                sb.draw(image, x1 + parallax_bg, Main.GROUND_LEVEL);
                this.x1 = x1;
            }
            else {
                sb.draw(image, x2 + parallax_bg, Main.GROUND_LEVEL);
                this.x2 = x2;
            }
        }
    }

    protected void currentRender(SpriteBatch sb, float x0, float x1, float x2){
        for(int i = 0; i < 3 ; i++){
            if(i == 0) {
                this.x0 = x0 + parallax_bg;
                sb.draw(image, this.x0, y);
                if(parallaxSpeed > 0) {
                    sb.draw(image, this.x0 - image.getRegionWidth(), y);
                }
            } else if(i == 1){
                this.x1 = x1 + parallax_bg;
                sb.draw(image, this.x1, y);
            } else {
                this.x2 = x2 + parallax_bg;
                sb.draw(image, this.x2, y);
            }
        }
    }

    public TextureRegion getTextureRegion(){
        return image;
    }

    public void update(float dt, float heroPosX){
        this.heroPosX = heroPosX;
    }

    public void update(float dt, float heroPosX, float playerSpeed){
        //Add velocity to the bg, to make bg look further away
        this.heroPosX = heroPosX;
        if(playerSpeed > 0) {
            parallax_bg += parallaxSpeed * dt;
        }
        else{
            parallax_bg += (parallaxSpeed - 30f) * dt;
        }

        if(parallax_bg >= image.getRegionWidth()){
            parallax_bg = 0;
        }
    }

    public void render(SpriteBatch sb) {
        for(int i = 1; i <= 5; i++){
            if(heroPosX >= area[i] && heroPosX < area[i+1]) {
                currentRender(sb, area[i - 1], area[i], area[i + 1]);
            }
        }
    }

}
