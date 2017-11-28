package Async.Logic.Utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCMonitorAsync {
    private Lock lock = new ReentrantLock();
    private Condition blocking = lock.newCondition();

    private final int capacity;
    private int[] buffer;
    private int toInsert = 0;
    private int toTake = 0;

    public PCMonitorAsync(int capacity, int[] buffer){
        this.capacity = capacity;
        this.buffer = buffer;
    }

    public int putOnBeg() {
        int element;
        lock.lock();
        try {
            if (buffer[toInsert] != 0) {
                element = capacity;
            } else {
                element = toInsert;
                buffer[toInsert] = 2;
                toInsert = (toInsert + 1) % capacity;
            }
        } finally {
            lock.unlock();
        }
        return element;
    }

    public void putOnEnd(int element) {
        lock.lock();
        try {
            buffer[element] = 2;
            if (element == toTake) {
                blocking.signal();
            }
        } finally {
            System.out.println("Produced " + element);
            lock.unlock();
        }
    }

    public int takeFromBeg() {
        int element;
        lock.lock();
        try {
            if (buffer[toTake] != 2) {
                try {
                    blocking.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            element = toTake;
            toTake = (toTake + 1) % capacity;

        } finally {
            lock.unlock();
        }
        return element;
    }

    public void takeFromEnd(int element) {
        lock.lock();
        try {
            buffer[element] = 0;
            toInsert = element;
        } finally {
            System.out.println("Consumed " + toTake);
            lock.unlock();
        }
    }
}
