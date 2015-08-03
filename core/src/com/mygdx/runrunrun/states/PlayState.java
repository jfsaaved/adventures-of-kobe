package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Hero;

import java.util.Random;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private Hero hero;
    private Block block;

    private TextureRegion bg;

    public PlayState(GSM gsm){
        super(gsm);


        hero = new Hero();
        block = new Block(400, 0);
        bg = Main.resource.getAtlas("assets").findRegion("bg1");

        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            hero.jump();
        }
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        block.update(dt);

        if(hero.getX() >= 960){
            Random rand = new Random();
            int x_block_pos = rand.nextInt(700) + 100;
            int y_block_pos = rand.nextInt(200) + 0;
            block = new Block(x_block_pos, y_block_pos);
        }

        cam.position.set(hero.getX() + 150, 100, 0);
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
