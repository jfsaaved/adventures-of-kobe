package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 07/08/2015.
 */
public class TextBoxImage extends TextImage {

    private TextureRegion[][] textBox;
    private int box_rows;
    private int box_cols;
    private float scale;

    public TextBoxImage(String text, float x, float y, float scale){
        super(text,x,y,scale);

        int size = 32;
        box_rows = 4;
        box_cols = 8;
        this.scale = scale;

        TextureRegion box_sheet = Main.resource.getAtlas("assets").findRegion("textbox1");
        int numCols = box_sheet.getRegionWidth() / size;
        int numRows = box_sheet.getRegionHeight() / size;

        textBox = new TextureRegion[numRows][numCols];

        for(int rows = 0; rows < numRows; rows++){
            for(int cols = 0; cols < numCols; cols++)
                textBox[rows][cols] = new TextureRegion(box_sheet,size*cols, size*rows, size, size);
        }
    }

    @Override
    public void render(SpriteBatch sb){
        int row = 0, col = 0;

        for (int i = 0; i < box_rows; i++) {
            for(int j = 0; j < box_cols; j++){

                if(i > 0 && i < box_rows - 1){
                    row = 1;
                }
                else if(i == box_rows - 1){
                    row = 2;
                }
                else{
                    row = i;
                }

                if(j > 0 && j < box_cols - 1){
                    col = 1;
                }
                else if(j == box_cols - 1){
                    col = 2;
                }
                else{
                    col = j;
                }

                sb.draw(textBox[row][col],x + (j * 32 * scale),y - (i * 32 * scale), 32*scale, 32*scale);
            }
        }
    }

}
