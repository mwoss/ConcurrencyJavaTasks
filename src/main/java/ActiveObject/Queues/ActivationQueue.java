package ActiveObject.Queues;

import ActiveObject.MethodRequest.IMethodRequest;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActivationQueue implements IActivationQueue<IMethodRequest> {

    private Queue<IMethodRequest> methodRequestQueue = new ConcurrentLinkedQueue<>();

    @Override
    public IMethodRequest dequeue() {
        return methodRequestQueue.poll();
    }

    @Override
    public void enqueue(IMethodRequest methodRequest) {
        methodRequestQueue.offer(methodRequest);
    }
}
