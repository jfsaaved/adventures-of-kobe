package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 07/08/2015.
 */
public class TextBoxImage extends TextImage {

    private TextureRegion[][] textBox;
    private int[][] text_to_box_index;
    private int box_rows;
    private int box_cols;
    private boolean textBox_hide;
    private float scale;
    private float text_draw_timer;

    public TextBoxImage(String text, float x, float y, float scale, float cam_width){
        super(text,x,y,scale);

        int size = 45;
        box_rows = 6;
        box_cols = ((int) cam_width) / 9;
        this.scale = scale;
        textBox_hide = false;
        this.text_hidden = false;

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
    public void setTextHide(boolean b){
        text_hidden = b;
    }

    @Override
    public void render(SpriteBatch sb){

        if(textBox_hide == false) {
            int row = 0, col = 0;
            int text_index = 0;

            float text_box_x = 0, text_box_y = 0;

            for (int i = 0; i < box_rows; i++) {
                for (int j = 0; j < box_cols; j++) {

                    if (i > 0 && i < box_rows - 1) row = 1;
                    else if (i == box_rows - 1) row = 2;
                    else row = i;

                    if (j > 0 && j < box_cols - 1) col = 1;
                    else if (j == box_cols - 1) col = 2;
                    else col = j;

                    sb.draw(textBox[row][col], x + (j * 45 * scale), y - (i * 45 * scale), 45 * scale, 45 * scale);

                    if (row == 1 && col == 1 && text_index < text.length()) {

                        char c = text.charAt(text_index);
                        int index = c - 32;

                        int row_text = index / fontSheet[0].length;
                        int col_text = index % fontSheet[0].length;

                        if (text_index < text.length() && this.text_hidden == false) {
                            sb.draw(fontSheet[row_text][col_text], x + (j * 45 * scale), y - (i * 45 * scale), 45 * scale, 45 * scale);
                            text_index++;
                        }

                    }
                }
            }
        }

    }

}
