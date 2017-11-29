package ActiveObject;

import java.util.concurrent.Future;

public class Proxy {
    private Scheduler scheduler;
    private Servant servant;

    public Proxy(){
        this.servant = new Servant();
        this.scheduler = new Scheduler();
    }

    public Future method1(){
        return null;
    }

    public Future method2(){
        return null;
    }
}
