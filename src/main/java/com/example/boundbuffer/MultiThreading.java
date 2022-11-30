package com.example.boundbuffer;

import com.example.boundbuffer.Models.Supermarket;

public class MultiThreading {
    private int size;
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

    void CreateSupermarket(int size){
        Supermarket supermarket = new Supermarket(size);
    }

    void CreateCustomers(int noCustomers){
        for(int i=0 ; i<noCustomers ; i++ ){
            Thread Customer = new Thread();
        }
    }

    void CreateVendors(int noVendors){
        for(int i=0; i<noVendors; i++){
            Thread Vendor = new Thread();
        }
    }
}
