package ActiveObject;

import ActiveObject.Future.Future;
import ActiveObject.MethodRequest.PutMethodRequest;
import ActiveObject.MethodRequest.TakeMethodRequest;
import ActiveObject.Scheduler.Scheduler;



public class Proxy {
    private Scheduler scheduler;
    private Servant servant;

    public Proxy(int capacity){
        this.servant = new Servant(capacity);
        this.scheduler = new Scheduler();
        this.scheduler.start();
    }

    public Future consume(int toConsume){
        Future consumeFuture = new Future();
        TakeMethodRequest takeMethodRequest = new TakeMethodRequest(consumeFuture, servant, toConsume);
        scheduler.enqueue(takeMethodRequest);

        return consumeFuture;
    }

    public Future produce(int toProcude){
        Future produceFuture = new Future();
        PutMethodRequest putMethodRequest = new PutMethodRequest(produceFuture, servant, toProcude);
        scheduler.enqueue(putMethodRequest);

        return produceFuture;
    }


}
