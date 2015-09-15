package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 343076 on 22/08/2015.
 */
public class Shop extends MoveableObject{

    private String[] dialogue;
    private String[] options;
    private String[] output;

    public Shop(float x, float y, TextureRegion image){
        super(x,y,image, Types.Shop);

        dialogue = new String[15];
        options = new String[15];
        output = new String[15];

        dialogue[0] = "Shop Owner: Hi there!                     " +
                   "Shop Owner: What would you like?          ";// +
                   //"duckie: chill.... i got this              ";

        dialogue[1] = "Shop Owner: Good bye!!                    " ;//+
                      //"Shop Owner: What would you like?          ";// +
                      //"duckie: chill.... i got this              ";

        //"Sleep     $100"
        options[0] = "Sleep$100";
        options[1] = "Bread$100";
        options[2] = "Soup $100";
        options[3] = "Sushi$100";
        options[4] = "Soda $100";
        options[5] = "Soda $100";
        options[6] = "Soda $100";
        options[7] = "Soda $100";
        options[8] = "Soda $100";
        options[9] = "Soda $100";

        output[0] = "" + options[0] + "" + options[1] + "" + options[2] + "" +
                    options[3] + "" + options[4] + "" + options[5] + "" +
                    options[6] + "" + options[7] + "" + options[8] + "" + options[9] ;
    }

    public String getDialogue(int i){
        return dialogue[i];
    }

    public String getOption(int i){return options[i];}

    public String getOptions(int i){return output[i];}

    public void update(float dt){
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
    }


}
