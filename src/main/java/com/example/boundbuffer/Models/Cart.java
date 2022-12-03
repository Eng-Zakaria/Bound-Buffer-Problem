package com.example.boundbuffer.Models;

public class Cart {
    private int noTicketsInCart =0;
    private Ticket ticketsCart[];
    /* customers can add 10 items at MAX in cart
            important to know if function of addItem return 1 means this function had successful to add this item
     */
    public Cart(){
        ticketsCart = new Ticket[10];
    }
    public void addTicket(Ticket I){
        if(noTicketsInCart == 9){
            ticketsCart[noTicketsInCart] = I;
            noTicketsInCart++;
        }
        else{
            System.out.println("Error : Max No tickets is reached");
        }
    }
    public int removeTicket(Ticket tick){
        if(noTicketsInCart ==0 || tick==null || ticketsCart == null)return 0;
          Ticket[] ticketTemp = new Ticket[ticketsCart.length];
          int index=0;
        for(int i = 0; i< noTicketsInCart; i++){
            if(ticketsCart[i].getId() != tick.getId()){
                ticketTemp[index] = ticketsCart[i];
                index++;
            }

        }
        ticketTemp[ticketsCart.length-1] = null;
        ticketsCart = ticketTemp;
        noTicketsInCart--;
        return 1;
    }



}
