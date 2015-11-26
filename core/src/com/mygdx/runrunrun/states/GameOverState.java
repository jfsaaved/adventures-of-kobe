package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.sprites.Coin;
import com.mygdx.runrunrun.ui.TextImage;

import org.w3c.dom.css.Rect;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import sun.net.www.http.HttpClient;

/**
 * Created by 343076 on 12/08/2015.
 */
public class GameOverState extends State {

    private TextureRegion gameOver;
    private TextureRegion play;
    private Rectangle playRect;
    private TextureRegion menu;
    private Rectangle menuRect;

    private TextImage highScore;
    private TextImage currentScore;

    private boolean fState; // 0 play again, 1 main menu;

    public GameOverState(GSM gsm, int score){
        super(gsm);

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        exitTransition = false;

        currentScore = new TextImage("YOUR SCORE:" + score, 0, 0, 0.5f );
        highScore = new TextImage("HIGH SCORE:" + Main.pref.getHighScore(), 0, 0, 0.5f);

        gameOver = new TextureRegion(Main.resource.getAtlas("assets").findRegion("gameover"));
        play = new TextureRegion(Main.resource.getAtlas("assets").findRegion("restart"));
        playRect = new Rectangle(Main.WIDTH/2 - play.getRegionWidth()/2,Main.HEIGHT/2,play.getRegionWidth(),play.getRegionHeight());
        menu = new TextureRegion(Main.resource.getAtlas("assets").findRegion("mainmenu"));
        menuRect = new Rectangle(Main.WIDTH/2 - menu.getRegionWidth()/2,Main.HEIGHT/2 - playRect.getHeight(),menu.getRegionWidth(),menu.getRegionHeight());

        currentScore.update(Main.WIDTH/2 + currentScore.getWidth()/2, Main.HEIGHT/2 - 130);
        highScore.update(Main.WIDTH/2 + highScore.getWidth()/2, Main.HEIGHT/2 - 160);

        if(score > Main.pref.getHighScore()) {
            Main.pref.setHighScore(score);
            currentScore.update("NEW HIGH SCORE: " + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2, 1f);
            currentScore.setTextHide(false);
        }else{
            currentScore.setTextHide(false);
            highScore.setTextHide(false);
        }

        sendScore();

    }

    private void sendScore(){
        try {
            String name = URLEncoder.encode("\""+Main.pref.getName()+"\"", "UTF8");
            String gold = URLEncoder.encode(""+Main.pref.getGold(),"UTF8");
            String score = URLEncoder.encode(""+Main.pref.getHighScore(),"UTF8");
            URL url = new URL("http://localhost:8080/kobe/NewEntry?name=" + name + "&gold=" + gold + "&score=" + score);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String USER_AGENT = "Mozilla/5.0";
            conn.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error: Connection failed.");
        }
    }

    public void handleInput(){
        if(Gdx.input.isTouched() && !exitTransition && !enterTransition){

            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            if(menuRect.contains(mouse.x, mouse.y)) {
                Main.sounds.playSound("select");
                fState = true;
                exitTransition = true;
                exitTransitionVal = 0f;
            }
            else if (playRect.contains(mouse.x, mouse.y)) {
                Main.sounds.playSound("select");
                fState = false;
                exitTransition = true;
                exitTransitionVal = 0f;
            }
        }
    }

    @Override
    protected void onExitTransition(float dt){
        if(exitTransition){
            exitTransitionVal += 1f * dt;
            if(exitTransitionVal >= 1f){
                if(!fState)
                    gsm.set(new PlayState(gsm, 5));
                else if(fState)
                    gsm.set(new MenuState(gsm));
            }
        }
    }

    public void update(float dt){
        handleInput();
        onEnterTransition(dt);
        onExitTransition(dt);
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();

        currentScore.render(sb);
        highScore.render(sb);

        sb.draw(gameOver,Main.WIDTH/2 - gameOver.getRegionWidth()/2, Main.HEIGHT/2 + 100);
        sb.draw(play,playRect.getX(),playRect.getY());
        sb.draw(menu,menuRect.getX(),menuRect.getY());

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        super.shapeRender(sr);
    }
}
