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

        dialogue = new String[6];
        output = new String[2];
        item = new Item[2];
        options = new String[2];
        itemSize = item.length;

        dialogue[0] = "Shop Owner: Hi there!                     " +
                      "Shop Owner: What would you like?          ";// +
                   //"duckie: chill.... i got this              ";

        dialogue[1] = "Shop Owner: Good bye!!                    " ;//+
                      //"Shop Owner: What would you like?          ";// +
                      //"duckie: chill.... i got this              ";

        dialogue[2] = "Shop Owner: Thank you!                    " +
                      "Shop Owner: Anything else?                " ;

        dialogue[3] = "Shop Owner: Not enough gold!!!            " +
                      "Shop Owner: Anything else?                " ;

        dialogue[4] = "Shop Owner: Whoa! You're flying!!         " ;

        dialogue[5] = "Shop Owner: You have full health!         " +
                      "Shop Owner: Anything else?                " ;

        // Items
        item[0] = Item.REST;
        item[1] = Item.CHILL;

        //"Sleep     $100"
        options[0] = "Rest   $5";
        options[1] = "Chill $50";

        output[0] =  "" + options[0] + "" + options[1];
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
