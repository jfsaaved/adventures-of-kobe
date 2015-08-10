package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 04/08/2015.
 */
public class TextImage extends Box{

    private TextureRegion[][] fontSheet;

    private String text;
    private float scale;
    private int size;

    private boolean hidden;

    public TextImage(String text, float x, float y, float scale){
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;

        size = 45;
        width = size * text.length();
        height = size;
        hidden = true;

        TextureRegion sheet = Main.resource.getAtlas("assets").findRegion("fontsheet");
        int numCols = sheet.getRegionWidth() / size;
        int numRows = sheet.getRegionHeight() / size;

        fontSheet = new TextureRegion[numRows][numCols];

        for(int rows = 0; rows < numRows; rows++){
            for(int cols = 0; cols < numCols; cols++)
                fontSheet[rows][cols] = new TextureRegion(sheet,size*cols, size*rows, size, size);
        }

    }

    public void setHide(boolean b){
        hidden = b;
    }

    public void update(String text, float x, float y, float scale){
        this.text = text;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    public void update(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch sb){
        if(hidden == false){
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
