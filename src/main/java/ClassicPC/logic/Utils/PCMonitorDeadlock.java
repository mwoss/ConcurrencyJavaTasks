package ClassicPC.logic.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCMonitorDeadlock {
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition produceCondition = lock.newCondition();
    private final Condition consumeCondition = lock.newCondition();

    private List<Object> bufferList;

    public PCMonitorDeadlock(int capacity) {
        this.capacity =  capacity;
        this.bufferList = new ArrayList<>();
    }

    public void produce(List<Object> products, Thread thread) throws InterruptedException {
        lock.lock();
        try {
            long  wait = 0;
            while (capacity < products.size() + bufferList.size()){
                wait++;
                produceCondition.await();

            }

            bufferList.addAll(products);
            System.out.println(Thread.currentThread().getName()+ "waited iterations = " + wait);
            consumeCondition.signal();

        } finally {
            lock.unlock();
        }
    }

    public void consume(int toConsume) throws InterruptedException {
        lock.lock();
        try {
            while(bufferList.size() < toConsume)
                consumeCondition.await();

            bufferList.subList(0, toConsume).clear();
//            System.out.println(Thread.currentThread().getName() + " consumed " + toConsume + " products");
//            Thread.sleep(50);

            produceCondition.signal();

        } finally {
            lock.unlock();
        }
    }
}
