package com.example.boundbuffer.Models;


public class Customer extends BoundBuffer{
    private int id;
    private String name;
    private String Email;
    private int noTicketPaidbyCustomers=0;
    private double balance;
    private Cart cart;
    private String Qrs[];
    private String pathForAllQrsFloderCT;

    public Customer(String name,String email,double balance){
        super();
        this.id = BoundBuffer.NoCustomers;
        BoundBuffer.NoCustomers++;
        this.name =name;
        this.Email = email;
        this.balance = balance;
        cart = new Cart();
        Qrs = new String[20];
        pathForAllQrsFloderCT =super.NewFloder( "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\QrCodes\\Qrcodes\\Cust", String.valueOf(this.id));
        BoundBuffer.customers.add(Customer.this);
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

    public void setNoTicketPaidbyCustomers(int noTicketPaidbyCustomers) {
        this.noTicketPaidbyCustomers = noTicketPaidbyCustomers;
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
