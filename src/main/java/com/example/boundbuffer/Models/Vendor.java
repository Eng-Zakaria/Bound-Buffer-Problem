package com.example.boundbuffer.Models;

public class Vendor {
    public Vendor(){

    }
    public synchronized void Insert(Object item, Tazkarty tazkarty) throws InterruptedException {
        tazkarty.empty.acquire();
        tazkarty.mutex.acquire();
        // add an item to the buffer

        tazkarty.isFull();

        tazkarty.tzaker[tazkarty.in] = item;
        tazkarty.count ++;
        tazkarty.in = (tazkarty.in + 1) % tazkarty.size;

        tazkarty.mutex.release();
        tazkarty.full.release();
        System.out.println("I'm Vendor ");
    }
}
