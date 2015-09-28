package com.mygdx.runrunrun.ui;

import com.mygdx.runrunrun.sprites.Hero;

/**
 * Created by 343076 on 13/09/2015.
 */
public class ItemButton extends Box{

    private Item item;
    private boolean hide;

    public ItemButton(float x, float y, float width, float height, Item item){

        super(x,y,width,height);
        this.item = item;
        hide = true;
    }

    public void setHide(boolean b){
        this.hide = b;
    }

    public void interact(Hero hero){
        if(!hide) {
            switch (this.item){
                case SLEEP:
                    if(hero.getCoins() >= 5) {
                        hero.restoreHealth();
                        hero.subtractCoins(5);
                    }
                    break;
                case BREAD:
                    hero.addBonuses(0,15);
                    break;
                case SOUP:
                    hero.addBonuses(15,0);
                    break;
            }
        }
    }


}
