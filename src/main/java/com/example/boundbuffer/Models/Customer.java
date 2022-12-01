package com.example.boundbuffer.Models;


public class Customer {
    public Customer(){

    }

    public synchronized void Remove(Object item, Tazkarty tazkarty) throws InterruptedException{
        tazkarty.empty.acquire();
        tazkarty.mutex.acquire();

        // remove an item from the buffer

        tazkarty.isEmpty();
        tazkarty.tzaker[tazkarty.out] = null;
        tazkarty.count--;
        tazkarty.out = (tazkarty.out + 1) % tazkarty.size;

        tazkarty.mutex.release();
        tazkarty.full.release();
        System.out.println("I'm Customer ");
    }

}
