package Async.App;

import Async.Logic.Models.Consumer;
import Async.Logic.Models.Producer;
import Async.Logic.Utils.PCMonitorAsync;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int capacity = 50;
        final int producerAmount = 10;
        List<Thread> prodList = new ArrayList<>();
        int[] buffer = new int[capacity]; //buffer is initialized with zeros

        PCMonitorAsync pcMonitor = new PCMonitorAsync(capacity, buffer);
        Consumer consumer = new Consumer(pcMonitor, buffer);
        consumer.start();
        for(int i=0; i<producerAmount; i++){
            prodList.add(new Producer(pcMonitor, buffer, capacity));
        }
        for(int i=0; i<producerAmount; i++){
            prodList.get(i).start();
        }

        try{
            consumer.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        for(int i=0; i< producerAmount; i++){
            try {
                prodList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
