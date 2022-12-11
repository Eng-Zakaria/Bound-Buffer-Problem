package com.example.boundbuffer.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    public int deleteTicket(Ticket I){
        System.out.println("0000000000----------------");
        System.out.println("here deleted");
        System.out.println("---------------------------");
        if(noTickets ==0 || I==null || ticketsForSellByEveryVendor == null || I.getOwner() != Vendor.this)return 0;
        System.out.println("here deleted");
        System.out.println("----------------");
        System.out.println("-------------------------");
        this.TotalnoTicketsIcludeQuntity -= I.getQuantity();
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathInfofile() {
        return pathInfofile;
    }

    public void setPathInfofile(String pathInfofile) {
        this.pathInfofile = pathInfofile;
    }


    public void setTicketsForSellByEveryVendor(ArrayList<Ticket> ticketsForSellByEveryVendor) {

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
        this.TotalnoTicketsIcludeQuntity -= quantity;
        System.out.println(      "vendors : " + this.getTicketsForSellByEveryVendor().get(  this.getTicketsForSellByEveryVendor().indexOf(t)).getQuantity());
        //this.getTicketsForSellByEveryVendor().get(  this.getTicketsForSellByEveryVendor().indexOf(t) ).setQuantity(t.getQuantity() - quantity);
        System.out.println(       "tickets : " +tickets.get( tickets.indexOf(t) ).getQuantity());
        //tickets.get( tickets.indexOf(t) ).setQuantity(t.getQuantity() - quantity);


        return 1;
    }




}

