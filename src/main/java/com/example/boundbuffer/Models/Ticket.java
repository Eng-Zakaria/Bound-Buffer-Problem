package com.example.boundbuffer.Models;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Locale;

public class Ticket extends BoundBuffer{
    private int id;
    private String name;
    private String type;
    private double price;
    private String imagePath;
    private String description;
    private String startTime;
    private String endTime;


    private Vendor owner;
    private Boolean available;
    private int quantity=0;
    private String Qrcode;

    private String pathOwner;
    private String pathInFolderOwner;
    private String pathInViewCT;
    private int issold=0;
    private int deadticket=0;


    public void setQrcode(String qrcode) {
        Qrcode = qrcode;
    }

    public String getPathOwner() {
        return pathOwner;
    }

    public void setPathOwner(String pathOwner) {
        this.pathOwner = pathOwner;
    }

    public String getPathInFolderOwner() {
        return pathInFolderOwner;
    }

    public void setPathInFolderOwner(String pathInFolderOwner) {
        this.pathInFolderOwner = pathInFolderOwner;
    }

    public String getPathInViewCT() {
        return pathInViewCT;
    }

    public void setPathInViewCT(String pathInViewCT) {
        this.pathInViewCT = pathInViewCT;
    }

    public String getStartTime() {
        return startTime;
    }

    public int setStartTime(String startTime) {
        if(!isValidFormat("dd-mm-yy",startTime, Locale.ENGLISH)) return 0;
        this.startTime = startTime;
        editValueLine(this.pathInFolderOwner,"StartTime",startTime,9);
      //  editValueLine(this.pathInViewCT,"StartTime",startTime,9);
        return 1;
    }

    public String getEndTime() {
        return endTime;
    }

    public int setEndTime(String endTime) {
        if(!isValidFormat("dd-mm-yy",endTime, Locale.ENGLISH)) return 0;
        this.endTime = endTime;
        editValueLine(this.pathInFolderOwner,"EndTime",endTime,10);
      //  editValueLine(this.pathInViewCT,"EndTime",endTime,10);
        return 1;
    }

    public String getQrcode() {
        return Qrcode;
    }

    public int getIssold() {
        return issold;
    }

    public int setIssold(int issold1) {

        if(this.issold == issold1) return 0;

         this.issold = issold1;
         return 1;
    }

    public void generateQrcode(String path, String data)
    {
        try {
            QR_Generate.CearteQr(path,data);
            this.Qrcode = path;
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        editValueLine(this.pathInFolderOwner,"quantity",String.valueOf(quantity),7);
      //  editValueLine(this.pathInViewCT,"quantity",String.valueOf(quantity),7);
        int totalQantityBeforeEdit = Integer.valueOf(ReadValueLine(this.pathOwner,"TicketsIncludeQuantity",7));

        if(totalQantityBeforeEdit > 0)
            editValueLine(this.pathOwner,"TicketsIncludeQuantity",String.valueOf(totalQantityBeforeEdit - quantity),7);


    }

    public int getIsDeadticket() {
        return deadticket;
    }

    public void setDeadticket(int deadticket) {
        this.deadticket = deadticket;
    }

    public Ticket(String PathFolderOwner, String pathOwner, String name, String type, int quantity, double price, String imagePath, String description, String startTime , String EndTime,int newTicket) {
        super();

        this.pathOwner = pathOwner;
        this.pathInFolderOwner = PathFolderOwner;
        this.name = name;
        this.type = type;
        this.quantity=quantity;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.available = true;
        this.endTime = EndTime;
        this.startTime = startTime;

        if(newTicket != 1)
            return;


        this.issold = 0;

        this.id = BoundBuffer.TotalNoTickets;
        String[] nameofattribues = {"PathFolderOwner","PathOwner","PathInViewCT","imagePath","id", "name", "type","quantity","Description","StartTime","EndTime","price"}; //
        this.pathInFolderOwner = Creetefiletxt(this.id+" ["+this.name+"] "+"["+this.startTime+"] "+"to "+" ["+this.endTime+"]",pathInFolderOwner+"\\");
        this.pathInViewCT = Creetefiletxt(this.id+" ["+this.name+"] "+"["+this.startTime+"] "+"to "+" ["+this.endTime+"]",rootDataBase+"\\alltickets\\");
        //"PathFolderOwner","PathOwner","PathInViewCT","imagePath","id", "name", "type","quantity","description","StartTime","EndTime","price"
        String[] values = {pathInFolderOwner,this.pathOwner,pathInViewCT,this.imagePath,String.valueOf(this.id), this.name, type,String.valueOf(this.quantity),description,this.startTime,this.endTime,String.valueOf(price)};
        WriteData(this.pathInViewCT,nameofattribues,values,12);
        WriteData(this.pathInFolderOwner,nameofattribues,values,12);
    }

    /*
    another code for endData input
    *LocalDate ld = LocalDate.of( 2026 , 1 , 23 );

     *
    *
     */

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        editValueLine(this.pathInFolderOwner,"name",name,5);
      //  editValueLine(this.pathInViewCT,"name",name,5);

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        editValueLine(this.pathInFolderOwner,"type",type,6);
      //  editValueLine(this.pathInViewCT,"type",type,6);

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        editValueLine(this.pathInFolderOwner,"price",Double.toString(price),11);
      //  editValueLine(this.pathInViewCT,"price",Double.toString(price),11);

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        editValueLine(this.pathInFolderOwner,"imagePath",imagePath,3);
      //  editValueLine(this.pathInViewCT,"imagePath",imagePath,3);

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        editValueLine(this.pathInFolderOwner,"Description",description,8);
       // editValueLine(this.pathInViewCT,"Description",description,8);

    }

