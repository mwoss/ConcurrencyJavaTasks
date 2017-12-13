package ActiveObject;

import java.util.TimerTask;

public class TimerRunnable implements Runnable {

    private Proxy proxy;
    private int time;

    public TimerRunnable(final Proxy proxy){
        this.proxy = proxy;
        this.time = 0;
    }

    @Override
    public void run() {
        System.out.println(time + " " + proxy.getConsExecuted() + " " + proxy.getProdExecuted());
        time += 2;
    }
}
