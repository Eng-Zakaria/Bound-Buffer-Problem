package com.example.boundbuffer.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Vendor extends BoundBuffer implements Runnable{
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
    private ArrayList<String> allPathForticketsCreatedByMeInCustomerView = null;


    public Vendor(String nameOfStore,String username,String password,String imagepath,String des,int newData) {
        super();

        this.nameOfStore = nameOfStore;
        this.username = username;
        this.password = password;
        this.imagepath = imagepath;
        this.des = des;
        this.noTickets = 0;
        ticketsForSellByEveryVendor = new ArrayList<>();
        allPathForticketsCreatedByMeInCustomerView = new ArrayList<String>();


        int check =0;
        if(checkAccount(username,"deleted").equals("1"))
            check = 1;

        if(newData !=0 ) {
            this.id = BoundBuffer.NoVendors;
            BoundBuffer.NoVendors++;
            setId(this.id,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idVEN.txt");

            String[] nameofattribues = {"imagePath","id", "nameOfStore", "userName","password","Description","TicketsForSell","TicketsIncludeQuantity","pathFolderTicketsCreatedByMeToSave"}; //
            this.pathFolderTicketCreatedByVendor =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\ticketsVendors\\", String.valueOf(this.id)+" ["+username+"] "+"Tickets");
            String[] values = {imagepath,String.valueOf(this.id), nameOfStore, username, password,des,String.valueOf(noTickets),String.valueOf(TotalnoTicketsIcludeQuntity),this.pathFolderTicketCreatedByVendor};
            this.pathInfofile = Creetefiletxt(this.id+" ["+username+"] ","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\vendors\\");
            WriteData(this.pathInfofile,nameofattribues,values,9);

            if(check == 1) {
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "deleted", "0");
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "pathInfo", this.pathInfofile);
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "password", this.password);
            }else {
                addLoginData("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt",username,password,pathInfofile);
            }
        }

    }
    // String PathFolderOwner,String pathOwner,String name, String type, int quantity,double price, String imagePath, String description,String startTime ,String EndTime
    public int addTicket(String name, String type,int quantity ,double price, String imagepath,String description,String startTime,String EndTime){
         if(price < 0 || quantity == 0) {
             System.out.println("invalid data");
             return 0;
         }
        //Dates as input
        if(BoundBuffer.isValidFormat("dd-mm-yy",startTime, Locale.ENGLISH)) {
            System.out.println("invalid date start time");
            return 0;
        }

        if(BoundBuffer.isValidFormat("dd-mm-yy",EndTime, Locale.ENGLISH)) {
            System.out.println("invalid date End time");

            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(sdf.parse(EndTime).before(sdf.parse(startTime))){
                return 0;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        Ticket I = new Ticket(this.pathFolderTicketCreatedByVendor,pathInfofile,name,type,quantity,price,imagepath, description, startTime, EndTime,1);
        I.setOwner( Vendor.this);

        ticketsForSellByEveryVendor.add(I);

        this.noTickets++;

        this.TotalnoTicketsIcludeQuntity+=quantity;

        this.allPathForticketsCreatedByMeInCustomerView.add(I.getPathInViewCT());

        BoundBuffer.tickets.add(I);
        BoundBuffer.TotalNoTickets++;


        setId(I.getId(),"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idTicket.txt");

        return 1;
    }

    @Override
    public void run() {

       for (int i=0; i< 5;i++)
        addTicket("tiket "+i+" "+this.username,"gold",120,10.0,".png","Event description","17-12-2022","20-12-2022");


    }


    public int deleteTicket(Ticket I){
        System.out.println("0000000000----------------");
        System.out.println("here deleted");
        System.out.println("---------------------------");
        System.out.println(I.getOwner() + " "+ Vendor.this);
        if(noTickets ==0 || I==null || ticketsForSellByEveryVendor == null || I.getOwner() != Vendor.this)return 0;
        System.out.println("here deleted");

        System.out.println("----------------");
        System.out.println("-------------------------");

        makeFileBeDeleted(I.getPathInFolderOwner(),I.getName());
        makeFileBeDeleted(I.getPathInViewCT(),I.getName());
        setTotalnoTicketsIcludeQuntity(this.TotalnoTicketsIcludeQuntity - I.getQuantity(),0);
        setNoTickets(this.noTickets - 1,0);
        ticketsForSellByEveryVendor.remove(I);
        tickets.remove(I);

        this.noTickets--;

        return 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getAllPathForticketsCreatedByMeInCustomerView() {
        return allPathForticketsCreatedByMeInCustomerView;
    }

    public void setAllPathForticketsCreatedByMeInCustomerView(ArrayList<String> allPathForticketsCreatedByMeInCustomerView ) {
            this.allPathForticketsCreatedByMeInCustomerView = allPathForticketsCreatedByMeInCustomerView;

    }

    public int addPathTicketCT(String pathTicketCreated){
        allPathForticketsCreatedByMeInCustomerView.add(pathTicketCreated);
        return 1;
    }

    public String getPassword() {
        return password;
    }

    public int setPassword(String newPassword) {
        if(newPassword != this.password) {
            System.out.println("Wrong password");
            System.out.println("you have to enter your correct old password");
            return 0;
        }

        this.password = newPassword;

        editValueLine(this.pathInfofile,"password",newPassword,4);
        return 1;


    }

    public String getPathInfofile() {
        return pathInfofile;
    }

    public void setPathInfofile(String pathInfofile) {
        this.pathInfofile = pathInfofile;
    }


    public void setTicketsForSellByEveryVendor(ArrayList<Ticket> ticketsForSellByEveryVendor) {
        if(ticketsForSellByEveryVendor.size() < 1 || ticketsForSellByEveryVendor == null){
            this.ticketsForSellByEveryVendor = new ArrayList<Ticket>(10);
            return;
        }

        this.ticketsForSellByEveryVendor = ticketsForSellByEveryVendor;

        this.noTickets = ticketsForSellByEveryVendor.size();
        int index = -1;
        System.out.println(noTickets);

        for(int i=0;i<noTickets;i++){
            for (int j=0;j<tickets.size();j++){
                if(ticketsForSellByEveryVendor.get(i).getId() == tickets.get(j).getId() && ticketsForSellByEveryVendor.get(i).hashCode() != tickets.get(j).hashCode()){
                    if(index < 0)
                        index = j;

                    BoundBuffer.tickets.remove(j);
                   // BoundBuffer.tickets.add(j,ticketsForSellByEveryVendor.get(i));
                }
            }
        }
        tickets.addAll(index,this.ticketsForSellByEveryVendor);


    }

    public String getPathFolderTicketCreatedByVendor() {
        return pathFolderTicketCreatedByVendor;
    }

    public void setPathFolderTicketCreatedByVendor(String pathFolderTicketCreatedByVendor) {
        this.pathFolderTicketCreatedByVendor = pathFolderTicketCreatedByVendor;
    }



    public String getNameOfStore() {
        return nameOfStore;
    }

    public void setNameOfStore(String nameOfStore) {
        this.nameOfStore = nameOfStore;
        editValueLine(this.pathInfofile,"nameOfStore",nameOfStore,3);

    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
        editValueLine(this.pathInfofile,"imagePath",imagepath,0);
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
        editValueLine(this.pathInfofile,"Description",des,5);
    }

    public int getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(int noTickets , int load) {
        this.noTickets = noTickets;
        if(load == 0)
        editValueLine(this.pathInfofile,"TicketsForSell",String.valueOf( super.NumberOfFilesinFolder(this.pathFolderTicketCreatedByVendor,".txt")),6);
    }

    public int getTotalnoTicketsIcludeQuntity() {
        return TotalnoTicketsIcludeQuntity;
    }

    public void setTotalnoTicketsIcludeQuntity(int totalnoTicketsIcludeQuntity , int  load) {
        TotalnoTicketsIcludeQuntity = totalnoTicketsIcludeQuntity;
        if(load == 0)
            editValueLine(this.pathInfofile,"TicketsIncludeQuantity",String.valueOf(totalnoTicketsIcludeQuntity),7);
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
        if(t.getOwner() != Vendor.this && !(this.ticketsForSellByEveryVendor.contains(t))) return -1;

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



        return 1;
    }



    public int getTotalNoTicketsIncludeQuantity(){


        return this.TotalnoTicketsIcludeQuntity;
    }

    private int reduceQuantity(Ticket t,int q){
        t.setQuantity(q);
        return 1;

    }

    public int soldTicket(Ticket t,int quantity){
        if(noTickets ==0 || ticketsForSellByEveryVendor == null || t.getOwner() != Vendor.this||!this.ticketsForSellByEveryVendor.contains(t))return 0;

        System.out.println("--------------------\n-------------");
        System.out.println(t.getQuantity() + " "+quantity);
        if(t.getQuantity() == quantity){
            System.out.println("here in === ");
            t.sold();
        }
        System.out.println("------here-----------");
        System.out.println("-------here-----------");
        t.setQuantity(t.getQuantity() - quantity);
        setTotalnoTicketsIcludeQuntity(this.TotalnoTicketsIcludeQuntity - quantity,0);
        System.out.println(      "vendors : " + this.getTicketsForSellByEveryVendor().get(  this.getTicketsForSellByEveryVendor().indexOf(t)).getQuantity());
        //this.getTicketsForSellByEveryVendor().get(  this.getTicketsForSellByEveryVendor().indexOf(t) ).setQuantity(t.getQuantity() - quantity);
        System.out.println(       "tickets : " +tickets.get( tickets.indexOf(t) ).getQuantity());
        //tickets.get( tickets.indexOf(t) ).setQuantity(t.getQuantity() - quantity);


        return 1;
    }
    public int logout(){
        vendors.remove(Vendor.this);
        if(vendors.indexOf(Vendor.this) > 0 ) return 0;

        return logout(this,this.username,this.password);

    }




}

