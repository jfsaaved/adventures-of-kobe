package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.backgrounds.Clouds;
import com.mygdx.runrunrun.backgrounds.Ground;
import com.mygdx.runrunrun.backgrounds.Mountains;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Coin;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.HitBlock;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.sprites.MovingBlock;
import com.mygdx.runrunrun.sprites.Shop;
import com.mygdx.runrunrun.sprites.Types;
import com.mygdx.runrunrun.ui.Item;
import com.mygdx.runrunrun.ui.ItemButton;
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
    private Shop shop;
    private Coin coin;
    private Vector<MoveableObject> objects;

    // BGs
    private Ground ground;
    private Clouds clouds;
    private Mountains mountains;
    private float mapSize;

    // Text
    private TextImage textSplash;

    // Shop Stuff
    private TextBoxImage shopTextBox;
    private TextBoxImage shopTextBoxOptions;
    private String currentDialogue;
    private String currentOption;
    private Vector<ItemButton> itemButtons;

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

        objects = new Vector<MoveableObject>();

        objects.add(new MovingBlock(0,0,Main.resource.getAtlas("assets").findRegion("coin1"), 20f));
        objects.lastElement().setHide(true);

        for(int i = 0 ; i < 10 ; i ++){

            if(i < 7){
                objects.add(new Block(0,0,Main.resource.getAtlas("assets").findRegion("block1")));
            }else{
                objects.add(new HitBlock(0,0,Main.resource.getAtlas("assets").findRegion("block2")));
            }

            objects.lastElement().setHide(true);
        }


        objects.add(new Shop(700, 32, Main.resource.getAtlas("assets").findRegion("building1")));
        objects.lastElement().setHide(false);

        mountains = new Mountains(0,0,Main.resource.getAtlas("assets").findRegion("bg1"), mapLength);
        ground = new Ground(0,0,Main.resource.getAtlas("assets").findRegion("ground1"), mapLength);
        clouds = new Clouds(0,Main.GROUND_LEVEL,Main.resource.getAtlas("assets").findRegion("clouds1"),mapLength);

        cam.setToOrtho(false, mapLength * 400, Main.HEIGHT);
        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

        coinsText = new TextImage(coins + "", cam.position.x + cam.viewportWidth/2 - 25, cam.position.y + cam.viewportHeight/2 - 39,0.20f);
        textSplash = new TextImage("",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);
        shopTextBox = new TextBoxImage("",cam.position.x - cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 - 9,0.20f,cam.viewportWidth);
        shopTextBoxOptions = new TextBoxImage("Hello",cam.position.x - cam.viewportWidth/2,cam.position.y + cam.viewportWidth/2 - 100,0f,cam.viewportWidth/4);
        shopTextBoxOptions.setRow(12);

        // ORDER IS SLEEP, BREAD, SOUP, SUSHI, SODA
        itemButtons = new Vector<ItemButton>();

        int initX = 122;
        Shop temp = (Shop) objects.lastElement();
        for(int i = 0; i < 5 ; i++){
            itemButtons.add(new ItemButton(cam.position.x + cam.viewportWidth/2,initX, 80, 9, temp.getItem(i)));
            initX -= 9;
        }


        mapSize = ground.getTextureRegion().getRegionWidth() * mapLength;
        currentCycle = 0;
        newCycle = false;

        cam_offset = 0;
        cam_acc = 0;

        coins = hero.getCoins();
        coinsText.setTextHide(false);

        shopTextBox.setTextHide(true);
        shopTextBox.setTextBox_hide(true);
        currentDialogue = "";
        currentOption = "";
        enteredShop = false;
        exitShopTimer = -1;

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            boolean jump = true;

            for(MoveableObject object : objects){
                if(object.getType().equals(Types.HitBlock) || object.getType().equals(Types.Block) || object.getType().equals(Types.MovingBlock)) {
                    if (object.contains(mouse.x, mouse.y) && !object.getHide()) {
                        object.interact();
                        return;
                    }
                }
            }

            if(hero.contains(mouse.x, mouse.y)){
                hero.interact();
                return;
            }

            for(ItemButton itemButton : itemButtons){
                if(itemButton.containsRect(mouse.x,mouse.y)){
                    itemButton.interact();
                    return;
                }
            }


            if(stopForShop){
                hero.toggleStop();
                if(!enteredShop){
                    currentDialogue = shop.getDialogue(0);
                    currentOption = shop.getOptions(0);
                    shopTextBox.setTextHide(false);
                    shopTextBox.setTextBox_hide(false);

                    shopTextBoxOptions.setTextHide(false);
                    shopTextBoxOptions.setTextBox_hide(false);
                    enteredShop = true;

                    for(ItemButton itemButton : itemButtons)
                        itemButton.setHide(false);
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
        }else if(firstObj.getType().equals(Types.Block) || firstObj.getType().equals(Types.MovingBlock)){
            blockDetection(firstObj, secondObj);
        }else if(firstObj.getType().equals(Types.HitBlock)){
            blockDetection(firstObj, secondObj);
        }
    }

    private void shopDetection(MoveableObject firstObj){
        shop = (Shop) firstObj;
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
            textSplash.setTextHide(false);
            hit_splash_cool_down--;
        }
        else{
            textSplash.setTextHide(true);
        }
    }

    private void onExitShop(){
        if(exitShopTimer > 0) {
            if (exitShopTimer > 97 && exitShopTimer < 98) {
                shopTextBox.setTextHide(true);
                shopTextBox.setTextBox_hide(true);

                shopTextBoxOptions.setTextHide(true);
                shopTextBoxOptions.setTextBox_hide(true);

                for(ItemButton itemButton : itemButtons)
                    itemButton.setHide(true);
            }
            else if(exitShopTimer < 97 && exitShopTimer > 20){
                if(currentDialogue.equals(shop.getDialogue(0))) {
                    currentDialogue = shop.getDialogue(1);
                    shopTextBox.setTextHide(false);
                    shopTextBox.setTextBox_hide(false);

                    shopTextBoxOptions.setTextHide(true);
                    shopTextBoxOptions.setTextBox_hide(true);

                    for(ItemButton itemButton : itemButtons)
                        itemButton.setHide(true);
                }
            }
            else if(exitShopTimer < 20){
                shopTextBox.setTextHide(true);
                shopTextBox.setTextBox_hide(true);

                shopTextBoxOptions.setTextHide(true);
                shopTextBoxOptions.setTextBox_hide(true);

                for(ItemButton itemButton : itemButtons)
                    itemButton.setHide(true);
            }
            exitShopTimer--;
        }
    }

    private void onNewCycle(){
        Random rand = new Random();

        int areaType = 2;

        if(newCycle){
            for (MoveableObject object : objects) {
                if(areaType == 1) {
                    if (object.getHide()) {
                        int newValX = rand.nextInt(1600) + 350;
                        if (newValX + object.getWidth() >= 1950) {
                            newValX = rand.nextInt(1600 - (int) object.getWidth()) + 350;
                            object.changePosition(newValX, object.getPosition().y);
                            object.setHide(false);
                        } else if (newValX < 1950) {
                            object.changePosition(newValX, object.getPosition().y);
                            object.setHide(false);
                        } else {
                            object.setHide(true);
                        }
                        if(object.getType().equals(Types.Shop))
                            object.setHide(true);
                    }
                }else{
                    if(object.getHide()){
                        if(object.getType().equals(Types.Shop))
                            object.setHide(false);
                        else
                            object.setHide(true);
                    }
                }
            }
            currentCycle++;
        }
    }

    private void onNewArea(){
        if((cam.position.x - hero.getPosition().x) > 147) {
            for (MoveableObject object : objects) {
                if (object.getPosition().x < hero.getPosition().x - (50 + object.getWidth()))
                    object.setHide(true);
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
        int shopTextBox_x_offset = 2;
        int shopTextBox_y_offset = 4;

        int coin_text_x_offset = 200;
        int coin_text_y_offset = 24;

        int hit_x_offset = 40;
        int hit_y_offset = 75;

        shopTextBox.update(currentDialogue,cam.position.x - cam.viewportWidth/2 + shopTextBox_x_offset, cam.position.y + cam.viewportHeight/2 - (9 + shopTextBox_y_offset),0.20f);
        shopTextBoxOptions.update(currentOption,cam.position.x - cam.viewportWidth/2 + cam.viewportWidth/2 + cam.viewportWidth/4, cam.position.y + cam.viewportHeight/2 - (70),0.20f);
        textSplash.update("HIT!", cam.position.x - hit_x_offset, cam.position.y + cam.viewportHeight / 2 - hit_y_offset, 0.5f);
        coinsText.update(coins + "", cam.position.x + cam.viewportWidth - coin_text_x_offset, cam.position.y + cam.viewportHeight/2 - coin_text_y_offset,0.20f);
    }

    private void updateButtons(){
        for(ItemButton itemButton : itemButtons)
            itemButton.update(cam.position.x - cam.viewportWidth / 2 + cam.viewportWidth / 2 + cam.viewportWidth / 4 + 10, itemButton.getY());
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

        onExitShop();
        onBlockCollision();
        onNewArea();
        onNewCycle();
        onExitCycle();

        ground.update(dt,hero.getPosition().x);
        mountains.update(dt, hero.getPosition().x, hero.getSpeed());
        clouds.update(dt, hero.getPosition().x, hero.getSpeed());

        updateCam(dt);
        updateTexts();
        updateButtons();

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

        for(int i = objects.size() - 1 ; i >= 0 ; i --){
            objects.elementAt(i).render(sb);
        }

        hero.render(sb);

        textSplash.render(sb);
        shopTextBox.renderBox(sb);
        shopTextBox.renderText(sb);

        shopTextBoxOptions.renderBox(sb);
        shopTextBoxOptions.renderText(sb);
        coinsText.render(sb);
        renderHealth(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(1, 1, 0, 1);

        for(ItemButton itemButton : itemButtons)
            sr.rect(itemButton.getX(),itemButton.getY(),itemButton.getWidth(), itemButton.getHeight());

        sr.end();
    }
}
