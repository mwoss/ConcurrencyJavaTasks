package ActiveObject.Queues;

import ActiveObject.MethodRequest.PutMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PutActivationQueueDEPR implements IActivationQueue<PutMethodRequest> {

    private Queue<PutMethodRequest> putMethodRequestQueue = new ConcurrentLinkedQueue<>();


    @Override
    public PutMethodRequest dequeue() {
        return putMethodRequestQueue.poll();
    }

    @Override
    public void enqueue(PutMethodRequest methodRequest) {
        putMethodRequestQueue.offer(methodRequest);
    }
}
