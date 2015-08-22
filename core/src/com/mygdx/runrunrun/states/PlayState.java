package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.ui.TextBoxImage;
import com.mygdx.runrunrun.ui.TextImage;

import java.util.Random;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private static float HIT_COOL_DOWN_MAX = 20f;

    // Moveable Objects
    private Hero hero;
    private Block block;

    // BGs
    private TextureRegion bg;
    private float current_bg_x;

    // Text
    private TextImage hit_splash;
    private TextBoxImage textBox;

    // UIs
    private TextureRegion health;

    // Cool Downs
    private float hit_cool_down;
    private float hit_splash_cool_down;

    // Events
    private float box_timer;
    private float box_exit_delay;

    public PlayState(GSM gsm){
        super(gsm);

        bg = Main.resource.getAtlas("assets").findRegion("bg1");
        hero = new Hero(0,0, Main.resource.getAtlas("assets").findRegion("Hero"), bg);
        block = new Block(200, 150, Main.resource.getAtlas("assets").findRegion("block"));

        current_bg_x = 0;

        health = Main.resource.getAtlas("assets").findRegion("Hero");

        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);
        hit_splash = new TextImage("HIT!",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);
        textBox = new TextBoxImage("",cam.position.x - cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 - 9,0.20f,cam.viewportWidth);
        textBox.setTextHide(true);
        textBox.setTextBox_hide(true);

        box_timer = 100f;
        box_exit_delay = 100f;
    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            hero.jump();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            hero.toggleStop();
        }
    }

    public void collisionDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(firstObj.contains(secondObj.getPosition())){
            hit_cool_down = HIT_COOL_DOWN_MAX;
            hit_splash_cool_down = 60f;
        }
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        block.update(dt);

        /*if(hero.getHealth_counter() <= 0){
            gsm.set(new GameOverState(gsm));
        }*/

        // On hit code below
        if(hit_cool_down > 0f){
            if(hit_cool_down == HIT_COOL_DOWN_MAX){
                hero.reduceHealth();
            }
            hit_cool_down--;
            hero.hit_animation(hit_cool_down);
        }
        else{
            collisionDetection(hero,block);
        }

        if(hit_splash_cool_down > 0f){
            hit_splash.setTextHide(false);
            hit_splash_cool_down--;
        }
        else{
            hit_splash.setTextHide(true);
        }

        // Position update below
        if(hero.getPosition().x == 0){
            Random rand = new Random();
            int x_block_pos = rand.nextInt(bg.getRegionWidth() - 20) + 20;
            int y_block_pos = rand.nextInt(200) + 0;
            block = new Block(x_block_pos, y_block_pos, Main.resource.getAtlas("assets").findRegion("block"));
        }

        cam.position.set(hero.getPosition().x + 150, 100, 0);
        cam.update();

        hit_splash.update("HIT!",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);

        int cam_x_offset = 2;
        int cam_y_offset = 4;

        // sample text "------------------------------------------"
        String text = "jesus: oh shit!!!!!!                      " +
                      "jesus: watch out!!                        " +
                      "duckie: chill.... i got this              ";
        textBox.update(text,cam.position.x - cam.viewportWidth/2 + cam_x_offset, cam.position.y + cam.viewportHeight/2 - (9 + cam_y_offset),0.20f);

        if(box_timer > 0){
            box_timer--;
        }
        else{
            textBox.setTextBox_hide(false);
            if(textBox.isTextHidden())
                textBox.setTextHide(false);

            if(textBox.isFinishDrawing()){
                box_exit_delay--;
                if(box_exit_delay <= 0){
                    textBox.setTextBox_hide(true);
                    if(!textBox.isTextHidden())
                        textBox.setTextHide(true);
                }
            }
        }


        //Add velocity to the bg, to make bg look further away

        if(hero.getSpeed() > 0) {
            current_bg_x++;
            if(current_bg_x >= bg.getRegionWidth()){
                current_bg_x = 0;
            }
        }

    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        for(int i = 0 ; i < 3 ; i ++) {
            if(i == 0)
                sb.draw(bg, current_bg_x - bg.getRegionWidth(), -20);
            else if(i == 1)
                sb.draw(bg, current_bg_x, -20);
            else if (i == 2)
                sb.draw(bg, current_bg_x + bg.getRegionWidth(), -20);
        }


        hit_splash.render(sb);
        textBox.renderBox(sb);
        textBox.renderText(sb);

        int health_y_offset = 4;
        for(int i = 1; i <= hero.getHealth_counter(); i++){
            sb.draw(health,cam.position.x + cam.viewportWidth/2 - (25 * i), cam.position.y + cam.viewportHeight/2 - (25 + health_y_offset),health.getRegionWidth()/2,health.getRegionHeight()/2);
        }

        block.render(sb);
        hero.render(sb);

        sb.end();

    }
}
