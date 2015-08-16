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

    private LinkedList<Integer> row_text_to_box_index;
    private LinkedList<Integer> col_text_to_box_index;
    private ListIterator<Integer> row_list_iterator;
    private ListIterator<Integer> col_list_iterator;
    private int text_index;
    private int row_iterator_val;
    private int col_iterator_val;
    private float text_delay;


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

        row_text_to_box_index = new LinkedList<Integer>();
        col_text_to_box_index = new LinkedList<Integer>();

        int row = 0, col = 0;
        for (int i = 0; i < box_rows; i++) {
            for (int j = 0; j < box_cols; j++) {
                if (i > 0 && i < box_rows - 1) row = 1;
                else if (i == box_rows - 1) row = 2;
                else row = i;

                if (j > 0 && j < box_cols - 1) col = 1;
                else if (j == box_cols - 1) col = 2;
                else col = j;

                if(row == 1 && col == 1){
                    row_text_to_box_index.add(i);
                    col_text_to_box_index.add(j);
                }
            }
        }

    }

    @Override
    public void setTextHide(boolean b){
        text_hidden = b;
        if(text_hidden == true){
            text_index = 0;
        }
        else{
            text_index = 0;
            text_delay = 10f;
        }
    }

    public boolean isTextHidden(){
        return text_hidden;
    }

    public void renderBox(SpriteBatch sb){
        if(textBox_hide == false) {
            int row = 0, col = 0;
            for (int i = 0; i < box_rows; i++) {
                for (int j = 0; j < box_cols; j++) {

                    if (i > 0 && i < box_rows - 1) row = 1;
                    else if (i == box_rows - 1) row = 2;
                    else row = i;

                    if (j > 0 && j < box_cols - 1) col = 1;
                    else if (j == box_cols - 1) col = 2;
                    else col = j;

                    sb.draw(textBox[row][col], x + (j * 45 * scale), y - (i * 45 * scale), 45 * scale, 45 * scale);

                }
            }
        }
    }

    public void renderText(SpriteBatch sb){

        row_list_iterator = row_text_to_box_index.listIterator();
        col_list_iterator = col_text_to_box_index.listIterator();
        row_iterator_val = row_list_iterator.next();
        col_iterator_val = col_list_iterator.next();

        if(this.text_hidden == false) {

            for(int i = 0; i < text_index; i++) {
                char c = text.charAt(i);
                int index = c - 32;

                int row_text = index / fontSheet[0].length;
                int col_text = index % fontSheet[0].length;

                sb.draw(fontSheet[row_text][col_text], x + (col_iterator_val * 45 * scale), y - (row_iterator_val * 45 * scale), 45 * scale, 45 * scale);

                if (col_list_iterator.hasNext())
                    col_iterator_val = col_list_iterator.next();
                if (row_list_iterator.hasNext())
                    row_iterator_val = row_list_iterator.next();
            }
        }

        if(text_index < text.length()){
            if(text_delay > 0){
                text_delay--;
            }else{
                text_index++;
                text_delay = 10f;
            }
        }

    }
}
