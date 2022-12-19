package com.example.boundbuffer.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Semaphore;

public class Vendor extends BoundBuffer implements Runnable{
    private int id;
    private String nameOfStore;
    private String imagepath;
    private String username;
    private String password;
    private String des;
    private int noTickets;
    private int TotalnoTicketsIcludeQuntity;
    private String pathInfofile;

    private double balanceVendor=0.0;
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
        ticketsForSellByEveryVendor = new ArrayList<>();
        allPathForticketsCreatedByMeInCustomerView = new ArrayList<String>();


        int check =0;
        if(checkAccount(username,"deleted").equals("1"))
            check = 1;

        if(newData !=0 ) {
            this.id = BoundBuffer.NoVendors;
            BoundBuffer.NoVendors++;
            setId(this.id,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idVEN.txt");

            String[] nameofattribues = {"imagePath","id", "nameOfStore", "userName","password","Description","TicketsForSell","TicketsIncludeQuantity","pathFolderTicketsCreatedByMeToSave","balanceVendor"}; //
            this.pathFolderTicketCreatedByVendor =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\ticketsVendors\\", String.valueOf(this.id)+" ["+username+"] "+"Tickets");
            String[] values = {imagepath,String.valueOf(this.id), nameOfStore, username, password,des,String.valueOf(noTickets),String.valueOf(TotalnoTicketsIcludeQuntity),this.pathFolderTicketCreatedByVendor,String.valueOf(balanceVendor)};
            this.pathInfofile = Creetefiletxt(this.id+" ["+username+"] ","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\vendors\\");
            WriteData(this.pathInfofile,nameofattribues,values,10);
            setNoTickets(0,0);
            setTotalnoTicketsIcludeQuntity(0,0);


            if(check == 1) {
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "deleted", "0");
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "pathInfo", this.pathInfofile);
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt", "account", username, "password", this.password);
            }else {
                addLoginData("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\vendorsLoginData.txt",username,password,pathInfofile);
            }
        }

    }
    private Semaphore semAddticket = BoundBuffer.s2;


    // ReentrantLock rel; -> anther solution for boundbuffer issue

    // String PathFolderOwner,String pathOwner,String name, String type, int quantity,double price, String imagePath, String description,String startTime ,String EndTime
    public int addTicket(String name, String type,int quantity ,double price, String imagepath,String description,String startTime,String EndTime){

        try {
            semAddticket.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        if(price < 0 || quantity <= 0) {
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
                System.out.println("invalid End time are you stupid to enter start time after end time");
                return 0;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        System.out.println("----------thread is currently execute["+Thread.currentThread().getName()+"]--------in this piece of code ");

        System.out.println("the state of this thread :"+Thread.currentThread().getState());
        System.out.println("Number of threads running now in add ticket method in vendors : "+vendorsAddingTicketsTheards.activeCount());

        System.out.println("------------------End of thread-"+Thread.currentThread().getName()+"----------------------------------");



        Ticket I = new Ticket(this.pathFolderTicketCreatedByVendor,pathInfofile,name,type,quantity,price,imagepath, description, startTime, EndTime,1);
        I.setOwner( Vendor.this);

        setNoTickets(noTickets+1,0);
        setTotalnoTicketsIcludeQuntity(TotalnoTicketsIcludeQuntity+quantity,0);

        ticketsForSellByEveryVendor.add(I);
        this.allPathForticketsCreatedByMeInCustomerView.add(I.getPathInViewCT());

        BoundBuffer.tickets.add(I);
        BoundBuffer.TotalNoTickets++;


        setId(TotalNoTickets,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idTicket.txt");
         semAddticket.release();
        return 1;
    }

    @Override
    public void run() {

       for (int i=0; i< 5;i++)
        addTicket("tiket "+i+" "+this.username,"gold",120,10.0,".png","Event description",now(),"20-12-2022");

    }
    public Thread addingTheard(){
        Thread t =new Thread(BoundBuffer.vendorsAddingTicketsTheards,this);
        t.start();
        return t;
    }
    public Thread addingTheard(String name1, String type1,int quantity1 ,double price1, String imagepath1,String description1,String startTime1,String EndTime1){
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                addTicket(name1, type1,quantity1 ,price1,imagepath1,description1,startTime1,EndTime1);
            }
        });
        t.setName("thread adding request for vendor id:"+getId());
        t.start();
        return t;
    }





    public int deleteTicket(Ticket I){
        System.out.println("here deleted");

        System.out.println(I.getOwner() + " "+ Vendor.this);
        if(noTickets ==0 || I==null || ticketsForSellByEveryVendor == null || I.getOwner() != Vendor.this)return 0;
        System.out.println("here deleted");


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

    public double getBalanceVendor() {
        return balanceVendor;
    }

    public void setBalanceVendor(double balanceVendor, int load) {
        this.balanceVendor = balanceVendor;
        if(load == 0)
            editValueLine(this.pathInfofile,"balanceVendor",String.valueOf(balanceVendor),9);

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

        if(index >= 0)
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


    public int soldTicketNotify(Ticket t,int quantity){
        if(noTickets ==0 || ticketsForSellByEveryVendor == null || t.getOwner() != Vendor.this||!this.ticketsForSellByEveryVendor.contains(t))return 0;

        System.out.println("--------------------\n-------------");
        System.out.println("tickets quantity before selling: "+t.getQuantity() + "Quantity required: "+quantity);
        setBalanceVendor(this.balanceVendor + (t.getPrice() * quantity),0);
        System.out.println("your balance had became"+this.balanceVendor);
        System.out.println( "vendors : " + this.getTicketsForSellByEveryVendor().get(  this.getTicketsForSellByEveryVendor().indexOf(t) ).getQuantity());
        System.out.println( "tickets : " +tickets.get( tickets.indexOf(t)  ).getQuantity());
        System.out.println("there are ticket(s) have been sold cheerz!!!"+"you have earned amount:"+t.getPrice() * quantity +"$");

        return 1;
    }
    public int logout(){
        vendors.remove(Vendor.this);
        if(vendors.indexOf(Vendor.this) > 0 ) return 0;

        return logout(this,this.username,this.password);

    }




}

