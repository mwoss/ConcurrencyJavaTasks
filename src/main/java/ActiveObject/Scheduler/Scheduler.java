package ActiveObject.Scheduler;

import ActiveObject.MethodRequest.IMethodRequest;
import ActiveObject.MethodRequest.PutMethodRequest;
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
        this.activationQueue.enqueue(methodRequest);
    }

    private IMethodRequest dequeue() {
        IMethodRequest methodRequest = null;
        methodRequest = this.activationQueue.dequeue();
        return methodRequest;
    }


    @Override
    public void run() {
        while (run) {
            IMethodRequest methodRequest = this.dequeue();

            if (methodRequest instanceof PutMethodRequest) {
                if (!putQueue.isEmpty()) {
                    putQueue.offer(methodRequest);
                    while (putQueue.peek().guard() && !putQueue.isEmpty())
                        putQueue.poll().execute();

                } else {
                    if (!methodRequest.guard())
                        putQueue.offer(methodRequest);
                    else
                        methodRequest.execute();
                }
            } else {
                if (!takeQueue.isEmpty()) {
                    takeQueue.offer(methodRequest);
                    while (takeQueue.peek().guard() && !takeQueue.isEmpty())
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
        if (this.scheduler == null) {
            this.scheduler = new Thread();
            this.scheduler.start();
        }
    }

}
