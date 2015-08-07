package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.ui.TextImage;

import java.util.Random;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    // Moveable Objects
    private Hero hero;
    private Block block;

    // BGs
    private TextureRegion bg;
    private float current_bg_x;

    // Text
    private TextImage hit_splash;

    // UIs
    private TextureRegion health;

    // Cool Downs
    private float hit_cool_down;
    private float hit_splash_cool_down;

    public PlayState(GSM gsm){
        super(gsm);

        hero = new Hero(0,0, Main.resource.getAtlas("assets").findRegion("Hero"));
        block = new Block(400, 0, Main.resource.getAtlas("assets").findRegion("block"));
        bg = Main.resource.getAtlas("assets").findRegion("bg1");
        health = Main.resource.getAtlas("assets").findRegion("Hero");

        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);
        hit_splash = new TextImage("HIT!",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            hero.jump();
        }
    }

    public void collisionDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(firstObj.contains(secondObj.getPosition())){
            hit_cool_down = 5f;
            hit_splash_cool_down = 60f;
        }
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        block.update(dt);

        if(hit_cool_down > 0f){
            if(hit_cool_down == 5f){
                hero.reduceHealth();
            }
            hit_cool_down--;
        }
        else{
            collisionDetection(hero,block);
        }

        if(hit_splash_cool_down > 0f){
            hit_splash.setHide(false);
            hit_splash_cool_down--;
        }
        else{
            hit_splash.setHide(true);
        }

        if(hero.getPosition().x >= 960){
            Random rand = new Random();
            int x_block_pos = rand.nextInt(940) + 20;
            int y_block_pos = rand.nextInt(200) + 0;
            block = new Block(x_block_pos, y_block_pos, Main.resource.getAtlas("assets").findRegion("block"));
        }

        cam.position.set(hero.getPosition().x + 150, 100, 0);
        cam.update();

        hit_splash.update("HIT!",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);


        current_bg_x += 3f;
        if(current_bg_x >= 960){
            current_bg_x = 0;
        }

    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        sb.draw(bg,current_bg_x,0);
        sb.draw(bg,current_bg_x + 960,0);
        sb.draw(bg,current_bg_x - 960,0);


        block.render(sb);
        hero.render(sb);

        hit_splash.render(sb);

        for(int i = 1; i <= hero.getHealth_counter(); i++){
            sb.draw(health,cam.position.x - 200 + (25 * i), cam.position.y + cam.viewportHeight/2 - 25,health.getRegionWidth()/2,health.getRegionHeight()/2);
        }

        sb.end();

    }
}
