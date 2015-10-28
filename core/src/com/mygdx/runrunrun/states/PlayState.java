package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.backgrounds.Clouds;
import com.mygdx.runrunrun.backgrounds.Ground;
import com.mygdx.runrunrun.backgrounds.Mountains;
import com.mygdx.runrunrun.handler.Sounds;
import com.mygdx.runrunrun.sprites.Block;
import com.mygdx.runrunrun.sprites.Coin;
import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.sprites.HitBlock;
import com.mygdx.runrunrun.sprites.MoveableObject;
import com.mygdx.runrunrun.sprites.MovingBlock;
import com.mygdx.runrunrun.sprites.Shop;
import com.mygdx.runrunrun.sprites.Types;
import com.mygdx.runrunrun.handler.Sounds;
import com.mygdx.runrunrun.ui.ItemButton;
import com.mygdx.runrunrun.ui.TextBoxImage;
import com.mygdx.runrunrun.ui.TextImage;

import java.util.Random;
import java.util.Vector;

/**
 * Created by 343076 on 25/07/2015.
 */
public class PlayState extends State{

    private static float HIT_COOL_DOWN_MAX = 60f;
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
    private TextImage pressToBegin;
    private TextImage levelText;
    private TextImage textSplash;
    private TextImage goToTown;

    // Shop Stuff
    private TextBoxImage shopTextBox;
    private TextBoxImage shopTextBoxOptions;
    private String currentDialogue;
    private String currentOption;
    private Vector<ItemButton> itemButtons;

    // UIs
    private TextureRegion health;
    private TextImage coinsText;

    // Cool Downs
    private float hit_cool_down;
    private float hit_splash_cool_down;

    // Events
    private boolean stopForShop;
    private boolean enteredShop;
    private float exitShopTimer;
    private int currentCycle;
    private boolean newCycle;

    // Town Events
    private boolean toTown;
    private boolean shopExited;
    private boolean inTown;
    private String toTownString;
    private int toTownTimer;
    private int toTownCoolDown;

    // Skills
    private int flyCount;

    // Camera
    private float cam_offset;
    private float cam_acc;

    // Level
    private int level;

    // Intro
    private boolean intro;
    private int flashVal;

    //Score
    private TextImage scoreImage;
    private int scoreTimer;
    private int score;

    // Music variables
    private Music beforePlay;
    private Music play;

    // Sound effects
    private Sound jumpSound;
    private boolean jumpSoundHelper;

    // Game over stuff
    private boolean gameOver;

    public PlayState(GSM gsm, int mapLength){
        super(gsm);

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        hero = new Hero(0,0, Main.resource.getAtlas("assets").findRegion("player"));

        level = 5;
        initCamera(mapLength);
        initLevelObj(level);
        initUI();
        initShopButtons();
        initBGs(mapLength);
        initStart();
        initMusic();
        initSounds();

        coinsText.setTextHide(false);
        shopTextBox.setTextHide(true);
        shopTextBox.setTextBox_hide(true);
        currentDialogue = "";
        currentOption = "";
        enteredShop = false;
        exitShopTimer = -1;
        flyCount = -1;
        toTown = false;
        inTown = false;

    }

    private void initMusic(){
        play = Main.musicContent.getMusic("play");
        beforePlay = Main.musicContent.getMusic("intro");
    }

    private void initSounds(){
        jumpSound = Main.sounds.getSound("select");
    }

    private void initCamera(int mapLength){
        cam.setToOrtho(false, mapLength * 400, Main.HEIGHT);
        cam.setToOrtho(false, Main.WIDTH/2, Main.HEIGHT/2);

        cam_offset = 0;
        cam_acc = 0;
    }

    private void initBGs(int mapLength){
        mountains = new Mountains(0,0,Main.resource.getAtlas("assets").findRegion("bg1"), mapLength);
        ground = new Ground(0,0,Main.resource.getAtlas("assets").findRegion("ground1"), mapLength);
        clouds = new Clouds(0,Main.GROUND_LEVEL,Main.resource.getAtlas("assets").findRegion("clouds1"),mapLength);

        mapSize = ground.getTextureRegion().getRegionWidth() * mapLength;
        currentCycle = 0;
        newCycle = false;
    }

