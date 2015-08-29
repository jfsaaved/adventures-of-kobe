package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Coin;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.HitBlock;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.sprites.Shop;
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
    private Block block;
    private HitBlock hitblock;
    private Shop shop;
    private Coin coin;
    private Vector<MoveableObject> objects;

    // BGs
    private TextureRegion ground;
    private TextureRegion bg;
    private TextureRegion clouds;
    private float current_bg_x;
    private float current_bg_x_clouds;

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

    // Camera
    private float cam_offset;
    private float cam_acc;

    public PlayState(GSM gsm){
        super(gsm);

        ground = Main.resource.getAtlas("assets").findRegion("dirt");
        clouds = Main.resource.getAtlas("assets").findRegion("clouds1");
        bg = Main.resource.getAtlas("assets").findRegion("bg1");
        health = Main.resource.getAtlas("assets").findRegion("Hero");

        hero = new Hero(0,0, Main.resource.getAtlas("assets").findRegion("Hero"), bg);
        block = new Block(800, 150, Main.resource.getAtlas("assets").findRegion("block"));
        hitblock = new HitBlock(700,100,Main.resource.getAtlas("assets").findRegion("bigblock"));
        shop = new Shop(480,32, Main.resource.getAtlas("assets").findRegion("house"));
        coin = new Coin(300,32, Main.resource.getAtlas("assets").findRegion("coin"));

        objects = new Vector<MoveableObject>();
        objects.add(hitblock);
        objects.add(block);
        objects.add(shop);
        objects.add(coin);

        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

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

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            if(hitblock.contains(mouse.x, mouse.y) && !hitblock.getHide()){
                hitblock.interact();
            }

            else if(stopForShop){
                hero.toggleStop();
                if(!enteredShop){
                    currentDialogue = shop.getDialogue(0);
                    textBox.setTextHide(false);
                    textBox.setTextBox_hide(false);
                    enteredShop = true;
                }
            }

            else{
                hero.jump();
            }
        }
    }

    private void collisionDetection(MoveableObject firstObj, MoveableObject secondObj){
        if(firstObj.getType().equals("shop")){
            shopDetection(firstObj);
        }else if(firstObj.getType().equals("coin")){
            coinDetection(firstObj);
        }else if(firstObj.getType().equals("block")){
            blockDetection(firstObj, secondObj);
        }else if(firstObj.getType().equals("hitblock")){
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

    private void setHideNonShopObjects(boolean b){
        for(MoveableObject object : objects){
            if(!object.getType().equals("shop")){
                object.setHide(b);
            }
        }
    }

    private void setHideShopObjects(boolean b){
        for(MoveableObject object : objects){
            if(object.getType().equals("shop")){
                object.setHide(b);
            }
        }
    }

    private void onNewCycle(){
        if(hero.hasEnteredNewCycle()){
            Random rand = new Random();
            int showShopVar = rand.nextInt(3) + 1;

            if(showShopVar == 1){
                setHideShopObjects(false);
                setHideNonShopObjects(true);
            }
            else{
                setHideShopObjects(true);
                setHideNonShopObjects(false);
            }
        }
    }

    private void onNewCamCycle(){
        if(cam_offset == Math.abs(MAX_CAM_OFFSET)){
            for(MoveableObject object : objects){
                if(!object.getType().equals("shop")) {
                    if (object.getSpawned()) {
                        if (object.getPosition().x < hero.getPosition().x - (50 + object.getRectangle().getWidth())) {
                            object.setSpawned(false);
                        }
                    } else {
                        float newPosX = hero.getPosition().x + 350;
                        if (newPosX >= 910) {
                            newPosX = newPosX - 910;
                        }
                        object.changePosition(newPosX, 32);
                        object.setSpawned(true);
                    }
                }
            }
        }
    }

    private void updateBG(float dt){
        //Add velocity to the bg, to make bg look further away
        if(hero.getSpeed() > 0) {
            current_bg_x += 20f * dt;
            if(current_bg_x >= bg.getRegionWidth()){
                current_bg_x = 0;
            }

            current_bg_x_clouds += 40f * dt;
            if (current_bg_x_clouds >= clouds.getRegionWidth()) {
                current_bg_x_clouds = 0;
            }
        }
        else{
            current_bg_x_clouds += 10f * dt;
            if (current_bg_x_clouds >= clouds.getRegionWidth()) {
                current_bg_x_clouds = 0;
            }
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
        hit_splash.update("HIT!",cam.position.x - hit_x_offset, cam.position.y + cam.viewportHeight/2 - hit_y_offset,0.5f);
        coinsText.update(coins + "", cam.position.x + cam.viewportWidth - coin_text_x_offset, cam.position.y + cam.viewportHeight/2 - coin_text_y_offset,0.20f);
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);

        for(MoveableObject object : objects){
            object.update(dt);
        }

        for(MoveableObject object : objects){
            collisionDetection(object,hero);
        }

        onBlockCollision();
        onExitShop();
        //onNewCamCycle();
        onNewCycle();

        updateCam(dt);
        updateTexts();
        updateBG(dt);

    }

    public void render(SpriteBatch sb){

        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        for(int i = 0 ; i < 3 ; i ++) {
            if(i == 0)
                sb.draw(clouds, current_bg_x_clouds - clouds.getRegionWidth(), 0);
            else if(i == 1)
                sb.draw(clouds, current_bg_x_clouds, 0);
            else if (i == 2)
                sb.draw(clouds, current_bg_x_clouds + clouds.getRegionWidth(), 0);
        }

        for(int i = 0 ; i < 3 ; i ++) {
            if(i == 0)
                sb.draw(bg, current_bg_x - bg.getRegionWidth(), -50);
            else if(i == 1)
                sb.draw(bg, current_bg_x, -50);
            else if (i == 2)
                sb.draw(bg, current_bg_x + bg.getRegionWidth(), -50);
        }


        for(int i = 0 ; i < 3 ; i ++) {
            if(i == 0)
                sb.draw(ground, 0 - ground.getRegionWidth(), 0);
            else if(i == 1)
                sb.draw(ground, 0, 0);
            else if (i == 2)
                sb.draw(ground, 0 + ground.getRegionWidth(), 0);
        }

        for(MoveableObject object : objects){
            object.render(sb);
        }
        hero.render(sb);

        hit_splash.render(sb);
        textBox.renderBox(sb);
        textBox.renderText(sb);

        int health_y_offset = 4;
        for(int i = 1; i <= hero.getHealth_counter(); i++){
            sb.draw(health,cam.position.x + cam.viewportWidth/2 - (25 * i), cam.position.y + cam.viewportHeight/2 - (25 + health_y_offset),health.getRegionWidth()/2,health.getRegionHeight()/2);
        }

        coinsText.render(sb);



        sb.end();

    }
}
