package com.example.boundbuffer.Models;

public class Cart {
    private int Nitem=0;
    private Item items[];
    /* customers can add 10 items at MAX in cart
            important to know if function of additem return 1 means this function had succuessful to add this item
     */
    public Cart(){
        items = new Item[10];
    }
    public int additem(Item I){
        items[Nitem] = I;
        Nitem++;
        return 1;
    }
    public int removeitem(Item I){
        if(Nitem ==0 || I==null || items == null)return 0;
          Item[] itemtemp = new Item[items.length];
          int index=0;
        for(int i=0;i<Nitem;i++){
            if(items[i].id() != I.id()){
                itemtemp[index] = items[i];
                index++;
            }

        }
        itemtemp[items.length-1] = null;
        items = itemtemp;
        Nitem--;
        return 1;
    }



}
