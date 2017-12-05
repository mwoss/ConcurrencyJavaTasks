package AsyncPC.Logic.Models.FirstSolution;

import AsyncPC.Logic.Utils.FirstSolution.PCMonitorAsync;

public class Consumer extends Thread {

    private final PCMonitorAsync asyncMonitor;
    private int[] buffer;


    public Consumer(PCMonitorAsync asyncMonitor, int[] buffer) {
        this.buffer = buffer;
        this.asyncMonitor = asyncMonitor;
    }

    @Override
    public void run() {
        int data;
        while(!Thread.currentThread().isInterrupted()){
            data = asyncMonitor.takeFromBeg();
            asyncMonitor.takeFromEnd(data);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
