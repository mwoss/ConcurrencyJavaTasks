package Classic.logic.Models;

import Classic.logic.Utils.PCMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer extends Thread {

    private final PCMonitor pcMonitor;
    private final int productsToProduceLimit;
    private final Random random;

    public Producer(PCMonitor monitor, int buforLimit) {
        this.pcMonitor = monitor;
        this.productsToProduceLimit = buforLimit;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int productsToProcude = random.nextInt(productsToProduceLimit) + 1;
                List<Object> products = new ArrayList<>();
                for(int i = 0; i<productsToProcude; i++){
                    products.add(new Object());
                }
                pcMonitor.produce(products);
            }
            catch (InterruptedException e){
                System.out.println("Producer thread stopped");
                return;
            }
        }
    }
}
