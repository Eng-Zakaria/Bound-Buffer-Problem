package com.example.boundbuffer.Models;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public Vendor owner;
    private Boolean available;
    private int quantity=0;
    private String Qrcode;

    private String pathOwner;
    private String pathInFolderOwner;
    private String pathInViewCT;


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

    public void generateQrcode(String path,String data)
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
    }

    public Ticket(String PathFolderOwner,String pathOwner,String name, String type, int quantity,double price, String imagePath, String description,String startTime ,String EndTime) {
        super();
        String[] nameofattribues = {"PathFolderOwner","PathOwner","PathInViewCT","imagePath","id", "name", "type","quantity","Description","StartTime","EndTime","price"}; //
        this.id = BoundBuffer.TotalNoTickets;
        this.pathOwner = pathOwner;
        this.pathInFolderOwner = PathFolderOwner;
        this.name = name;
        this.type = type;
        if(quantity >0) this.quantity=quantity;
        if(price >= 0) this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.available = true;

         //Dates as input
        if(BoundBuffer.isValidFormat("dd-MM-yyyy",startTime,Locale.ENGLISH)) {
            this.startTime = startTime;

        }else{

            return;
        }
        if(BoundBuffer.isValidFormat("dd-MM-yyyy",EndTime, Locale.ENGLISH)){
            this.endTime = EndTime;
        }
        else{
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(sdf.parse(this.endTime).after(sdf.parse(this.startTime))){
                System.out.println("tickets for this event have dead");
                return;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.pathOwner = pathOwner;
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
            available =false;
            return true;
        }else {

            return false;

        }



    }
    public int buy(Customer c,int quantity){
        if(this.quantity == 0 || this.available == false || quantity > this.quantity){
            System.out.println("those tickets are not available "+this.name);
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(this.endTime);
        System.out.println(now());
        try {
            if(sdf.parse(now()).after(sdf.parse(this.endTime))){
                System.out.println("tickets for this event have dead");
                return -1;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(c.getBalance() < (this.price * quantity)){
            System.out.println("your balance is not enough to checkout for these tickets: "+this.name+" your balance: "+c.getBalance());
            return -2;
        }else {
            this.quantity -= quantity;
        }

        this.owner.soldTicket(Ticket.this,quantity);
        System.out.println("your charage: "+c.getBalance());
        c.setBalance(c.getBalance()-(this.price*quantity));
        c.increasetNoTicketPaidbyCustomers(quantity);
        this.generateQrcode(c.getPathForAllQrsFloder()+"\\"+c.getIndexQr()+this.name+"[" +this.owner.getNameOfStore() +"]"+".png",c.getEmail()+" id:"+String.valueOf(c.getId())+this.startTime+" End:"+this.endTime);
        c.recieveQr(this.Qrcode);
        System.out.println("the amount to pay: "+this.price+"\n"+" your balance after amount and tax: "+c.getBalance()+"\n"+"you will receive tickets on your email ASAP when we receive the a mount keep check your email "+"event name: "+this.name+"we will see you at date :"+endTime+" Don't be late");
        System.out.println("-------------------recipt-------------------------");
        return 1;

    }



}
