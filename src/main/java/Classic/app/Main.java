package Classic.app;

import Classic.logic.Models.Consumer;
import Classic.logic.Models.Producer;
import Classic.logic.Models.ProducerMax;
import Classic.logic.Models.ProducerOnlyMin;
import Classic.logic.Utils.PCMonitor;
import Classic.logic.Utils.PCMonitorDeadlock;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        final int capacity = 1000;
//        final int threadsAmount = 50;
//        List<Thread> prodList = new ArrayList<>();
//        List<Thread> consList = new ArrayList<>();
//
//        PCMonitor pcMonitor = new PCMonitor(capacity);
//        for(int i=0; i<threadsAmount; i++){
//            prodList.add(new Producer(pcMonitor, capacity));
//            consList.add(new Consumer(pcMonitor, capacity));
//            prodList.get(i).start();
//            consList.get(i).start();
//        }
//        Thread.sleep(10000);
//
//        for(int i=0; i<threadsAmount; i++){
//            prodList.get(i).interrupt();
//            consList.get(i).interrupt();
//        }
//        for(int i=0; i<threadsAmount; i++){
//            prodList.get(i).join();
//            consList.get(i).join();
//        }
        final int capacity = 50;
        final int threadsAmount = 20;
        List<Thread> prodList = new ArrayList<>();
        List<Thread> consList = new ArrayList<>();

        PCMonitorDeadlock pcMonitor = new PCMonitorDeadlock(capacity);
        prodList.add(new ProducerMax(pcMonitor, capacity));
        prodList.get(0).start();
        System.out.println(prodList.get(0).getName() + "Fat thread name");
        for (int i = 1; i < threadsAmount - 1; i++) {
            prodList.add(new ProducerOnlyMin(pcMonitor, capacity));
            prodList.get(i).start();
        }
        for (int i = 0; i < 35; i++) {
            consList.add(new Consumer(pcMonitor, capacity));
            consList.get(i).start();
        }
        for (int i = 0; i < threadsAmount; i++) {
            prodList.get(i).join();
        }
        for (int i = 0; i < 35; i++) {
            consList.get(i).join();
        }
    }
}
