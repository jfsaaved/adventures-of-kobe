package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.MoveableObject;

import java.util.Random;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private Hero hero;
    private Block block;

    private TextureRegion bg;

    private float hit_cool_down;

    public PlayState(GSM gsm){
        super(gsm);


        hero = new Hero(0,0);
        block = new Block(400, 0);
        bg = Main.resource.getAtlas("assets").findRegion("bg1");

        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            hero.jump();
        }
    }

    public void collisionDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(firstObj.contains(secondObj.getPosition())){
            System.out.println("HIT!!");
            hit_cool_down = 5f;
        }
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        block.update(dt);

        if(hit_cool_down > 0f){
            hit_cool_down--;
        }
        else{
            collisionDetection(hero,block);
        }

        if(hero.getPosition().x >= 960){
            Random rand = new Random();
            int x_block_pos = rand.nextInt(700) + 100;
            int y_block_pos = rand.nextInt(200) + 0;
            block = new Block(x_block_pos, y_block_pos);
        }

        cam.position.set(hero.getPosition().x + 150, 100, 0);
        cam.update();
    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        sb.draw(bg,0,0);
        sb.draw(bg,960,0);
        sb.draw(bg,-960,0);

        hero.render(sb);
        block.render(sb);

        sb.end();

    }
}
