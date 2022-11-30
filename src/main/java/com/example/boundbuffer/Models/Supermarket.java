package com.example.boundbuffer.Models;
import java.util.concurrent.Semaphore;

public class Supermarket extends BoundBuffer {

    public Supermarket(){
        int size = 10;

        BoundBuffer supermarket = new BoundBuffer(size);

        final Semaphore mutex = new Semaphore(1);
        final Semaphore empty = new Semaphore(size);
        final Semaphore full = new Semaphore(0);
    }


    public Supermarket(int supermarketSize){

        int size = supermarketSize;
        BoundBuffer supermarket = new BoundBuffer(size);

        final Semaphore mutex = new Semaphore(1);
        final Semaphore empty = new Semaphore(size);
        final Semaphore full = new Semaphore(0);

    }

}
