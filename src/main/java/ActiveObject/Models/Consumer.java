package ActiveObject.Models;

import ActiveObject.Future.Future;
import ActiveObject.Proxy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Consumer extends Thread {

    private Proxy proxy;
    private Random random;
    private int bufferLimit;
    private List<Future> taskList = new LinkedList<>();


    public Consumer(Proxy proxy, int bufferLimit){
        this.proxy = proxy;
        this.random = new Random();
        this.bufferLimit = bufferLimit;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run() {
        while(true){
            taskList.add(proxy.consume(random.nextInt(bufferLimit / 2) + 1));

            try{
                Thread.sleep(500); //give him some time to finish job
                for(Iterator<Future> iterator = taskList.iterator(); iterator.hasNext();){
                    Future task = iterator.next();
                    if(task.isFinished()){
                        task.getResult();
                        iterator.remove();
                    }
                }
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