    private void initUI(){
        health = Main.resource.getAtlas("assets").findRegion("heart");


        coinsText = new TextImage(hero.getCoins() + "", cam.position.x + cam.viewportWidth/2 - 25, cam.position.y + cam.viewportHeight/2 - 39,0.20f);
        textSplash = new TextImage("",cam.position.x + cam.viewportWidth/2 - 150, cam.position.y + cam.viewportHeight/2 - 100,0.5f);
        goToTown = new TextImage("",cam.position.x + cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2,0.25f);
        levelText = new TextImage("",cam.position.x + cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 + 100,0.25f);
        scoreImage = new TextImage("",cam.position.x + cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 + 100,0.25f);
        pressToBegin = new TextImage("",cam.position.x + cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 + 100,0.25f);

        shopTextBox = new TextBoxImage("",cam.position.x - cam.viewportWidth/2, cam.position.y + cam.viewportHeight/2 - 9,0.20f,cam.viewportWidth);
        shopTextBoxOptions = new TextBoxImage("",cam.position.x - cam.viewportWidth/2,cam.position.y + cam.viewportWidth/2 - 100,0f,cam.viewportWidth/4);

        Shop temp = (Shop) objects.lastElement();
        int options = temp.getItemSize();
        shopTextBoxOptions.setRow(options + 2);

        goToTown.setTextHide(false);
        levelText.setTextHide(false);
        scoreImage.setTextHide(false);

        toTownString = "TOWN";
    }

    private void initStart(){
        pressToBegin.update("PRESS TO BEGIN", cam.position.x, cam.position.y + cam.viewportHeight / 2 - 75, 0.5f);
        pressToBegin.setTextHide(false);

        hero.toggleStop(true);
        flashVal = 60;
        intro = true;
    }

    private void initLevelObj(int level){

        objects = new Vector<MoveableObject>();

        // Coins
        objects.add(new Coin(0,0, Main.resource.getAtlas("assets").findRegion("coin1")));
        objects.lastElement().setHide(true);

        // Moving objects
        objects.add(new MovingBlock(0, 0, Main.resource.getAtlas("assets").findRegion("block1"), 20f));
        objects.lastElement().setHide(true);

        // Blocks
        for (int i = 0; i < level; i++) {
            if (i < (level / 2) + 2) {
                objects.add(new Block(0, 0, Main.resource.getAtlas("assets").findRegion("block1")));
            } else {
                objects.add(new HitBlock(0, 0, Main.resource.getAtlas("assets").findRegion("block2")));
            }
            objects.lastElement().setHide(true);
        }

        // Shops
        objects.add(new Shop(700, 32, Main.resource.getAtlas("assets").findRegion("building1")));
        objects.lastElement().setHide(true);

    }

    private void initShopButtons(){
        // ORDER IS SLEEP, BREAD, SOUP, SUSHI, SODA
        itemButtons = new Vector<ItemButton>();
        int initX = 122;
        Shop temp = (Shop) objects.lastElement();
        for(int i = 0; i < temp.getItemSize() ; i++){
            itemButtons.add(new ItemButton(cam.position.x + cam.viewportWidth/2,initX, 80, 9, temp.getItem(i)));
            initX -= 9;
        }

    }

    public void changeDialogue(int i){
        shopTextBox.setTextHide(true);
        currentDialogue = shop.getDialogue(i);
        shopTextBox.setTextHide(false);
    }

