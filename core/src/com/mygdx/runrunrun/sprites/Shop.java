package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 343076 on 22/08/2015.
 */
public class Shop extends MoveableObject{

    private float scale;
    private String[] dialogue;
    private Rectangle rect;

    public Shop(float x, float y, TextureRegion image){
        super(x,y,image);

        rect = new Rectangle(this.position.x, this.position.y, this.width, this.height);

        dialogue = new String[15];

        dialogue[0] = "Shop Owner: Hi there!                     " +
                   "Shop Owner: What would you like?          ";// +
                   //"duckie: chill.... i got this              ";

        dialogue[1] = "Shop Owner: Good bye!!                    " ;//+
                      //"Shop Owner: What would you like?          ";// +
                      //"duckie: chill.... i got this              ";
    }

    @Override
    public boolean contains(Vector2 vector){
        return rect.contains(vector);
    }

    public String getDialogue(int i){
        return dialogue[i];
    }

    public void update(float dt){
        super.update(dt);
    }

    public void render(SpriteBatch sb){
        sb.draw(image,position.x,position.y, width, height);
    }

}
