package AsyncPC.App;

import AsyncPC.Logic.Utils.FinalSolution.PCMonitorAsyncQueue2;

public class TimerRunnableAsync implements Runnable {
    private PCMonitorAsyncQueue2 monitor;
    private int time;

    public TimerRunnableAsync(PCMonitorAsyncQueue2 monitor, int threadAmount) {
        this.monitor = monitor;
        this.time = 0;
    }

    @Override
    public void run() {
        System.out.println(time + " " + monitor.getConsExecuted() + " " + monitor.getProdExecuted());
        time += 2;
    }
}
