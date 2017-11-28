package Async.Logic.Models;

import Async.Logic.Utils.PCMonitorAsync;

public class Producer extends Thread{
    private final PCMonitorAsync asyncMonitor;
    private int[] buffer;
    private final int capacity;

    public Producer(PCMonitorAsync asyncMonitor, int[] buffer, int capacity) {
        this.buffer = buffer;
        this.capacity = capacity;
        this.asyncMonitor = asyncMonitor;
    }

    @Override
    public void run() {
        int data;
        while (!Thread.currentThread().isInterrupted()) {
            data = asyncMonitor.putOnBeg();
            if (data != capacity) {
                asyncMonitor.putOnEnd(data);
            } else {
                System.out.println("Lost portion\n");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Produced " + data + "\n");
        }
    }
}
