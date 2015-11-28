package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;

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

    private TextImage enter;
    private TextImage back;
    private TextImage exit;
    private Rectangle enterRect;
    private Rectangle backRect;
    private Rectangle exitRect;

    public NameState(GSM gsm){
        super(gsm);

        /**
         * Transition variables for the parent
         */
        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;
        exitTransition = false;

        /**
         * Text images for enter and exit
         */
        enter = new TextImage("ENTER",0,0,0.5f);
        back = new TextImage("BACK",0,0,0.5f);
        exit = new TextImage("EXIT",0,0,0.5f);
        enter.setTextHide(false);
        back.setTextHide(false);
        exit.setTextHide(false);
        enter.update((Main.WIDTH / 2 + 370), enter.getHeight() * 6);
        back.update((Main.WIDTH / 2 + 370 - (45/2)), back.getHeight() * 4) ;
        exit.update((Main.WIDTH/2 + 370 - (45/2)), exit.getHeight() * 2);

        enterRect = new Rectangle(Main.WIDTH / 2 + 260,enter.getHeight() * 5,enter.getWidth(),enter.getHeight());
        backRect = new Rectangle(Main.WIDTH / 2 + 260,back.getHeight() * 3,back.getWidth(),back.getHeight());
        exitRect = new Rectangle(Main.WIDTH / 2 + 260,exit.getHeight(),exit.getWidth(),exit.getHeight());

        /**
         * Below are the letter boxes images
         */
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
        if(Gdx.input.justTouched() && !exitTransition && !enterTransition){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            if(enterRect.contains(mouse.x,mouse.y)){
                Main.sounds.playSound("select");
                exitTransition = true;
                exitTransitionVal = 0f;
            }else if(exitRect.contains(mouse.x,mouse.y)){
                Main.sounds.playSound("select");
                exitTransition = true;
                exitTransitionVal = 0f;
            }
        }
    }

    @Override
    protected void update(float dt) {

        handleInput();
        onExitTransition(dt);
        onEnterTransition(dt);


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

        enter.render(sb);
        back.render(sb);
        exit.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        super.shapeRender(sr);
    }


}