    public void handleInput(){
        if(Gdx.input.justTouched() && !enterTransition && !exitTransition){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            boolean jump = true;

            if(intro){
                intro = false;
                hero.toggleStop(false);
                pressToBegin.setTextHide(true);
                return;
            }

            for(MoveableObject object : objects){
                if(object.getType().equals(Types.HitBlock) || object.getType().equals(Types.Block) || object.getType().equals(Types.MovingBlock)) {
                    if (object.contains(mouse.x, mouse.y) && !object.getHide()) {
                        object.kill();
                        return;
                    }
                }else if(object.getType().equals(Types.Coin)){
                    if (object.contains(mouse.x, mouse.y) && !object.getHide()) {
                        object.interact();
                        return;
                    }
                }
            }

            if(hero.contains(mouse.x, mouse.y) && !stopForShop && !hero.isFlying()){
                hero.interact();
                return;
            }

            if(goToTown.containsRect(mouse.x,mouse.y) && toTownCoolDown <= 0){
                toTown = true;
                toTownTimer = 40;
                toTownString = "TRAVELING";
                toTownCoolDown = 1000;
                shopExited = false;
                inTown = true;
                return;
            }

            for(ItemButton itemButton : itemButtons){
                if(itemButton.containsRect(mouse.x,mouse.y)){

                    itemButton.interact(hero, this);

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
                if(hero.getJump())
                    jumpSoundHelper = true;
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
            if(flyCount <= 0)
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

        coin = (Coin) firstObj;

        if(firstObj.overlaps(hero.getRectangle())){
            if(firstObj.getHide() == false) {
                hero.addCoin( coin.getValue());
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

    private void onIntro(){

        if(enterTransition){
            pressToBegin.setTextHide(true);
            return;
        }

        if(intro){

            if(flashVal > 0){
                if(flashVal > 30){
                   pressToBegin.setTextHide(false);
                }else{
                    pressToBegin.setTextHide(true);
                }
                flashVal--;
            }else{
                flashVal = 60;
            }
        }
    }

    private void onBlockCollision(){
        // On hit code below
        if(hit_cool_down > 0f){
            if(hit_cool_down == HIT_COOL_DOWN_MAX){
                hero.reduceHealth();
                if(hero.getHealth_counter() == 0) {
                    hero.reduceHealth();
                    gameOver = true;
                }
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

    private void onTownClick(){
        if(toTown) {
            if (toTownTimer > 0) {
                if (toTownTimer > 20) {
                    goToTown.setTextHide(true);
                } else {
                    goToTown.setTextHide(false);
                }
                toTownTimer--;
            } else {
                toTownTimer = 40;
            }
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
                if(currentDialogue.equals(shop.getDialogue(0)) || currentDialogue.equals(shop.getDialogue(2)) || currentDialogue.equals(shop.getDialogue(3))) {
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

        if(newCycle){

            if(flyCount > 0){
                flyCount--;
            }else if(flyCount == 0){
                hero.setFly(false);
                flyCount--;
            }

            if(!toTown){
                currentCycle++;
                if(currentCycle % 10 == 0){
                    if(level < 24) {
                        level++;
                        initLevelObj(level);
                    }
                }
            }

            for (MoveableObject object : objects) {
                if(!toTown) {
                    if(!shopExited)
                        shopExited = true;
                    if(inTown)
                        inTown = false;

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
                        if(object.getType().equals(Types.Shop)) {
                            object.changePosition(700,object.getPosition().y);
                            object.setHide(false);
                        }
                        else
                            object.setHide(true);
                    }
                }
            }
            if(toTown) {
                toTown = false;
                toTownString = "IN TOWN";
                goToTown.setTextHide(false);
            }
        }
    }

    private void onNewArea(){

        if(shopExited) {
            if (toTownCoolDown > 0 && !toTown) {
                toTownCoolDown--;
                toTownString = toTownCoolDown + "";
            }else{
                shopExited = false;
                toTownString = "TOWN";
            }
        }

        //if((cam.position.x - hero.getPosition().x) > 147) {
        for (MoveableObject object : objects) {
            if(object.getType().equals(Types.Shop)){
                if (object.getPosition().x < hero.getPosition().x - (100 + object.getWidth()))
                    object.setHide(true);
            }else {
                if (object.getPosition().x < hero.getPosition().x - (50 + object.getWidth()))
                    object.setHide(true);
            }
        }
        //}
    }

    private void onGameOver(){
        if(gameOver){
            gameOver = false;
            hero.toggleStop(true);
            hero.setFly(true);
            hero.setFade(true);
            exitTransition = true;
            exitTransitionVal = 0f;
        }
    }

    @Override
    protected void onExitTransition(float dt){
        if(exitTransition){

            exitTransitionVal += 1f * dt;
            if(exitTransitionVal >= 1f){
                gsm.set(new GameOverState(gsm, score));
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

        int coin_text_x_offset = 20;
        int coin_text_y_offset = 40;

        int hit_y_offset = 75;

        shopTextBox.update(currentDialogue,cam.position.x - cam.viewportWidth/2 + shopTextBox_x_offset, cam.position.y + cam.viewportHeight/2 - (9 + shopTextBox_y_offset),0.20f);
        shopTextBoxOptions.update(currentOption,cam.position.x - cam.viewportWidth/2 + cam.viewportWidth/2 + cam.viewportWidth/4, cam.position.y + cam.viewportHeight/2 - (70),0.20f);

        textSplash.update("HIT!", cam.position.x, cam.position.y + cam.viewportHeight / 2 - hit_y_offset, 0.5f);
        pressToBegin.update("PRESS TO BEGIN", cam.position.x, cam.position.y + cam.viewportHeight / 2 - 75, 0.5f);
        goToTown.update(toTownString, cam.position.x - 150, 16, 0.25f);
        levelText.update("LEVEL" + (level - 4),cam.position.x + 150, 20, 0.25f);
        scoreImage.update("SCORE" + score, cam.position.x + 150, 8, 0.20f);

        coinsText.update(hero.getCoins() + "", cam.position.x + cam.viewportWidth/2 - coin_text_x_offset, cam.position.y + cam.viewportHeight/2 - coin_text_y_offset,0.20f);
    }

    private void updateButtons(){
        for(ItemButton itemButton : itemButtons)
            itemButton.update(cam.position.x - cam.viewportWidth/2 + cam.viewportWidth / 2 + cam.viewportWidth / 4 + 10, itemButton.getY());
    }

    private void updateMusic(){
        if(intro){
            if(!beforePlay.isPlaying()){
                beforePlay.play();
            }
        }else if(gameOver || exitTransition){
            if(play.isPlaying()) {
                play.stop();
                play.dispose();
            }
            if(beforePlay.isPlaying()){
                beforePlay.stop();
                beforePlay.dispose();
            }
        }else{
            if(beforePlay.isPlaying())
                beforePlay.stop();
            if (!play.isPlaying()) {
                play.play();
            }
        }
    }

    private void updateSounds(){
        if(jumpSoundHelper) {
            jumpSound.play();
            jumpSoundHelper = false;
        }
    }

    private void onExitCycle(){
        if(hero.getPosition().x >= mapSize){
            hero.changePosition(hero.getPosition().x - mapSize,hero.getPosition().y);
            newCycle = true;
        }else
            newCycle = false;
    }

    private void scoreCalculator(){
        if(!intro) {
            if (scoreTimer == 60) {
                scoreTimer = 0;
                score++;
            }
            if (!inTown)
                scoreTimer++;
        }
    }

    public void setFly(boolean b){
        if(b){
            flyCount = 1;

        }else{
            flyCount = -1;
        }
    }

    public void update(float dt){

        handleInput();

        hero.update(dt);
        for(MoveableObject object : objects) object.update(dt);
        for(MoveableObject object : objects) collisionDetection(object,hero);

        scoreCalculator();

        onEnterTransition(dt);
        onIntro();
        onTownClick();
        onExitShop();
        onBlockCollision();
        onGameOver();
        onExitTransition(dt);
        onNewArea();
        onNewCycle();
        onExitCycle();

        ground.update(dt,hero.getPosition().x);
        mountains.update(dt, hero.getPosition().x, hero.getSpeed());
        clouds.update(dt, hero.getPosition().x, hero.getSpeed());

        updateMusic();
        updateSounds();
        updateCam(dt);
        updateTexts();
        updateButtons();

    }

    private void renderHealth(SpriteBatch sb){
        int health_y_offset = 4;
        for(int i = 1; i <= hero.getHealth_counter(); i++)
            sb.draw(health,cam.position.x + cam.viewportWidth/2 - (16 * i), cam.position.y + cam.viewportHeight/2 - (25 + health_y_offset),health.getRegionWidth(),health.getRegionHeight());
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

        scoreImage.render(sb);
        pressToBegin.render(sb);
        textSplash.render(sb);
        goToTown.render(sb);
        levelText.render(sb);

        shopTextBox.renderBox(sb);
        shopTextBox.renderText(sb);
        shopTextBoxOptions.renderBox(sb);
        shopTextBoxOptions.renderText(sb);

        coinsText.render(sb);
        renderHealth(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if(enterTransition) {
            sr.setColor((new Color(0, 0, 0, enterTransitionVal)));
            sr.rect(hero.getPosition().x - 200, hero.getPosition().y - 100, Main.WIDTH, Main.HEIGHT);
        }
        else if(exitTransition) {
            sr.setColor(new Color(0, 0, 0, exitTransitionVal));
            sr.rect(cam.position.x - 200, cam.position.y - 100, Main.WIDTH, Main.HEIGHT);
        }

        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);


        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);


        //sr.rect(hero.getRectangle().getX(), hero.getRectangle().getY(), hero.getRectangle().getWidth(),hero.getRectangle().getHeight());
        //sr.setColor(1, 1, 0, 1);

        /*
        if(enteredShop) {
            for (ItemButton itemButton : itemButtons)
                sr.rect(itemButton.getX(), itemButton.getY(), itemButton.getWidth(), itemButton.getHeight());

            sr.rect(shop.getRectangle().getX(), shop.getRectangle().getY(), shop.getRectangle().getWidth(), shop.getRectangle().getHeight());
        }

        if(toTownCoolDown <= 0)
            sr.rect(goToTown.getX(), goToTown.getY(), goToTown.getWidth(), goToTown.getHeight());
        */

        sr.end();

    }
}
