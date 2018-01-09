package AsyncPC.Logic.Models.FinalSolution;

import AsyncPC.Logic.Utils.FinalSolution.Buffer;
import AsyncPC.Logic.Utils.FinalSolution.PCMonitorAsyncQueue2;

import java.util.ArrayList;
import java.util.Random;

public class ConsumerQ2 extends Thread {

    private Random random;
    private final int buferLimit;
    private PCMonitorAsyncQueue2 pcMonitorAsyncQueue;
    private Buffer buffer;

    public ConsumerQ2(int buferLimit, Buffer buffer, PCMonitorAsyncQueue2 asyncMonitor){
        this.random = new Random();
        this.buferLimit = buferLimit;
        this.pcMonitorAsyncQueue = asyncMonitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int toConsume;
        ArrayList<Integer> elementsToConsume;
        while(!Thread.currentThread().isInterrupted()){
            toConsume = random.nextInt(this.buferLimit / 2) + 1;
            try{
                elementsToConsume = this.pcMonitorAsyncQueue.takeFromBeg(toConsume);
//                System.out.println("Consumer named: " + Thread.currentThread().getName() + " gonna consume: " + elementsToConsume.toString());
                for(Integer element : elementsToConsume)
                    this.buffer.takeElement(element);
                this.pcMonitorAsyncQueue.takeFromEnd(elementsToConsume);
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
