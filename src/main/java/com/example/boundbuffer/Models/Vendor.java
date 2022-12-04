package com.example.boundbuffer.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Vendor extends BoundBuffer{
    private int id;
    private String nameOfStore;
    private String imagepath;
    private String username;
    private String password;
    private String des;
    private int noTickets;
    private int TotalnoTicketsIcludeQuntity=0;

    private String pathInfofile;
    private ArrayList<Ticket> ticketsForSellByEveryVendor =null;
    private String pathFolderTicketCreatedByVendor;
    private String allPathForticketsCreatedByMe[];


    public Vendor(String nameOfStore,String username,String password,String imagepath,String des) {
        super();
        String[] nameofattribues = {"imagePath","id", "nameOfStore", "userName","password","Description","TicketsForSell","TicketsIncludeQuantity","pathFolderTicketsCreatedByMeToSave"}; //
        this.id = BoundBuffer.NoVendors;
        BoundBuffer.NoVendors++;
        setId(this.id,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idVEN.txt");
        this.nameOfStore = nameOfStore;
        this.username = username;
        this.password = password;
        this.imagepath = imagepath;
        this.des = des;
        this.pathFolderTicketCreatedByVendor =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\ticketsVendors\\", String.valueOf(this.id)+" ["+username+"] "+"Tickets");
        this.noTickets = NumberOftextFilesinFolder(pathFolderTicketCreatedByVendor);
        String[] values = {imagepath,String.valueOf(this.id), nameOfStore, username, password,des,String.valueOf(noTickets),String.valueOf(TotalnoTicketsIcludeQuntity),this.pathFolderTicketCreatedByVendor};
        ticketsForSellByEveryVendor = new ArrayList<>();
        BoundBuffer.vendors.add(Vendor.this);
        this.pathInfofile = Creetefiletxt(this.id+" ["+username+"] ","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\vendors\\");
        WriteData(this.pathInfofile,nameofattribues,values,9);
    }
    // String PathFolderOwner,String pathOwner,String name, String type, int quantity,double price, String imagePath, String description,String startTime ,String EndTime
    public int addTicket(String name, String type,int quantity ,double price, String imagepath,String description,String startTime,String EndTime){

        Ticket I = new Ticket(this.pathFolderTicketCreatedByVendor,pathInfofile,name,type,quantity,price,imagepath, description, startTime, EndTime);
        I.owner = Vendor.this;
        ticketsForSellByEveryVendor.add(I);
        this.noTickets++;
        this.TotalnoTicketsIcludeQuntity+=quantity;
        BoundBuffer.tickets.add(I);

        BoundBuffer.TotalNoTickets++;
        setId(I.getId(),"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idTicket.txt");
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
                t.setEndTime(  new SimpleDateFormat("dd-MM-yyyy").format(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }else if(filed == 7){
            t.setQuantity(Integer.parseInt(newData));

        }


        this.ticketsForSellByEveryVendor.add(t);
        BoundBuffer.tickets.add(t);
        return 1;
    }
    public int getTotalNoTicketsIncludeQuantity(){


        return this.TotalnoTicketsIcludeQuntity;
    }

    private int reduceQuantity(Ticket t,int q){
        t.setQuantity(q);
        this.ticketsForSellByEveryVendor.add(t);
        BoundBuffer.tickets.add(t);
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

