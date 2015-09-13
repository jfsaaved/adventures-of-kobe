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

    public Shop(float x, float y, TextureRegion image){
        super(x,y,image, Types.Shop);

        dialogue = new String[15];
        options = new String[15];

        dialogue[0] = "Shop Owner: Hi there!                     " +
                   "Shop Owner: What would you like?          ";// +
                   //"duckie: chill.... i got this              ";

        dialogue[1] = "Shop Owner: Good bye!!                    " ;//+
                      //"Shop Owner: What would you like?          ";// +
                      //"duckie: chill.... i got this              ";

        //"Shop Owner: Hi there"
        options[0] = "Sleep     :     $100" +
                     "Eat       :     $100";
    }

    public String getDialogue(int i){
        return dialogue[i];
    }

    public String getOptions(int i){return options[i];}

    public void update(float dt){
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
    }


}
