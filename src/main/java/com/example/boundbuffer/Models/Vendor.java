package com.example.boundbuffer.Models;
import com.example.boundbuffer.Models.*;
import java.util.concurrent.Semaphore;

public class Vendor {
    public Vendor(){

    }
    public synchronized void Insert(Object item, Supermarket supermarket ) throws InterruptedException {
        supermarket.empty.acquire();
        supermarket.mutex.acquire();
        // add an item to the buffer

        supermarket.isFull();

        supermarket.stock[supermarket.in] = item;
        supermarket.count ++;
        supermarket.in = (supermarket.in + 1) % supermarket.size;

        supermarket.mutex.release();
        supermarket.full.release();
        System.out.println("I'm Vendor ");
    }
}
