package com.example.boundbuffer.Models;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BoundBuffer{
    public static int Niteminsystem=0;
    public static ArrayList<Item> allitems;
    int count = 0;
    int in = 0;
    int out = 0;
    Object stock[];

    int size = 10;
    Semaphore mutex ;
    Semaphore empty;
    Semaphore full ;


    public BoundBuffer(int size){
        allitems = new ArrayList<>();
        count = 0;
        in = 0;
        out = 0;
        size = getSize();
        mutex = new Semaphore(1);
        empty = new Semaphore(getSize());
        full =  new Semaphore(0);
        stock = new Object[size];

    }

    public BoundBuffer() {
        size = getSize();
    }


    public void setSize(int size) {
        this.size = size;
    }
    public int getSize(){
        return  this.size;
    }


    boolean isFull(){
        return (count==size)?  false : false ;
    }
    boolean isEmpty(){
         return (count==0)? false:false ;
    }
}
