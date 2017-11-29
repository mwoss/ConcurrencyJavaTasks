package ActiveObject;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler implements Runnable{

    private ConcurrentLinkedQueue concurrentLinkedQueue;
    public Scheduler(){
        this.concurrentLinkedQueue = new ConcurrentLinkedQueue<IMethodRequest>();
    }

    public void dispatch(){

    }

    public void enqueue(IMethodRequest task){

    }

    @Override
    public void run() {

    }
}
