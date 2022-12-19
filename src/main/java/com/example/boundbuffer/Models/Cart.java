package com.example.boundbuffer.Models;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cart {
    private int noTicketsInCart =0;
    private int quentatiyForEachTickets[];
    private Ticket ticketsCart[];

    private double total = 0.0;

    private int totalNumberInCart=0;
    /* customers can add 10 items at MAX in cart
            important to know if function of addItem return 1 means this function had successful to add this item
     */
    public Cart(){
        if(ticketsCart == null)
        ticketsCart = new Ticket[10];
        if(quentatiyForEachTickets == null)
            quentatiyForEachTickets = new int[10];

    }

    public Ticket[] getTicketsCart() {
        return ticketsCart;
    }

    public int getNoTicketsInCart() {
        return noTicketsInCart;
    }

    public void setNoTicketsInCart(int noTicketsInCart) {
        this.noTicketsInCart = noTicketsInCart;
    }

    public void setTicketsCart(Ticket[] ticketsCart) {
        this.ticketsCart = ticketsCart;
    }

    public int searchInCart(Ticket t){
        for (int i=0;i<noTicketsInCart;i++){
            if(t.getId() == ticketsCart[i].getId())
                return i;

        }
        return -1;

    }
    public int getQuentatiyForItemInCart(int indexInQuentatiyArray){

            return quentatiyForEachTickets[indexInQuentatiyArray];

    }

    public int addTicketToCart(Ticket I,int quentatiy){
        int SearchIndex = searchInCart(I);
        int nq=0;
        if(SearchIndex >= 0) {
            nq = getQuentatiyForItemInCart(SearchIndex);
        }



        System.out.println(nq+quentatiy);
        if(I.getQuantity() == 0 || I.getAvailable() == false || quentatiy+nq > I.getQuantity()){
            System.out.println("those tickets are not available for now: "+I.getName());
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if(sdf.parse(now()).after(sdf.parse(I.getEndTime()))){
                System.out.println("tickets for this event have been dead");
                return -1;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        if(noTicketsInCart < 10){
            if(SearchIndex >=0){
                quentatiyForEachTickets[SearchIndex] += quentatiy;
                totalNumberInCart += quentatiy;
                total += quentatiy * I.getPrice();
                return 1;
            }
            ticketsCart[noTicketsInCart] = I;
            quentatiyForEachTickets[noTicketsInCart] = quentatiy;
            totalNumberInCart += quentatiy;
            total += quentatiy * I.getPrice();
            noTicketsInCart++;
        }
        else{
            System.out.println("Error : Max No tickets is reached");
        }
        return 1;
    }

    public int[] getQuentatiyForEachTickets() {
        return quentatiyForEachTickets;
    }

    public int getTotalNumberInCart() {
        return totalNumberInCart;
    }

    public void setTotalNumberInCart(int totalNumberInCart) {
        this.totalNumberInCart = totalNumberInCart;
    }

    public void setQuentatiyForEachTickets(int[] quentatiyForEachTickets) {
        this.quentatiyForEachTickets = quentatiyForEachTickets;
    }
    public int setNewCart(){
        System.out.println("in set new");
        ticketsCart = null;
        quentatiyForEachTickets = null;
        totalNumberInCart = 0;
        noTicketsInCart = 0;
        total = 0.0;
        ticketsCart = new Ticket[10];
        quentatiyForEachTickets = new int[10];
        return 1;
    }

    public int changeQuentatiy(Ticket t, int NewQuentatiy){
        int indexTobeChanage = searchInCart(t);
        if (noTicketsInCart == 0 || t == null || ticketsCart == null || indexTobeChanage == -1 || totalNumberInCart == 0 ){
            System.out.println("invaild input");
            return -2;
        }

        if (NewQuentatiy < 0) return -1;


        int SearchIndex = indexTobeChanage;

        if (t.getQuantity() == 0 || t.getAvailable() == false || NewQuentatiy  > t.getQuantity() || NewQuentatiy < 0 ) {
            System.out.println("those tickets are became not available(you have to come early) Or invaild number entered: " + t.getName());
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (sdf.parse(now()).after(sdf.parse(t.getEndTime()))) {
                System.out.println("tickets for this event have been dead invaild tickets");
                return -1;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(NewQuentatiy ==0){
            removeTicket(t);
            return 1;
        }else if(NewQuentatiy > 0){
            int beforeChanageValue = quentatiyForEachTickets[indexTobeChanage];
            quentatiyForEachTickets[indexTobeChanage] = NewQuentatiy;
            totalNumberInCart -= beforeChanageValue;
            totalNumberInCart += NewQuentatiy;
            total -= (t.getPrice()* beforeChanageValue);
            total += (t.getPrice() * NewQuentatiy);
            return 1;
        }

    return 0;

    }

    public int removeTicket(Ticket tick){
        if(noTicketsInCart ==0 || tick==null || ticketsCart == null)return 0;

          Ticket[] ticketTemp = new Ticket[ticketsCart.length];
          int[] QTemp = new int[quentatiyForEachTickets.length];
          int index=0;

        for(int i = 0; i< noTicketsInCart; i++){
            if(ticketsCart[i].getId() != tick.getId()){
                ticketTemp[index] = ticketsCart[i];
                QTemp[index] = quentatiyForEachTickets[i];
                index++;
            }else {
                this.totalNumberInCart -= quentatiyForEachTickets[i];

            }

        }


        ticketsCart = ticketTemp;
        quentatiyForEachTickets = QTemp;
        total -= tick.getPrice();
        noTicketsInCart--;

        return 1;
    }
    public String now() {
        Calendar cal = Calendar.getInstance();

        Format f = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = f.format(new Date());
        return strDate;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}
