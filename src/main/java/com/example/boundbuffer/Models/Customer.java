package com.example.boundbuffer.Models;


public class Customer {
    public Customer(){

    }

    public synchronized void Remove(Object item, Supermarket supermarket ) throws InterruptedException{
        supermarket.empty.acquire();
        supermarket.mutex.acquire();

        // remove an item from the buffer

        supermarket.isEmpty();
        supermarket.stock[supermarket.out] = null;
        supermarket.count--;
        supermarket.out = (supermarket.out + 1) % supermarket.size;

        supermarket.mutex.release();
        supermarket.full.release();
        System.out.println("I'm Customer ");
    }

}
