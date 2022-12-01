package com.example.boundbuffer.Models;

public class Ticket extends BoundBuffer{
    private int id;
    private String name;
    private String type;
    private double price;
    private String imagePath;
    private String description;

    public Vendor owner;
    private Boolean available;
    public Ticket(String name, String type, double price, String imagePath, String description) {
        this.id = BoundBuffer.TotalNoTickets;
        BoundBuffer.TotalNoTickets++;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.available = true;
    }

    public int getId(){

        return id;
    }
    /*
    * sold return 1 if and only if this item is aleardy in our store and there is no one has bughit it
    *             ( important to know)
    *      in case fun return 0 that's means this item is shown in our GUI as for sell  (not sold)
    *      but this item aleardy bought form someone (sold)
    *
    * */
    public Boolean sold(){
        if(available ==true){
            available =false;
            return true;
        }else {

            return false;

        }



    }



}
