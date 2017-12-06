package ActiveObject.Queues;

import ActiveObject.MethodRequest.TakeMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TakeActivationQueueDEPR implements IActivationQueue<TakeMethodRequest>{

    private Queue<TakeMethodRequest> takeMethodRequestQueue = new ConcurrentLinkedQueue<>();

    @Override
    public TakeMethodRequest dequeue() {
        return takeMethodRequestQueue.poll();
    }

    @Override
    public void enqueue(TakeMethodRequest methodRequest) {
        takeMethodRequestQueue.offer(methodRequest);
    }
}
