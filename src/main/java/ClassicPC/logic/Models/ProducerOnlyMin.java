package ClassicPC.logic.Models;

import ClassicPC.logic.Utils.PCMonitorDeadlock;

import java.util.ArrayList;
import java.util.List;

public class ProducerOnlyMin extends Thread{
    private final PCMonitorDeadlock pcMonitor;

    public ProducerOnlyMin(PCMonitorDeadlock monitor, int buforLimit) {
        this.pcMonitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
//                System.out.println(Thread.currentThread().getName() + "MIN producing");
                List<Object> products = new ArrayList<>();
                products.add(new Object());
                pcMonitor.produce(products, this);
            }
            catch (InterruptedException e){
//                System.out.println(Thread.currentThread().getName() + "MIN stopped");
                return;
            }
        }
    }
}
