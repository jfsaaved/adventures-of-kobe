package com.mygdx.runrunrun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.runrunrun.Main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by 343076 on 07/08/2015.
 */
public class TextBoxImage extends TextImage {

    private TextureRegion[][] textBox;
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

    public boolean isTextHidden(){
        return text_hidden;
    }

    @Override
    public void render(SpriteBatch sb){

        LinkedList<Integer> row_text_to_box_index = new LinkedList<Integer>();
        LinkedList<Integer> col_text_to_box_index = new LinkedList<Integer>();

        if(textBox_hide == false) {
            int row = 0, col = 0;

            for (int i = 0; i < box_rows; i++) {
                for (int j = 0; j < box_cols; j++) {


                    // Decides whether to draw a border or inside box, inside box are row == 1 and col == 1
                    if (i > 0 && i < box_rows - 1) row = 1;
                    else if (i == box_rows - 1) row = 2;
                    else row = i;

                    if (j > 0 && j < box_cols - 1) col = 1;
                    else if (j == box_cols - 1) col = 2;
                    else col = j;

                    if(row == 1 && col == 1){
                        row_text_to_box_index.add(j);
                        col_text_to_box_index.add(i);
                    }

                    sb.draw(textBox[row][col], x + (j * 45 * scale), y - (i * 45 * scale), 45 * scale, 45 * scale);
                }
            }

        }

        if(this.text_hidden == false) {
            ListIterator<Integer> i = row_text_to_box_index.listIterator();
            ListIterator<Integer> j = row_text_to_box_index.listIterator();

            int text_index = 0;
            int tmp = i.next();
            int tmp2 = j.next();

            while(i.hasNext()){
                while(j.hasNext()){
                    if(text_index < text.length()) {
                        char c = text.charAt(text_index);
                        int index = c - 32;

                        int row_text = index / fontSheet[0].length;
                        int col_text = index % fontSheet[0].length;

                        sb.draw(fontSheet[row_text][col_text], x + (tmp2 * 45 * scale), y - (tmp * 45 * scale), 45 * scale, 45 * scale);
                        text_index++;
                    }
                    tmp2 = j.next();
                }
                tmp = i.next();
            }
        }
    }
}