    public Vendor getOwner() {
        return owner;
    }

    public void setOwner(Vendor owner) {
        this.owner = owner;
    }

    public Boolean getAvailable() {
        return available;
    }

    public int MakeNoLongerAvailablity(int load) {
        if(available == false) return 0;
            if(load == 0) {


                editValueLine(this.pathInFolderOwner, "Description", "~" + this.description, 8);
              //  editValueLine(this.pathInViewCT, "Description", "~" + this.description, 8);
                editValueLine(this.pathInFolderOwner, "quantity", "0", 7);
                //editValueLine(this.pathInViewCT, "quantity", "0", 7);


                int noTicketsBeforeEdit = Integer.valueOf(ReadValueLine(this.pathOwner,"TicketsForSell",6));
                int totalQantityBeforeEdit = Integer.valueOf(ReadValueLine(this.pathOwner,"TicketsIncludeQuantity",7));
                if(noTicketsBeforeEdit > 0 && totalQantityBeforeEdit > 0) {
                    editValueLine(this.pathOwner, "TicketsForSell", String.valueOf((noTicketsBeforeEdit - 1)), 6);
                    editValueLine(this.pathOwner,"TicketsIncludeQuantity",String.valueOf(totalQantityBeforeEdit - this.quantity),7);
                }

            }
        this.available = false;
        return 1;
    }
    /*
    * compareTwoDates
    * if fun return 1 means Date 1 occurs after Date 2
    *        return 2 means Date 2 occurs after Date 2 or in another word Date 1 occurs before Date 2
    *        return 0 means Both dates are equal
    *        return -1 means d1 or d2 is NOT assigned to a real date or spacific
    * */



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
            setIssold(1);
            MakeNoLongerAvailablity(0);
            return true;
        }else {

            return false;

        }



    }


    public int sellTickets(int quantity){

        System.out.println(this.quantity == 0);
        System.out.println(!this.available);
        System.out.println(quantity > this.quantity);
        System.out.println(issold == 1);
        /*
        if(this.quantity == 0 || !this.available || quantity > this.quantity || this.issold == 1){
            System.out.println("those tickets are not available "+this.name);
            return 0;
        }

        if(this.getIsDeadticket() == 1)
            return -1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(sdf.parse(now()).after(sdf.parse(this.endTime))){
                System.out.println("tickets for this event have dead");
                this.setDeadticket(1);
                return -2;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


         */

        if(quantity == this.quantity)
            sold();
        else
            setQuantity(quantity);


        return 1;
        }




    public int buy(Customer c,int quantity){

        System.out.println("---------------------receipt for ticket: \\\\\" "+this.name+" \\\\\"---------------------------");


        if(c.getBalance() < (this.price * quantity)){
            System.out.println("your balance is not enough to checkout for these tickets: "+this.name+" your balance: "+c.getBalance()+"change is :"+ ( (quantity*price) - c.getBalance()) );
            return -3;
        }

         int sell = sellTickets(quantity);
        if( sell <= 0){
            //without applied the solution I had checked tickets before adding it in cart so i don't need to check that again
            //so when i'm trying to buy tickets for customer logically without insure there is one only customer can have those tickets, moreover ,without re
            //thanking in concurrency way (regardless The time there are many customers trying to buy total quantity of THE SAME ticket) actaly we will pass all of these requstes
            // to buy total of quantity of the same ticket (DOES NOT MAKE ANY SENSE TO DO THAT BUT AS YOU WENT prf. (0_-))
           // return sell;
        }


        System.out.println("The date of purchase: "+now());

        System.out.println("your total balance is: "+c.getBalance());

        c.setBalance(c.getBalance()-(this.price*quantity));

        System.out.println("the price for single ticket ["+this.name +"] is: "+this.price);
        System.out.println("so total price for all tickets\n"+"you need: "+quantity +"for event "+this.name);
        System.out.println("the amount to pay for ticket(s)["+this.name+"]: "+this.price * quantity+"\n"+" your balance after amount and tax: "+c.getBalance()+"\n"+"you will receive tickets on your email ASAP when we receive the a mount keep check your email\n"+"event name: "+this.name+"\n we will see you at from date: "+startTime+"to: "+endTime+"\n Don't be late(0_-)\n");

        c.increasetNoTicketPaidbyCustomers(quantity);
        System.out.println("the number of tickets you have: "+c.getNoTicketPaidbyCustomers());
        String nameOfStore = "";
        if(this.owner != null) {
            nameOfStore = this.owner.getNameOfStore();
            this.owner.soldTicketNotify(Ticket.this, quantity);
        } else{
            nameOfStore = ReadValueLine(this.pathOwner, "nameOfStore", 2);
        }
        this.generateQrcode(c.getPathForAllQrsFloder()+"\\"+(c.getIndexQr()+1)+this.name+"[" +nameOfStore +"]"+".png",  "owner: "+nameOfStore+"\nname of event: "+this.name+"idEvent: "+this.id+"\nEmail: "+c.getEmail()+"\n customer's id: "+String.valueOf(c.getId())+"\nStart Time:"+this.startTime+" End Time:"+this.endTime+"\n number of person: "+ quantity+"The date of purchase : "+now());
        c.recieveQr(this.Qrcode);
        System.out.println("quantity ");
        System.out.println(quantity);
        System.out.println("-------------------recipt-------------------------");
        return 1;

    }



}
