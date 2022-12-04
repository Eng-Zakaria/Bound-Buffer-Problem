package com.example.boundbuffer.Models;


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

    public Customer(String name,String email,String password,double balance){
        super();
        String[] nameofattribues = {"id", "name", "email", "password","noTicketPaidbyCustomers","balance","pathForallQrsFloderCT"}; //
        this.id = BoundBuffer.NoCustomers;
        BoundBuffer.NoCustomers++;
        setId(this.id,"D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idCT.txt");
        this.name =name;
        this.Email = email;
        this.password = password;
        this.balance = balance;
        cart = new Cart();
        Qrs = new String[20];
        pathForAllQrsFloderCT =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\QrCodesForCT", String.valueOf(this.id)+" ["+email+"]");
        String[] values = {String.valueOf(this.id), name, email, password,String.valueOf(noTicketPaidbyCustomers), String.valueOf(balance),pathForAllQrsFloderCT};
        BoundBuffer.customers.add(Customer.this);
        this.pathFileForInfo = Creetefiletxt(this.id+" ["+email+"] ","D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\Customers\\");
        WriteData(this.pathFileForInfo,nameofattribues,values,7);
    }
    private int indexCart=0;
    public int addToCart(Ticket t,int quentatiy){
      this.cart.addTicketToCart(t,quentatiy);
      indexCart++;
      return 1;
    }
    public int checkOut(){
        Ticket[] T= cart.getTicketsCart();
        int[] Q =cart.getQuentatiyForEachTickets();
        if(this.cart.getTotalNumberInCart()<=0){
            System.out.println("the Cart is Empty, There is no tickets in it");
            return -4;

        }
        if(T.length > 10 || Q.length > 10 || T == null ){
            System.out.println("maximum number of Ticket you can buy it is 10 Tickets");
            return -3;
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

    public void setPathForAllQrsFloder(String pathForAllQrsFloder) {
        this.pathForAllQrsFloderCT = pathForAllQrsFloder;
    }

    public String[] getAllQrs() {
        return Qrs;
    }
    private int indexQr=0;
    public void recieveQr(String NewQr) {
        this.Qrs[indexQr] = NewQr;
        indexQr++;
    }
    public int reduceQuentatiyInCart(Ticket t,int NewQuentatiy){
       return cart.reduceAndIncreaseQuentatiy(t,NewQuentatiy);

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

    public void setEmail(String email) {
        Email = email;
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
}
