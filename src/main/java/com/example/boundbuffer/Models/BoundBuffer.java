package com.example.boundbuffer.Models;
import java.util.concurrent.Semaphore;

public class BoundBuffer{

    int count = 0;
    int in = 0;
    int out = 0;
    Object tzaker[];

    int size = 10;
    Semaphore mutex ;
    Semaphore empty;
    Semaphore full ;


    public BoundBuffer(int size){
        count = 0;
        in = 0;
        out = 0;
        size = getSize();
        mutex = new Semaphore(1);
        empty = new Semaphore(getSize());
        full =  new Semaphore(0);
        tzaker = new Object[size];

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
