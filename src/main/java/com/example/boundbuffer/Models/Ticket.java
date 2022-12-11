package com.example.boundbuffer.Models;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getQrcode() {
        return Qrcode;
    }

    public int getIssold() {
        return issold;
    }

    public void setIssold(int issold) {
        this.issold = issold;
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

        //editValueLine(this.pathInFolderOwner,"quantity",,)


    }

    public int getDeadticket() {
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

        this.endTime = EndTime;
        this.startTime = startTime;

        if(newTicket != 1)
            return;


        this.available = true;

        this.issold = 0;

        this.id = BoundBuffer.TotalNoTickets;
        String[] nameofattribues = {"PathFolderOwner","PathOwner","PathInViewCT","imagePath","id", "name", "type","quantity","Description","StartTime","EndTime","price"}; //
        this.pathInFolderOwner = Creetefiletxt(this.id+" ["+this.name+"] "+"["+this.startTime+"] "+"to "+" ["+this.endTime+"]",pathInFolderOwner+"\\");
        this.pathInViewCT = Creetefiletxt(this.id+" ["+this.name+"] "+"["+this.startTime+"] "+"to "+" ["+this.endTime+"]","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\alltickets\\");
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
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setAvailable(Boolean available) {
        this.available = available;
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
            setAvailable(false);
            return true;
        }else {

            return false;

        }



    }

    public int buy(Customer c,int quantity){

        System.out.println("---------------------receipt for ticket: \\\\\" "+this.name+" \\\\\"---------------------------");
        if(this.quantity == 0 || this.available == false || quantity > this.quantity){
            System.out.println("those tickets are not available "+this.name);
            return 0;
        }

        if(this.getDeadticket() == 1)
            return -1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


        try {
            if(sdf.parse(now()).after(sdf.parse(this.endTime))){
                System.out.println("tickets for this event have dead");
                this.setDeadticket(1);
                return -1;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(c.getBalance() < (this.price * quantity)){
            System.out.println("your balance is not enough to checkout for these tickets: "+this.name+" your balance: "+c.getBalance()+"change is :"+ ( (quantity*price) - c.getBalance()) );
            return -2;
        }

        this.owner.soldTicket(Ticket.this,quantity);


        System.out.println("The date of purchase: "+now());

        System.out.println("your total balance is: "+c.getBalance());

        c.setBalance(c.getBalance()-(this.price*quantity));

        System.out.println("the price for single ticket ["+this.name +"] is: "+this.price);
        System.out.println("so total price for all tickets\n"+"you need: "+quantity +"for event "+this.name);
        System.out.println("the amount to pay for ticket(s)["+this.name+"]: "+this.price * quantity+"\n"+" your balance after amount and tax: "+c.getBalance()+"\n"+"you will receive tickets on your email ASAP when we receive the a mount keep check your email\n"+"event name: "+this.name+"\n we will see you at from date: "+startTime+"to: "+endTime+"\n Don't be late(0_-)\n");

        c.increasetNoTicketPaidbyCustomers(quantity);
        System.out.println("the number of tickets you have: "+c.getNoTicketPaidbyCustomers());

        this.generateQrcode(c.getPathForAllQrsFloder()+"\\"+c.getIndexQr()+this.name+"[" +this.owner.getNameOfStore() +"]"+".png",  "owner: "+this.owner.getNameOfStore()+"\nname of event: "+this.name+"idEvent: "+this.id+"\nEmail: "+c.getEmail()+"\n customer's id: "+String.valueOf(c.getId())+"\nStart Time:"+this.startTime+" End Time:"+this.endTime+"\n number of person: "+ quantity+"The date of purchase : "+now());

        c.recieveQr(this.Qrcode);
        System.out.println("quantity ");
        System.out.println(quantity);
        System.out.println("-------------------recipt-------------------------");
        return 1;

    }



}
