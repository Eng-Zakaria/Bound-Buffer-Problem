package com.example.boundbuffer.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Vendor extends BoundBuffer{
    private int id;
    private String nameOfStore;
    private String imagepath;
    private String des;
    private int noTickets=0;
    private int TotalnoTicketsIcludeQuntity=0;

    private ArrayList<Ticket> ticketsForSellByEveryVendor =null;

    public Vendor(String name,String imagepath,String des) {
        super();
        this.id = BoundBuffer.NoVendors;
        this.nameOfStore = name;
        this.imagepath = imagepath;
        this.des = des;
        ticketsForSellByEveryVendor = new ArrayList<>();
        BoundBuffer.NoVendors++;
        BoundBuffer.vendors.add(Vendor.this);
    }
    public int addTicket(String name, String type,int qu ,double price, String imagepath,String des,String EndTime){
        Ticket I = new Ticket(name,type,qu,price,imagepath,des,EndTime);
        I.owner = Vendor.this;
        ticketsForSellByEveryVendor.add(noTickets,I);
        I.setIndexInVendor(noTickets);
        this.noTickets++;
        this.TotalnoTicketsIcludeQuntity+=qu;
        BoundBuffer.tickets.add(BoundBuffer.TotalNoTickets,I);
        I.setIndexInCustomer(BoundBuffer.TotalNoTickets);
        BoundBuffer.TotalNoTickets++;
        return 1;
    }
    public int deleteTicket(Ticket I){
        if(noTickets ==0 || I==null || ticketsForSellByEveryVendor == null || I.owner != Vendor.this)return 0;
        this.TotalnoTicketsIcludeQuntity -= I.getQuantity();
        ticketsForSellByEveryVendor.remove(I);
        super.tickets.remove(I);
        noTickets--;
        int tBefore=super.TotalNoTickets--;
        return checkfortrue(tBefore);
    }

    public String getNameOfStore() {
        return nameOfStore;
    }

    public void setNameOfStore(String nameOfStore) {
        this.nameOfStore = nameOfStore;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(int noTickets) {
        this.noTickets = noTickets;
    }

    public int getTotalnoTicketsIcludeQuntity() {
        return TotalnoTicketsIcludeQuntity;
    }

    public void setTotalnoTicketsIcludeQuntity(int totalnoTicketsIcludeQuntity) {
        TotalnoTicketsIcludeQuntity = totalnoTicketsIcludeQuntity;
    }

    public int checkfortrue(int t){
        if(tickets.size() == noTickets && super.TotalNoTickets == t +1)
            return 1;
        return 0;
    }
    /*
    *
    *
    * String name, String type, double price, String imagepath,String des,String EndTime
    * */

    public ArrayList<Ticket> getTicketsForSellByEveryVendor() {
        return ticketsForSellByEveryVendor;
    }

    public int modifyTicket(Ticket t, int filed, String newData){
        if(t.owner != Vendor.this && !(this.ticketsForSellByEveryVendor.contains(t))) return -1;

        if(filed == 1){

            t.setName(newData);

        }else if(filed == 2){
            t.setType(newData);

        }else if(filed ==3){
            t.setPrice(Double.parseDouble(newData));

        }else if(filed ==4){
            t.setImagePath(newData);

        }else if(filed == 5){

            t.setDescription(newData);
        }else if(filed == 6){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date = dateFormat.parse(newData);
                t.setEndTime(  new SimpleDateFormat("yyyy-MM-dd").format(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }else if(filed == 7){
            t.setQuantity(Integer.parseInt(newData));

        }


        this.ticketsForSellByEveryVendor.set(t.getIndexInVendor(),t);
        BoundBuffer.tickets.set(t.getIndexInCustomer(),t);
        return 1;
    }
    public int getTotalNoTicketsIncludeQuantity(){


        return this.TotalnoTicketsIcludeQuntity;
    }

    private int reduceQuantity(Ticket t,int q){
        t.setQuantity(q);
        this.ticketsForSellByEveryVendor.set(t.getIndexInVendor(),t);
        BoundBuffer.tickets.set(t.getIndexInCustomer(),t);
        return 1;

    }

    public int soldTicket(Ticket t,int quantity){
        if(noTickets ==0 || ticketsForSellByEveryVendor == null || t.owner != Vendor.this||!this.ticketsForSellByEveryVendor.contains(t))return 0;
        if(t.getQuantity() == quantity){
            this.deleteTicket(t);
            t.sold();
            return 1;
        }

        this.TotalnoTicketsIcludeQuntity -= quantity;


        return 1;
    }




}

