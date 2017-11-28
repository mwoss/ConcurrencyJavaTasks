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

    public ArrayList<Integer> insertOnBeg(int amountToProduce) throws InterruptedException {
        lock.lock();
        ArrayList<Integer> elements = new ArrayList<>();
        try{
            while(empty.size() < amountToProduce)
                waitProducer.await();

            for(int iter = 0; iter < amountToProduce; iter++)
                elements.add(empty.poll());
        }
        finally {
            lock.unlock();
        }
        return elements;
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
    public ArrayList<Integer> takeFromBeg(int amountToConsume) throws InterruptedException {
        lock.lock();
        ArrayList<Integer> elements = new ArrayList<>();
        try{
            while(full.size() < amountToConsume) {
                waitConsumer.await();
            }

            for(int iter = 0; iter < amountToConsume; iter++)
                elements.add(full.poll());
        }
        finally {
            lock.unlock();
        }
        return elements;
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
