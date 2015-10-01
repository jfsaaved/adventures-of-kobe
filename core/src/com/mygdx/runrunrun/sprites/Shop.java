package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.ui.Item;

/**
 * Created by 343076 on 22/08/2015.
 */
public class Shop extends MoveableObject{

    private String[] dialogue;
    private Item[] item;
    private String[] options;
    private String[] output;
    private int itemSize;

    public Shop(float x, float y, TextureRegion image){
        super(x,y,image, Types.Shop);

        dialogue = new String[3];
        output = new String[3];
        item = new Item[3];
        options = new String[3];
        itemSize = item.length;

        dialogue[0] = "Shop Owner: Hi there!                     " +
                   "Shop Owner: What would you like?          ";// +
                   //"duckie: chill.... i got this              ";

        dialogue[1] = "Shop Owner: Good bye!!                    " ;//+
                      //"Shop Owner: What would you like?          ";// +
                      //"duckie: chill.... i got this              ";

        // Items
        item[0] = Item.SLEEP;
        item[1] = Item.BREAD;
        item[2] = Item.SOUP;

        //"Sleep     $100"
        options[0] = "Sleep  $5";
        options[1] = "Eat   $20";
        options[2] = "Chill $50";

        output[0] =  "" + options[0] + "" + options[1] + "" + options[2];
        /*output[0] = "" + options[0] + "" + options[1] + "" + options[2] + "" +
                    options[3] + "" + options[4] + "" + options[5] + "" +
                    options[6] + "" + options[7] + "" + options[8] + "" + options[9] ;*/
    }

    public String getDialogue(int i){
        return dialogue[i];
    }

    public String getOptions(int i){return output[i];}

    public Item getItem(int i){
        return item[i];
    }

    public int getItemSize(){
        return itemSize;
    }

    public void update(float dt){
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
    }


}
