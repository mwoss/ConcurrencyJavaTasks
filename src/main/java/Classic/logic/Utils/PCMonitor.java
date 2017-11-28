package Classic.logic.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCMonitor {
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition firstProducent = lock.newCondition();
    private final Condition firstConsumer = lock.newCondition();
    private final Condition otherProducents = lock.newCondition();
    private final Condition otherConsumers = lock.newCondition();
    private boolean firstProdCont = false;
    private boolean firstConsCont = false;

    private List<Object> bufferList;

    public PCMonitor(int capacity) {
        this.capacity = 2 * capacity;
        this.bufferList = new ArrayList<>(2 * capacity);
    }

    public void produce(List<Object> products) throws InterruptedException {
        lock.lock();
        try {
            if (firstProdCont)
                otherProducents.await();

            firstProdCont = true;

            while (capacity - bufferList.size() < products.size())
                firstProducent.await();

            bufferList.addAll(products);
            //firstProdCont = false;
            System.out.println(Thread.currentThread().getName() + " produced " + products.size() + " products");
            Thread.sleep(100);


            otherProducents.signal();
            firstConsumer.signal();

        } finally {
            lock.unlock();
        }
    }

    public void consume(int toConsume) throws InterruptedException {
        lock.lock();
        try {
            if (firstConsCont)
                otherConsumers.await();

            firstConsCont = true;

            while (bufferList.size() < toConsume)
                firstConsumer.await();


            bufferList.subList(0, toConsume).clear();
            //firstConsCont = false;
            System.out.println(Thread.currentThread().getName() + " consumed " + toConsume + " products");
            Thread.sleep(100);

            otherConsumers.signal();
            firstProducent.signal();

        } finally {
            lock.unlock();
        }
    }

}
