package Async.Logic.Models;

import Async.Logic.Utils.PCMonitorAsyncQueue;

import java.util.ArrayList;
import java.util.Random;

public class ProducentQ extends Thread{
    private final int buforLimit;
    private PCMonitorAsyncQueue pcMonitorAsyncQueue;
    private Random random;


    public ProducentQ(int buforLimit, PCMonitorAsyncQueue pcMonitorAsync){
        this.buforLimit =  buforLimit;
        this.pcMonitorAsyncQueue = pcMonitorAsync;
        this.random = new Random();
    }

    @Override
    public void run() {
        int toProduce;
        ArrayList<Integer> elementsToProduce;
        while(!Thread.currentThread().isInterrupted()){
            toProduce = random.nextInt(this.buforLimit) +1;
            try {
                elementsToProduce = pcMonitorAsyncQueue.insertOnBeg(toProduce);
                this.printBuffor(elementsToProduce);
                pcMonitorAsyncQueue.insertOnEnd(elementsToProduce);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBuffor(ArrayList<Integer> elementsToProduce){
        for(int i =0; i < elementsToProduce.size() -1; i++){
            int num = i +1;
            System.out.println("Producer named: " + Thread.currentThread().getName() + "produce from position: "
                    + elementsToProduce.get(i) + "  Element: " + num + " of " + elementsToProduce.size());
        }
    }
}
