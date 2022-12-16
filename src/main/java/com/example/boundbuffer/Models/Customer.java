package com.example.boundbuffer.Models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Customer extends BoundBuffer implements Runnable{
    private int id;
    private String name;
    private String Email;
    private String password;
    private int noTicketPaidbyCustomers=0;
    private double balance;
    private Cart cart;
    private String []Qrs;
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
            setId(this.id,rootDataBase+"\\preload\\idCT.txt");
            String[] nameofattribues = {"id", "name", "email", "password","noTicketPaidbyCustomers","balance","pathForallQrsFloderCT"}; //
            pathForAllQrsFloderCT =NewFloder( rootDataBase+"\\QrCodesForCT\\", String.valueOf(this.id)+" ["+email+"]");
            String[] values = {String.valueOf(this.id), name, email, password,String.valueOf(noTicketPaidbyCustomers), String.valueOf(balance),pathForAllQrsFloderCT};
            this.pathFileForInfo = Creetefiletxt(this.id+" ["+email+"] ",rootDataBase+"\\Customers\\");
            WriteData(this.pathFileForInfo,nameofattribues,values,7);

            if(check == 1) {
                editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",email ,"deleted", "0");
                editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",email ,"pathInfo", this.pathFileForInfo);
                editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",email ,"password", this.password);
            }else {

                addLoginData(rootDataBase+"\\SearchData\\CustomersLoginData.txt",Email,password,pathFileForInfo);

            }
        }

    }
    private int indexCart= 0;
    public int addToCart(Ticket t,int quentatiy){
       if(t == null || quentatiy == 0)
           return 0;
      this.cart.addTicketToCart(t,quentatiy);
      indexCart++;
      return 1;
    }
   /*
       types of invalid attributes in tickets !!!!!ziko & ziko = 0!!!!!

       -1 -> availability
       -2 -> Dead ticket
       -3 -> balance for specific ticket is less that customer have NOT for all tickets in cart just for this ticket because all we will check total attirube in cart, hance we save on this attribute total

    * ziko pay your fucking attention here
    *       this function (checkTicketsINCart)
    *       return 2d array of int such as ->
    *       index  of ticket that's have issue | type of invalid data
    *

          emp:
          array that's returned :
          indexOfArray :

                         0    1

                0         1  -1   -> means in second ticket in cart there is avalibality issue(this ticket are not be longer )
                1         1  -3   -> means in second ticket in cart there is balance issue( customer doesn't have enough balance to buy this single single single ticket NOT ALL i know hance all but if customer remove this ticket MAY can make checkout and balance will be good to do that
                2         3  -3   -> same as (1 -3) but in third ticket in cart
                3         3  -2   -> means in third ticket in cart there is dead ticket (dead time have been before )
                4         6  -1   ->same as (1 -1) but in 7th ticket in cart

    *
    *
    *
    * */

    public int[][] checkTicketsInCart() {
          int[][] incorrectedTicketsInCart = new int  [  cart.getNoTicketsInCart()  ] [2];
          int index = 0;
        for (int i = 0; i < this.cart.getNoTicketsInCart(); i++) {


            if ( this.cart.getTicketsCart()[i].getQuantity() == 0 || this.cart.getTicketsCart()[i].getAvailable() == false || this.cart.getQuentatiyForEachTickets() [i] > this.cart.getTicketsCart()[i].getQuantity()) {
                System.out.println("those tickets are not available(quantity) :" +this.cart.getTicketsCart()[i].getName()+"possion in :"+ i);

                incorrectedTicketsInCart[index][index] = i;
                incorrectedTicketsInCart[index][1] = -1;
                index++;
            }


            if(this.cart.getTicketsCart()[i].getDeadticket() == 1) {
                System.out.println("Dead ticket");
                incorrectedTicketsInCart[index][index] =i;
                incorrectedTicketsInCart[index][1] = -2;
                index++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            try {
                if (sdf.parse(now()).after(sdf.parse(this.cart.getTicketsCart()[i].getEndTime()))) {
                    System.out.println("tickets for this event have dead :"+this.cart.getTicketsCart()[i].getName());
                    this.cart.getTicketsCart()[i].setDeadticket(1);
                    incorrectedTicketsInCart[ index][index] =i;
                    incorrectedTicketsInCart[index][1] = -2;
                    index++;

                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (this.balance < (this.cart.getTicketsCart()[i].getPrice() * this.cart.getQuentatiyForEachTickets()[i])) {
                System.out.println("your balance is not enough to buy for these tickets: " + this.name + " your balance: " + this.getBalance());
                incorrectedTicketsInCart[ index][index] =i;
                incorrectedTicketsInCart[index][1] = -3;
                index++;
            }

        }
        if (index > 0)
            return incorrectedTicketsInCart;
        else
            return null;
    }
    public int checkOut(){
        Ticket[] T= cart.getTicketsCart();
        int[] Q =cart.getQuentatiyForEachTickets();

        if(this.cart.getTotalNumberInCart() <= 0 || T==null || Q == null){
            System.out.println("the Cart is Empty, There is no tickets in it");
            return -4;
        }
        if(T.length > 10 || Q.length > 10 ){
            System.out.println("maximum number of Ticket you can buy it is 10 Tickets , reduce what you went to checkout");
            return -3;
        }

        int [][] checkedT = checkTicketsInCart();

         while (checkedT != null)
             checkedT = checkTicketsInCart();



        for (int i=0;i<cart.getNoTicketsInCart();i++) {
            int y =T[i].buy(Customer.this, Q[i]);
            if(y != 1) return y;
        }
         int issetnew = this.cart.setNewCart();
          return issetnew;
    }

    @Override
    public void run() {
        checkOut();

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
        editValueLine(this.pathFileForInfo,"name",name,1);
    }

    public int getNoTicketPaidbyCustomers() {
        return noTicketPaidbyCustomers;
    }

    public void increasetNoTicketPaidbyCustomers(int noTicketPaidbyCustomers) {
        this.noTicketPaidbyCustomers += noTicketPaidbyCustomers;
        setNoTicketPaidbyCustomers(this.noTicketPaidbyCustomers,0);

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
        editValueLine(this.pathFileForInfo,"balance",String.valueOf(balance),5);
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

    public void setNoTicketPaidbyCustomers(int noTicketPaidbyCustomers, int load) {
        this.noTicketPaidbyCustomers = noTicketPaidbyCustomers;

        if(load == 0)
        editValueLine(this.pathFileForInfo,"noTicketPaidbyCustomers",String.valueOf(noTicketPaidbyCustomers),4);

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
    public int setEmail(String email){
      String[] temail= dataWrittenInFilesName(rootDataBase+"\\Customers",1);


       if(stringContainInArrayString(temail,email) < 0){
           editValueLine(this.pathFileForInfo,"email",email,2);
           String newPath = renameFile(this.pathFileForInfo,this.id+" ["+email+"] .txt");
           this.pathFileForInfo = newPath;
           editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",this.Email,"account",email);
           editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",email,"pathInfo",newPath);
           newPath = renameDirectory(this.pathForAllQrsFloderCT,this.id + " ["+email+"]");
           editValueLine(this.pathFileForInfo,"pathForallQrsFloderCT",newPath,6);
           this.Email = email;
           return 1;
       }else {

           System.out.println("Emails must be unique");
           System.out.println("this email["+email+"] is not unique");

           return 0;
       }


    }
    public int setPassword(String oldPassword,String newPassword) {
        if(oldPassword != this.password) {
            System.out.println("Wrong password");
            System.out.println("you have to enter your correct old password");
            return 0;
        }

        this.password = newPassword;

        editValueLine(this.pathFileForInfo,"password",newPassword,3);

        return 1;
    }


    public int logout(){
       customers.remove(Customer.this);
       if(customers.indexOf(Customer.this) > 0 ) return 0;

       return logout(this,this.Email,this.password);

    }
}
