package com.mygdx.runrunrun.ui;

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

    public void itemTest(){
        if(!hide) {
            if (this.item.equals(Item.SUSHI)) {
                System.out.println("You pressed Sushi!");
            }
        }
    }


}
