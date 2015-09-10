package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.backgrounds.Clouds;
import com.mygdx.runrunrun.backgrounds.Ground;
import com.mygdx.runrunrun.backgrounds.Mountains;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Coin;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.HitBlock;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.sprites.Shop;
import com.mygdx.runrunrun.sprites.Types;
import com.mygdx.runrunrun.ui.TextBoxImage;
import com.mygdx.runrunrun.ui.TextImage;

import java.util.Random;
import java.util.Vector;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private static float HIT_COOL_DOWN_MAX = 20f;
    private static float MAX_CAM_OFFSET = 150;

    // Moveable Objects
    private Hero hero;
    private Block[] block;
    private HitBlock[] hitblock;
    private Shop shop;
    private Coin coin;
    private Vector<MoveableObject> objects;

    // BGs
    private Ground ground;
    private Clouds clouds;
    private Mountains mountains;

    private float current_bg_x;
    private float current_bg_x_clouds;
    private float mapSize;

    // Text
    private TextImage hit_splash;
    private TextBoxImage textBox;
    private String currentDialogue;

    // UIs
    private TextureRegion health;
    private TextImage coinsText;
    private int coins;

    // Cool Downs
    private float hit_cool_down;
    private float hit_splash_cool_down;

    // Events
    private boolean stopForShop;
    private boolean enteredShop;
    private float exitShopTimer;
    private int currentCycle;
    private boolean newCycle;

    // Camera
    private float cam_offset;
    private float cam_acc;

    public PlayState(GSM gsm, int mapLength){
        super(gsm);

        health = Main.resource.getAtlas("assets").findRegion("player");

        hero = new Hero(0,0, Main.resource.getAtlas("assets").findRegion("player"));
        /*shop = new Shop(1950 - 199,32, Main.resource.getAtlas("assets").findRegion("building1"));
        coin = new Coin(-200,32, Main.resource.getAtlas("assets").findRegion("coin1"));*/

        objects = new Vector<MoveableObject>();
        for(int i = 0 ; i < 10 ; i ++){
            if(i <= 4){
                objects.add(new Block(0,0,Main.resource.getAtlas("assets").findRegion("block1")));
            }else{
                objects.add(new HitBlock(0,0,Main.resource.getAtlas("assets").findRegion("block2")));
            }
            objects.elementAt(i).setHide(true);
        }

        //objects.add(shop);
        //objects.add(coin);

        mountains = new Mountains(0,0,Main.resource.getAtlas("assets").findRegion("bg1"), mapLength);
        ground = new Ground(0,0,Main.resource.getAtlas("assets").findRegion("ground1"), mapLength);
        clouds = new Clouds(0,Main.GROUND_LEVEL,Main.resource.getAtlas("assets").findRegion("clouds1"),mapLength);

        cam.setToOrtho(false, mapLength * 400, Main.HEIGHT);
        //cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

        coinsText = new TextImage(coins + "", cam.position.x + cam.viewportWidth/2 - 25, cam.position.y + cam.viewportHeight/2 - 39,0.20f);
        hit_splash = new TextImage("",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);
        textBox = new TextBoxImage("",cam.position.x - cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 - 9,0.20f,cam.viewportWidth);

        cam_offset = 0;
        cam_acc = 0;

        coins = hero.getCoins();

        coinsText.setTextHide(false);
        textBox.setTextHide(true);
        textBox.setTextBox_hide(true);

        currentDialogue = "";

        enteredShop = false;
        exitShopTimer = -1;

        current_bg_x = 0;
        current_bg_x_clouds = 0;
        mapSize = ground.getTextureRegion().getRegionWidth() * mapLength;

        currentCycle = 0;
        newCycle = false;

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            boolean jump = true;

            for(MoveableObject object : objects){
                if(object.getType().equals(Types.HitBlock)) {
                    if (object.contains(mouse.x, mouse.y) && !object.getHide()) {
                        object.interact();
                        jump = false;
                    }
                }
            }

            if(stopForShop){
                hero.toggleStop();
                if(!enteredShop){
                    currentDialogue = shop.getDialogue(0);
                    textBox.setTextHide(false);
                    textBox.setTextBox_hide(false);
                    enteredShop = true;
                }
            }else if(jump){
                hero.jump();
            }


        }
    }

    private void collisionDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(firstObj.getType().equals(Types.Shop)){
            shopDetection(firstObj);
        }else if(firstObj.getType().equals(Types.Coin)){
            coinDetection(firstObj);
        }else if(firstObj.getType().equals(Types.Block)){
            blockDetection(firstObj, secondObj);
        }else if(firstObj.getType().equals(Types.HitBlock)){
            blockDetection(firstObj, secondObj);
        }
    }

    private void shopDetection(MoveableObject firstObj){
        if(firstObj.contains(hero.getPosition()) && firstObj.getHide() == false) {
            stopForShop = true;
            exitShopTimer = 100;
        }else{
            stopForShop = false;
            enteredShop = false;
            if(hero.getStop()){
                hero.toggleStop();
            }
        }
    }

    private void coinDetection(MoveableObject firstObj){
        if(firstObj.overlaps(hero.getRectangle())){
            if(firstObj.getHide() == false) {
                hero.addCoin(1);
                coins = hero.getCoins();
                firstObj.setHide(true);
            }
        }
    }

    private void blockDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(!firstObj.getHide()) {
            if (firstObj.overlaps(secondObj.getRectangle())) {
                if (hit_cool_down == 0) {
                    hit_cool_down = HIT_COOL_DOWN_MAX;
                    hit_splash_cool_down = 60f;
                }
            }
        }
    }

    private void onBlockCollision(){
        // On hit code below
        if(hit_cool_down > 0f){
            if(hit_cool_down == HIT_COOL_DOWN_MAX){
                hero.reduceHealth();
            }
            hit_cool_down--;
            hero.hit_animation(hit_cool_down);
        }
        else{
            hit_cool_down = 0;
        }

        if(hit_splash_cool_down > 0f){
            hit_splash.setTextHide(false);
            hit_splash_cool_down--;
        }
        else{
            hit_splash.setTextHide(true);
        }
    }

    private void onExitShop(){
        if(exitShopTimer > 0) {
            if (exitShopTimer > 97 && exitShopTimer < 98) {
                textBox.setTextHide(true);
                textBox.setTextBox_hide(true);
            }
            else if(exitShopTimer < 97 && exitShopTimer > 20){
                if(currentDialogue.equals(shop.getDialogue(0))) {
                    currentDialogue = shop.getDialogue(1);
                    textBox.setTextHide(false);
                    textBox.setTextBox_hide(false);
                }
            }
            else if(exitShopTimer < 20){
                textBox.setTextHide(true);
                textBox.setTextBox_hide(true);
            }
            exitShopTimer--;
        }
    }

    private void onNewCycle(){
        if(newCycle){

            Random rand = new Random();
            int i = 0;
            int j = 0;

            for(MoveableObject object : objects){
                if(object.getHide()) {
                    int newValX = rand.nextInt(1600) + 350;
                    if(newValX == 1950)
                        object.changePosition(newValX - object.getWidth(), object.getPosition().y);
                    else {

                        for(MoveableObject object2 : objects){
                            if(object.overlaps(object2.getRectangle()))
                                System.out.println("Overlapping");
                        }

                        object.changePosition(newValX, object.getPosition().y);
                    }
                    object.setHide(false);
                }
            }
            currentCycle++;
        }
    }

    private void onNewArea(){
        for(MoveableObject object : objects){
            if(object.getPosition().x < hero.getPosition().x - ( 50 + object.getWidth()))
                object.setHide(true);
        }
    }

    private void updateCam(float dt){
        if(hero.getSpeed() > 0) {
            if(cam_offset < Math.abs(MAX_CAM_OFFSET)){
                cam_offset += 40f * dt;
                cam_acc = 30f;
            }
            if(cam_offset > Math.abs(MAX_CAM_OFFSET)){
                cam_offset = Math.abs(MAX_CAM_OFFSET);
            }
        }else{
            if(cam_offset > 0){
                cam_offset -= (10f + cam_acc) * dt;
                if(cam_acc > 0) {
                    cam_acc -= 0.5f;
                }
            }
            if(cam_offset < 0){
                cam_offset = 0;
            }
        }
        cam.position.set(hero.getPosition().x + cam_offset, 100, 0);
        cam.update();
    }

    private void updateTexts(){
        int textBox_x_offset = 2;
        int textBox_y_offset = 4;

        int coin_text_x_offset = 200;
        int coin_text_y_offset = 24;

        int hit_x_offset = 40;
        int hit_y_offset = 75;

        textBox.update(currentDialogue,cam.position.x - cam.viewportWidth/2 + textBox_x_offset, cam.position.y + cam.viewportHeight/2 - (9 + textBox_y_offset),0.20f);
        hit_splash.update("HIT!", cam.position.x - hit_x_offset, cam.position.y + cam.viewportHeight / 2 - hit_y_offset, 0.5f);
        coinsText.update(coins + "", cam.position.x + cam.viewportWidth - coin_text_x_offset, cam.position.y + cam.viewportHeight/2 - coin_text_y_offset,0.20f);
    }

    private void onExitCycle(){
        if(hero.getPosition().x >= mapSize){
            hero.changePosition(hero.getPosition().x - mapSize,hero.getPosition().y);
            newCycle = true;
        }else
            newCycle = false;
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        for(MoveableObject object : objects) object.update(dt);
        for(MoveableObject object : objects) collisionDetection(object,hero);

        /*onExitShop();*/

        onBlockCollision();
        onNewArea();
        onNewCycle();
        onExitCycle();

        ground.update(dt,hero.getPosition().x);
        mountains.update(dt, hero.getPosition().x, hero.getSpeed());
        clouds.update(dt, hero.getPosition().x, hero.getSpeed());

        //updateCam(dt);
        updateTexts();

    }

    private void renderHealth(SpriteBatch sb){
        int health_y_offset = 4;
        for(int i = 1; i <= hero.getHealth_counter(); i++)
            sb.draw(health,cam.position.x + cam.viewportWidth/2 - (25 * i), cam.position.y + cam.viewportHeight/2 - (25 + health_y_offset),health.getRegionWidth()/2,health.getRegionHeight()/2);
    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        clouds.render(sb);
        mountains.render(sb);
        ground.render(sb);
        for(MoveableObject object : objects)
            object.render(sb);
        hero.render(sb);

        hit_splash.render(sb);
        textBox.renderBox(sb);
        textBox.renderText(sb);
        coinsText.render(sb);
        renderHealth(sb);

        sb.end();
    }
}
