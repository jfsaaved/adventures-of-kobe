package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 04/08/2015.
 */
public class TextImage extends Box{

    protected TextureRegion[][] fontSheet;

    protected String text;
    protected float scale;

    protected boolean text_hidden;

    protected TextImage(String text){
        this.text = text;

        int size = 45;
        width = size * text.length();
        height = size;
        text_hidden = true;

        TextureRegion sheet = Main.resource.getAtlas("assets").findRegion("fontsheet");
        int numCols = sheet.getRegionWidth() / size;
        int numRows = sheet.getRegionHeight() / size;

        fontSheet = new TextureRegion[numRows][numCols];

        for(int rows = 0; rows < numRows; rows++){
            for(int cols = 0; cols < numCols; cols++)
                fontSheet[rows][cols] = new TextureRegion(sheet,size*cols, size*rows, size, size);
        }
    }

    public TextImage(String text, float x, float y, float scale){
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;

        int size = 45;
        width = size * text.length();
        height = size;
        text_hidden = true;

        TextureRegion sheet = Main.resource.getAtlas("assets").findRegion("fontsheet");
        int numCols = sheet.getRegionWidth() / size;
        int numRows = sheet.getRegionHeight() / size;

        fontSheet = new TextureRegion[numRows][numCols];

        for(int rows = 0; rows < numRows; rows++){
            for(int cols = 0; cols < numCols; cols++)
                fontSheet[rows][cols] = new TextureRegion(sheet,size*cols, size*rows, size, size);
        }

        rect = new Rectangle(this.x, this.y, this.width * scale, this.height * scale);

    }

    public void setTextHide(boolean b){
        text_hidden = b;
    }

    public void update(String text, float x, float y, float scale){
        this.text = text;
        this.scale = scale;
        this.x = x;
        this.y = y;

        int size = 45;
        width = size * text.length() * scale;
        height = size * scale;

        rect.setWidth(this.width);
        rect.setHeight(this.height);
        rect.setPosition( this.x - width / 2, y - height / 2);
    }

    public float getX(){
        return this.rect.getX();
    }

    public float getY(){
        return this.rect.getY();
    }

    public float getWidth(){
        return this.rect.getWidth();
    }

    public float getHeight(){
        return this.rect.getHeight();
    }

    public void update(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch sb){
        if(text_hidden == false){
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                int index;
                index = c - 32;

                int row = index / fontSheet[0].length;
                int col = index % fontSheet[0].length;
                sb.draw(fontSheet[row][col], x - width / 2 + (45 * scale) * i, y - height / 2, 45 * scale, 45 * scale);
            }
        }
    }

}
