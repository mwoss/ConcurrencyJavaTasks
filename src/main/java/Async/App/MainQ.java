package Async.App;

import Async.Logic.Models.ConsumerQ;
import Async.Logic.Models.ProducentQ;
import Async.Logic.Utils.PCMonitorAsyncQueue;

import java.util.ArrayList;
import java.util.List;

public class MainQ {
    public static void main(String[] args) {
        int capacity = 100;
        int threadAmount = 10;
        PCMonitorAsyncQueue pcMonitorAsyncQueue = new PCMonitorAsyncQueue(capacity);
        List<Thread> prodList = new ArrayList<>();
        List<Thread> consList = new ArrayList<>();

        for (int i = 0; i < threadAmount; i++) {
            consList.add(new ConsumerQ(capacity, pcMonitorAsyncQueue));
            prodList.add(new ProducentQ(capacity, pcMonitorAsyncQueue));
        }

        for (int i = 0; i < threadAmount; i++) {
            consList.get(i).start();
            prodList.get(i).start();
        }

        for (int i = 0; i < threadAmount; i++) {
            try {
                consList.get(i).join();
                prodList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
