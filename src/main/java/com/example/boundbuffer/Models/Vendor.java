package com.example.boundbuffer.Models;
import com.example.boundbuffer.Models.*;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Vendor extends BoundBuffer{
    private int id;
    private String nameofstore;
    private String imagepath;
    private String des;

    private int Nitem=0;
    private ArrayList<Item> itemsforsell;


    public Vendor(int id,String name,String imagepath,String des){
         this.id =id;
         this.nameofstore = name;
         this.imagepath = imagepath;
         this.des=des;
         itemsforsell = new ArrayList<>();

    }
    public int additem(int id,String name, String type, double price, String category, String imagepath,String des){
        Item I = new Item(name,type,price,category,imagepath,des);
        I.onwer = Vendor.this;
        itemsforsell.add(I);
        Nitem++;
        BoundBuffer.allitems.add(I);
        return 1;
    }
    public int deleteitem(Item I){
        if(Nitem ==0 || I==null || itemsforsell == null)return 0;
        itemsforsell.remove(I);
        Nitem--;
       return checkfortrue();
    }
    public int checkfortrue(){
        if(itemsforsell.size() == Nitem)
            return 1;
        return 0;
    }


}
