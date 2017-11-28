package Async.Logic.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCMonitorAsyncQueue {
    private final Lock lock = new ReentrantLock();
    private final Condition waitConsumer = lock.newCondition();
    private final Condition waitProducer = lock.newCondition();

    private Queue<Integer> empty;
    private Queue<Integer> full;

    private int capacity;
    public PCMonitorAsyncQueue(int capacity){
        this.capacity = 2 * capacity;
        this.empty = new ConcurrentLinkedQueue<>();
        this.full = new ConcurrentLinkedQueue<>();

        for(int i=0; i<this.capacity; i++)
            empty.add(i);
    }

    public ArrayList<Integer> insertOnBeg(int amount){
        lock.lock();
        try{

        }
        finally {
            lock.unlock();
        }
        return null;
    }

    public void insertOnEnd(ArrayList<Integer> elements){
        lock.lock();
        try{
            full.addAll(elements);
            waitConsumer.signal();
        }
        finally {
            lock.unlock();
        }
    }
    public ArrayList<Integer> takeFromBeg(int amount) throws InterruptedException {
        lock.lock();
        try{
            while(empty.size() < amount)
                waitConsumer.await();

            //dodac do obiektu ktory zostanie zwrocony
            
        }
        finally {
            lock.unlock();
        }
        return null;
    }
    public void takeFromEnd(ArrayList<Integer> elements){
        lock.lock();
        try{
          empty.addAll(elements);
          waitProducer.signal();
        }
        finally {
           lock.unlock();
        }
    }
}
