package ActiveObject.Queues;

import ActiveObject.MethodRequest.IMethodRequest;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActivationQueue implements IActivationQueue<IMethodRequest> {

    private BlockingQueue<IMethodRequest> methodRequestQueue = new LinkedBlockingQueue<>();

    @Override
    public IMethodRequest dequeue() throws InterruptedException {
        return methodRequestQueue.poll();
    }

    @Override
    public void enqueue(IMethodRequest methodRequest) throws InterruptedException {
        methodRequestQueue.offer(methodRequest);
    }
}
