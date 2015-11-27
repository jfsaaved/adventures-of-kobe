package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runrunrun.Main;

import java.util.Vector;

/**
 * Created by 343076 on 26/11/2015.
 */
public class NameState extends State {

    private TextureRegion bg;
    private TextureRegion bg2;
    private int currentBGX;

    private TextureRegion fontSheet;
    private Vector<Rectangle> letterBox;

    public NameState(GSM gsm){
        super(gsm);

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        exitTransition = false;

        fontSheet = new TextureRegion(Main.resource.getAtlas("assets").findRegion("nameSheet"));
        bg = new TextureRegion(Main.resource.getAtlas("assets").findRegion("tiled_bg"));
        bg2 = bg;

        letterBox = new Vector<Rectangle>();
        int initX = Main.WIDTH/2 - fontSheet.getRegionWidth()/2;
        int initY = 2;
        for(int row = 0 ; row < 10 ; row ++ ){
            for(int col = 0 ; col < 6 ; col ++ ){
                letterBox.add(new Rectangle(initX + (row * 45),initY + (col * 45),45,45));
            }
        }

    }

    @Override
    protected void onExitTransition(float dt) {
        if(exitTransition){
            exitTransitionVal += 1f * dt;
            if(exitTransitionVal >= 1f){
                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {

        handleInput();
        onEnterTransition(dt);
        onExitTransition(dt);

        if(currentBGX < bg.getRegionWidth()){
            currentBGX++;
        }else{
            currentBGX = 0;
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        sb.draw(bg,currentBGX - bg.getRegionWidth(),0);
        sb.draw(bg2,currentBGX,0);

        sb.draw(fontSheet,Main.WIDTH/2 - fontSheet.getRegionWidth()/2, -180);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){

        super.shapeRender(sr);

        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);

        if(!enterTransition || !exitTransition) {
            sr.setColor(Color.WHITE);
            for (Rectangle box : letterBox) {
                sr.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            }
        }

        sr.end();
    }


}
