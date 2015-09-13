package com.mygdx.runrunrun.ui;

/**
 * Created by 343076 on 13/09/2015.
 */
public class ItemButton extends Box{

    private Item item;

    public ItemButton(float x, float y, float width, float height, Item item){

        super(x,y,width,height);
        this.item = item;
    }

    public void itemTest(){
        if(this.item.equals(Item.SUSHI)){
            System.out.println("You pressed Sushi!");
        }
    }


}
