package com.example.boundbuffer.Models;


import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Customer extends BoundBuffer{
    private int id;
    private String name;
    private String Email;
    private String password;
    private int noTicketPaidbyCustomers=0;
    private double balance;
    private Cart cart;
    private String Qrs[];
    private String pathForAllQrsFloderCT;
    private String pathFileForInfo;

    public Customer(String name,String email,String password,double balance,int newData){
        super();
        this.name =name;
        this.Email = email;
        this.password = password;
        this.balance = balance;
        cart = new Cart();
        Qrs = new String[20];

        int check = 0;
        if(checkAccount(email,"deleted").equals("1"))
            check = 1;

        if(newData !=0){
            this.id = BoundBuffer.NoCustomers;
            BoundBuffer.NoCustomers++;
            setId(this.id,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idCT.txt");
            String[] nameofattribues = {"id", "name", "email", "password","noTicketPaidbyCustomers","balance","pathForallQrsFloderCT"}; //
            pathForAllQrsFloderCT =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\QrCodesForCT\\", String.valueOf(this.id)+" ["+email+"]");
            String[] values = {String.valueOf(this.id), name, email, password,String.valueOf(noTicketPaidbyCustomers), String.valueOf(balance),pathForAllQrsFloderCT};
            this.pathFileForInfo = Creetefiletxt(this.id+" ["+email+"] ","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\Customers\\");
            WriteData(this.pathFileForInfo,nameofattribues,values,7);

            if(check == 1) {
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\CustomersLoginData.txt","account",email ,"deleted", "0");
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\CustomersLoginData.txt","account",email ,"pathInfo", this.pathFileForInfo);
                editValueInSearchFile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\CustomersLoginData.txt","account",email ,"password", this.password);
            }else {

                addLoginData("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\SearchData\\CustomersLoginData.txt",Email,password,pathFileForInfo);

            }
        }

    }
    private int indexCart= 0;
    public int addToCart(Ticket t,int quentatiy){


      this.cart.addTicketToCart(t,quentatiy);
      indexCart++;
      return 1;
    }


  // -1 -> availability -2-> dead ticket -3-> amount is less that need it
    //this.cart.getQuentatiyForEachTickets() [i]
    // this.cart.getTicketsCart()[i].
    private Pair<Integer, Integer> checkTicketsInCart() {

        for (int i = 0; i < this.cart.getNoTicketsInCart(); i++) {
            if ( this.cart.getTicketsCart()[i].getQuantity() == 0 || this.cart.getTicketsCart()[i].getAvailable() == false || this.cart.getQuentatiyForEachTickets() [i] > this.cart.getTicketsCart()[i].getQuantity()) {
                System.out.println("those tickets are not available(quantity) :" +this.cart.getTicketsCart()[i].getName()+"possion in :"+ i);
                return new Pair<Integer, Integer>(i, -1);
            }


            if(this.cart.getTicketsCart()[i].getDeadticket() == 1)
                return new Pair<Integer, Integer>(i, -2);


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(this.cart.getTicketsCart()[i].getEndTime());
            System.out.println(now());

            try {
                if (sdf.parse(now()).after(sdf.parse(this.cart.getTicketsCart()[i].getEndTime()))) {
                    System.out.println("tickets for this event have dead :"+this.cart.getTicketsCart()[i].getName());
                    this.cart.getTicketsCart()[i].setDeadticket(1);
                    return new Pair<Integer, Integer>(i, -2);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (this.balance < (this.cart.getTicketsCart()[i].getPrice() * this.cart.getQuentatiyForEachTickets()[i])) {
                System.out.println("your balance is not enough to buy for these tickets: " + this.name + " your balance: " + this.getBalance());
                return new Pair<Integer, Integer>(i, -3);
            }

        }
        return new Pair<Integer, Integer>(1, 1);
    }
    public int checkOut(){
        Ticket[] T= cart.getTicketsCart();
        int[] Q =cart.getQuentatiyForEachTickets();
        if(this.cart.getTotalNumberInCart() <= 0 || T==null || Q == null){
            System.out.println("the Cart is Empty, There is no tickets in it");
            return -4;

        }
        if(T.length > 10 || Q.length > 10 ){
            System.out.println("maximum number of Ticket you can buy it is 10 Tickets");
            return -3;
        }

        Pair<Integer, Integer> p = checkTicketsInCart();
        int c1 =p.getKey();
        int c2 = p.getValue();
        if(c1 != 1 && c2 != 1){
            return c1;
        }

        for (int i=0;i<cart.getNoTicketsInCart();i++) {
            int y =T[i].buy(Customer.this, Q[i]);
            if(y != 1) return y;
        }
         int issetnew = this.cart.setNewCart();
          return issetnew;
    }
    public int removeFromCart(Ticket t){
        this.cart.removeTicket(t);
        return 1;
    }


    public String getPathForAllQrsFloder() {
        return pathForAllQrsFloderCT;
    }

    private int indexQr=-1;
    public void setPathForAllQrsFolder(String pathForAllQrsFloder) {
        System.out.println(pathForAllQrsFloder);
        this.pathForAllQrsFloderCT = pathForAllQrsFloder;
        String[] qtemp = Directorylist(this.pathForAllQrsFloderCT,1,".png");

        System.out.println(Arrays.toString(qtemp));

        Qrs = new String[20+ qtemp.length];

        for(int i=0;i<qtemp.length;i++){
            Qrs[i] = qtemp[i];
        }

        if(qtemp.length > 0)
            indexQr = qtemp.length;
        else
           indexQr =0;
        System.out.println(indexQr);
        System.out.println(Arrays.toString(Qrs));
        qtemp = null;
    }

    public String[] getAllQrs() {
        return Qrs;
    }

    public void recieveQr(String NewQr) {
        this.Qrs[indexQr] = NewQr;
        indexQr++;
    }

    public int removeAllTicketsInCart(){
        return cart.setNewCart();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoTicketPaidbyCustomers() {
        return noTicketPaidbyCustomers;
    }

    public void increasetNoTicketPaidbyCustomers(int noTicketPaidbyCustomers) {
        this.noTicketPaidbyCustomers += noTicketPaidbyCustomers;
    }


    public String getEmail() {
        return Email;
    }


    public String[] getQrs() {
        return Qrs;
    }

    public void setQrs(String[] qrs) {
        Qrs = qrs;
    }


    public int getIndexQr() {
        return indexQr;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoTicketPaidbyCustomers(int noTicketPaidbyCustomers) {
        this.noTicketPaidbyCustomers = noTicketPaidbyCustomers;
    }

    public String getPathForAllQrsFloderCT() {
        return pathForAllQrsFloderCT;
    }



    public String getPathFileForInfo() {
        return pathFileForInfo;
    }

    public void setPathFileForInfo(String pathFileForInfo) {
        this.pathFileForInfo = pathFileForInfo;
    }

    public int getIndexCart() {
        return indexCart;
    }

    public void setIndexCart(int indexCart) {
        this.indexCart = indexCart;
    }

    public void setIndexQr(int indexQr) {
        this.indexQr = indexQr;
    }

    public int setPassword(String oldPassword,String newPassword) {
        if(oldPassword != this.password) return 0;
        this.password = newPassword;
        // just uncomment and find out what is index for password by open file plz note that we started indexing with 0 in files
        // don't forget to delete this comment (-_0)
      // editValueLine(this.pathFileForInfo,password,newPassword,//index started with 0);

        return 1;
    }
}
