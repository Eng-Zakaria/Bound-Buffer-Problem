package com.example.boundbuffer;

public class MultiThreading {
    int size;
    private int noCustomers;
    private int noVendors;

    MultiThreading(int size , int noCustomers, int noVendors ){
        this.size= size;
        this.noCustomers = noCustomers;
        this.noVendors = noVendors;
    }

    MultiThreading(){
        this.size= 10;
        this.noCustomers = 6;
        this.noVendors = 4;
    }




}
