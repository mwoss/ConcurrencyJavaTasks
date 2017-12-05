package AsyncPC.Logic.Models.SecSolutionBetter;

import AsyncPC.Logic.Utils.SecSolutionBetter.Buffer;
import AsyncPC.Logic.Utils.SecSolutionBetter.PCMonitorAsyncQueue2;

import java.util.ArrayList;
import java.util.Random;

public class ProducerQ2 extends Thread{
    private Random random;
    private final int buferLimit;
    private PCMonitorAsyncQueue2 pcMonitorAsyncQueue;
    private Buffer buffer;


    public ProducerQ2(int buferLimit,Buffer buffer, PCMonitorAsyncQueue2 asyncMonitor){
        this.random = new Random();
        this.buferLimit = buferLimit;
        this.pcMonitorAsyncQueue = asyncMonitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int toProduce;
        ArrayList<Integer> elementsToProduce;
        while(!Thread.currentThread().isInterrupted()){
            toProduce = random.nextInt(this.buferLimit / 3) + 1;
            try{
                elementsToProduce = this.pcMonitorAsyncQueue.insertOnBeg(toProduce);
                System.out.println("Producer named: " + Thread.currentThread().getName() + "gonna produce: " + elementsToProduce.toString());
                for(Integer element : elementsToProduce)
                    this.buffer.setElement(random.nextInt(this.buferLimit / 2) + 1, element);
                this.pcMonitorAsyncQueue.insertOnEnd(elementsToProduce);
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
