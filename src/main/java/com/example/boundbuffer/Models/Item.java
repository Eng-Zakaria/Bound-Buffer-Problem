package com.example.boundbuffer.Models;

public class Item extends BoundBuffer{
    private int id;
    private String name;
    private String type;
    private double price;
    private String category;
    private String imagepath;
    private String des;
    public Vendor onwer;
    private int exist=1;
    public Item( String name, String type, double price, String category, String imagepath,String des) {
        this.id = BoundBuffer.Niteminsystem;
        BoundBuffer.Niteminsystem++;
        this.name = name;
        this.type = type;
        this.price = price;
        this.category = category;
        this.des=des;
        this.imagepath = imagepath;
    }

    public int id(){

        return id;
    }
    /*
    * sold return 1 if and only if this item is aleardy in our store and there is no one has bughit it
    *             ( important to know)
    *      in case fun return 0 that's means this item is shown in our GUI as for sell  (not sold)
    *      but this item aleardy bought form someone (sold)
    *
    * */
    public int sold(){
        if(exist ==1){
            exist =0;
            return 1;
        }else {

            return 0;

        }



    }



}
