package AsyncPC.Logic.Utils.SecSolutionBetter;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PCMonitorAsyncQueue2 {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstCons = lock.newCondition();
    private final Condition otherCons = lock.newCondition();
    private final Condition firstProd = lock.newCondition();
    private final Condition otherProd = lock.newCondition();

    private boolean waitCons = false;
    private boolean waitProd = false;

    private Queue<Integer> empty;
    private Queue<Integer> full;

    private int prodExecuted;
    private int consExecuted;

    public PCMonitorAsyncQueue2(int capacity){
        this.empty = new ConcurrentLinkedQueue<>();
        this.full = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < capacity; i++)
            empty.add(i);

        prodExecuted = 0;
        consExecuted = 0;
    }


    public ArrayList<Integer> insertOnBeg(int amountToProduce) throws InterruptedException {
        lock.lock();
        ArrayList<Integer> elements = new ArrayList<>();
        try{
            if(waitProd)
                otherProd.await();

            waitProd = true;
            while(empty.size() < amountToProduce)
                firstProd.await();

            for (int iter = 0; iter < amountToProduce; iter++)
                elements.add(empty.poll());

            if(!lock.hasWaiters(otherProd))
                waitProd = false;
        }
        finally {
            lock.unlock();
        }
        return elements;
    }

    public void insertOnEnd(ArrayList<Integer> elements) {
        lock.lock();
        try{
            full.addAll(elements);
            firstCons.signal();
            otherProd.signal();
        }
        finally {
            prodExecuted++;
            lock.unlock();
        }
    }

    public ArrayList<Integer> takeFromBeg(int amountToConsume) throws InterruptedException {
        lock.lock();
        ArrayList<Integer> elements = new ArrayList<>();
        try{
            if(waitCons){
                otherCons.await();
            }

            waitCons = true;
            while (full.size() < amountToConsume)
                firstCons.await();

            for (int iter = 0; iter < amountToConsume; iter++)
                elements.add(full.poll());

            if(!lock.hasWaiters(otherCons)){
                waitCons = false;
            }
        }
        finally {
            lock.unlock();
        }
        return elements;
    }

    public void takeFromEnd(ArrayList<Integer> elements) {
        lock.lock();
        try{
            empty.addAll(elements);
            {
                firstProd.signal();
                otherCons.signal();
            }
        }
        finally {
            consExecuted++;
            lock.unlock();
        }
    }

    public int getProdExecuted() {
        return prodExecuted;
    }

    public int getConsExecuted() {
        return consExecuted;
    }
}
