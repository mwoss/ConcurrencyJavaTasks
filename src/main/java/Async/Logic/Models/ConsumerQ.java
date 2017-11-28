package Async.Logic.Models;

import Async.Logic.Utils.PCMonitorAsync;
import Async.Logic.Utils.PCMonitorAsyncQueue;

import java.util.ArrayList;
import java.util.Random;

public class ConsumerQ extends Thread{

    private final int buforLimit;
    private PCMonitorAsyncQueue pcMonitorAsyncQueue;
    private Random random;


    public ConsumerQ(int buforLimit, PCMonitorAsyncQueue pcMonitorAsync){
        this.buforLimit =  buforLimit;
        this.pcMonitorAsyncQueue = pcMonitorAsync;
        this.random = new Random();
    }

    @Override
    public void run() {
        int toConsume;
        ArrayList<Integer> elementsToConsume;
        while(!Thread.currentThread().isInterrupted()){
            toConsume = random.nextInt(this.buforLimit) +1;
            try {
                elementsToConsume = pcMonitorAsyncQueue.takeFromBeg(toConsume);
                this.printBuffor(elementsToConsume);
                pcMonitorAsyncQueue.takeFromEnd(elementsToConsume);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBuffor(ArrayList<Integer> elementsToConsume){
        for(int i =0; i < elementsToConsume.size() -1; i++){
            int num = i + 1;
            System.out.println("Consumer named: " + Thread.currentThread().getName() + " consumed from position: "
            + elementsToConsume.get(i) + "  Element number: " + num + " of " + elementsToConsume.size());
        }
    }
}
