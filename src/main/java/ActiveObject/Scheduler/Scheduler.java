package ActiveObject.Scheduler;

import ActiveObject.MethodRequest.IMethodRequest;
import ActiveObject.MethodRequest.PutMethodRequest;
import ActiveObject.MethodRequest.TakeMethodRequest;
import ActiveObject.Queues.ActivationQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler implements Runnable {

    private Queue<IMethodRequest> putQueue = new ConcurrentLinkedQueue<>();
    private Queue<IMethodRequest> takeQueue = new ConcurrentLinkedQueue<>();
    private ActivationQueue activationQueue = new ActivationQueue();
    private boolean run = true;
    private Thread scheduler;

    public void enqueue(IMethodRequest methodRequest) {
        try {
            this.activationQueue.enqueue(methodRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private IMethodRequest dequeue() {
        IMethodRequest methodRequest = null;
        try {
            methodRequest = this.activationQueue.dequeue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return methodRequest;
    }


    @Override
    public void run() {
        while (run) {
            IMethodRequest methodRequest = this.dequeue();

            if (methodRequest instanceof PutMethodRequest) {
                if (!putQueue.isEmpty()) {
                    putQueue.offer(methodRequest);
                    while (!putQueue.isEmpty() && putQueue.peek().guard())
                        putQueue.poll().execute();

                } else {
                    if (!methodRequest.guard())
                        putQueue.offer(methodRequest);
                    else
                        methodRequest.execute();
                }
            } else if (methodRequest instanceof TakeMethodRequest) {
                if (!takeQueue.isEmpty()) {
                    takeQueue.offer(methodRequest);
                    while (!takeQueue.isEmpty() && takeQueue.peek().guard())
                        takeQueue.poll().execute();
                } else {
                    if (!methodRequest.guard()) {
                        takeQueue.offer(methodRequest);
                    } else {
                        methodRequest.execute();
                    }
                }
            }
        }
    }

    public void stopScheduler() {
        run = false;
    }

    public void start() {
        if (scheduler == null) {
            scheduler = new Thread(this);
            scheduler.start();
        }
    }

}
